/*
Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries @ European Union, 2015-2016.

This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it 
and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of 
the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.

 */
package eu.europa.ec.fisheries.ers.service.bean;

import eu.europa.ec.fisheries.ers.fa.utils.FaReportSourceEnum;
import eu.europa.ec.fisheries.ers.message.exception.ActivityMessageException;
import eu.europa.ec.fisheries.ers.message.producer.ActivityMessageProducer;
import eu.europa.ec.fisheries.ers.service.EventService;
import eu.europa.ec.fisheries.ers.service.FishingTripService;
import eu.europa.ec.fisheries.ers.service.FluxMessageService;
import eu.europa.ec.fisheries.ers.service.search.FilterMap;
import eu.europa.ec.fisheries.uvms.activity.message.event.GetFLUXFAReportMessageEvent;
import eu.europa.ec.fisheries.uvms.activity.message.event.GetFishingTripListEvent;
import eu.europa.ec.fisheries.uvms.activity.message.event.carrier.EventMessage;
import eu.europa.ec.fisheries.uvms.activity.model.exception.ActivityModelMarshallException;
import eu.europa.ec.fisheries.uvms.activity.model.mapper.JAXBMarshaller;
import eu.europa.ec.fisheries.uvms.activity.model.schemas.*;
import eu.europa.ec.fisheries.uvms.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import un.unece.uncefact.data.standard.fluxfareportmessage._3.FLUXFAReportMessage;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.jms.JMSException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.*;


@LocalBean
@Stateless
public class ActivityEventServiceBean implements EventService {
    private  static final Logger LOG = LoggerFactory.getLogger(ActivityEventServiceBean.class);

     @EJB
     private FluxMessageService fluxMessageService;

    private @EJB
    FishingTripService fishingTripService;

    private @EJB
    ActivityMessageProducer producer;

    @Override
    public void getFLUXFAReportMessage(@Observes @GetFLUXFAReportMessageEvent EventMessage message) {
        LOG.info("inside Activity module GetFLUXFAReportMessage");
        try {
            SetFLUXFAReportMessageRequest baseRequest = JAXBMarshaller.unmarshallTextMessage(message.getJmsMessage(), SetFLUXFAReportMessageRequest.class);
            LOG.info("ActivityModuleRequest unmarshalled");
            if(baseRequest==null){
                LOG.error("Unmarshalled SetFLUXFAReportMessageRequest is null. Something went wrong");
                return;
            }

            FLUXFAReportMessage fluxFAReportMessage =extractFLUXFAReportMessage(baseRequest.getRequest());

            fluxMessageService.saveFishingActivityReportDocuments(fluxFAReportMessage.getFAReportDocuments(), extractPluginType(baseRequest.getPluginType()));

        } catch (ActivityModelMarshallException e) {
            LOG.error("Exception while trying to unmarshall SetFLUXFAReportMessageRequest in Activity",e);
        } catch (ServiceException e) {
            LOG.error("Exception while trying to saveFishingActivityReportDocuments in Activity",e);
        }
    }

    @Override
    public void getFishingTripList(@Observes @GetFishingTripListEvent EventMessage message) throws ServiceException {
        LOG.info("Got JMS inside Activity to get FishingTripIds:");
        try {
            LOG.debug("JMS Incoming text message: {}", message.getJmsMessage().getText());
            FishingTripRequest baseRequest = JAXBMarshaller.unmarshallTextMessage(message.getJmsMessage(), FishingTripRequest.class);
            LOG.debug("FishingTriId Request Unmarshalled");
            FishingTripResponse baseResponse =fishingTripService.getFishingTripIdsForFilter(extractFiltersAsMap(baseRequest),extractFiltersAsMapWithMultipleValues(baseRequest));

            String response =JAXBMarshaller.marshallJaxBObjectToString(baseResponse);
            LOG.debug("FishingTriId response marshalled");
            producer.sendMessageBackToRecipient(message.getJmsMessage(),response);
            LOG.debug("Response sent back.");
        } catch (ActivityModelMarshallException | ActivityMessageException | JMSException e) {
            LOG.error("Error while communication ", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Map<SearchFilter,String>  extractFiltersAsMap(FishingTripRequest baseRequest) throws ServiceException {
        Set<SearchFilter> filtersWithMultipleValues= FilterMap.getFiltersWhichSupportMultipleValues();
        Map<SearchFilter,String> searchMap = new HashMap<>();
        List<SingleValueTypeFilter> filterTypes= baseRequest.getSingleValueFilters();
        for(SingleValueTypeFilter filterType : filterTypes) {
            SearchFilter filter = filterType.getKey();
            if (filtersWithMultipleValues.contains(filter)) {
                throw new ServiceException("Filter provided with Single Value. Application Expects values as List for the Filter :" + filter);
            }
            searchMap.put(filterType.getKey(),filterType.getValue());
        }

        return searchMap;
    }

    private Map<SearchFilter,List<String>>  extractFiltersAsMapWithMultipleValues(FishingTripRequest baseRequest) throws ServiceException {
        Set<SearchFilter> filtersWithMultipleValues= FilterMap.getFiltersWhichSupportMultipleValues();
        Map<SearchFilter,List<String>> searchMap = new HashMap<>();
        List<ListValueTypeFilter> filterTypes= baseRequest.getListValueFilters();
        for(ListValueTypeFilter filterType : filterTypes){
            SearchFilter filter = filterType.getKey();
            if(!filtersWithMultipleValues.contains(filter)) {
                throw new ServiceException("Filter provided with multiple Values do not support Multiple Values. Filter name is:" + filter);
            }
            searchMap.put(filterType.getKey(),filterType.getValues());
        }
        return searchMap;
    }

    private FaReportSourceEnum extractPluginType(PluginType pluginType) {
        if(pluginType == null){
            return FaReportSourceEnum.FLUX;
        }
        return pluginType == PluginType.FLUX ? FaReportSourceEnum.FLUX : FaReportSourceEnum.MANUAL;
    }

    public FLUXFAReportMessage extractFLUXFAReportMessage(String request) throws ActivityModelMarshallException{
        JAXBContext jc = null;
        FLUXFAReportMessage fluxFAReportMessage = null;
        try {
            jc = JAXBContext.newInstance(FLUXFAReportMessage.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            StringReader sr = new StringReader(request);
            StreamSource source = new StreamSource(sr);
             fluxFAReportMessage = (FLUXFAReportMessage) unmarshaller.unmarshal(source);
        } catch (JAXBException | NullPointerException e) {
            throw new ActivityModelMarshallException("[Exception while trying to unmarshall FLUXFAReportMessage in Activity ]", e);
        }
       return fluxFAReportMessage;
    }
}
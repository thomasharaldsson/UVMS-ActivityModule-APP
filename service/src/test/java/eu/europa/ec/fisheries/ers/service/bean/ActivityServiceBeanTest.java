/*
 *
 * Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries © European Union, 2015-2016.
 *
 * This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package eu.europa.ec.fisheries.ers.service.bean;

import eu.europa.ec.fisheries.ers.fa.dao.FaReportDocumentDao;
import eu.europa.ec.fisheries.ers.fa.dao.FishingActivityDao;
import eu.europa.ec.fisheries.ers.fa.entities.FaReportDocumentEntity;
import eu.europa.ec.fisheries.ers.service.mapper.FaReportDocumentMapper;
import eu.europa.ec.fisheries.ers.service.util.MapperUtil;
import eu.europa.ec.fisheries.uvms.activity.model.dto.fareport.FaReportCorrectionDTO;
import eu.europa.ec.fisheries.uvms.activity.model.dto.fareport.details.FaReportDocumentDetailsDTO;
import eu.europa.ec.fisheries.uvms.exception.ServiceException;
import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._18.FAReportDocument;

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by padhyad on 8/9/2016.
 */
public class ActivityServiceBeanTest {

    @Mock
    EntityManager em;

    @Mock
    FishingActivityDao fishingActivityDao;

    @Mock
    FaReportDocumentDao faReportDocumentDao;

    @InjectMocks
    ActivityServiceBean activityService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    @SneakyThrows
    public void testGetFaReportCorrections() {

        //Mock
        FaReportDocumentEntity faReportDocumentEntity = MapperUtil.getFaReportDocumentEntity();
        faReportDocumentEntity.getFluxReportDocument().setFluxReportDocumentId(null);
        Mockito.doReturn(Arrays.asList(faReportDocumentEntity)).when(faReportDocumentDao).findFaReportsByReferenceId(Mockito.any(String.class));

        //Trigger
        List<FaReportCorrectionDTO> faReportCorrectionDTOList = activityService.getFaReportCorrections("TEST ID");

        //Verify
        Mockito.verify(faReportDocumentDao, Mockito.times(1)).findFaReportsByReferenceId("TEST ID");

        //Test
        FaReportCorrectionDTO faReportCorrectionDTO = faReportCorrectionDTOList.get(0);
        assertEquals(faReportDocumentEntity.getStatus(), faReportCorrectionDTO.getCorrectionType());
        assertEquals(faReportDocumentEntity.getFluxReportDocument().getCreationDatetime(), faReportCorrectionDTO.getCorrectionDate());
        assertEquals(faReportDocumentEntity.getFluxReportDocument().getFluxReportDocumentId(), faReportCorrectionDTO.getFaReportIdentifier());
        assertEquals(faReportDocumentEntity.getFluxReportDocument().getOwnerFluxPartyId(), faReportCorrectionDTO.getOwnerFluxParty());
    }

    @Test
    @SneakyThrows
    public void testGetFaReportDocumentDetails() throws Exception {

        //Mock
        List<FaReportDocumentEntity> faReportDocumentEntities = getMockedFishingActivityReportEntities();
        Mockito.doReturn(getMockedFishingActivityReportEntities()).when(faReportDocumentDao).findFaReportsByIds(Mockito.any(Collection.class));

        //Trigger
        FaReportDocumentDetailsDTO faReportDocumentDetailsDTO = activityService.getFaReportDocumentDetails("TEST");

        //Verify
        Mockito.verify(faReportDocumentDao, Mockito.times(1)).findFaReportsByIds(Mockito.any(Collection.class));

        //Test
        assertEquals(faReportDocumentEntities.get(0).getTypeCode(), faReportDocumentDetailsDTO.getTypeCode());
        assertEquals(faReportDocumentEntities.get(0).getFmcMarker(), faReportDocumentDetailsDTO.getFmcMarker());
        assertEquals(faReportDocumentEntities.get(0).getAcceptedDatetime(), faReportDocumentDetailsDTO.getAcceptedDateTime());
        assertEquals(faReportDocumentEntities.get(0).getFluxReportDocument().getCreationDatetime(), faReportDocumentDetailsDTO.getCreationDateTime());
        assertEquals(faReportDocumentEntities.get(0).getFluxReportDocument().getFluxReportDocumentId(), faReportDocumentDetailsDTO.getFluxReportDocumentId());
        assertEquals(faReportDocumentEntities.get(0).getFluxReportDocument().getPurposeCode(), faReportDocumentDetailsDTO.getPurposeCode());
        assertEquals(faReportDocumentEntities.get(0).getFluxReportDocument().getReferenceId(), faReportDocumentDetailsDTO.getReferenceId());
        assertEquals(faReportDocumentEntities.get(0).getFluxReportDocument().getOwnerFluxPartyId(), faReportDocumentDetailsDTO.getOwnerFluxPartyId());
        assertEquals(faReportDocumentEntities.get(0).getStatus(), faReportDocumentDetailsDTO.getStatus());

    }

    @Test(expected = ServiceException.class)
    @SneakyThrows
    public void testGetFaReportDocumentDetails_ExpectedException() throws Exception {

        //Mock
        List<FaReportDocumentEntity> faReportDocumentEntities = getMockedFishingActivityReportEntities();
        Mockito.doReturn(null).when(faReportDocumentDao).findFaReportsByIds(Mockito.any(Collection.class));

        //Trigger
        FaReportDocumentDetailsDTO faReportDocumentDetailsDTO = activityService.getFaReportDocumentDetails("TEST");

        //Verify
        Mockito.verify(faReportDocumentDao, Mockito.times(1)).findFaReportsByIds(Mockito.any(Collection.class));
    }

    private List<FaReportDocumentEntity> getMockedFishingActivityReportEntities() {
        List<FaReportDocumentEntity> faReportDocumentEntities = new ArrayList<>();
        FAReportDocument faReportDocument = MapperUtil.getFaReportDocument();
        faReportDocumentEntities.add(FaReportDocumentMapper.INSTANCE.mapToFAReportDocumentEntity(faReportDocument, new FaReportDocumentEntity()));
        return faReportDocumentEntities;
    }
}
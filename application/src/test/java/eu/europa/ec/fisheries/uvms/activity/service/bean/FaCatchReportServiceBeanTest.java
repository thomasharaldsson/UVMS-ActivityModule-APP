/*
Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries @ European Union, 2015-2016.

This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it
and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of
the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.

 */
package eu.europa.ec.fisheries.uvms.activity.service.bean;

import eu.europa.ec.fisheries.uvms.activity.fa.dao.FaCatchDao;
import eu.europa.ec.fisheries.uvms.activity.fa.dao.proxy.FaCatchSummaryCustomProxy;
import eu.europa.ec.fisheries.uvms.activity.model.schemas.GroupCriteria;
import eu.europa.ec.fisheries.uvms.activity.model.schemas.SearchFilter;
import eu.europa.ec.fisheries.uvms.activity.service.dto.fareport.summary.FACatchDetailsDTO;
import eu.europa.ec.fisheries.uvms.activity.service.dto.fareport.summary.FACatchSummaryDTO;
import eu.europa.ec.fisheries.uvms.activity.service.search.FishingActivityQuery;
import eu.europa.ec.fisheries.uvms.activity.service.util.MapperUtil;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FaCatchReportServiceBeanTest {

    @Mock
    private FaCatchDao faCatchDao;

    @InjectMocks
    private FaCatchReportServiceBean faCatchReportService;

    @Test
    @SneakyThrows
    public void testGetCatchesTableForCatchDetailsScreen() {
        FishingActivityQuery query = new FishingActivityQuery();
        List<GroupCriteria> groupByFields = new ArrayList<>();
        groupByFields.add(GroupCriteria.DATE_DAY);
        groupByFields.add(GroupCriteria.FAO_AREA);
        groupByFields.add(GroupCriteria.TERRITORY);
        groupByFields.add(GroupCriteria.EFFORT_ZONE);
        groupByFields.add(GroupCriteria.GFCM_GSA);
        groupByFields.add(GroupCriteria.GFCM_STAT_RECTANGLE);
        groupByFields.add(GroupCriteria.ICES_STAT_RECTANGLE);
        groupByFields.add(GroupCriteria.RFMO);
        groupByFields.add(GroupCriteria.SPECIES);
        query.setGroupByFields(groupByFields);
        Map<SearchFilter, String> searchCriteriaMap = new EnumMap<SearchFilter, String>(SearchFilter.class);
        searchCriteriaMap.put(SearchFilter.TRIP_ID,"NOR-TRP-20160517234053706");
        query.setSearchCriteriaMap(searchCriteriaMap);

        //Trigger
        FACatchDetailsDTO faCatchDetailsDTO= faCatchReportService.getCatchDetailsScreen("NOR-TRP-20160517234053706");

        Mockito.verify(faCatchDao, Mockito.times(2)).getGroupedFaCatchData(any(FishingActivityQuery.class), any(Boolean.class));

        //Verify
        assertNotNull( faCatchDetailsDTO);
    }


    @Test
    @SneakyThrows
    public void testGetCatchSummaryReport() {
        FishingActivityQuery query = new FishingActivityQuery();
        List<GroupCriteria> groupByFields = new ArrayList<>();
        groupByFields.add(GroupCriteria.DATE_DAY);
        groupByFields.add(GroupCriteria.FAO_AREA);
        groupByFields.add(GroupCriteria.TERRITORY);
        groupByFields.add(GroupCriteria.EFFORT_ZONE);
        groupByFields.add(GroupCriteria.GFCM_GSA);
        groupByFields.add(GroupCriteria.GFCM_STAT_RECTANGLE);
        groupByFields.add(GroupCriteria.ICES_STAT_RECTANGLE);
        groupByFields.add(GroupCriteria.RFMO);
        groupByFields.add(GroupCriteria.SPECIES);
        query.setGroupByFields(groupByFields);
        Map<SearchFilter, String> searchCriteriaMap = new EnumMap<SearchFilter, String>(SearchFilter.class);
        searchCriteriaMap.put(SearchFilter.TRIP_ID,"NOR-TRP-20160517234053706");
        query.setSearchCriteriaMap(searchCriteriaMap);
        Map<FaCatchSummaryCustomProxy, List<FaCatchSummaryCustomProxy>> groupedData = MapperUtil.getGroupedFaCatchSummaryCustomEntityData();
        when((faCatchDao).getGroupedFaCatchData(any(FishingActivityQuery.class),any(Boolean.class))).thenReturn(groupedData);

        //Trigger
        FACatchSummaryDTO fACatchSummaryDTO= faCatchReportService.getCatchSummaryReport(query,false);

        Mockito.verify(faCatchDao, Mockito.times(1)).getGroupedFaCatchData(any(FishingActivityQuery.class), any(Boolean.class));

        //Verify
        assertNotNull( fACatchSummaryDTO);
    }
}

/*
Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries  European Union, 2015-2016.

This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it 
and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of 
the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.

 */
package eu.europa.ec.fisheries.uvms.activity.service.search;

import java.util.List;
import java.util.Map;

import eu.europa.ec.fisheries.uvms.activity.model.schemas.GroupCriteria;
import eu.europa.ec.fisheries.uvms.activity.model.schemas.SearchFilter;
import eu.europa.ec.fisheries.uvms.commons.rest.dto.PaginationDto;

/**
 * This class is used to define Filters to be applied on Fishing Activities
 * It also contains list of grouping Criteria to be applied
 */
public class FishingActivityQuery {

    private PaginationDto pagination;
    private Map<SearchFilter, String> searchCriteriaMap;
    private Map<SearchFilter, List<String>> searchCriteriaMapMultipleValues;
    private SortKey sorting;
    private List<GroupCriteria> groupByFields;
    private Boolean showOnlyLatest;
    private Boolean useStatusInsteadOfPurposeCode;

    public FishingActivityQuery() {
        // Assuming jackson needs this for serializing/deserializing
    }

    public PaginationDto getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDto pagination) {
        this.pagination = pagination;
    }

    public Map<SearchFilter, String> getSearchCriteriaMap() {
        return searchCriteriaMap;
    }

    public void setSearchCriteriaMap(Map<SearchFilter, String> searchCriteriaMap) {
        this.searchCriteriaMap = searchCriteriaMap;
    }

    public Map<SearchFilter, List<String>> getSearchCriteriaMapMultipleValues() {
        return searchCriteriaMapMultipleValues;
    }

    public void setSearchCriteriaMapMultipleValues(Map<SearchFilter, List<String>> searchCriteriaMapMultipleValues) {
        this.searchCriteriaMapMultipleValues = searchCriteriaMapMultipleValues;
    }

    public SortKey getSorting() {
        return sorting;
    }

    public void setSorting(SortKey sorting) {
        this.sorting = sorting;
    }

    public List<GroupCriteria> getGroupByFields() {
        return groupByFields;
    }

    public void setGroupByFields(List<GroupCriteria> groupByFields) {
        this.groupByFields = groupByFields;
    }

    public Boolean getShowOnlyLatest() {
        return showOnlyLatest;
    }

    public void setShowOnlyLatest(Boolean showOnlyLatest) {
        this.showOnlyLatest = showOnlyLatest;
    }

    public Boolean getUseStatusInsteadOfPurposeCode() {
        return useStatusInsteadOfPurposeCode;
    }

    public void setUseStatusInsteadOfPurposeCode(Boolean useStatusInsteadOfPurposeCode) {
        this.useStatusInsteadOfPurposeCode = useStatusInsteadOfPurposeCode;
    }
}

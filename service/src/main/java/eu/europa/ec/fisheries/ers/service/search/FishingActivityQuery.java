/*
Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries  European Union, 2015-2016.

This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it 
and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of 
the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.

 */
package eu.europa.ec.fisheries.ers.service.search;

import java.util.List;

/**
 * Created by sanera on 24/06/2016.
 */
public class FishingActivityQuery {
    private Pagination pagination;
    private  List<ListCriteria> searchCriteria;
    private SortKey sortKey;

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public void setSearchCriteria(List<ListCriteria> searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public List<ListCriteria> getSearchCriteria() {
        return searchCriteria;
    }


    public Pagination getPagination() {

        return pagination;
    }

    public SortKey getSortKey() {
        return sortKey;
    }

    public void setSortKey(SortKey sortKey) {
        this.sortKey = sortKey;
    }

    @Override
    public String toString() {
        return "FishingActivityQuery{" +
                "pagination=" + pagination +
                ", searchCriteria=" + searchCriteria +
                ", sortKey=" + sortKey +
                '}';
    }
}
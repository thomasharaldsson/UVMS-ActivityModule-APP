/*
Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries @ European Union, 2015-2016.

This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it
and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of
the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.

*/
package eu.europa.ec.fisheries.ers.service.dto.view.facatch;

import com.fasterxml.jackson.annotation.JsonView;
import eu.europa.ec.fisheries.ers.service.dto.view.parent.FishingActivityView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kovian on 01/03/2017.
 */
public class FaCatchGroupDto {

    @JsonView(FishingActivityView.CommonView.class)
    private String type;

    @JsonView(FishingActivityView.CommonView.class)
    private String species;

    @JsonView(FishingActivityView.CommonView.class)
    private Double calculatedWeight;

    @JsonView(FishingActivityView.CommonView.class)
    private FaCatchDenomLocationDto locations;

    @JsonView(FishingActivityView.CommonView.class)
    private Map<String, FaCatchGroupDetailsDto> groupingDetails;

    public FaCatchGroupDto() {
        groupingDetails = new HashMap<>();
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public FaCatchDenomLocationDto getLocations() {
        return locations;
    }
    public void setLocations(FaCatchDenomLocationDto locations) {
        this.locations = locations;
    }
    public String getSpecies() {
        return species;
    }
    public void setSpecies(String specie) {
        this.species = specie;
    }
    public Double getCalculatedWeight() {
        return calculatedWeight;
    }
    public void setCalculatedWeight(Double calculatedWeight) {
        this.calculatedWeight = calculatedWeight;
    }
    public Map<String, FaCatchGroupDetailsDto> getGroupingDetails() {
        return groupingDetails;
    }
    public void setGroupingDetails(Map<String, FaCatchGroupDetailsDto> groupingDetails) {
        this.groupingDetails = groupingDetails;
    }
}
/*
Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries @ European Union, 2015-2016.

This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it 
and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of 
the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.

 */
package eu.europa.ec.fisheries.ers.service.mapper;

import eu.europa.ec.fisheries.ers.fa.entities.*;
import eu.europa.ec.fisheries.ers.fa.utils.FluxLocationTypeEnum;
import eu.europa.ec.fisheries.uvms.activity.model.dto.ContactPersonDTO;
import eu.europa.ec.fisheries.uvms.activity.model.dto.FishingActivityReportDTO;
import eu.europa.ec.fisheries.uvms.activity.model.dto.fareport.details.FishingActivityDetailsDTO;
import eu.europa.ec.fisheries.uvms.activity.model.dto.fareport.details.FluxLocationDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._18.*;
import un.unece.uncefact.data.standard.unqualifieddatatype._18.IDType;


import java.util.*;

/**
 * Created by padhyad on 5/17/2016.
 */
@Mapper(uses = {FaCatchMapper.class, DelimitedPeriodMapper.class, FishingGearMapper.class, GearProblemMapper.class, FishingTripMapper.class, FluxCharacteristicsMapper.class})
public abstract class FishingActivityMapper extends BaseMapper {

    public static final FishingActivityMapper INSTANCE = Mappers.getMapper(FishingActivityMapper.class);
    public static final String LocationTypeArea ="AREA";
    public static final String LocationTypePort ="LOCATION";

    @Mappings({
            @Mapping(target = "typeCode", expression = "java(getCodeType(fishingActivity.getTypeCode()))"),
            @Mapping(target = "typeCodeListid", expression = "java(getCodeTypeListId(fishingActivity.getTypeCode()))"),
            @Mapping(target = "occurence", expression = "java(convertToDate(fishingActivity.getOccurrenceDateTime()))"),
            @Mapping(target = "reasonCode", expression = "java(getCodeType(fishingActivity.getReasonCode()))"),
            @Mapping(target = "reasonCodeListId", expression = "java(getCodeTypeListId(fishingActivity.getReasonCode()))"),
            @Mapping(target = "vesselActivityCode", expression = "java(getCodeType(fishingActivity.getVesselRelatedActivityCode()))"),
            @Mapping(target = "vesselActivityCodeListId", expression = "java(getCodeTypeListId(fishingActivity.getVesselRelatedActivityCode()))"),
            @Mapping(target = "fisheryTypeCode", expression = "java(getCodeType(fishingActivity.getFisheryTypeCode()))"),
            @Mapping(target = "fisheryTypeCodeListId", expression = "java(getCodeTypeListId(fishingActivity.getFisheryTypeCode()))"),
            @Mapping(target = "speciesTargetCode", expression = "java(getCodeType(fishingActivity.getSpeciesTargetCode()))"),
            @Mapping(target = "speciesTargetCodeListId", expression = "java(getCodeTypeListId(fishingActivity.getSpeciesTargetCode()))"),
            @Mapping(target = "operationQuantity", expression = "java(getQuantityInLong(fishingActivity.getOperationsQuantity()))"),
            @Mapping(target = "fishingDurationMeasure", expression = "java(getMeasure(fishingActivity.getFishingDurationMeasure()))"),
            @Mapping(target = "flapDocumentId", expression = "java(getFlapDocId(fishingActivity.getSpecifiedFLAPDocument()))"),
            @Mapping(target = "flapDocumentSchemeId", expression = "java(getFlapDocSchemeId(fishingActivity.getSpecifiedFLAPDocument()))"),
            @Mapping(target = "faReportDocument", expression = "java(faReportDocumentEntity)"),
            @Mapping(target = "sourceVesselCharId", expression = "java(getSourceVesselStorageCharacteristics(fishingActivity.getSourceVesselStorageCharacteristic(), fishingActivityEntity))"),
            @Mapping(target = "destVesselCharId", expression = "java(getDestVesselStorageCharacteristics(fishingActivity.getDestinationVesselStorageCharacteristic(), fishingActivityEntity))"),
            @Mapping(target = "fishingActivityIdentifiers", expression = "java(mapToFishingActivityIdentifierEntities(fishingActivity.getIDS(), fishingActivityEntity))"),
            @Mapping(target = "delimitedPeriods", expression = "java(getDelimitedPeriodEntities(fishingActivity.getSpecifiedDelimitedPeriods(), fishingActivityEntity))"),
            @Mapping(target = "fishingTrips", expression = "java(getFishingTripEntities(fishingActivity.getSpecifiedFishingTrip(), fishingActivityEntity))"),
            @Mapping(target = "fishingGears", expression = "java(getFishingGearEntities(fishingActivity.getSpecifiedFishingGears(), fishingActivityEntity))"),
            @Mapping(target = "fluxCharacteristics", expression = "java(getFluxCharacteristicsEntities(fishingActivity.getSpecifiedFLUXCharacteristics(), fishingActivityEntity))"),
            @Mapping(target = "gearProblems", expression = "java(getGearProblemEntities(fishingActivity.getSpecifiedGearProblems(), fishingActivityEntity))"),
            @Mapping(target = "fluxLocations", expression = "java(getFluxLocationEntities(fishingActivity.getRelatedFLUXLocations(), fishingActivityEntity))"),
            @Mapping(target = "faCatchs", expression = "java(getFaCatchEntities(fishingActivity.getSpecifiedFACatches(), fishingActivityEntity))"),
            @Mapping(target = "allRelatedFishingActivities", expression = "java(getAllRelatedFishingActivities(fishingActivity.getRelatedFishingActivities(), faReportDocumentEntity, fishingActivityEntity))")
    })
    public abstract FishingActivityEntity mapToFishingActivityEntity(FishingActivity fishingActivity, FaReportDocumentEntity faReportDocumentEntity, @MappingTarget FishingActivityEntity fishingActivityEntity);

    public List<FishingActivityReportDTO> mapToFishingActivityReportDTOList(List<FishingActivityEntity> activityList){

        List<FishingActivityReportDTO> activityReportDTOList= new ArrayList<>();

        for(FishingActivityEntity entity:activityList){
            activityReportDTOList.add(mapToFishingActivityReportDTO(entity));
        }

        return activityReportDTOList;
    }

    @Mappings({
            @Mapping(target = "uniqueReportId", expression = "java(getUniqueId(entity))"),
            @Mapping(target = "from", expression = "java(getFrom(entity))"),
            @Mapping(source = "occurence", target = "occurence"),
            @Mapping(target = "vesselTransportMeansName", expression = "java(getVesselTransportMeansName(entity))"),
            @Mapping(target = "vesselTransportMeansIdList", expression = "java(getVesselTransportMeansId(entity))"),
            @Mapping(target = "purposeCode", expression = "java(getPurposeCode(entity))"),
            @Mapping(target = "FAReportType", expression = "java(getFAReportTypeCode(entity))"),
            @Mapping(source = "typeCode", target = "activityType"),
            @Mapping(target = "areas", expression = "java(getFishingActivityLocationTypes(entity,LocationTypeArea))"),
            @Mapping(target = "port", expression = "java(getFishingActivityLocationTypes(entity,LocationTypePort))"),
            @Mapping(target = "fishingGear", expression = "java(getFishingGears(entity))"),
            @Mapping(target = "speciesCode", expression = "java(getSpeciesCode(entity))"),
            @Mapping(target = "quantity", expression = "java(getQuantity(entity))"),
            @Mapping(target = "fluxLocations", expression = "java(null)"),
            @Mapping(target = "fishingGears", expression = "java(null)"),
            @Mapping(target = "fluxCharacteristics", expression = "java(null)"),
            @Mapping(target = "delimitedPeriod", expression = "java(null)"),
            @Mapping(target = "contactPerson", expression = "java(getContactPerson(entity))")
    })
    public abstract FishingActivityReportDTO mapToFishingActivityReportDTO(FishingActivityEntity entity);

    @Mappings({
            @Mapping(target = "sourceVesselId", source = "sourceVesselCharId.vesselId"),
            @Mapping(target = "sourceVesselTypeCode", source = "sourceVesselCharId.vesselTypeCode"),
            @Mapping(target = "destVesselId", source = "destVesselCharId.vesselId"),
            @Mapping(target = "destVesselTypeCode", source = "destVesselCharId.vesselTypeCode"),
            @Mapping(target = "activityTypeCode", source = "typeCode"),
            @Mapping(target = "occurence", source = "occurence"),
            @Mapping(target = "reasonCode", source = "reasonCode"),
            @Mapping(target = "vesselActivityCode", source = "vesselActivityCode"),
            @Mapping(target = "fisheryTypeCode", source = "fisheryTypeCode"),
            @Mapping(target = "speciesTargetCode", source = "speciesTargetCode"),
            @Mapping(target = "operationQuantity", source = "operationQuantity"),
            @Mapping(target = "fishingDurationMeasure", source = "fishingDurationMeasure"),
            @Mapping(target = "flapDocumentId", source = "flapDocumentId"),
            @Mapping(target = "fishingActivityIds", expression = "java(getFishingActivityIds(fishingActivityEntity.getFishingActivityIdentifiers()))"),
            @Mapping(target = "fishingTrip", source = "fishingTrips"),
            @Mapping(target = "faCatches", source = "faCatchs"),
            @Mapping(target = "fishingGears", source = "fishingGears"),
            @Mapping(target = "gearProblems", source = "gearProblems"),
            @Mapping(target = "delimitedPeriods", source = "delimitedPeriods"),
            @Mapping(target = "fluxCharacteristics", source = "fluxCharacteristics"),
            @Mapping(target = "fluxLocations", expression = "java(getFluxLocationDetailsDTOs(fishingActivityEntity.getFluxLocations()))")
    })
    public abstract FishingActivityDetailsDTO mapToFishingActivityDetailsDTO(FishingActivityEntity fishingActivityEntity);

    public abstract List<FishingActivityDetailsDTO> mapToFishingActivityDetailsDTOList(Set<FishingActivityEntity> fishingActivityEntities);

    protected List<FluxLocationDetailsDTO> getFluxLocationDetailsDTOs(Set<FluxLocationEntity> fluxLocationEntities) {
        List<FluxLocationDetailsDTO> fluxLocationDetailsDTOs = new ArrayList<>();
        for (FluxLocationEntity fluxLocationEntity : fluxLocationEntities) {
            fluxLocationDetailsDTOs.add(FluxLocationMapper.INSTANCE.mapToFluxLocationDetailsDTO(fluxLocationEntity));
        }
        return fluxLocationDetailsDTOs;
    }

    public abstract List<FishingActivityDetailsDTO> mapToFishingActivityDetailsDTOList(List<FishingActivityEntity> fishingActivityEntities);

    protected List<String> getFishingActivityIds(Set<FishingActivityIdentifierEntity> fishingActivityIdentifiers) {
        List<String> fishingActivityIds = new ArrayList<>();
        for (FishingActivityIdentifierEntity identifierEntity : fishingActivityIdentifiers) {
            fishingActivityIds.add(identifierEntity.getFaIdentifierId());
        }
        return fishingActivityIds;
    }

    @Mappings({
            @Mapping(target = "faIdentifierId", expression = "java(getIdType(idType))"),
            @Mapping(target = "faIdentifierSchemeId", expression = "java(getIdTypeSchemaId(idType))")
    })
    protected abstract FishingActivityIdentifierEntity mapToFishingActivityIdentifierEntity(IDType idType);

    protected String getUniqueId(FishingActivityEntity entity){
        if(entity ==null || entity.getFaReportDocument() == null || entity.getFaReportDocument().getFluxReportDocument() ==null){
            return null;
        }

        return  entity.getFaReportDocument().getFluxReportDocument().getFluxReportDocumentId();
    }

    protected String getFrom(FishingActivityEntity entity){
        if(entity ==null || entity.getFaReportDocument() == null || entity.getFaReportDocument().getFluxReportDocument() ==null){
            return null;
        }

        return  entity.getFaReportDocument().getFluxReportDocument().getOwnerFluxPartyName();
    }


    protected String getPurposeCode(FishingActivityEntity entity){
        if(entity ==null || entity.getFaReportDocument() == null || entity.getFaReportDocument().getFluxReportDocument() ==null){
            return null;
        }

        return  entity.getFaReportDocument().getFluxReportDocument().getPurposeCode();
    }

    protected String getFAReportTypeCode(FishingActivityEntity entity){
        if(entity ==null || entity.getFaReportDocument() == null ){
            return null;
        }

        return  entity.getFaReportDocument().getTypeCode();
    }

    protected String getVesselTransportMeansName(FishingActivityEntity entity){
        if(entity ==null || entity.getFaReportDocument() == null || entity.getFaReportDocument().getVesselTransportMeans() ==null){
            return null;
        }

       return  entity.getFaReportDocument().getVesselTransportMeans().getName();
    }

    protected List<String> getVesselTransportMeansId(FishingActivityEntity entity){
        if(entity ==null || entity.getFaReportDocument() == null || entity.getFaReportDocument().getVesselTransportMeans() ==null
                || entity.getFaReportDocument().getVesselTransportMeans().getVesselIdentifiers() ==null){
            return Collections.emptyList();
        }

        List<String> vesselTransportIdList = new ArrayList<>();
        Set<VesselIdentifierEntity> identifierList =entity.getFaReportDocument().getVesselTransportMeans().getVesselIdentifiers();

        for (VesselIdentifierEntity identity: identifierList){
            vesselTransportIdList.add(identity.getVesselIdentifierId());
        }
        return vesselTransportIdList;
    }

    protected List<String> getSpeciesCode(FishingActivityEntity entity){
        if(entity ==null || entity.getFaCatchs() == null ){
            return Collections.emptyList();
        }

        List<String> speciesCode = new ArrayList<>();
        Set<FaCatchEntity> faCatchList =entity.getFaCatchs();

        for (FaCatchEntity faCatch: faCatchList){
            speciesCode.add(faCatch.getSpeciesCode());
        }

        return speciesCode;
    }

    protected List<ContactPersonDTO> getContactPerson(FishingActivityEntity entity){
        if(entity ==null || entity.getFaReportDocument() == null || entity.getFaReportDocument().getVesselTransportMeans() ==null
                || entity.getFaReportDocument().getVesselTransportMeans().getContactParty() ==null){
            return Collections.emptyList();
        }
        List<ContactPersonDTO> contactPersonList = new ArrayList<>();
        Set<ContactPartyEntity> contactPartyList =entity.getFaReportDocument().getVesselTransportMeans().getContactParty();

        for (ContactPartyEntity contactParty: contactPartyList){
            contactPersonList.add(ContactPersonMapper.INSTANCE.mapToContactPersonDTO(contactParty.getContactPerson()));
        }
        return contactPersonList;
    }

    protected List<Long> getQuantity(FishingActivityEntity entity){
        if(entity ==null || entity.getFaCatchs() == null ){
            return Collections.emptyList();
        }
        List<Long> quantity = new ArrayList<>();
        Set<FaCatchEntity> faCatchList =entity.getFaCatchs();

        for (FaCatchEntity faCatch: faCatchList){
            quantity.add(faCatch.getUnitQuantity());
        }
        return quantity;
    }


    protected List<String> getFishingGears(FishingActivityEntity entity){
        if(entity ==null || entity.getFishingGears() == null ){
            return Collections.emptyList();
        }
        List<String> gears = new ArrayList<>();
        Set<FishingGearEntity> gearList =entity.getFishingGears();

        for (FishingGearEntity gear: gearList){
            gears.add(gear.getTypeCode());
        }
        return gears;
    }


    protected List<String> getFishingActivityLocationTypes(FishingActivityEntity entity,String locationType){
        if(entity ==null || entity.getFluxLocations() == null ){
            return Collections.emptyList();
        }
        List<String> areas = new ArrayList<>();
        Set<FluxLocationEntity> fluxLocations =entity.getFluxLocations();

         for (FluxLocationEntity location: fluxLocations){
           if(locationType.equalsIgnoreCase(location.getTypeCode())) {
               areas.add(location.getTypeCodeListId());
           }
        }
        return areas;
    }




    protected Set<FishingActivityEntity> getAllRelatedFishingActivities(List<FishingActivity> fishingActivity, FaReportDocumentEntity faReportDocumentEntity, FishingActivityEntity parentFishingActivity) {
        if (fishingActivity == null || fishingActivity.isEmpty()) {
            return null;
        }
        Set<FishingActivityEntity> relatedFishingActivityEntities = new HashSet<>();
        for (FishingActivity relatedFishingActivity : fishingActivity) {
            FishingActivityEntity relatedFishingActivityEntity = FishingActivityMapper.INSTANCE.mapToFishingActivityEntity(relatedFishingActivity, faReportDocumentEntity, new FishingActivityEntity());
            relatedFishingActivityEntity.setRelatedFishingActivity(parentFishingActivity);
            relatedFishingActivityEntities.add(relatedFishingActivityEntity);
        }
        return relatedFishingActivityEntities;
    }

    protected Set<FaCatchEntity> getFaCatchEntities(List<FACatch> faCatches, FishingActivityEntity fishingActivityEntity) {
        if (faCatches == null || faCatches.isEmpty()) {
            return null;
        }
        Set<FaCatchEntity> faCatchEntities = new HashSet<>();
        for (FACatch faCatch : faCatches) {
            FaCatchEntity faCatchEntity = FaCatchMapper.INSTANCE.mapToFaCatchEntity(faCatch, fishingActivityEntity, new FaCatchEntity());
            faCatchEntities.add(faCatchEntity);
        }
        return faCatchEntities;
    }

    protected Set<FluxLocationEntity> getFluxLocationEntities(List<FLUXLocation> fluxLocations, FishingActivityEntity fishingActivityEntity) {
        if (fluxLocations == null || fluxLocations.isEmpty()) {
            return null;
        }
        Set<FluxLocationEntity> fluxLocationEntities = new HashSet<>();
        for (FLUXLocation fluxLocation : fluxLocations) {
            FluxLocationEntity fluxLocationEntity = FluxLocationMapper.INSTANCE.mapToFluxLocationEntity(fluxLocation, FluxLocationTypeEnum.FA_RELATED, fishingActivityEntity, new FluxLocationEntity());
            fluxLocationEntities.add(fluxLocationEntity);
        }
        return fluxLocationEntities;
    }

    protected Set<GearProblemEntity> getGearProblemEntities(List<GearProblem> gearProblems, FishingActivityEntity fishingActivityEntity) {
        if (gearProblems == null || gearProblems.isEmpty()) {
            return null;
        }
        Set<GearProblemEntity> gearProblemEntities = new HashSet<>();
        for (GearProblem gearProblem : gearProblems) {
            GearProblemEntity gearProblemEntity =GearProblemMapper.INSTANCE.mapToGearProblemEntity(gearProblem, fishingActivityEntity, new GearProblemEntity());
            gearProblemEntities.add(gearProblemEntity);
        }
        return gearProblemEntities;
    }

    protected Set<FluxCharacteristicEntity> getFluxCharacteristicsEntities(List<FLUXCharacteristic> fluxCharacteristics, FishingActivityEntity fishingActivityEntity) {
        if (fluxCharacteristics == null || fluxCharacteristics.isEmpty()) {
            return null;
        }
        Set<FluxCharacteristicEntity> fluxCharacteristicEntities = new HashSet<>();
        for (FLUXCharacteristic fluxCharacteristic : fluxCharacteristics) {
            FluxCharacteristicEntity fluxCharacteristicEntity = FluxCharacteristicsMapper.INSTANCE.mapToFluxCharEntity(fluxCharacteristic, fishingActivityEntity, new FluxCharacteristicEntity());
            fluxCharacteristicEntities.add(fluxCharacteristicEntity);
        }
        return fluxCharacteristicEntities;
    }

    protected Set<FishingGearEntity> getFishingGearEntities(List<FishingGear> fishingGears, FishingActivityEntity fishingActivityEntity) {
        if (fishingGears == null || fishingGears.isEmpty()) {
            return null;
        }
        Set<FishingGearEntity> fishingGearEntities = new HashSet<>();
        for (FishingGear fishingGear : fishingGears) {
            FishingGearEntity fishingGearEntity = FishingGearMapper.INSTANCE.mapToFishingGearEntity(fishingGear, fishingActivityEntity, new FishingGearEntity());
            fishingGearEntities.add(fishingGearEntity);
        }
        return fishingGearEntities;
    }

    protected Set<FishingTripEntity> getFishingTripEntities(FishingTrip fishingTrip, FishingActivityEntity fishingActivityEntity) {
        if (fishingTrip == null) {
            return null;
        }
        return new HashSet<>(Arrays.asList(FishingTripMapper.INSTANCE.mapToFishingTripEntity(fishingTrip, fishingActivityEntity, new FishingTripEntity())));
    }

    protected Set<DelimitedPeriodEntity> getDelimitedPeriodEntities(List<DelimitedPeriod> delimitedPeriods, FishingActivityEntity fishingActivityEntity) {
        if (delimitedPeriods == null || delimitedPeriods.isEmpty()) {
            return null;
        }
        Set<DelimitedPeriodEntity> delimitedPeriodEntities =  new HashSet<>();
        for (DelimitedPeriod delimitedPeriod : delimitedPeriods) {
            DelimitedPeriodEntity delimitedPeriodEntity = DelimitedPeriodMapper.INSTANCE.mapToDelimitedPeriodEntity(delimitedPeriod, fishingActivityEntity, new DelimitedPeriodEntity());
            delimitedPeriodEntities.add(delimitedPeriodEntity);
        }
       return delimitedPeriodEntities;
    }

    protected Set<FishingActivityIdentifierEntity> mapToFishingActivityIdentifierEntities(List<IDType> idTypes, FishingActivityEntity fishingActivityEntity) {
        if (idTypes == null && idTypes.isEmpty()) {
            return null;
        }
        Set<FishingActivityIdentifierEntity> identifierEntities = new HashSet<>();
        for (IDType idType : idTypes) {
            FishingActivityIdentifierEntity identifier = FishingActivityMapper.INSTANCE.mapToFishingActivityIdentifierEntity(idType);
            identifier.setFishingActivity(fishingActivityEntity);
            identifierEntities.add(identifier);
        }
        return identifierEntities;
    }

    protected VesselStorageCharacteristicsEntity getSourceVesselStorageCharacteristics(VesselStorageCharacteristic sourceVesselStorageChar, FishingActivityEntity fishingActivityEntity) {
        if (sourceVesselStorageChar == null) {
            return null;
        }
        return VesselStorageCharacteristicsMapper.INSTANCE.mapToSourceVesselStorageCharEntity(sourceVesselStorageChar, fishingActivityEntity, new VesselStorageCharacteristicsEntity());
    }

    protected VesselStorageCharacteristicsEntity getDestVesselStorageCharacteristics(VesselStorageCharacteristic destVesselStorageChar, FishingActivityEntity fishingActivityEntity) {
        if (destVesselStorageChar == null) {
            return null;
        }
        return VesselStorageCharacteristicsMapper.INSTANCE.mapToDestVesselStorageCharEntity(destVesselStorageChar, fishingActivityEntity, new VesselStorageCharacteristicsEntity());
    }

    protected String getFlapDocId(FLAPDocument flapDocument) {
        if (flapDocument == null) {
            return null;
        }
        return (flapDocument.getID() == null) ? null : getIdType(flapDocument.getID());
    }

    protected String getFlapDocSchemeId(FLAPDocument flapDocument) {
        if (flapDocument == null) {
            return null;
        }
        return (flapDocument.getID() == null) ? null : getIdTypeSchemaId(flapDocument.getID());
    }
}
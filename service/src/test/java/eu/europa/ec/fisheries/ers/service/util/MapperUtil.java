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

package eu.europa.ec.fisheries.ers.service.util;

import eu.europa.ec.fisheries.ers.fa.entities.*;
import eu.europa.ec.fisheries.ers.fa.utils.FaReportStatusEnum;
import eu.europa.ec.fisheries.uvms.activity.model.dto.config.ActivityConfigDTO;
import eu.europa.ec.fisheries.uvms.activity.model.dto.config.FaReportConfigDTO;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._18.*;
import un.unece.uncefact.data.standard.unqualifieddatatype._18.*;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by padhyad on 7/27/2016.
 */
public class MapperUtil {

    public static ActivityConfigDTO getSourceActivityConfigDTO() {
        ActivityConfigDTO activityConfigDTO = new ActivityConfigDTO();
        FaReportConfigDTO faReportConfigDTO = new FaReportConfigDTO();
        faReportConfigDTO.setFaReport(Arrays.asList("Report1", "Report2", "Report3"));
        faReportConfigDTO.setFishingActivity(Arrays.asList("Activity1", "Activity2", "Activity3", "Activity4"));
        faReportConfigDTO.setFishingTrip(Arrays.asList("Trip1", "Trip2", "Trip3", "Trip4"));
        faReportConfigDTO.setDelimitedPeriod(Arrays.asList("Delimit1", "Delimit2", "Delimit3"));
        faReportConfigDTO.setFaCatches(Arrays.asList("Catch1", "Catch2", "Catch3"));
        faReportConfigDTO.setFishingGears(Arrays.asList("Gear1", "Gear2", "Gear3"));
        faReportConfigDTO.setGearCharacteristics(Arrays.asList("char1", "char2", "char3"));
        faReportConfigDTO.setFluxLocations(Arrays.asList("location1", "location2", "location3"));

        faReportConfigDTO.setFluxCharacteristics(null);
        faReportConfigDTO.setStructuredAddress(null);
        faReportConfigDTO.setAapProcess(null);
        faReportConfigDTO.setAapProduct(null);
        faReportConfigDTO.setAapStock(null);
        faReportConfigDTO.setGearProblems(null);
        faReportConfigDTO.setVessel(null);
        faReportConfigDTO.setContactParty(null);
        faReportConfigDTO.setContactPerson(null);

        activityConfigDTO.setFaReportConfig(faReportConfigDTO);
        return activityConfigDTO;
    }

    public static ActivityConfigDTO getTargetActivityConfigDTO() {
        ActivityConfigDTO activityConfigDTO = new ActivityConfigDTO();
        FaReportConfigDTO faReportConfigDTO = new FaReportConfigDTO();
        faReportConfigDTO.setFluxCharacteristics(Arrays.asList("char1", "char2", "char3"));
        faReportConfigDTO.setStructuredAddress(Arrays.asList("Address1", "Address2", "Address3"));
        faReportConfigDTO.setAapProcess(Arrays.asList("process1", "process2", "process3"));
        faReportConfigDTO.setAapProduct(Arrays.asList("product1", "product2", "product3"));
        faReportConfigDTO.setAapStock(Arrays.asList("Stock1", "Stock2", "Stock3"));
        faReportConfigDTO.setGearProblems(Arrays.asList("problem1", "problem2", "problem3"));
        faReportConfigDTO.setVessel(Arrays.asList("vessel1", "vessel2", "vessel3"));
        faReportConfigDTO.setContactParty(Arrays.asList("Party1", "Party2", "Party3"));
        faReportConfigDTO.setContactPerson(Arrays.asList("Person1", "Person2", "Person3"));

        faReportConfigDTO.setFaReport(null);
        faReportConfigDTO.setFishingActivity(null);
        faReportConfigDTO.setFishingTrip(null);
        faReportConfigDTO.setDelimitedPeriod(null);
        faReportConfigDTO.setFaCatches(null);
        faReportConfigDTO.setFishingGears(null);
        faReportConfigDTO.setGearCharacteristics(null);
        faReportConfigDTO.setFluxLocations(null);

        activityConfigDTO.setFaReportConfig(faReportConfigDTO);
        return activityConfigDTO;
    }

    public static FishingActivityEntity getFishingActivityEntity(){


        FaCatchEntity faCatchEntity=new   FaCatchEntity(1,null,null, "AREA","FLUX_LOCATION_TYPE","speciesCode","CODE_LIST",12l,10d,"kg","KG_LISTID","PROD_USAGE","PROD_USAGE_LISTID","WM_CODE","WEIGHT_MEASURE");
        SizeDistributionEntity SizeDistributionEntity = new SizeDistributionEntity(1,"LSC","FA_SIZE_CLASS","FA_SIZE_CATEGORY","FA_SIZE_CATEGORY", faCatchEntity);
        FaReportDocumentEntity faReportDocumentEntity=new  FaReportDocumentEntity(1,null,null,"AREA","FLUX_LOCATION_TYPE",java.sql.Date.valueOf("2014-12-12"),"fmc","fmc_list",null,null) ;
        FluxReportDocumentEntity fluxReportDocumentEntity=new FluxReportDocumentEntity(1, "REPORT_ID","REF_ID",java.sql.Date.valueOf("2014-12-12"),"CODE11","CODELISTID","PURPOSE","OWNER_PARTY_ID","NAME", faReportDocumentEntity) ;
        Set< FaCatchEntity > faCatchs =new HashSet<>();
        faCatchs.add(faCatchEntity);
        DelimitedPeriodEntity delimitedPeriodEntity =new  DelimitedPeriodEntity(1, null, null, java.sql.Date.valueOf("2014-12-12"), java.sql.Date.valueOf("2016-12-12"), 11d);
        Set< DelimitedPeriodEntity > delimitedPeriods =new HashSet<>();
        delimitedPeriods.add(delimitedPeriodEntity);

        FishingGearEntity fishingGearEntity=new  FishingGearEntity(1, null, "roleid", "roleCode", "typeCodeListId", "typeCode", null, null, null);
        Set<FishingGearEntity> fishingGears = new HashSet<>();
        fishingGears.add(fishingGearEntity);

        FluxLocationEntity fluxLocationEntity=new   FluxLocationEntity(1, faCatchEntity, null, "typeCode", "typeCodeListId", "countryId", "rfmoCode", 11d, 13d, "fluxLocationType"," countryIdSchemeId", "fluxLocationIdentifierSchemeId", "fluxLocationIdentifier", " geopoliticalRegionCode", "geopoliticalRegionCodeListId", " name", " sovereignRightsCountryCode", " jurisdictionCountryCode", 11d, "systemId", null, null);
        Set<FluxLocationEntity> fluxLocations = new HashSet<>();
        fluxLocations.add(fluxLocationEntity);



        FishingActivityEntity fishingActivityEntity = new  FishingActivityEntity(
                    1,faReportDocumentEntity,null,null,"TYPECODE","FLUX_LOCATION_TYPE",java.sql.Date.valueOf("2014-12-12"),
        "REASONCODE","REASON_CODE_LIST","VESSEL_ACTIVITY","VESSEL_CODE_LIST","FISHERY_CODE","FISHERY_CODE_LIST","SPECIES_CODE","speciesTargetCodeListId",12l,11d,"flapid","flapschemeid",null,faCatchs,delimitedPeriods,
         null,null,fishingGears,null,null,fluxLocations,null);



          return fishingActivityEntity;
    }

    public static FaReportDocumentEntity getFaReportDocumentEntity() {
        FaReportDocumentEntity faReportDocumentEntity = new FaReportDocumentEntity();
        faReportDocumentEntity.setStatus(FaReportStatusEnum.UPDATED.getStatus());
        faReportDocumentEntity.setTypeCode("Type Code 1");
        faReportDocumentEntity.setTypeCodeListId("57thfy-58tjd84-58thjf-58tjrj9");
        faReportDocumentEntity.setAcceptedDatetime(new Date());
        faReportDocumentEntity.setFmcMarker("FMC Marker");
        faReportDocumentEntity.setFmcMarkerListId("FMC Marker list Id");

        FaReportIdentifierEntity faReportIdentifierEntity = new FaReportIdentifierEntity();
        faReportIdentifierEntity.setFaReportIdentifierId("Identifier Id 1");
        faReportIdentifierEntity.setFaReportIdentifierSchemeId("57th785-tjf845-tjfui5-tjfuir8");
        faReportIdentifierEntity.setFaReportDocument(faReportDocumentEntity);
        faReportDocumentEntity.setFaReportIdentifiers(new HashSet<FaReportIdentifierEntity>(Arrays.asList(faReportIdentifierEntity)));

        FluxReportDocumentEntity fluxReportDocumentEntity = getFluxReportDocumentEntity();
        fluxReportDocumentEntity.setFaReportDocument(faReportDocumentEntity);
        faReportDocumentEntity.setFluxReportDocument(fluxReportDocumentEntity);
        return faReportDocumentEntity;
    }

    public static FluxReportDocumentEntity getFluxReportDocumentEntity() {
        FluxReportDocumentEntity fluxReportDocumentEntity = new FluxReportDocumentEntity();
        fluxReportDocumentEntity.setCreationDatetime(new Date());
        fluxReportDocumentEntity.setFluxReportDocumentId("Doc Id 1");
        fluxReportDocumentEntity.setOwnerFluxPartyId("Flux party Id 1");
        fluxReportDocumentEntity.setOwnerFluxPartyName("Flux party name");
        fluxReportDocumentEntity.setPurpose("Test purpose");
        fluxReportDocumentEntity.setPurposeCode("5");
        fluxReportDocumentEntity.setPurposeCodeListId("57thf-58fj88-4d9834-thdue");
        fluxReportDocumentEntity.setReferenceId("Ref Id 1");
        return fluxReportDocumentEntity;
    }


    public static AAPProcess getAapProcess() {
        List<CodeType> codeList = Arrays.asList(getCodeType("AAPPROCESS", "qbdcg-3fhr5-rd4kd5-erigks5k"));
        NumericType numericType = getNumericType(123);
        AAPProcess aapProcess = new AAPProcess(codeList, numericType, null, Arrays.asList(getAapProduct()));
        return aapProcess;
    }

    public static AAPProduct getAapProduct() {
        CodeType speciesCode = getCodeType("Species 1", "qbdcg-3fhr5-rd4kd5-er5tgd5k");
        QuantityType quantityType = getQuantityType(123);
        MeasureType measureType = getMeasureType(123, "Measure 1", "qbdcg-3fhr5-rd4kd5-er5tgd5k");
        CodeType weighingMeansCode = getCodeType("Weighing Means 1", "qbd43cg-3fhr5t65-rd4kd5rt4-er5tgd5k");
        CodeType usageCode = getCodeType("Usage Code 1", "qbd43cg-3fhr5t65-rd4kd5rt4-er5tgd5k");
        QuantityType packagingUnitQuantity = getQuantityType(1234);
        CodeType packagingTypeCode = getCodeType("packaging type 1", "qbd43cg-3fhr5t65-rd4kd5rt4-er5tgd5k");
        MeasureType packagingUnitAverageWeightMeasure = getMeasureType(123, "Weight  Measure 1", "qbdcg-3fhr5-rd4kd5-er5tgd5k");
        SalesPrice totalSalesPrice = getSalesPrice(getAmountType(123, "qbd43cg-3fhr5t65-rd4kd5rt4-er5tgd5k", "1"));
        SizeDistribution specifiedSizeDistribution = getSizeDistribution(getCodeType("catagory 1", "qbd43cg-3fhr5t65-rd4kd5rt4-er5tgd5k"),
                getCodeType("class code1", "qbd43cg-3fhr5t65-rd45674-er5tgd5k"));
        AAPProduct aapProduct = new AAPProduct(speciesCode, quantityType, measureType, weighingMeansCode,
                usageCode, packagingUnitQuantity, packagingTypeCode, packagingUnitAverageWeightMeasure, null, totalSalesPrice, specifiedSizeDistribution, null, null);
        return aapProduct;
    }

    public static AAPStock getAapStock() {
        AAPStock aapStock = new AAPStock(getIdType("Id Type1", "4fjgt7-4rifi65-4rjf75-4ru85gf"));
        return aapStock;
    }

    public static StructuredAddress getStructuredAddress() {
        IDType id = getIdType("ID type 1", "5ryit6-5tj47e-45jfyr-4tu57fg");
        CodeType postcodeCode = getCodeType("Post code 1", "5ryit6-5tj47e-45jfyr-4tu57fg-rt54tgr");
        TextType buildingName = getTextType("Test Building");
        TextType streetName = getTextType("Test Street");
        TextType cityName = getTextType("Test City");
        IDType countryID = getIdType("Test Country", "ryfht53-fht5-6htfur-57thft");
        TextType citySubDivisionName = getTextType("Test city subdivision");
        TextType countryName = getTextType("Test Country");
        TextType countrySubDivisionName = getTextType("Test country subdivision");
        TextType blockName = getTextType("Test Block");
        TextType plotIdentification = getTextType("123");
        TextType postOfficeBox = getTextType("548675");
        TextType buildingNumber = getTextType("12345");
        TextType staircaseNumber = getTextType("3456");
        TextType floorIdentification = getTextType("8888");
        TextType roomIdentification = getTextType("555");
        StructuredAddress structuredAddress = new StructuredAddress(id, postcodeCode, buildingName, streetName,
                cityName, countryID, citySubDivisionName, countryName, countrySubDivisionName, blockName, plotIdentification,
                postOfficeBox, buildingNumber, staircaseNumber, floorIdentification, roomIdentification);
        return structuredAddress;
    }

    public static ContactParty getContactParty() {
        ContactParty contactParty = new ContactParty();
        CodeType roleCode = getCodeType("Role Code 1", "4ryf65-ghtd74r-5yr64e-tuf64e");
        contactParty.setRoleCodes(Arrays.asList(roleCode));
        contactParty.setSpecifiedContactPersons(Arrays.asList(getContactPerson()));
        contactParty.setSpecifiedStructuredAddresses(Arrays.asList(getStructuredAddress()));
        return contactParty;
    }

    public static ContactPerson getContactPerson() {
        TextType title = getTextType("MR");
        TextType givenName = getTextType("Test Name");
        TextType middleName = getTextType("Test Middle Name");
        TextType familyNamePrefix = getTextType("Test Prefix");
        TextType familyName = getTextType("Test Family Name");
        TextType nameSuffix = getTextType("Test Suffix");
        CodeType genderCode = getCodeType("Gender", "4ryf65-fhtfyd-thfey45-tu5r7ght");
        TextType alias = getTextType("Test Alias");
        ContactPerson contactPerson = new ContactPerson(title, givenName, middleName, familyNamePrefix,
                familyName, nameSuffix, genderCode, alias, null, null, null, null);
        return contactPerson;
    }

    public static DelimitedPeriod getDelimitedPeriod() {
        DateTimeType startDate = getDateTimeType("2016-07-01 11:15:00");
        DateTimeType endDate = getDateTimeType("2016-07-01 11:15:00");
        MeasureType measureType = getMeasureType(500, "Measure Type 1", "4rhfy5-fhtydr-tyfr85-ghtyd54");
        DelimitedPeriod delimitedPeriod = new DelimitedPeriod(startDate, endDate, measureType);
        return delimitedPeriod;
    }

    public static RegistrationLocation getRegistrationLocation() {
        IDType countryID = getIdType("ID 1", "3tyfhr-57rgt75-thfyr75-684utfg");
        List<TextType> descriptions = Arrays.asList(getTextType("This is Test Text"));
        CodeType geopoliticalRegionCode = getCodeType("Region Code 1", "57tug6-tfu576-5tud75-t57e5td-56tdwe");
        List<IDType> ids = Arrays.asList(getIdType("ID 2", "fhtyr8-45jrf-5784fhrt-thf75"));
        List<TextType> names = Arrays.asList(getTextType("This is Test Name"));
        CodeType typeCode = getCodeType("Code type 1", "475rhf-587trhdy-thgy576-thfr64");
        RegistrationLocation registrationLocation = new RegistrationLocation(countryID, descriptions, geopoliticalRegionCode, ids, names, typeCode, null);
        return registrationLocation;
    }

    public static RegistrationEvent getRegistrationEvent() {
        List<TextType> descriptions = Arrays.asList(getTextType("This is test Text"));
        DateTimeType occurrenceDateTime = getDateTimeType("2016-07-01 11:15:00");
        RegistrationLocation relatedRegistrationLocation = getRegistrationLocation();
        RegistrationEvent registrationEvent = new RegistrationEvent(descriptions, occurrenceDateTime, relatedRegistrationLocation);
        return registrationEvent;
    }

    public static VesselTransportMeans getVesselTransportMeans() {
        VesselTransportMeans vesselTransportMeans = new VesselTransportMeans();
        CodeType roleCode = getCodeType("Role Code 1", "ryft4-ghty67-57thf-57tg5");
        List<TextType> names = Arrays.asList(getTextType("Test Name"));
        List<FLAPDocument> grantedFLAPDocuments = Arrays.asList(getFlapDocument());
        List<IDType> ids = Arrays.asList(getIdType("ID 1", "fhtg56-5thd75-thf93-thfrye"));
        List<ContactParty> specifiedContactParties = Arrays.asList(getContactParty());
        List<RegistrationEvent> specifiedRegistrationEvents = Arrays.asList(getRegistrationEvent());

        vesselTransportMeans.setRoleCode(roleCode);
        vesselTransportMeans.setNames(names);
        vesselTransportMeans.setGrantedFLAPDocuments(grantedFLAPDocuments);
        vesselTransportMeans.setIDS(ids);
        vesselTransportMeans.setSpecifiedContactParties(specifiedContactParties);
        vesselTransportMeans.setSpecifiedRegistrationEvents(specifiedRegistrationEvents);
        return vesselTransportMeans;
    }

    public static VesselStorageCharacteristic getVesselStorageCharacteristic() {
        VesselStorageCharacteristic vesselStorageCharacteristic = new VesselStorageCharacteristic();
        List<CodeType> typeCodes = Arrays.asList(getCodeType("Code 1", "57tyf-ghtyrf-ghtyru-ght756h"));
        IDType id = getIdType("ID 1", "687yu5-tught6-thfyr-5yt74e");
        vesselStorageCharacteristic.setID(id);
        vesselStorageCharacteristic.setTypeCodes(typeCodes);
        return vesselStorageCharacteristic;
    }

    public static SizeDistribution getSizeDistribution() {
        CodeType categoryCode = getCodeType("Catagory code 1", "57t3yf-ght43yrf-ght56yru-ght7565h");
        List<CodeType> classCodes = Arrays.asList(getCodeType("Class Type 1", "57tyf-ghtyrf-ghtyru-ght756h"));
        SizeDistribution sizeDistribution = new SizeDistribution(categoryCode, classCodes);
        return sizeDistribution;
    }

    public static GearCharacteristic getGearCharacteristics() {
        CodeType typeCode = getCodeType("Code 1", "57t3yf-ght43yrf-ght56yru-ght7565h");
        List<TextType> descriptions = Arrays.asList(getTextType("This is sample text"));
        MeasureType valueMeasure = getMeasureType(123, "Unit code1", "57t3yf-ght43yrf-ght56yru-ght7565h");
        DateTimeType valueDateTime = getDateTimeType("2016-07-01 11:15:00");
        IndicatorType valueIndicator = getIndicatorType(true, "Test value", "Test format");
        CodeType valueCode = getCodeType("Code type 1", "4fhry5-thfyr85-67thf-5htr84");
        TextType value = getTextType("This is sample Text");
        QuantityType valueQuantity = getQuantityType(123);
        GearCharacteristic gearCharacteristic = new GearCharacteristic(typeCode, descriptions, valueMeasure, valueDateTime, valueIndicator, valueCode, value, valueQuantity);
        return gearCharacteristic;
    }

    public static FLUXCharacteristic getFluxCharacteristics() {
        CodeType typeCode = getCodeType("Code 1", "57t3yf-ght43yrf-ght56yru-ght7565h");
        List<TextType> descriptions = Arrays.asList(getTextType("This is test description"));
        MeasureType valueMeasure = getMeasureType(333, "Measure type 1", "57t3yf-ght43yrf-ght56yru-ght7565h");
        DateTimeType valueDateTime = getDateTimeType("2016-07-01 11:15:00");
        IndicatorType valueIndicator = getIndicatorType(true, "Test value", "Test format");
        CodeType valueCode = getCodeType("Code Value 1", "57tr4t3yf-ght43yrf-ght56yr5u-ght75365h");
        List<TextType> values = Arrays.asList(getTextType("This is sample value"));
        QuantityType valueQuantity =getQuantityType(123);
        FLUXCharacteristic fluxCharacteristic = new FLUXCharacteristic(typeCode, descriptions, valueMeasure, valueDateTime, valueIndicator, valueCode, values, valueQuantity);
        return fluxCharacteristic;
    }

    public static FishingGear getFishingGear() {
        FishingGear fishingGear = new FishingGear();
        CodeType typeCode = getCodeType("Code Type 1", "57t3yf-ght43yrf-ght56yru-ght7565h");
        List<CodeType> roleCodes = Arrays.asList(getCodeType("Role Code 1", "57t3yf-g43yrf-ght56ru-ght65h"));
        fishingGear.setTypeCode(typeCode);
        fishingGear.setRoleCodes(roleCodes);
        fishingGear.setApplicableGearCharacteristics(Arrays.asList(getGearCharacteristics()));
        return fishingGear;
    }

    public static FishingTrip getFishingTrip() {
        List<IDType> ids = Arrays.asList(getIdType("ID 1", "fhty58-gh586t-5tjf8-t58rjewe"));
        CodeType typeCode = getCodeType("Code Type 1", "57t3yf-ght43yrf-ght56yru-ght7565h");
        List<DelimitedPeriod> specifiedDelimitedPeriods = Arrays.asList(getDelimitedPeriod());
        FishingTrip fishingTrip = new FishingTrip(ids, typeCode, specifiedDelimitedPeriods);
        return fishingTrip;
    }

    public static GearProblem getGearProblem() {
        CodeType typeCode = getCodeType("Code Type 1", "fhty58-gh586t-5tjf8-t58rjewe");
        QuantityType affectedQuantity = getQuantityType(222);
        List<CodeType> recoveryMeasureCodes = Arrays.asList(getCodeType("Quantity Code 1", "57t3yf-ght43yrf-ght56yru-ght7565h"));
        List<FishingGear> relatedFishingGears = Arrays.asList(getFishingGear());
        GearProblem gearProblem = new GearProblem(typeCode, affectedQuantity, recoveryMeasureCodes, null, relatedFishingGears);
        return gearProblem;
    }

    public static FLUXLocation getFluxLocation() {
        FLUXLocation fluxLocation = new FLUXLocation();
        CodeType typeCode = getCodeType("Code Type 1", "fhty58-gh586t-5tjf8-t58rjewe");
        IDType countryID = getIdType("ID 1", "fhtg56-5thd75-thf6t93-thfrye");
        CodeType regionalFisheriesManagementOrganizationCode = getCodeType("RFMO1", "fhty58-gh586t-5tjf8-t58rjewe");
        FLUXGeographicalCoordinate specifiedPhysicalFLUXGeographicalCoordinate = getFluxGeographicalCoordinate();
        IDType id = getIdType("ID 2", "fhty58-gh586t-5tjf8-t58rjewe");
        CodeType geopoliticalRegionCode = getCodeType("Code type 2", "fhty258-g3h586t-5t4jf8-t58rjew5e");
        List<TextType> names = Arrays.asList(getTextType("This is sample name"));
        IDType sovereignRightsCountryID = getIdType("sovereign rights id 1", "fhty58-gh5486t-5t5jf8-t58rjewe");
        IDType jurisdictionCountryID = getIdType("jurisdiction country id 1", "fht1y58-gh5876t-5t3jf8-t58rjewe");
        List<FLUXCharacteristic> applicableFLUXCharacteristics = Arrays.asList(getFluxCharacteristics());
        List<StructuredAddress> postalStructuredAddresses = Arrays.asList(getStructuredAddress());
        StructuredAddress physicalStructuredAddress = getStructuredAddress();

        fluxLocation.setTypeCode(typeCode);
        fluxLocation.setCountryID(countryID);
        fluxLocation.setRegionalFisheriesManagementOrganizationCode(regionalFisheriesManagementOrganizationCode);
        fluxLocation.setSpecifiedPhysicalFLUXGeographicalCoordinate(specifiedPhysicalFLUXGeographicalCoordinate);
        fluxLocation.setID(id);
        fluxLocation.setGeopoliticalRegionCode(geopoliticalRegionCode);
        fluxLocation.setNames(names);
        fluxLocation.setSovereignRightsCountryID(sovereignRightsCountryID);
        fluxLocation.setJurisdictionCountryID(jurisdictionCountryID);
        fluxLocation.setApplicableFLUXCharacteristics(applicableFLUXCharacteristics);
        fluxLocation.setPostalStructuredAddresses(postalStructuredAddresses);
        fluxLocation.setPhysicalStructuredAddress(physicalStructuredAddress);
        return fluxLocation;
    }

    public static FLUXReportDocument getFluxReportDocument() {
        List<IDType> ids = Arrays.asList(getIdType("ID 1", "fhty58-gh586t-5tjf8-t58rjewe"));
        IDType referencedID = getIdType("Ref ID 1", "fhty58-gh586t-5tjf8-t58rjewe");
        DateTimeType creationDateTime = getDateTimeType("2016-07-01 11:15:00");
        CodeType purposeCode = getCodeType("Purpose Code 1", "fhr596t-ght7u48-fjrudu5-tjfue8554");
        final TextType purpose = getTextType("This is Test Text");
        CodeType typeCode = getCodeType("Code Type 1", "fhty58-gh586t-5tjf8-t58rjewe");
        FLUXParty ownerFLUXParty = new FLUXParty(Arrays.asList(getIdType("Owner flux party id 1", "58fjrut-tjfuri-586jte-5jfur")),
                Arrays.asList(getTextType("This is sample text for owner flux party")));
        FLUXReportDocument fluxReportDocument = new FLUXReportDocument(ids, referencedID, creationDateTime, purposeCode, purpose, typeCode, ownerFLUXParty);
        return fluxReportDocument;
    }

    public static FACatch getFaCatch() {
        CodeType speciesCode = getCodeType("Species code 1", "47rfh-5hry4-thfur75-4hf743");
        QuantityType unitQuantity = getQuantityType(100);
        MeasureType weightMeasure = getMeasureType(123, "Measure code 1", "586jhg-5htuf95-5jfit-5jtier8");
        CodeType weighingMeansCode = getCodeType("Weighing means code 1", "5854tt5-gjtdir-5j85tui-589git");
        CodeType usageCode = getCodeType("Usage code 1", "58thft-58fjd8-gt85eje-hjgute8");
        CodeType typeCode = getCodeType("Type code 1", "57thre-fn48320-fn39fjr-tjfow84");
        final List<FishingTrip> relatedFishingTrips = Arrays.asList(getFishingTrip());
        SizeDistribution specifiedSizeDistribution = getSizeDistribution();
        List<AAPStock> relatedAAPStocks = Arrays.asList(getAapStock());
        List<AAPProcess> appliedAAPProcesses = Arrays.asList(getAapProcess());
        List<SalesBatch> relatedSalesBatches = null;
        List<FLUXLocation> specifiedFLUXLocations = Arrays.asList(getFluxLocation());
        List<FishingGear> usedFishingGears = Arrays.asList(getFishingGear());
        List<FLUXCharacteristic> applicableFLUXCharacteristics = Arrays.asList(getFluxCharacteristics());
        List<FLUXLocation> destinationFLUXLocations = Arrays.asList(getFluxLocation());
        FACatch faCatch = new FACatch(speciesCode, unitQuantity, weightMeasure, weighingMeansCode, usageCode,
                typeCode, relatedFishingTrips, specifiedSizeDistribution, relatedAAPStocks, appliedAAPProcesses, relatedSalesBatches,
                specifiedFLUXLocations, usedFishingGears, applicableFLUXCharacteristics, destinationFLUXLocations);
        return faCatch;
    }

    public static FishingActivity getFishingActivity() {
        FishingActivity fishingActivity = getStandardFishingActivity();
        List<FishingActivity> relatedFishingActivities = Arrays.asList(getStandardFishingActivity());
        fishingActivity.setRelatedFishingActivities(relatedFishingActivities);
        return fishingActivity;
    }

    public static FAReportDocument getFaReportDocument() {
        CodeType typeCode = getCodeType("Code Type 1", "fhr574fh-thrud754-kgitjf754-gjtufe89");
        CodeType fmcMarkerCode = getCodeType("Fmz marker 1", "h49rh-fhrus33-fj84hjs82-4h84hw82");
        List<IDType> relatedReportIDs = Arrays.asList(getIdType("ID 1", "47rfh-5hry4-thfur75-4hf743"));
        DateTimeType acceptanceDateTime = getDateTimeType("2016-07-01 11:15:00");
        FLUXReportDocument relatedFLUXReportDocument = getFluxReportDocument();
        List<FishingActivity> specifiedFishingActivities = Arrays.asList(getFishingActivity());
        VesselTransportMeans specifiedVesselTransportMeans = getVesselTransportMeans();
        FAReportDocument faReportDocument = new FAReportDocument(typeCode, fmcMarkerCode, relatedReportIDs, acceptanceDateTime,
                relatedFLUXReportDocument, specifiedFishingActivities, specifiedVesselTransportMeans);
        return faReportDocument;
    }

    private static FishingActivity getStandardFishingActivity() {
        List<IDType> ids = Arrays.asList(getIdType("Id 1", "fhr574fh-thrud754-kgitjf754-gjtufe89"));
        CodeType typeCode = getCodeType("Code Type 1", "4hryf0-t58thf-6jtue8-6jtie84");
        DateTimeType occurrenceDateTime = getDateTimeType("2016-07-01 11:15:00");
        CodeType reasonCode = getCodeType("Reason code 1", "h49rh-fhrus33-fj84hjs82-4h84hw82");
        CodeType vesselRelatedActivityCode = getCodeType("Vessel activity 1", "58thft-58fjd8-gt85eje-hjgute8");
        CodeType fisheryTypeCode = getCodeType("Fishing Type code 1", "57thre-fn48320-fn39fjr-tjfow84");
        CodeType speciesTargetCode = getCodeType("Species code 1", "47rfh-5hry4-thfur75-4hf743");
        QuantityType operationsQuantity = getQuantityType(100);
        MeasureType fishingDurationMeasure = getMeasureType(500, "duration 1", "4hr2yf0-t583thf-6jgttue8-6jtie844");
        FLAPDocument specifiedFLAPDocument = getFlapDocument();
        VesselStorageCharacteristic sourceVesselStorageCharacteristic = getVesselStorageCharacteristic();
        VesselStorageCharacteristic destinationVesselStorageCharacteristic = getVesselStorageCharacteristic();
        List<FACatch> specifiedFACatches = Arrays.asList(getFaCatch());
        List<FLUXLocation> relatedFLUXLocations = Arrays.asList(getFluxLocation());
        List<GearProblem> specifiedGearProblems = Arrays.asList(getGearProblem());
        List<FLUXCharacteristic> specifiedFLUXCharacteristics = Arrays.asList(getFluxCharacteristics());
        List<FishingGear> specifiedFishingGears = Arrays.asList(getFishingGear());
        List<DelimitedPeriod> specifiedDelimitedPeriods = Arrays.asList(getDelimitedPeriod());
        FishingTrip specifiedFishingTrip = getFishingTrip();
        VesselTransportMeans relatedVesselTransportMeans = getVesselTransportMeans();
        FishingActivity fishingActivity = new FishingActivity(ids, typeCode, occurrenceDateTime, reasonCode, vesselRelatedActivityCode, fisheryTypeCode,
                speciesTargetCode, operationsQuantity, fishingDurationMeasure, specifiedFACatches, relatedFLUXLocations, specifiedGearProblems,
                specifiedFLUXCharacteristics, specifiedFishingGears, sourceVesselStorageCharacteristic, destinationVesselStorageCharacteristic,
                null, specifiedFLAPDocument, specifiedDelimitedPeriods, specifiedFishingTrip, relatedVesselTransportMeans);
        return fishingActivity;
    }

    private static FLUXGeographicalCoordinate getFluxGeographicalCoordinate() {
        return new FLUXGeographicalCoordinate(
                getMeasureType(123, "longitude", "fhty58-gh586t-5tjf8-t58rjewe"),
                getMeasureType(234, "latitude", "fhty258-g3h586t-5t4jf8-t58rjew5e"),
                getMeasureType(345, "altitude", "fhty58-gh5686t-5tjf8-t58rjew8e"),
                getIdType("System ID1", "fhty58-gh58666t-5tjf438-t58rjewe")
        );
    }

    private static IndicatorType getIndicatorType(Boolean indicator, String value, String format) {
        IndicatorType indicatorType = new IndicatorType();
        indicatorType.setIndicator(indicator);
        indicatorType.setIndicatorString(new IndicatorType.IndicatorString(value, format));
        return indicatorType;
    }

    private static FLAPDocument getFlapDocument() {
        FLAPDocument flapDocument = new FLAPDocument();
        IDType id = getIdType("ID 1", "fhtg56-5thd75-thf6t93-thfrye");
        flapDocument.setID(id);
        return flapDocument;
    }

    private static DateTimeType getDateTimeType(String value) {
        try {
            DateTimeType dateTimeType = new DateTimeType();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(df.parse(value));
            XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().
                    newXMLGregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED, cal.getTimeZone().LONG).normalize();
            dateTimeType.setDateTime(xmlDate);
            return dateTimeType;
        } catch (Exception e) {
            return null;
        }
    }

    private static TextType getTextType(String value) {
        TextType textType = new TextType();
        textType.setValue(value);
        return textType;
    }

    public static IDType getIdType(String type, String schemeId) {
        IDType idType = new IDType();
        idType.setValue(type);
        idType.setSchemeID(schemeId);
        return idType;
    }

    private static SizeDistribution getSizeDistribution(CodeType catagoryCode, CodeType classCode) {
        SizeDistribution sizeDistribution = new SizeDistribution();
        sizeDistribution.setCategoryCode(catagoryCode);
        sizeDistribution.setClassCodes(Arrays.asList(classCode));
        return sizeDistribution;
    }
    private static SalesPrice getSalesPrice(AmountType amountType) {
        SalesPrice salesPrice = new SalesPrice();
        salesPrice.setChargeAmounts(Arrays.asList(amountType));
        return salesPrice;
    }

    private static AmountType getAmountType(int value, String listId, String currencyId) {
        AmountType amountType = new AmountType();
        amountType.setValue(new BigDecimal(value));
        amountType.setCurrencyCodeListVersionID(listId);
        amountType.setCurrencyID(currencyId);
        return amountType;
    }

    public static CodeType getCodeType(String value, String listId) {
        CodeType codeType = new CodeType();
        codeType.setValue(value);
        codeType.setListID(listId);
        return codeType;
    }

    private static NumericType getNumericType(int value) {
        NumericType numericType = new NumericType();
        numericType.setValue(new BigDecimal(value));
        return numericType;
    }

    private static QuantityType getQuantityType(int value) {
        QuantityType quantityType = new QuantityType();
        quantityType.setValue(new BigDecimal(value));
        return quantityType;
    }

    private static MeasureType getMeasureType(int value, String unitCode, String listId) {
        MeasureType measureType = new MeasureType();
        measureType.setValue(new BigDecimal(value));
        measureType.setUnitCode(unitCode);
        measureType.setUnitCodeListVersionID(listId);
        return measureType;
    }
}

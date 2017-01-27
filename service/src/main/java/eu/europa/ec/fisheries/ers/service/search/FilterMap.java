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

package eu.europa.ec.fisheries.ers.service.search;

import eu.europa.ec.fisheries.uvms.activity.model.schemas.SearchFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sanera on 12/07/2016.
 *
 * Filter Fishing Activity Reports functionality require Query to be generated Dynamically based on Filters provided.
 * This class provides mapping information to Query Builder.
 */
public class FilterMap {

    public static final String OWNER_ID                     = "ownerId";
    public static final String FROM_ID                      = "fromId";
    public static final String OCCURENCE_START_DATE         = "startDate";
    public static final String OCCURENCE_END_DATE           = "endDate";
    public static final String VESSEL_IDENTITY_NAME         = "vtName";
    public static final String VESSEL_IDENTIFIRE            = "vtSchemeId";
    public static final String VTM_GUIDS                    = "guids";
    public static final String PURPOSE_CODE                 = "purposeCode";
    public static final String REPORT_TYPE_CODE             = "faReportTypeCode";
    public static final String ACTIVITY_TYPE_CODE           = "activityTypecode";
    public static final String AREA_ID                      = "fluxAreaId";
    public static final String PORT_ID                      = "fluxPortId";
    public static final String FISHING_GEAR                 = "fishingGearType";
    public static final String SPECIES_CODE                 = "speciesCode";
    public static final String QUANTITY_MIN                 = "minWeight";
    public static final String QUANTITY_MAX                 = "maxWeight";
    public static final String CONTACT_PERSON_NAME          = "agent";

    public static final String VESSEL_TRANSPORT_TABLE_ALIAS = "fa.vesselTransportMeans vt";
    public static final String FA_CATCH_TABLE_ALIAS         = " a.faCatchs faCatch ";
    public static String DELIMITED_PERIOD_TABLE_ALIAS       = " a.delimitedPeriods dp ";
    public static final String FLUX_REPORT_DOC_TABLE_ALIAS  = " fa.fluxReportDocument flux ";
    public static final String FLUX_PARTY_TABLE_ALIAS       = " flux.fluxParty fp ";
    public static final String MASTER_MAPPING               = " vt.contactParty cparty JOIN FETCH cparty.contactPerson cPerson ";
    public static final String DATASOURCE                   = "dataSource";
    public static final String FAREPORT_ID                  = "faReportId";
    public static final String AREA_GEOM                    = "areaGeom";

    public static final String FLUX_REP_MESSAGE_FROM_FA_REP = "fa.fluxFaReportMessage fluxMsg ";
    public static final String FLUX_REP_DOC_FROM_MESSAGE    = "fluxMsg.fluxReportDocument fluxRepDoc ";
    public static final String FLUX_PARTY_FOR_MESSAGE       = "fluxRepDoc.fluxParty fpFrom ";

    // This contains Table Join and Where condition mapping for each Filter
    private static EnumMap<SearchFilter, FilterDetails> filterMappings        = new EnumMap<>(SearchFilter.class);
    // For Sort criteria, which expression should be used
    private static EnumMap<SearchFilter, String> filterSortMappings           = new EnumMap<>(SearchFilter.class);
    // Query parameter mapping
    private static EnumMap<SearchFilter, String> filterQueryParameterMappings = new EnumMap<>(SearchFilter.class);
    // Special case for star and end date sorting
    private static EnumMap<SearchFilter, String> filterSortWhereMappings      = new EnumMap<>(SearchFilter.class);
    // List of filters which support multiple values
    private static Set<SearchFilter> filtersWhichSupportMultipleValues        = new HashSet<>();

    private FilterMap() {
        super();
    }

    static {
        populateFiltersWhichSupportMultipleValues();
        populateFilterMappings();
        populateFilterQueryParameterMappings();
        populateFilterSortMappings();
        populateFilterSortWhereMappings();
    }


    /**
     * Below method stores mapping for each Filter criteria. Mapping will provide information on table joins
     * required for the criteria and Where conditions which needs to be applied for the criteria
     */
    public static void populateFilterMappings() {
        filterMappings.put(SearchFilter.SOURCE, new FilterDetails(StringUtils.SPACE, "fa.source =:" + DATASOURCE));
        filterMappings.put(SearchFilter.OWNER, new FilterDetails(" fp.fluxPartyIdentifiers fpi", "fpi.fluxPartyIdentifierId =:" + OWNER_ID + StringUtils.SPACE));
        filterMappings.put(SearchFilter.FROM,  new FilterDetails(" fpFrom.fluxPartyIdentifiers fpiFrom", "fpiFrom.fluxPartyIdentifierId =:" + FROM_ID + StringUtils.SPACE));
        filterMappings.put(SearchFilter.PERIOD_START, new FilterDetails(DELIMITED_PERIOD_TABLE_ALIAS, "( dp.startDate >= :" + OCCURENCE_START_DATE + "  OR a.occurence  >= :" + OCCURENCE_START_DATE + " )"));
        filterMappings.put(SearchFilter.PERIOD_END, new FilterDetails(DELIMITED_PERIOD_TABLE_ALIAS, " dp.endDate <= :" + OCCURENCE_END_DATE));
        filterMappings.put(SearchFilter.VESSEL_NAME, new FilterDetails("fa.vesselTransportMeans vt", "vt.name IN (:" + VESSEL_IDENTITY_NAME + ")"));
        filterMappings.put(SearchFilter.VESSEL_IDENTIFIRE, new FilterDetails("vt.vesselIdentifiers vi", "vi.vesselIdentifierId IN (:" + VESSEL_IDENTIFIRE + ")"));
        filterMappings.put(SearchFilter.VESSEL_GUIDS, new FilterDetails("fa.vesselTransportMeans vtMeans", "vtMeans.guid IN (:" + VTM_GUIDS + ")"));
        filterMappings.put(SearchFilter.PURPOSE, new FilterDetails(FLUX_REPORT_DOC_TABLE_ALIAS, "flux.purposeCode IN (:" + PURPOSE_CODE + ")"));
        filterMappings.put(SearchFilter.REPORT_TYPE, new FilterDetails(StringUtils.SPACE, "fa.typeCode IN (:" + REPORT_TYPE_CODE + ")"));
        filterMappings.put(SearchFilter.ACTIVITY_TYPE, new FilterDetails(StringUtils.SPACE, "a.typeCode IN (:" + ACTIVITY_TYPE_CODE + ")"));
        filterMappings.put(SearchFilter.AREAS, new FilterDetails("a.fluxLocations fluxLoc", "( fluxLoc.typeCode IN ('AREA') and fluxLoc.fluxLocationIdentifier =:" + AREA_ID + " )"));
        filterMappings.put(SearchFilter.PORT, new FilterDetails("a.fluxLocations fluxLoc", "( fluxLoc.typeCode IN ('LOCATION') and fluxLoc.fluxLocationIdentifier IN (:" + PORT_ID + " ))"));
        filterMappings.put(SearchFilter.GEAR, new FilterDetails("a.fishingGears fg", "fg.typeCode IN (:" + FISHING_GEAR + ")"));
        filterMappings.put(SearchFilter.SPECIES, new FilterDetails(FA_CATCH_TABLE_ALIAS + " LEFT JOIN FETCH faCatch.aapProcesses aprocess LEFT JOIN FETCH aprocess.aapProducts aprod ", "( faCatch.speciesCode IN (:" + SPECIES_CODE + ") " + " OR aprod.speciesCode IN (:" + SPECIES_CODE + "))"));
        filterMappings.put(SearchFilter.QUANTITY_MIN, new FilterDetails(FA_CATCH_TABLE_ALIAS + " LEFT JOIN FETCH faCatch.aapProcesses aprocess LEFT JOIN FETCH aprocess.aapProducts aprod ", " (faCatch.calculatedWeightMeasure  BETWEEN :" + QUANTITY_MIN));
        filterMappings.put(SearchFilter.QUANTITY_MAX, new FilterDetails(StringUtils.SPACE, "  :" + QUANTITY_MAX + ") "));
        filterMappings.put(SearchFilter.MASTER, new FilterDetails(" fa.vesselTransportMeans vt JOIN FETCH vt.contactParty cparty JOIN FETCH cparty.contactPerson cPerson", "(UPPER(cPerson.title) IN (:" + CONTACT_PERSON_NAME + ") " + " or " +
                "UPPER(cPerson.givenName) IN (:" + CONTACT_PERSON_NAME + ") " + " or UPPER(cPerson.middleName) IN (:" + CONTACT_PERSON_NAME + ") " + " or UPPER(cPerson.familyName) IN (:" + CONTACT_PERSON_NAME + ") " + StringUtils.SPACE +
                "or UPPER(cPerson.familyNamePrefix) IN (:" + CONTACT_PERSON_NAME + ") " + " or UPPER(cPerson.nameSuffix) IN (:" + CONTACT_PERSON_NAME + ") " + " or UPPER(cPerson.alias) IN (:" + CONTACT_PERSON_NAME + ") " + ")"));
        filterMappings.put(SearchFilter.FA_REPORT_ID, new FilterDetails(StringUtils.SPACE, "fa.id =:" + FAREPORT_ID));
        filterMappings.put(SearchFilter.AREA_GEOM, new FilterDetails(StringUtils.SPACE, "intersects(fa.geom, :" + AREA_GEOM + ") = true "));

    }

    /**
     * For Sort by start date and End date, it needs special treatment. We need to use subQuery to make sure We pick up
     * only first Start or End date from the list of dates.
     * Below method helps that special case.
     */
    private static void populateFilterSortWhereMappings() {
        filterSortWhereMappings.put(SearchFilter.PERIOD_START, "dp1.startDate");
        filterSortWhereMappings.put(SearchFilter.PERIOD_END, "dp1.endDate");
    }


    /**
     * Below method provides mapping which should be used in order by clause.
     * This will achieve sorting for the criteria.
     */
    private static void populateFilterSortMappings() {
        filterSortMappings.put(SearchFilter.PERIOD_START,  "dp.startDate");
        filterSortMappings.put(SearchFilter.PERIOD_END,    "dp.endDate");
        filterSortMappings.put(SearchFilter.REPORT_TYPE,   "fa.typeCode");
        filterSortMappings.put(SearchFilter.SOURCE,        "fa.source");
        filterSortMappings.put(SearchFilter.ACTIVITY_TYPE, "a.typeCode");
        filterSortMappings.put(SearchFilter.OCCURRENCE,    "a.occurence");
        filterSortMappings.put(SearchFilter.PURPOSE,       "flux.purposeCode");
    }


    /**
     * To put values in Query, Query Builder needs to know name used in query to be mapped to value.
     * Put that mapping here
     */
    private static void populateFilterQueryParameterMappings() {
        filterQueryParameterMappings.put(SearchFilter.SOURCE, DATASOURCE);
        filterQueryParameterMappings.put(SearchFilter.VESSEL_GUIDS, VTM_GUIDS);
        filterQueryParameterMappings.put(SearchFilter.OWNER, OWNER_ID);
        filterQueryParameterMappings.put(SearchFilter.FROM, FROM_ID);
        filterQueryParameterMappings.put(SearchFilter.PERIOD_START, OCCURENCE_START_DATE);
        filterQueryParameterMappings.put(SearchFilter.PERIOD_END, OCCURENCE_END_DATE);
        filterQueryParameterMappings.put(SearchFilter.VESSEL_NAME, VESSEL_IDENTITY_NAME);
        filterQueryParameterMappings.put(SearchFilter.VESSEL_IDENTIFIRE, VESSEL_IDENTIFIRE);
        filterQueryParameterMappings.put(SearchFilter.PURPOSE, PURPOSE_CODE);
        filterQueryParameterMappings.put(SearchFilter.REPORT_TYPE, REPORT_TYPE_CODE);
        filterQueryParameterMappings.put(SearchFilter.ACTIVITY_TYPE, ACTIVITY_TYPE_CODE);
        filterQueryParameterMappings.put(SearchFilter.AREAS, AREA_ID);
        filterQueryParameterMappings.put(SearchFilter.PORT, PORT_ID);
        filterQueryParameterMappings.put(SearchFilter.GEAR, FISHING_GEAR);
        filterQueryParameterMappings.put(SearchFilter.SPECIES, SPECIES_CODE);
        filterQueryParameterMappings.put(SearchFilter.QUANTITY_MIN, QUANTITY_MIN);
        filterQueryParameterMappings.put(SearchFilter.QUANTITY_MAX, QUANTITY_MAX);
        filterQueryParameterMappings.put(SearchFilter.MASTER, CONTACT_PERSON_NAME);
        filterQueryParameterMappings.put(SearchFilter.FA_REPORT_ID, FAREPORT_ID);
        filterQueryParameterMappings.put(SearchFilter.AREA_GEOM, AREA_GEOM);
    }

    private static void populateFiltersWhichSupportMultipleValues() {
        filtersWhichSupportMultipleValues.add(SearchFilter.VESSEL_NAME);
        filtersWhichSupportMultipleValues.add(SearchFilter.VESSEL_IDENTIFIRE);
        filtersWhichSupportMultipleValues.add(SearchFilter.REPORT_TYPE);
        filtersWhichSupportMultipleValues.add(SearchFilter.ACTIVITY_TYPE);
        filtersWhichSupportMultipleValues.add(SearchFilter.PORT);
        filtersWhichSupportMultipleValues.add(SearchFilter.GEAR);
        filtersWhichSupportMultipleValues.add(SearchFilter.SPECIES);
        filtersWhichSupportMultipleValues.add(SearchFilter.MASTER);
        filtersWhichSupportMultipleValues.add(SearchFilter.PURPOSE);
    }


    public static Map<SearchFilter, FilterDetails> getFilterMappings() {
        return filterMappings;
    }
    public static Map<SearchFilter, String> getFilterSortMappings() {
        return filterSortMappings;
    }
    public static Map<SearchFilter, String> getFilterSortWhereMappings() {
        return filterSortWhereMappings;
    }
    public static Map<SearchFilter, String> getFilterQueryParameterMappings() {
        return filterQueryParameterMappings;
    }
    public static Set<SearchFilter> getFiltersWhichSupportMultipleValues() {
        return filtersWhichSupportMultipleValues;
    }

}

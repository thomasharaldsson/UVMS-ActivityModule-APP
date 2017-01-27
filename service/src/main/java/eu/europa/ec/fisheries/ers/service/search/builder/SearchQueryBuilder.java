/*
 *
 * Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries European Union, 2015-2016.
 *
 * This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package eu.europa.ec.fisheries.ers.service.search.builder;

import eu.europa.ec.fisheries.ers.fa.utils.GeometryUtils;
import eu.europa.ec.fisheries.ers.fa.utils.WeightConversion;
import eu.europa.ec.fisheries.ers.service.search.FilterDetails;
import eu.europa.ec.fisheries.ers.service.search.FilterMap;
import eu.europa.ec.fisheries.ers.service.search.FishingActivityQuery;
import eu.europa.ec.fisheries.ers.service.search.SortKey;
import eu.europa.ec.fisheries.uvms.activity.model.schemas.SearchFilter;
import eu.europa.ec.fisheries.uvms.common.DateUtils;
import eu.europa.ec.fisheries.uvms.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.*;

/**
 * Created by sanera on 28/09/2016.
 */
public abstract class SearchQueryBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(SearchQueryBuilder.class);
    private static final String JOIN_FETCH = " JOIN FETCH ";
    private static final String LEFT = " LEFT ";
    private static final String JOIN =  " JOIN ";
    private  static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static Map<SearchFilter,String> mappings =  FilterMap.getFilterQueryParameterMappings();


    /**
     * Create SQL dynamically based on Filter criteria
     *
     * @param query
     * @return
     * @throws ServiceException
     */
    public  abstract StringBuilder createSQL(FishingActivityQuery query) throws ServiceException ;

    /**
     * Create Table Joins based on Filters provided by user. Avoid joining unnecessary tables
     *
     * @param sql
     * @param query
     * @return
     */
    public  StringBuilder createJoinTablesPartForQuery(StringBuilder sql, FishingActivityQuery query) {
        LOG.debug("Create Join Tables part of Query");
        Map<SearchFilter, FilterDetails> mappings = FilterMap.getFilterMappings();
        Set<SearchFilter> keySet = new HashSet<>();
        if (query.getSearchCriteriaMap() != null && !query.getSearchCriteriaMap().isEmpty()) {
            keySet.addAll(query.getSearchCriteriaMap().keySet());
        }
        if(query.getSearchCriteriaMapMultipleValues() !=null && !query.getSearchCriteriaMapMultipleValues().isEmpty()) {
            keySet.addAll(query.getSearchCriteriaMapMultipleValues().keySet());
        }
            for (SearchFilter key : keySet) {
                FilterDetails details = mappings.get(key);
                String joinString = null;
                if (details != null) {
                    joinString = details.getJoinString();
                }
                if (joinString == null || sql.indexOf(joinString) != -1) {// If the Table join for the Filter is already present in SQL, do not join the table again
                    continue;
                }
                completeQueryDependingOnKey(sql, key, joinString);
            }
        getJoinPartForSortingOptions(sql, query);

        LOG.debug("Generated SQL for JOIN Part :" + sql);
        return sql;
    }

    private  void completeQueryDependingOnKey(StringBuilder sql, SearchFilter key, String joinString) {
        switch (key) {
            case MASTER:
                if (sql.indexOf(FilterMap.VESSEL_TRANSPORT_TABLE_ALIAS) != -1) {  // If vesssel table is already joined, use join string accordingly
                    joinString = FilterMap.MASTER_MAPPING;
                }
                appendJoinString(sql, joinString);
                break;
            case VESSEL_IDENTIFIRE:
                tryAppendIfConditionDoesntExist(sql, FilterMap.VESSEL_TRANSPORT_TABLE_ALIAS);
                appendOnlyJoinString(sql, joinString);
                break;
            case FROM_ID:
                tryAppendIfConditionDoesntExist(sql, FilterMap.FLUX_REPORT_DOC_TABLE_ALIAS);
                tryAppendIfConditionDoesntExist(sql, FilterMap.FLUX_PARTY_TABLE_ALIAS); // Add missing join for required table
                appendJoinString(sql, joinString);
                break;
            default:
                appendJoinString(sql, joinString);
                break;
        }
    }

    protected void appendOnlyJoinString(StringBuilder sql, String joinString) {
        sql.append(JOIN).append(joinString).append(StringUtils.SPACE);
    }

    protected  void appendJoinString(StringBuilder sql, String joinString) {
        sql.append(JOIN_FETCH).append(joinString).append(StringUtils.SPACE);
    }

    /**
     * Add missing join for required table if doesn't already exist in the query;
     *
     * @param sql
     * @param valueToFindAndApply
     */
    private  void tryAppendIfConditionDoesntExist(StringBuilder sql, String valueToFindAndApply) {
        if (sql.indexOf(valueToFindAndApply) == -1) { // Add missing join for required table
            sql.append(JOIN_FETCH).append(valueToFindAndApply);
        }
    }


    /**
     * This method makes sure that Table join is present for the Filter for which sorting has been requested.
     *
     * @param sql
     * @param query
     * @return
     */
    private  StringBuilder getJoinPartForSortingOptions(StringBuilder sql, FishingActivityQuery query) {
        SortKey sort = query.getSorting();
        // IF sorting has been requested and
        if (sort == null) {
            return sql;
        }

        SearchFilter field = sort.getSortBy();

        if(field == null) {
            return sql;
        }

        // Make sure that the field which we want to sort, table Join is present for it.
        switch (getFiledCase(sql, field)) {
            case 1:
                appendLeftJoin(sql, FilterMap.DELIMITED_PERIOD_TABLE_ALIAS);
                break;
            case 2:
                appendLeftJoin(sql, FilterMap.FLUX_REPORT_DOC_TABLE_ALIAS);
                break;
            case 3:
                if (sql.indexOf(FilterMap.FLUX_REPORT_DOC_TABLE_ALIAS) == -1) {
                    appendLeftJoin(sql, FilterMap.FLUX_REPORT_DOC_TABLE_ALIAS);
                }
                if (sql.indexOf(FilterMap.FLUX_PARTY_TABLE_ALIAS) == -1) {
                    appendLeftJoin(sql, FilterMap.FLUX_PARTY_TABLE_ALIAS);
                }
                break;
            default:
                break;
        }

        return sql;
    }

    private  int getFiledCase(StringBuilder sql, SearchFilter field) {
        if (SearchFilter.PERIOD_START.equals(field) || SearchFilter.PERIOD_END.equals(field) && sql.indexOf(FilterMap.DELIMITED_PERIOD_TABLE_ALIAS) == -1) {
            return 1;
        } else if (SearchFilter.PURPOSE.equals(field) && sql.indexOf(FilterMap.FLUX_REPORT_DOC_TABLE_ALIAS) == -1) {
            return 2;
        }
        return 0;
    }

    private  void appendLeftJoin(StringBuilder sql, String delimitedPeriodTableAlias) {
        sql.append(LEFT).append(JOIN_FETCH).append(delimitedPeriodTableAlias);
    }

    public StringBuilder createWherePartForQueryForFilters(StringBuilder sql,FishingActivityQuery query){
        Map<SearchFilter, FilterDetails> mappings = FilterMap.getFilterMappings();
        Set<SearchFilter> keySet = new HashSet<>();
        if(query.getSearchCriteriaMap() !=null && !query.getSearchCriteriaMap().isEmpty())
            keySet.addAll(query.getSearchCriteriaMap().keySet());
        if(query.getSearchCriteriaMapMultipleValues() !=null && !query.getSearchCriteriaMapMultipleValues().isEmpty())
            keySet.addAll(query.getSearchCriteriaMapMultipleValues().keySet());

            // Create Where part of SQL Query
            int i = 0;
            for (SearchFilter key : keySet) {
               // String filterMapping=

                if ( (SearchFilter.QUNTITY_MIN.equals(key) && keySet.contains(SearchFilter.QUNTITY_MAX)) ||
                        (mappings.get(key) == null )) { // skip this as MIN and MAX both are required to form where part. Treat it differently
                    continue;
                }
                String mapping = mappings.get(key).getCondition();
                if (i != 0) {
                    sql.append(" and ");
                }

                if(SearchFilter.QUNTITY_MIN.equals(key) ){
                    sql.append("((faCatch.calculatedWeightMeasure >= :").append(FilterMap.QUNTITY_MIN).append(" OR aprod.calculatedWeightMeasure >= :").append(FilterMap.QUNTITY_MIN).append(" ))") ;

                }else if (SearchFilter.QUNTITY_MAX.equals(key)) {
                    sql.append(" ( ");
                    sql.append(mappings.get(SearchFilter.QUNTITY_MIN).getCondition()).append(" and ").append(mapping);
                    sql.append(" OR (aprod.calculatedWeightMeasure  BETWEEN :").append(FilterMap.QUNTITY_MIN).append(" and :").append(FilterMap.QUNTITY_MAX + ")");
                    sql.append(" ) ");
                } else {
                    sql.append(mapping);
                }
                i++;
            }
        return sql;
    }

    /**
     * Build Where part of the query based on Filter criterias
     * @param sql
     * @param query
     * @return
     */
    public abstract StringBuilder createWherePartForQuery(StringBuilder sql, FishingActivityQuery query) ;


    /**
     * Create sorting part for the Query
     * @param sql
     * @param query
     * @return
     * @throws ServiceException
     */
    public StringBuilder createSortPartForQuery(StringBuilder sql, FishingActivityQuery query) throws ServiceException {
        LOG.debug("Create Sorting part of Query");
        SortKey sort = query.getSorting();
        if (sort != null && sort.getSortBy() !=null) {
            SearchFilter field = sort.getSortBy();
            if (SearchFilter.PERIOD_START.equals(field) || SearchFilter.PERIOD_END.equals(field)) {
                getSqlForStartAndEndDateSorting(sql, field, query);
            }
            String orderby =" ASC ";
            if(sort.isReversed()) {
                orderby = " DESC ";
            }
            String sortFieldMapping = FilterMap.getFilterSortMappings().get(field);
            if(sortFieldMapping ==null) {
                throw new ServiceException("Information about which database field to be used for sorting is unavailable");
            }
            sql.append(" order by ").append( sortFieldMapping).append(orderby);
        } else {
            sql.append(" order by fa.acceptedDatetime ASC ");
        }
        LOG.debug("Generated Query After Sort :" + sql);
        return sql;
    }

    /**
     * Special treatment for date sorting . In the resultset, One record can have multiple dates. But We need to consider only one date from the list. and then sort that selected date across resultset
     *
     * @param sql
     * @param filter
     * @param query
     * @return
     */
    public  StringBuilder getSqlForStartAndEndDateSorting(StringBuilder sql, SearchFilter filter, FishingActivityQuery query) {
        Map<SearchFilter, String> searchCriteriaMap = query.getSearchCriteriaMap();
        if(searchCriteriaMap == null){
            return sql;
        }
        sql.append(" and(  ");
        sql.append(FilterMap.getFilterSortMappings().get(filter));
        sql.append(" =(select max(").append(FilterMap.getFilterSortWhereMappings().get(filter)).append(") from a.delimitedPeriods dp1  ");

        if (searchCriteriaMap.containsKey(filter)) {
            sql.append(" where ");
            sql.append(" ( dp1.startDate >= :startDate  OR a.occurence  >= :startDate ) ");
            if (searchCriteriaMap.containsKey(SearchFilter.PERIOD_END)) {
                sql.append(" and  dp1.endDate <= :endDate ");
            }
        }
        sql.append(" ) ");
        sql.append(" OR dp is null ) ");
        return sql;
    }

    // Assumption for the weight is, calculated_weight_measure is in Kg.
    // IF we get WEIGHT MEASURE as TON, we need to convert the input value to Kilograms.
    public static Double normalizeWeightValue(String value, String weightMeasure) {
        Double valueConverted = Double.parseDouble(value);
        if (WeightConversion.TON.equals(weightMeasure)) {
            valueConverted = WeightConversion.convertToKiloGram(Double.parseDouble(value), WeightConversion.TON);
        }
        return valueConverted;
    }

    public Query fillInValuesForTypedQuery(FishingActivityQuery query,Query typedQuery) throws ServiceException {

        Map<SearchFilter, String> searchCriteriaMap = query.getSearchCriteriaMap();
        Map<SearchFilter, List<String>> searchForMultipleValues = query.getSearchCriteriaMapMultipleValues();

        if (searchCriteriaMap != null && !searchCriteriaMap.isEmpty()) {
            typedQuery = applySingleValuesToQuery(searchCriteriaMap, typedQuery);
        }
        if(searchForMultipleValues!=null && !searchForMultipleValues.isEmpty()) {
            typedQuery = applyListValuesToQuery(searchForMultipleValues, typedQuery);
        }

        return typedQuery;

    }


    private Query applySingleValuesToQuery(Map<SearchFilter,String> searchCriteriaMap,Query typedQuery) throws ServiceException {

        // Assign values to created SQL Query
        for (Map.Entry<SearchFilter,String> entry : searchCriteriaMap.entrySet()){

            SearchFilter key =  entry.getKey();
            String value=  entry.getValue();


            //For WeightMeasure there is no mapping present, In that case
            if(mappings.get(key) ==null) {
                continue;
            }

            if(value ==null || value.isEmpty()) {
                throw new ServiceException("Value for filter " + key + " is null or empty");
            }

            switch (key) {
                case PERIOD_START:
                    typedQuery.setParameter(mappings.get(key), DateUtils.parseToUTCDate(value,FORMAT));
                    break;
                case PERIOD_END:
                    typedQuery.setParameter(mappings.get(key), DateUtils.parseToUTCDate(value,FORMAT));
                    break;
                case QUNTITY_MIN:
                    typedQuery.setParameter(mappings.get(key), SearchQueryBuilder.normalizeWeightValue(value,searchCriteriaMap.get(SearchFilter.WEIGHT_MEASURE)));
                    break;
                case QUNTITY_MAX:
                    typedQuery.setParameter(mappings.get(key), SearchQueryBuilder.normalizeWeightValue(value,searchCriteriaMap.get(SearchFilter.WEIGHT_MEASURE)));
                    break;
                case MASTER:
                    typedQuery.setParameter(mappings.get(key), value.toUpperCase());
                    break;
                case FA_REPORT_ID:
                    typedQuery.setParameter(mappings.get(key), Integer.parseInt(value));
                    break;
                case AREA_GEOM:
                    try {
                        typedQuery.setParameter(mappings.get(key), GeometryUtils.wktToGeom(value));
                    } catch (ServiceException e) {
                        LOG.error("Error while trying to convert AREA_GEOM wkt To Geometry."+value);
                        throw new ServiceException("Error while trying to convert AREA_GEOM wkt To Geometry.",e);
                    }
                    break;
                default:
                    typedQuery.setParameter(mappings.get(key), value);
                    break;
            }

        }
        return typedQuery;
    }



    private Query applyListValuesToQuery(Map<SearchFilter,List<String>> searchCriteriaMap,Query typedQuery) throws ServiceException {

        // Assign values to created SQL Query
        for (Map.Entry<SearchFilter,List<String>> entry : searchCriteriaMap.entrySet()){

            SearchFilter key =  entry.getKey();
            List<String> valueList=  entry.getValue();
            //For WeightMeasure there is no mapping present, In that case
            if(mappings.get(key) ==null) {
                continue;
            }

            if(valueList ==null || valueList.isEmpty()) {
                throw new ServiceException("valueList for filter " + key + " is null or empty");
            }

            if(SearchFilter.MASTER.equals(key)){
                List<String> uppperCaseValList=new ArrayList<>();
                  for(String val:valueList){
                      uppperCaseValList.add(val.toUpperCase());
                  }
                typedQuery.setParameter(mappings.get(key), uppperCaseValList);
            }else
                 typedQuery.setParameter(mappings.get(key), valueList);

        }
        return typedQuery;
    }
}

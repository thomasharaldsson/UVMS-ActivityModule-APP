package eu.europa.ec.fisheries.uvms.activity.rest.resources;

import eu.europa.ec.fisheries.ers.service.bean.ActivityService;
import eu.europa.ec.fisheries.ers.service.search.FishingActivityQuery;
import eu.europa.ec.fisheries.uvms.activity.model.dto.FishingActivityReportDTO;
import eu.europa.ec.fisheries.uvms.exception.ServiceException;
import eu.europa.ec.fisheries.uvms.rest.resource.UnionVMSResource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by sanera on 28/06/2016.
 */
@Path("/activity")
@Slf4j
@Stateless
public class ActivityResource  extends UnionVMSResource {
    private final static Logger LOG = LoggerFactory.getLogger(ActivityResource.class);

    @Context
    private UriInfo context;

    @EJB
    private ActivityService activityService;



    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listByQuery")
    public Response listActivityReportsByQuery(FishingActivityQuery fishingActivityQuery) {

        LOG.info("Query Received to search Fishing Activity Reports. "+fishingActivityQuery);
        Response responseMethod;
        if(fishingActivityQuery==null)
            return  createErrorResponse("Query to find list is null.");

        List<FishingActivityReportDTO> dtoList= null;
        try {
            dtoList = activityService.getFishingActivityListByQuery(fishingActivityQuery);
            responseMethod = createSuccessResponse(dtoList);
            LOG.info("successful");
        } catch (ServiceException e) {
            LOG.error("Exception while trying to get Fishing Activity Report list.",e);
            responseMethod = createErrorResponse("Exception while trying to get Fishing Activity Report list.");
        }
        return responseMethod;
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listActivityReports() {

        LOG.info("listActivityReports ");
        List<FishingActivityReportDTO> dtoList=activityService.getFishingActivityList();
        Response responseMethod = createSuccessResponse(dtoList);
        LOG.info("successful");
        return responseMethod;
    }



}

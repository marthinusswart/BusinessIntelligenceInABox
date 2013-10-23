package com.intellibps.bib.rest;

import com.google.gson.Gson;
import com.intellibps.bib.jobs.QueuedJobManager;
import com.intellibps.bib.report.Report;
import com.intellibps.bib.security.SessionManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/07
 * Time: 7:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/queued_uploads")
public class QueuedJobService {

    private QueuedJobManager queuedJobManager = new QueuedJobManager();
    private java.util.logging.Logger logger = Logger.getLogger(SimpleDataService.class.getName());

    @GET
    @Path("process")
    public Response loadReports() throws IllegalAccessException
    {
        String result = "";

        try
        {
        queuedJobManager.processQueuedJobs();
        }
        catch (Exception ex)
        {
            logger.severe(ex.toString());
        }

        return Response.status(200).entity(result).build();

    }
}

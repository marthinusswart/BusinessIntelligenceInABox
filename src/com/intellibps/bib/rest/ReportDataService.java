package com.intellibps.bib.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellibps.bib.customer.Company;
import com.intellibps.bib.persistence.PersistenceController;
import com.intellibps.bib.report.Report;
import com.intellibps.bib.security.*;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.security.auth.login.CredentialException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/13
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
@Path("/data")
public class ReportDataService
{

    private SessionManager sessionManager = new SessionManager();
    private java.util.logging.Logger logger = Logger.getLogger(SimpleDataService.class.getName());

    @GET
    @Path("reports/{param}")
    public Response loadReports(@PathParam("param") String companyId, @QueryParam("sessionid") String sessionId) throws IllegalAccessException
    {
        String result = "";

        if (sessionManager.isLoggedIn(sessionId))
        {
            logger.info("Loading reports");
            List<Report> reports = loadReports(Long.parseLong(companyId));

            Gson gson = new Gson();
            result = gson.toJson(reports);
        } else
        {
            throw new IllegalAccessException("Not logged in");
        }

        return Response.status(200).entity(result).build();

    }

    @GET
    @Path("user_reports/{param}")
    public Response loadUserReports(@PathParam("param") String sessionId) throws IllegalAccessException
    {
        String result = "";

        if (sessionManager.isLoggedIn(sessionId))
        {
            logger.info("Loading reports");
            Credentials credentials = sessionManager.credentials(sessionId);
            List<Report> reports = loadUserReports(credentials.userId().getId());

            Gson gson = new Gson();
            result = gson.toJson(reports);
        } else
        {
            throw new IllegalAccessException("Not logged in");
        }

        return Response.status(200).entity(result).build();

    }

    private List<Report> loadReports(long companyId)
    {
        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        List<Report> reports = new ArrayList<Report>();
        Company company;

        try
        {
            logger.info("Company Id: " + companyId);
            company = (Company) persistenceManager.getObjectById(Company.class, companyId);

            Iterator<com.google.appengine.api.datastore.Key> reportKeys = company.reports().iterator();
            while (reportKeys.hasNext())
            {

                Report report = persistenceManager.getObjectById(Report.class, reportKeys.next().getId());
                logger.info("Adding report: " + report.name());
                reports.add(report);

                Iterator<com.google.appengine.api.datastore.Key> roles = report.roles().iterator();
                while (roles.hasNext())
                {

                    Role role = persistenceManager.getObjectById(Role.class, roles.next().getId());
                    logger.info("Adding role: " + role.name());
                    report.fullRoles().add(role);
                }
            }

            logger.info("Reports loaded " + reports.size());

        } finally
        {
            persistenceManager.close();
        }

        Iterator<Report> reportIterator = reports.iterator();
        // mark the retrieved reports as not new
        while (reportIterator.hasNext())
        {
            Report report = reportIterator.next();
            report.isNew(false);
            report.isDirty(false);
        }

        return reports;
    }

    private List<Report> loadUserReports(long userId)
    {
        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        PermissionManager permissionManager = new PermissionManager();
        List<Report> reports = new ArrayList<Report>();
        List<Report> companyReports = new ArrayList<Report>();
        User user;


        try
        {
            logger.info("User Id: " + userId);
            user = (User) persistenceManager.getObjectById(User.class, userId);

            Iterator<com.google.appengine.api.datastore.Key> roles  = user.roles().iterator();
            while (roles.hasNext())
            {

                Role role = persistenceManager.getObjectById(Role.class, roles.next().getId());
                logger.info("Adding user role: " + role.name());
                user.fullRoles().add(role);
            }

            companyReports = loadReports(user.company().getId());

            for (int i=0; i<companyReports.size();i++)
            {
                Report report = companyReports.get(i);
                if (permissionManager.canUserAccessReport(user, report)  )
                {
                reports.add(report);
                }
            }

            logger.info("Reports loaded " + reports.size());

        } finally
        {
            persistenceManager.close();
        }

        Iterator<Report> reportIterator = reports.iterator();
        // mark the retrieved reports as not new
        while (reportIterator.hasNext())
        {
            Report report = reportIterator.next();
            report.isNew(false);
            report.isDirty(false);
        }

        return reports;
    }


    @PUT
    @Path("reports/{param}")
    public Response saveReports(@PathParam("param") String companyId, @QueryParam("sessionid") String sessionId, String data) throws IllegalAccessException
    {
        String result = "";
        if (sessionManager.isLoggedIn(sessionId))
        {

            PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
            List<Report> reports;
            Type type = new TypeToken<List<Report>>()
            {
            }.getType();
            Gson gson = new Gson();
            Company company;

            try
            {
                logger.info("Received Reports: " + data);

                reports = gson.fromJson(data, type);
                logger.info("Parsed Reports: " + reports.size());
                logger.info("Report Id:" + reports.get(0).id());

                Iterator<Report> iterator = reports.iterator();
                while (iterator.hasNext())
                {
                    Report report = iterator.next();

                    if (report.isNew())
                    {
                        logger.info("Company Id: " + companyId);
                        company = (Company) persistenceManager.getObjectById(Company.class, Long.parseLong(companyId));

                        logger.info("Adding Report Roles");
                        Iterator<Role> roleIterator = report.fullRoles().iterator();
                        while (roleIterator.hasNext())
                        {
                            Role psRole = persistenceManager.getObjectById(Role.class, roleIterator.next().id().getId());
                            logger.info("Loaded role: " + psRole.id());
                            report.roles().add(psRole.id());
                        }

                        logger.info("Saving New Report");
                        persistenceManager.makePersistent(report);
                        logger.info("Saved Report - Kind: " + report.id().getKind() + " Id: " + report.id().getId());

                        logger.info("Add report to company");
                        company.reports().add(report.id());
                        persistenceManager.makePersistent(company);

                    } else if (report.isDeleted())
                    {
                        logger.info("Deleting Report - Kind: " + report.id().getKind() + " Id: " + report.id().getId());
                        Report psReport = (Report) persistenceManager.getObjectById(Report.class, report.id().getId());


                        logger.info("Company Id: " + companyId);
                        company = (Company) persistenceManager.getObjectById(Company.class, Long.parseLong(companyId));
                        company.reports().remove(psReport);

                        persistenceManager.deletePersistent(psReport);
                        persistenceManager.makePersistent(company);

                    } else if (report.isDirty())
                    {
                        logger.info("Saving Report - Kind: " + report.id().getKind() + " Id: " + report.id().getId());
                        Report psReport = (Report) persistenceManager.getObjectById(Report.class, report.id().getId());
                        logger.info("Updating Report - Kind: " + psReport.id().getKind() + " Id: " + psReport.id().getId());
                        psReport.copyFrom(report);

                        Iterator<Role> roleIterator = report.fullRoles().iterator();
                        while (roleIterator.hasNext())
                        {
                            Role role = roleIterator.next();
                            Role psRole = persistenceManager.getObjectById(Role.class, role.id().getId());
                            logger.info("Adding Report Role: " + psRole.id());
                            psReport.roles().add(psRole.id());
                        }

                        persistenceManager.makePersistent(psReport);
                        logger.info("Saved Report - Kind: " + report.id().getKind() + " Id: " + report.id().getId());

                    }

                }


            } finally
            {
                persistenceManager.close();
            }

            reports = loadReports(Long.parseLong(companyId));
            result = gson.toJson(reports);

        } else
        {
            throw new IllegalAccessException("Not logged in");
        }


        return Response.status(201).entity(result).build();
    }
}

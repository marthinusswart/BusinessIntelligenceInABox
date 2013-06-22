package com.intellibps.bib.rest;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */

import com.google.appengine.api.log.LogService;
import com.google.appengine.api.memcache.Expiration;
import com.google.gson.reflect.TypeToken;
import com.intellibps.bib.customer.Company;
import com.intellibps.bib.customer.ContactInfo;
import com.intellibps.bib.persistence.PersistanceController;
import com.intellibps.bib.security.Credentials;
import com.intellibps.bib.security.SessionManager;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.intellibps.bib.security.User;

@Path("/data")
public class SimpleDataService
{
    private SessionManager sessionManager = new SessionManager();
    private java.util.logging.Logger logger = Logger.getLogger(SimpleDataService.class.getName());

    @GET
    @Path("companies/{param}")
    public Response loadCompanies(@PathParam("param") String msg, @QueryParam("email") String email)
    {
           String result = "";

        if (sessionManager.isLoggedIn(email))
        {
            List<Company> companies = loadCompanies();

            Gson gson = new Gson();
            result = gson.toJson(companies);
        }
        else
        {
            result = "Not logged in";
        }

        return Response.status(200).entity(result).build();

    }

    private List<Company> loadCompanies()
    {
        PersistenceManager persistenceManager = PersistanceController.persistenceManagerFactory.getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.customer.Company");
        List<Company> companies;

        try
        {
           companies = (List<Company>) query.execute();
           Iterator<Company> companyIterator = companies.iterator();
            logger.info("Companies loaded");

            // mark the retrieved companies as not new
            while(companyIterator.hasNext())
            {
                Company company = companyIterator.next();
                company.isNew(false);
                company.isDirty(false);

            }


        }

        finally
        {
            persistenceManager.close();
        }

        return companies;
    }

    @PUT
    @Path("companies/{param}")
    public Response saveCompanies(@PathParam("param") String msg, @QueryParam("email") String email, String data)
    {
        String result = "";
        if (sessionManager.isLoggedIn(email))
        {

            PersistenceManager persistenceManager = PersistanceController.persistenceManagerFactory.getPersistenceManager();
            List<Company> companies;
            Type type = new TypeToken<List<Company>>(){}.getType();
            Gson gson = new Gson();

            try
            {
               logger.info("Received Companies: " + data);

               companies = gson.fromJson(data, type);
                logger.info("Parsed Companies: " + companies.size());
                logger.info("Company Id:" + companies.get(0).id());

                Iterator<Company> iterator = companies.iterator();
                while (iterator.hasNext())
                {
                    Company company = iterator.next();

                   if
                        (company.isNew())
                {
                    logger.info("Saving New Company");
                    persistenceManager.makePersistent(company);
                    logger.info("Saved Company - Kind: " + company.id().getKind() + " Id: " + company.id().getId());
                    company.isNew(false);
                    company.isDirty(false);
                }
                   else if (company.isDeleted())
                   {
                       logger.info("Deleting Company - Kind: " + company.id().getKind() + " Id: " + company.id().getId());
                       Company psCompany = (Company)persistenceManager.getObjectById(Company.class, company.id().getId());
                       persistenceManager.deletePersistent(psCompany);

                   }
                    else if (company.isDirty())
                    {
                        logger.info("Saving Company - Kind: " + company.id().getKind() + " Id: " + company.id().getId());
                    Company psCompany = (Company)persistenceManager.getObjectById(Company.class, company.id().getId());
                        logger.info("Updating Company - Kind: " + psCompany.id().getKind() + " Id: " + psCompany.id().getId() + " ContactInfo: " + psCompany.contactInfo().id().getId());
                    psCompany.copyFrom(company);
                    persistenceManager.makePersistent(psCompany);
                        logger.info("Saved Company - Kind: " + company.id().getKind() + " Id: " + company.id().getId()+ " ContactInfo: " + psCompany.contactInfo().id().getId());
                    company.isNew(false);
                    company.isDirty(false);
                    }

                }


            }

            finally
            {
                persistenceManager.close();
            }

            companies = loadCompanies();
            result = gson.toJson(companies);

        }
        else
        {
            result = "Not logged in";
        }



        return Response.status(201).entity(result).build();
    }
}

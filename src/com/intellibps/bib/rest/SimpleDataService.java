package com.intellibps.bib.rest;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */

import com.google.gson.reflect.TypeToken;
import com.intellibps.bib.customer.Company;
import com.intellibps.bib.persistence.PersistenceController;
import com.intellibps.bib.security.SessionManager;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
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
    public Response loadCompanies(@PathParam("param") String msg, @QueryParam("sessionid") String sessionId) throws IllegalAccessException
    {
        String result = "";

        if (sessionManager.isLoggedIn(sessionId))
        {
            logger.info("Loading companies");
            List<Company> companies = loadCompanies();

            Gson gson = new Gson();
            result = gson.toJson(companies);
        } else
        {
            throw new IllegalAccessException("Not logged in");
        }

        return Response.status(200).entity(result).build();

    }

    private List<Company> loadCompanies()
    {
        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.customer.Company");
        List<Company> companies;

        try
        {
            companies = (List<Company>) query.execute();

            logger.info("Companies loaded " + companies.size());

        } finally
        {
            persistenceManager.close();
        }

        Iterator<Company> companyIterator = companies.iterator();
        // mark the retrieved companies as not new
        while (companyIterator.hasNext())
        {
            Company company = companyIterator.next();
            company.isNew(false);
            company.isDirty(false);
        }

        return companies;
    }

    @PUT
    @Path("companies/{param}")
    public Response saveCompanies(@PathParam("param") String msg, @QueryParam("sessionid") String sessionId, String data)
    {
        String result = "";
        if (sessionManager.isLoggedIn(sessionId))
        {

            PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
            List<Company> companies;
            Type type = new TypeToken<List<Company>>()
            {
            }.getType();
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

                    } else if (company.isDeleted())
                    {
                        logger.info("Deleting Company - Kind: " + company.id().getKind() + " Id: " + company.id().getId());
                        Company psCompany = (Company) persistenceManager.getObjectById(Company.class, company.id().getId());
                        persistenceManager.deletePersistent(psCompany);

                    } else if (company.isDirty())
                    {
                        logger.info("Saving Company - Kind: " + company.id().getKind() + " Id: " + company.id().getId());
                        Company psCompany = (Company) persistenceManager.getObjectById(Company.class, company.id().getId());
                        logger.info("Updating Company - Kind: " + psCompany.id().getKind() + " Id: " + psCompany.id().getId() + " ContactInfo: " + psCompany.contactInfo().id().getId());
                        psCompany.copyFrom(company);
                        persistenceManager.makePersistent(psCompany);
                        logger.info("Saved Company - Kind: " + company.id().getKind() + " Id: " + company.id().getId() + " ContactInfo: " + psCompany.contactInfo().id().getId());

                    }

                }


            } finally
            {
                persistenceManager.close();
            }

            companies = loadCompanies();
            result = gson.toJson(companies);

        } else
        {
            result = "Not logged in";
        }


        return Response.status(201).entity(result).build();
    }

    @GET
    @Path("users/{param}")
    public Response loadUsers(@PathParam("param") String companyId, @QueryParam("sessionid") String sessionId)
    {
        String result = "";

        if (sessionManager.isLoggedIn(sessionId))
        {
            List<User> users = loadUsers(Long.parseLong(companyId));

            Gson gson = new Gson();
            result = gson.toJson(users);
        } else
        {
            result = "Not logged in";
        }

        return Response.status(200).entity(result).build();

    }

    private List<User> loadUsers(long companyId)
    {
        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.security.User WHERE company==:company");
        List<User> users;
        Company company;
        logger.info("Loading users");

        try
        {
            company = (Company) persistenceManager.getObjectById(Company.class, companyId);
            users = (List<User>) query.execute(company.id());

            logger.info("Users loaded " + users.size());

        } finally
        {
            persistenceManager.close();
        }

        Iterator<User> userIterator = users.iterator();
        // mark the retrieved users as not new
        while (userIterator.hasNext())
        {
            User user = userIterator.next();
            logger.info("User password: " + user.password());
            // default to 1to8
            user.password(User.DEFAULT_PASSWORD);
            user.isNew(false);
            user.isDirty(false);

        }

        return users;
    }

    @PUT
    @Path("users/{param}")
    public Response saveUsers(@PathParam("param") String companyId, @QueryParam("sessionid") String sessionId, String data)
    {
        String result = "";
        if (sessionManager.isLoggedIn(sessionId))
        {

            PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
            List<User> users;
            Type type = new TypeToken<List<User>>()
            {
            }.getType();
            Gson gson = new Gson();
            Company company;

            try
            {
                logger.info("Received Users: " + data);

                users = gson.fromJson(data, type);
                logger.info("Parsed Users: " + users.size());

                Iterator<User> iterator = users.iterator();
                while (iterator.hasNext())
                {
                    User user = iterator.next();

                    if (user.isNew())
                    {
                        logger.info("Company Id: " + companyId);
                        company = (Company) persistenceManager.getObjectById(Company.class, Long.parseLong(companyId));
                        user.company(company.id());
                        logger.info("Saving New User");
                        persistenceManager.makePersistent(user);
                        logger.info("Saved User - Kind: " + user.id().getKind() + " Id: " + user.id().getId());

                    } else if (user.isDeleted())
                    {
                        logger.info("Deleting User - Kind: " + user.id().getKind() + " Id: " + user.id().getId());
                        User psUser = (User) persistenceManager.getObjectById(User.class, user.id().getId());
                        persistenceManager.deletePersistent(psUser);

                    } else if (user.isDirty())
                    {
                        logger.info("Saving User - Kind: " + user.id().getKind() + " Id: " + user.id().getId());
                        User psUser = (User) persistenceManager.getObjectById(User.class, user.id().getId());
                        logger.info("Updating User - Kind: " + psUser.id().getKind() + " Id: " + psUser.id().getId());
                        logger.info("User password: " + user.password() + " psUser password: " + psUser.password());
                        psUser.copyFrom(user);
                        persistenceManager.makePersistent(psUser);
                        logger.info("Saved User - Kind: " + user.id().getKind() + " Id: " + user.id().getId());

                    }

                }


            } finally
            {
                persistenceManager.close();
            }

            users = loadUsers(Long.parseLong(companyId));
            result = gson.toJson(users);

        } else
        {
            result = "Not logged in";
        }


        return Response.status(201).entity(result).build();
    }
}

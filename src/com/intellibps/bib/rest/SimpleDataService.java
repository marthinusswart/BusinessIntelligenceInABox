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
import com.intellibps.bib.security.Role;
import com.intellibps.bib.security.SessionManager;

import javax.annotation.security.RolesAllowed;
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
    public Response saveCompanies(@PathParam("param") String msg, @QueryParam("sessionid") String sessionId, String data) throws IllegalAccessException
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

                    if (company.isNew())
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
            throw new IllegalAccessException("Not logged in");
        }


        return Response.status(201).entity(result).build();
    }

    @GET
    @Path("users/{param}")
    public Response loadUsers(@PathParam("param") String companyId, @QueryParam("sessionid") String sessionId)    throws IllegalAccessException
    {
        String result = "";

        if (sessionManager.isLoggedIn(sessionId))
        {
            List<User> users = loadUsers(Long.parseLong(companyId));

            Gson gson = new Gson();
            result = gson.toJson(users);
        } else
        {
            throw new IllegalAccessException("Not logged in");
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

            Iterator<User> userIterator = users.iterator();

            while(userIterator.hasNext())
            {
                User user = userIterator.next();
                Iterator<com.google.appengine.api.datastore.Key> roles  = user.roles().iterator();
                while (roles.hasNext())
                {

                    Role role = persistenceManager.getObjectById(Role.class, roles.next().getId());
                    logger.info("Adding role: " + role.name());
                    user.fullRoles().add(role);
                }
            }


        } finally
        {
            persistenceManager.close();
        }

        Iterator<User> userIterator = users.iterator();
        // mark the retrieved users as not new
        while (userIterator.hasNext())
        {
            User user = userIterator.next();
            // default to 1to8
            user.password(User.DEFAULT_PASSWORD);
            user.isNew(false);
            user.isDirty(false);

        }

        return users;
    }

    @PUT
    @Path("users/{param}")
    public Response saveUsers(@PathParam("param") String companyId, @QueryParam("sessionid") String sessionId, String data) throws IllegalAccessException
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

                        logger.info("Adding User Roles");
                        Iterator<Role> roleIterator = user.fullRoles().iterator();
                        while (roleIterator.hasNext())
                        {
                            Role psRole = persistenceManager.getObjectById(Role.class, roleIterator.next().id().getId());
                            logger.info("Loaded role: " + psRole.id());
                            user.roles().add(psRole.id());
                        }

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
                        psUser.copyFrom(user);

                        logger.info("Adding User Roles");
                        Iterator<Role> roleIterator = user.fullRoles().iterator();
                        while (roleIterator.hasNext())
                        {
                            Role role = roleIterator.next();
                            Role psRole = persistenceManager.getObjectById(Role.class, role.id().getId());
                            logger.info("Adding User Role: " + psRole.id());
                            psUser.roles().add(psRole.id());
                        }

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
            throw new IllegalAccessException("Not logged in");
        }


        return Response.status(201).entity(result).build();
    }

    @GET
    @Path("roles/{param}")
    public Response loadRoles(@PathParam("param") String msg, @QueryParam("sessionid") String sessionId)  throws IllegalAccessException
    {
        String result = "";

        if (sessionManager.isLoggedIn(sessionId))
        {
            List<Role> roles = loadRoles();

            Gson gson = new Gson();
            result = gson.toJson(roles);
        } else
        {
            throw new IllegalAccessException("Not logged in");
        }

        return Response.status(200).entity(result).build();

    }

    private List<Role> loadRoles()
    {
        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.security.Role");
        List<Role> roles;
        logger.info("Loading roles");

        try
        {

            roles = (List<Role>) query.execute();

            logger.info("Roles loaded " + roles.size());

        } finally
        {
            persistenceManager.close();
        }

        Iterator<Role> roleIterator = roles.iterator();
        // mark the retrieved users as not new
        while (roleIterator.hasNext())
        {
            Role role = roleIterator.next();
            role.isNew(false);
            role.isDirty(false);

        }

        return roles;
    }

    @PUT
    @Path("roles/{param}")
    public Response saveRoles(@PathParam("param") String msg, @QueryParam("sessionid") String sessionId, String data) throws IllegalAccessException
    {
        String result = "";
        if (sessionManager.isLoggedIn(sessionId))
        {

            PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
            List<Role> roles;
            Type type = new TypeToken<List<Role>>()
            {
            }.getType();
            Gson gson = new Gson();

            try
            {
                logger.info("Received Roles: " + data);

                roles = gson.fromJson(data, type);
                logger.info("Parsed Roles: " + roles.size());
                logger.info("Role Id:" + roles.get(0).id());

                Iterator<Role> iterator = roles.iterator();
                while (iterator.hasNext())
                {
                    Role role = iterator.next();

                    if(role.isNew())
                    {
                        logger.info("Saving New Role");
                        persistenceManager.makePersistent(role);
                        logger.info("Saved Role - Kind: " + role.id().getKind() + " Id: " + role.id().getId());

                    } else if (role.isDeleted())
                    {
                        logger.info("Deleting Role - Kind: " + role.id().getKind() + " Id: " + role.id().getId());
                        Role psCompany = (Role) persistenceManager.getObjectById(Role.class, role.id().getId());
                        persistenceManager.deletePersistent(psCompany);

                    } else if (role.isDirty())
                    {
                        logger.info("Saving Role - Kind: " + role.id().getKind() + " Id: " + role.id().getId());
                        Role psRole = (Role) persistenceManager.getObjectById(Role.class, role.id().getId());
                        logger.info("Updating Role - Kind: " + psRole.id().getKind() + " Id: " + psRole.id().getId());
                        psRole.copyFrom(role);
                        persistenceManager.makePersistent(psRole);
                        logger.info("Saved Role - Kind: " + role.id().getKind() + " Id: " + role.id().getId());

                    }

                }


            } finally
            {
                persistenceManager.close();
            }

            roles = loadRoles();
            result = gson.toJson(roles);

        } else
        {
            throw new IllegalAccessException("Not logged in");
        }


        return Response.status(201).entity(result).build();
    }
}

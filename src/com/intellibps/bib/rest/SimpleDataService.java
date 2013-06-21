package com.intellibps.bib.rest;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */

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
import java.util.List;
import com.google.gson.Gson;
import com.intellibps.bib.security.User;

@Path("/data")
public class SimpleDataService
{
    private SessionManager sessionManager = new SessionManager();

    @GET
    @Path("companies/{param}")
    public Response loadCompanies(@PathParam("param") String msg, @QueryParam("email") String email)
    {
        ArrayList<Company> companies2 = new ArrayList<Company>();
        Company company;

        for (int i=1;i<=15;i++)
        {
           company = new Company();
            company.name("Company " + i);
            company.contactInfo(new ContactInfo());
            company.contactInfo().businessEmail("company"+i+"@mail.com");
            company.contactInfo().businessPhoneNo("1234-" + i);
            company.city("City "+i);
            company.companyRegistrationNo("CMP-1234-"+i);
            company.country("South Africa");
            company.contactInfo().mobilePhoneNo("(082) 123 89"+i);
            company.contactInfo().physicalAddress(i+" Some Street\r\nJohannesburg\r\n2000");
            company.contactInfo().postalAddress("P.O. Box " + i +"\r\nJohannesburg\r\n2001");
            companies2.add(company);

        }




        String result = "";

        if (sessionManager.isLoggedIn(email))
        {
            PersistenceManager persistenceManager = PersistanceController.persistenceManagerFactory.getPersistenceManager();
            Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.customer.Company");
            List<Company> companies;

            try
            {
               companies = (List<Company>) query.execute();
               Gson gson = new Gson();
               result = gson.toJson(companies);
            }

            finally
            {
                persistenceManager.close();
            }


        }
        else
        {
            result = "Not logged in";
        }

        return Response.status(200).entity(result).build();

    }

    @PUT
    @Path("companies/{param}")
    public Response saveCompanies(@PathParam("param") String msg, @QueryParam("email") String email, String data)
    {
        String result = "";
        if (sessionManager.isLoggedIn(email))
        {

            PersistenceManager persistenceManager = PersistanceController.persistenceManagerFactory.getPersistenceManager();
            Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.customer.Company");
            List<Company> companies;
            Type type = new TypeToken<List<Company>>(){}.getType();

            try
            {
                System.out.println("Received Companies: " + data);
                Gson gson = new Gson();
               companies = gson.fromJson(data, type);
                System.out.println("Parsed Companies: " + companies.size());
                System.out.println("Company Id:" + companies.get(0).id());
            }

            finally
            {
                persistenceManager.close();
            }

        }
        else
        {
            result = "Not logged in";
        }



        return Response.status(201).entity(result).build();
    }
}

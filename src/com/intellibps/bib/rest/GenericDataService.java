package com.intellibps.bib.rest;

import com.google.gson.Gson;
import com.intellibps.bib.customer.Company;
import com.intellibps.bib.data.DataManager;
import com.intellibps.bib.data.GenericData;
import com.intellibps.bib.data.GenericDataType;
import com.intellibps.bib.security.SessionManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/21
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/data")
public class GenericDataService
{
    private SessionManager sessionManager = new SessionManager();
    private java.util.logging.Logger logger = Logger.getLogger(GenericDataService.class.getName());

    @GET
    @Path("company_datatypes/{param}")
    public Response loadCompanyDataTypes(@PathParam("param") String sessionId) throws IllegalAccessException
    {
        String result = "";
        Company company = null;
        DataManager dataManager = new DataManager();
        HashMap<String, GenericDataType> dataTypeMap = new HashMap<String, GenericDataType>();
        List<String> dataTypes = new Vector<String>();

        if (sessionManager.isLoggedIn(sessionId))
        {
            company = dataManager.loadCompany(sessionManager.credentials(sessionId).companyId());
            logger.info("Loading company types for company: " + company.name());
            List<GenericData> dataSet = dataManager.loadGenericData(company);

            Iterator<GenericData> dataIterator = dataSet.iterator();

            while (dataIterator.hasNext())
            {
                GenericData data = dataIterator.next();
                dataTypeMap.put(data.name(), new GenericDataType(data.name()));

            }

            Gson gson = new Gson();
            result = gson.toJson(dataTypeMap.values());
            logger.info("The JSON of datatypes: " + result);
            dataManager.close();
        } else
        {
            throw new IllegalAccessException("Not logged in");
        }

        return Response.status(200).entity(result).build();

    }

    @GET
    @Path("company_datatypedata/{param}")
    public Response loadCompanyDataTypes(@PathParam("param") String reportDataType, @QueryParam("sessionid") String sessionId) throws IllegalAccessException
    {
        String result = "";
        Company company = null;
        DataManager dataManager = new DataManager();

        if (sessionManager.isLoggedIn(sessionId))
        {
            company = dataManager.loadCompany(sessionManager.credentials(sessionId).companyId());
            logger.info("Loading company types for company: " + company.name());
            List<GenericData> dataSet = dataManager.loadGenericData(company, reportDataType);
            Vector<GenericData> strippedData = new Vector<GenericData>();

            Iterator<GenericData> dataIterator = dataSet.iterator();

            while (dataIterator.hasNext())
            {
                GenericData data = dataIterator.next();
                strippedData.add(data.clone(true));
            }

            Gson gson = new Gson();
            result = gson.toJson(strippedData);
            logger.info("The JSON of datatypes: " + result);
            dataManager.close();
        } else
        {
            throw new IllegalAccessException("Not logged in");
        }

        return Response.status(200).entity(result).build();

    }

    @GET
    @Path("company_datatypedetail/{param}")
    public Response loadCompanyDataTypeDetail(@PathParam("param") Long reportDataTypeId, @QueryParam("sessionid") String sessionId) throws IllegalAccessException
    {
        String result = "";
        Company company = null;
        DataManager dataManager = new DataManager();

        if (sessionManager.isLoggedIn(sessionId))
        {
            company = dataManager.loadCompany(sessionManager.credentials(sessionId).companyId());
            logger.info("Loading company type detail for company: " + company.name());
            GenericData dataSet = dataManager.loadGenericData(reportDataTypeId);

            //Gson gson = new Gson();
            result = dataSet.toJson();
            logger.info("The JSON of datatypedetail: " + result);
            dataManager.close();
        } else
        {
            throw new IllegalAccessException("Not logged in");
        }

        return Response.status(200).entity(result).build();

    }
}

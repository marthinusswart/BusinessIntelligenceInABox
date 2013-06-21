package com.intellibps.bib.rest;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 21:48
 * To change this template use File | Settings | File Templates.
 */

import com.intellibps.bib.security.SessionManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/simple")
public class SimpleRestService
{
    private SessionManager sessionManager = new SessionManager();

    @GET
    @Path("/{param}")
    public Response printMessage(@PathParam("param") String msg, @QueryParam("email") String email)
    {


        String result = "";

        if (sessionManager.isLoggedIn(email))
        {
        result = "Restful example : " + msg;
        }
        else
        {
            result = "Not logged in";
        }

        return Response.status(200).entity(result).build();

    }
}

package com.intellibps.bib.servlets;

import com.intellibps.bib.security.SessionManager;
import com.intellibps.bib.util.WebHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public class BiBMain extends HttpServlet
{
    SessionManager sessionManager = new SessionManager();
    WebHelper webHelper = new WebHelper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String action = request.getParameter("action");

        if ((action != null) && (action.equals("login")))
        {
            doLogin(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (!sessionManager.isLoggedIn(webHelper.getSessionId(request.getCookies())))
        {
            request.getRequestDispatcher("main/login.jsp").include(request, response);
        }
        else
        {
            request.getRequestDispatcher("main/landingarea.jsp").include(request, response);
        }
    }

    private void doLogin(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)  throws  javax.servlet.ServletException, IOException
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String sessionId = sessionManager.login(email, password);
        if (sessionId == null)
        {
            request.getRequestDispatcher("main/login.jsp").include(request, response);
        }
        else
        {
            Cookie cookie = new Cookie("sessionid", sessionId);
            response.addCookie(cookie);
            response.sendRedirect("/main");
            //request.getRequestDispatcher("controlpanel/controlpanel.jsp").include(request, response);
        }
    }
}

package com.intellibps.bib.servlets;

import com.intellibps.bib.security.SessionManager;
import com.intellibps.bib.util.WebHelper;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/18
 * Time: 20:10
 * To change this template use File | Settings | File Templates.
 */
public class ControlPanel extends javax.servlet.http.HttpServlet
{
    SessionManager sessionManager = new SessionManager();
    WebHelper webHelper = new WebHelper();

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        String action = request.getParameter("action");

        if ((action != null) && (action.equals("login")))
        {
            doLogin(request,response);
        }
        else if ((action != null) && (action.equals("changepwd")))
        {
            //changePasswordProcess()
            request.getRequestDispatcher("controlpanel/change-pwd.jsp").include(request, response);

        }
        else
        {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<html><head></head><body>");

            Enumeration<String> params = request.getParameterNames();

            while (params.hasMoreElements())
            {
                                           printWriter.println(params.nextElement());
            }

            printWriter.println("</body></html>");

                                               printWriter.close();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        if (request.getParameter("init") != null)
        {
            if (request.getParameter("init").equals("yes"))
            {
                if (!sessionManager.initialise())
                {
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println("<html><head></head><body>Failed to initialise</body></html>");
                }
                else
                {
                    request.getRequestDispatcher("controlpanel/login.jsp").include(request, response);
                }
            }
            else if (request.getParameter("init").equals("encrypt"))
            {
                sessionManager.encryptDefaultUserPassword();
            }
        }
        else
        if (!sessionManager.isLoggedIn(webHelper.getSessionId(request.getCookies())))
        {
            request.getRequestDispatcher("controlpanel/login.jsp").include(request, response);
        }
        else
        {
            request.getRequestDispatcher("controlpanel/controlpanel.jsp").include(request, response);
        }

    }

    private void doLogin(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)  throws  javax.servlet.ServletException, IOException
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String sessionId = sessionManager.login(email, password);
        if (sessionId == null)
        {
            request.getRequestDispatcher("controlpanel/login.jsp").include(request, response);
        }
        else
        {
            Cookie cookie = new Cookie("sessionid", sessionId);
            response.addCookie(cookie);
            response.sendRedirect("/cpanel");
            //request.getRequestDispatcher("controlpanel/controlpanel.jsp").include(request, response);
        }
    }

    private void changePassword(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)  throws  javax.servlet.ServletException, IOException
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String sessionId = sessionManager.login(email, password);
        if (sessionId == null)
        {
            request.getRequestDispatcher("controlpanel/login.jsp").include(request, response);
        }
        else
        {
            Cookie cookie = new Cookie("sessionid", sessionId);
            response.addCookie(cookie);
            response.sendRedirect("/cpanel");
            //request.getRequestDispatcher("controlpanel/controlpanel.jsp").include(request, response);
        }
    }

}

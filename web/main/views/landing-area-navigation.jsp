<%--
  Created by IntelliJ IDEA.
  User: marthinus
  Date: 2013/07/27
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="com.intellibps.bib.security.SessionManager, com.intellibps.bib.util.WebHelper, com.intellibps.bib.security.PermissionManager" %>

<%
    SessionManager sessionManager = new SessionManager();
    WebHelper webHelper = new WebHelper();
    PermissionManager permissionManager = new PermissionManager();
    String sessionId = webHelper.getSessionId(request.getCookies());

    if (sessionManager.isLoggedIn(sessionId))
    {
        if (permissionManager.canViewReports(sessionManager.credentials(sessionId)))
        {
%>

<jsp:include page="report-navigation.jsp"></jsp:include>

<%
        }
    }
%>


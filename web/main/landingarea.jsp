<%--
  Created by IntelliJ IDEA.
  User: marthinus
  Date: 2013/07/27
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="landingAreaModule">
<head>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery-ui.min.js"></script>
    <script src="/js/angular.min.js"></script>

    <link rel="stylesheet" href="/css/ui-lightness/jquery-ui.css"
          type="text/css">
    <link rel="stylesheet" href="/main/css/default.css" type="text/css">
    <link rel="stylesheet" href="/css/carbon.css" type="text/css">
    <script type="text/javascript" src="/directives/jquery-ui.js"></script>
    <script type="text/javascript" src="/main/controllers/landingarea.js"></script>


    <title>Business Intelligence in a Box</title>
</head>
<body class="background-style">


<h1>Business Intelligence in a Box</h1>

<div class="container ui-corner-all" ng-controller="landingAreaController">
  <div class="left-area">
      <jsp:include page="views/landing-area-navigation.partial.jsp"></jsp:include>
  </div>
</div>

<initialise comment="Initialise all the different controls that need the DOM to be available first">
</initialise>
</body>
</html>
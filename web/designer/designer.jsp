<%--
  Created by IntelliJ IDEA.
  User: marthinusswart
  Date: 2013/11/04
  Time: 7:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="designerModule">
<head>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery-ui.min.js"></script>
    <script src="/js/angular.min.js"></script>
    <script src="/js/angular-cookies.min.js"></script>
    <script src="/js/unslider.min.js"></script>

    <link rel="stylesheet" href="/css/ui-lightness/jquery-ui.css"
          type="text/css">
    <link rel="stylesheet" href="/designer/css/default.css" type="text/css">
    <link rel="stylesheet" href="/designer/css/new-report-wizard.css" type="text/css">
    <link rel="stylesheet" href="/css/carbon.css" type="text/css">
    <link rel="stylesheet" href="/css/cube.css" type="text/css">
    <script type="text/javascript" src="/directives/jquery-ui.js"></script>
    <script type="text/javascript" src="/directives/unslider.js"></script>
    <script type="text/javascript" src="/designer/modules/new-report-wizard.js"></script>
    <script type="text/javascript" src="/designer/modules/designer.js"></script>


    <title>Business Intelligence in a Box</title>
</head>
<body class="background-style">

<div class="container ui-corner-all" ng-controller="designerController">


    <div class="navigation-area" ng-include src="navigationPanel.url"></div>
    <div id="mainPanel" name="mainPanel" class="main-area" ng-include src="activeContent.url"></div>

</div>

<initialise comment="Initialise all the different controls that need the DOM to be available first">
</initialise>
</body>
</html>
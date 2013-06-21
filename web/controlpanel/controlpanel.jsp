<%--
  Created by IntelliJ IDEA.
  User: marthinus
  Date: 2013/06/19
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="controlPanel">
<head>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery-ui.min.js"></script>
    <script src="/js/angular.min.js"></script>

    <link rel="stylesheet" href="/css/pepper-grinder/jquery-ui.css" type="text/css">
    <link rel="stylesheet" href="/controlpanel/css/default.css" type="text/css">

    <script type="text/javascript" src="/directives/jquery-ui.js"></script>
    <script type="text/javascript" src="/controlpanel/controllers/controlpanel.js"></script>


    <title>Business Intelligence in a Box Control Panel</title>
</head>
<body>

<div ng-controller="controlPanelController">
    <h1>Business Intelligence in a Box Control Panel</h1>

    <div id="tabs" style="position: absolute;top: 100px;bottom: 10px;left:10px;right: 10px;min-width: 950px;min-height:500px;">
        <ul>
            <li><a href="#tabs1">Company Management</a></li>
            <li><a href="#tabs2">User Management</a></li>
            <li><a href="#tabs3">Role Management</a></li>
        </ul>
        <div id="tabs1">
            <div id="toolbar" class="ui-widget-header ui-corner-all" style="position:absolute;left:10px;width: 242px;">
                <button id="addButton" class="toolbar-button" ng-click="addCompany()" style="margin-left: 5px;">Add</button>
                <button id="deleteButton" class="toolbar-button">Delete</button>
                <button id="saveButton" class="toolbar-button" ng-click="saveCompanies()">Save</button>
            </div>
            <div id="listbox-div" style="position:absolute;top:100px;bottom:10px;left:10px;width: 200px;">
                <select id="company-list" ng-model="company"
                        ng-options="obj.name for obj in companies" size="10"
                        class="fill styled">

                </select>

            </div>
            <div id="company-detail" class="main-content"
                 style="position: absolute;top:100px;bottom:10px;left:220px;right:10px;">
                <div style="position: absolute;width: 350px;top:10px;bottom: 10px;left:10px;">
                    Company Name:
                    <div><input type="text" class="fill rounded-border" ng-model="company.name"></div>
                    <p></p>
                    Registration No:
                    <div><input type="text" class="fill rounded-border" ng-model="company.companyRegistrationNo"></div>
                    <p></p>
                    Home City:
                    <div><input type="text" class="fill rounded-border" ng-model="company.city"></div>
                    <p></p>
                    Home Country:
                    <div><input type="text" class="fill rounded-border" ng-model="company.country"></div>
                    <p></p>
                    Business Phone:
                    <div><input type="text" class="fill rounded-border" ng-model="company.contactInfo.businessPhoneNo"></div>
                    <p></p>
                    Mobile Phone:
                    <div><input type="text" class="fill rounded-border" ng-model="company.contactInfo.mobilePhoneNo"></div>
                    <p></p>
                    Business Email:
                    <div><input type="text" class="fill rounded-border" ng-model="company.contactInfo.businessEmail"></div>
                </div>
                <div style="position: absolute;left:370px;width:350px;top:10px;bottom: 10px;right:10px;">
                    Physical Address:
                    <div><textarea type="text" class="fill rounded-border" ng-model="company.contactInfo.physicalAddress" style="height: 100px;"></textarea></div>
                    <p></p>
                    Postal Address:
                    <div><textarea type="text" class="fill rounded-border" ng-model="company.contactInfo.postalAddress" style="height: 100px;"></textarea></div>
                </div>

            </div>

        </div>
        <div id="tabs2"></div>
        <div id="tabs3"></div>
    </div>
</div>
<script>
    // make the JQ tabs
    $("#tabs").tabs();

    // make the JQ buttons
    $("#saveButton").button();
    $("#deleteButton").button();
    $("#addButton").button();
</script>
</body>
</html>
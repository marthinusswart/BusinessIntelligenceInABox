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
    <!-- Breaks the databinding -->
    <!--<script src="/js/jquery.maskedinput.min.js"></script>-->

    <link rel="stylesheet" href="/css/pepper-grinder/jquery-ui.css" type="text/css">
    <link rel="stylesheet" href="/controlpanel/css/default.css" type="text/css">

    <script type="text/javascript" src="/controlpanel/models/company.js"></script>
    <script type="text/javascript" src="/controlpanel/models/user.js"></script>
    <script type="text/javascript" src="/directives/jquery-ui.js"></script>
    <script type="text/javascript" src="controlpanel/directives/controlpanel-dialogs.js"></script>
    <script type="text/javascript" src="/controlpanel/controllers/controlpanel.js"></script>
    <title>Business Intelligence in a Box Control Panel</title>
</head>
<body>

<div ng-controller="controlPanelController">
    <h1>Business Intelligence in a Box Control Panel</h1>

    <div id="tabs" style="position: absolute;top: 100px;bottom: 10px;left:10px;right: 10px;min-width: 950px;min-height:500px;">
        <ul>
            <li><a href="#companyTab">Company Management</a></li>
            <li><a href="#userTab">User Management</a></li>
            <li><a href="#roleTab">Role Management</a></li>
        </ul>
        <div id="companyTab">
            <div id="company-toolbar" class="ui-widget-header ui-corner-all" style="position:absolute;left:10px;right:10px;">
                <button id="addCompanyButton" class="toolbar-button" ng-click="addCompany()" style="margin-left: 5px;">Add</button>
                <button id="deleteCompanyButton" class="toolbar-button" ng-click="deleteCompany()">Delete</button>
                <button id="saveCompanyButton" class="toolbar-button" ng-click="saveCompanies()">Save</button>
                Company is dirty = {{company.isDirty}} Companies is dirty = {{companies.isDirty}}
            </div>
            <div id="company-listbox-div" style="position:absolute;top:100px;bottom:10px;left:10px;width: 250px;">
                <select id="company-list" ng-model="company"
                        ng-options="obj.name for obj in companies" size="10"
                        class="fill styled">
                </select>

            </div>
            <div id="company-detail" class="main-content"
                 style="position: absolute;top:100px;bottom:10px;left:270px;right:10px;">
                <div style="position: absolute;width: 350px;top:10px;bottom: 10px;left:10px;">
                    Company Name:
                    <div><input type="text" class="fill rounded-border" ng-model="company.name" ng-change="company.isDirty=true"></div>
                    <p></p>
                    Registration No:
                    <div><input type="text" class="fill rounded-border" ng-model="company.companyRegistrationNo" ng-change="company.isDirty=true"></div>
                    <p></p>
                    Home City:
                    <div><input type="text" class="fill rounded-border" ng-model="company.city" ng-change="company.isDirty=true"></div>
                    <p></p>
                    Home Country:
                    <div><input type="text" class="fill rounded-border" ng-model="company.country" ng-change="company.isDirty=true"></div>
                    <p></p>
                    Business Phone:
                    <div><input type="text" id="businessphone" class="fill rounded-border" ng-model="company.contactInfo.businessPhoneNo" ng-change="company.isDirty=true"></div>
                    <p></p>
                    Mobile Phone:
                    <div><input type="text" class="fill rounded-border" ng-model="company.contactInfo.mobilePhoneNo" ng-change="company.isDirty=true"></div>
                    <p></p>
                    Business Email:
                    <div><input type="text" class="fill rounded-border" ng-model="company.contactInfo.businessEmail" ng-change="company.isDirty=true"></div>
                </div>
                <div style="position: absolute;left:370px;width:350px;top:10px;bottom: 10px;right:10px;">
                    Physical Address:
                    <div><textarea type="text" class="fill rounded-border" ng-model="company.contactInfo.physicalAddress"  ng-change="company.isDirty=true" style="height: 100px;"></textarea></div>
                    <p></p>
                    Postal Address:
                    <div><textarea type="text" class="fill rounded-border" ng-model="company.contactInfo.postalAddress"  ng-change="company.isDirty=true" style="height: 100px;"></textarea></div>
                </div>

            </div>

        </div>
        <div id="userTab">
            <div id="user-toolbar" class="ui-widget-header ui-corner-all" style="position:absolute;left:10px;right:10px;">
                <button id="addUserButton" class="toolbar-button" ng-click="addUser()" style="margin-left: 5px;">Add</button>
                <button id="deleteUserButton" class="toolbar-button" ng-click="deleteUser()">Delete</button>
                <button id="saveUserButton" class="toolbar-button" ng-click="saveSelectedCompanyUsers()">Save</button>
                User is dirty = {{user.isDirty}} Users is dirty = {{users.isDirty}}
            </div>
            <div id="user-listbox-div" style="position:absolute;top:100px;bottom:10px;left:10px;width: 250px;">
                <select id="user-list" ng-model="user"
                        ng-options="obj.email for obj in users" size="10"
                        class="fill styled">
                </select>

            </div>
            <div id="user-detail" class="main-content"
                 style="position: absolute;top:100px;bottom:10px;left:270px;right:10px;">
                <div style="position: absolute;width: 350px;top:10px;bottom: 10px;left:10px;">
                    Firstname:
                    <div><input type="text" class="fill rounded-border" ng-model="user.firstname" ng-change="user.isDirty=true"></div>
                    <p></p>
                    Lastname:
                    <div><input type="text" class="fill rounded-border" ng-model="user.lastname" ng-change="user.isDirty=true"></div>
                    <p></p>
                    Email:
                    <div><input type="text" class="fill rounded-border" ng-model="user.email" ng-change="user.isDirty=true"></div>
                    <p></p>
                    Password:
                    <div><input type="password" class="fill rounded-border" ng-model="user.password" ng-change="user.isDirty=true"></div>
                </div>
                <div style="position: absolute;left:370px;width:350px;top:10px;bottom: 10px;right:10px;">
                </div>

            </div>
        </div>
        <div id="roleTab"></div>
    </div>
    <delete-company-dialog></delete-company-dialog>
    <confirmation-dialog></confirmation-dialog>
    <initialise comment="Initialise all the different controls that need the DOM to be available first">

        <jq-button button-id="saveCompanyButton"></jq-button>
        <jq-button button-id="deleteCompanyButton"></jq-button>
        <jq-button button-id="addCompanyButton"></jq-button>
        <jq-button button-id="saveUserButton"></jq-button>
        <jq-button button-id="deleteUserButton"></jq-button>
        <jq-button button-id="addUserButton"></jq-button>
        <jq-tabs tabs-id="tabs" before-activate-callback="functionRetriever('tabSelect')"></jq-tabs>
    </initialise>

</div>

</body>
</html>
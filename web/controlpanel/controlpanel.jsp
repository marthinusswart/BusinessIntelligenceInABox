<%--
  Created by IntelliJ IDEA.
  User: marthinus
  Date: 2013/06/19
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="controlPanel">
<head>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery-ui.min.js"></script>
    <script src="/js/angular.min.js"></script>
    <script src="/js/angular-cookies.min.js"></script>
    <!-- Breaks the databinding -->
    <!--<script src="/js/jquery.maskedinput.min.js"></script>-->

    <link rel="stylesheet" href="/css/ui-lightness/jquery-ui.css"
          type="text/css">
    <link rel="stylesheet" href="/controlpanel/css/default.css" type="text/css">

    <script type="text/javascript" src="/controlpanel/models/company.js"></script>
    <script type="text/javascript" src="/controlpanel/models/user.js"></script>
    <script type="text/javascript" src="/controlpanel/models/role.js"></script>
    <script type="text/javascript" src="/controlpanel/models/report.js"></script>
    <script type="text/javascript" src="/directives/jquery-ui.js"></script>
    <script type="text/javascript" src="controlpanel/directives/controlpanel-dialogs.js"></script>
    <script type="text/javascript" src="/controlpanel/controllers/controlpanel.js"></script>
    <title>Business Intelligence in a Box Control Panel</title>
</head>
<body>

<div ng-controller="controlPanelController">
    <h1>Business Intelligence in a Box Control Panel</h1>

    <div id="tabs"
         class="main-tabs">
        <ul>
            <li><a href="#companyTab">Company Management</a></li>
            <li><a href="#userTab">User Management</a></li>
            <li><a href="#roleTab">Role Management</a></li>
            <li><a href="#reportTab">Report Management</a></li>
        </ul>
        <div id="companyTab">
            <div id="company-toolbar" class="ui-state-active ui-corner-all"
                 style="position:absolute;left:10px;right:10px;">
                <button id="addCompanyButton" class="toolbar-button" ng-click="addCompany()" style="margin-left: 5px;">Add</button>
                <button id="deleteCompanyButton" class="toolbar-button" ng-click="deleteCompany()">Delete</button>
                <button id="saveCompanyButton" class="toolbar-button" ng-click="saveCompanies()">Save</button>

            </div>
            <div id="company-listbox-div" class="listbox">
                <select id="company-list" ng-model="company"
                        ng-options="obj.name for obj in companies" size="10"
                        class="fill styled">
                </select>

            </div>
            <div id="company-detail" class="main-content">
                <div class="left-area">
                    Company Name:
                    <div><input type="text" class="fill rounded-border" ng-model="company.name"
                                ng-change="company.isDirty=true"></div>
                    <p></p>
                    Registration No:
                    <div><input type="text" class="fill rounded-border" ng-model="company.companyRegistrationNo"
                                ng-change="company.isDirty=true"></div>
                    <p></p>
                    Home City:
                    <div><input type="text" class="fill rounded-border" ng-model="company.city"
                                ng-change="company.isDirty=true"></div>
                    <p></p>
                    Home Country:
                    <div><input type="text" class="fill rounded-border" ng-model="company.country"
                                ng-change="company.isDirty=true"></div>
                    <p></p>
                    Business Phone:
                    <div><input type="text" id="businessphone" class="fill rounded-border"
                                ng-model="company.contactInfo.businessPhoneNo" ng-change="company.isDirty=true"></div>
                    <p></p>
                    Mobile Phone:
                    <div><input type="text" class="fill rounded-border" ng-model="company.contactInfo.mobilePhoneNo"
                                ng-change="company.isDirty=true"></div>
                    <p></p>
                    Business Email:
                    <div><input type="text" class="fill rounded-border" ng-model="company.contactInfo.businessEmail"
                                ng-change="company.isDirty=true"></div>
                    <p></p>
                    Company Code:
                    <div><input type="text" class="fill rounded-border" ng-model="company.companyCode"
                                ng-change="company.isDirty=true"></div>
                </div>
                <div class="right-area">
                    Physical Address:
                    <div><textarea type="text" class="fill rounded-border"
                                   ng-model="company.contactInfo.physicalAddress" ng-change="company.isDirty=true"
                                   style="height: 100px;"></textarea></div>
                    <p></p>
                    Postal Address:
                    <div><textarea type="text" class="fill rounded-border" ng-model="company.contactInfo.postalAddress"
                                   ng-change="company.isDirty=true" style="height: 100px;"></textarea></div>
                </div>

            </div>
            <div id="company-status-bar" class="status-bar rounded-border">
                <div style="position: absolute;left:5px;width: 200px;">
                    <button id="logoutButton" class="toolbar-button" ng-click="logout()">Logout</button>
                </div>
                <div style="position: absolute;left:300px;top:2px;right: 10px;">
                    <p align="right">Company is dirty = {{company.isDirty}} Companies is dirty = {{companies.isDirty}}, SessionId = {{sessionId}} </p>
                </div>
            </div>

        </div>
        <div id="userTab">
            <div id="user-toolbar" class="ui-state-active ui-corner-all"
                 style="position:absolute;left:10px;right:10px;">
                <button id="addUserButton" class="toolbar-button" ng-click="addUser()" style="margin-left: 5px;">Add</button>
                <button id="deleteUserButton" class="toolbar-button" ng-click="deleteUser()">Delete</button>
                <button id="saveUserButton" class="toolbar-button" ng-click="saveSelectedCompanyUsers()">Save</button>


            </div>
            <div id="user-listbox-div" class="listbox">
                <select id="user-list" ng-model="user"
                        ng-options="obj.email for obj in users" size="10"
                        ng-change="userChanged()"
                        class="fill styled">
                </select>

            </div>
            <div id="user-detail" class="main-content">
                <div class="left-area">
                    Firstname:
                    <div><input type="text" class="fill rounded-border" ng-model="user.firstname"
                                ng-change="user.isDirty=true"></div>
                    <p></p>
                    Lastname:
                    <div><input type="text" class="fill rounded-border" ng-model="user.lastname"
                                ng-change="user.isDirty=true"></div>
                    <p></p>
                    Email:
                    <div><input type="text" class="fill rounded-border" ng-model="user.email"
                                ng-change="user.isDirty=true"></div>
                    <p></p>
                    Password:
                    <div><input type="password" class="fill rounded-border" ng-model="user.password"
                                ng-change="user.isDirty=true"></div>
                </div>
                <div class="right-area">

                    Roles:
                    <div id="user-roles-toolbar" class="rounded-border"
                            >
                        <select id="user-role-list" ng-model="userRole"
                                ng-options="obj.name for obj in user.fullRoles" size="10"
                                class="fill styled-inner-border"
                                style="height: 88px;">
                        </select>

                        <button id="addUserRoleButton" class="toolbar-button" ng-click="addUserRole()" style="margin-left: 5px;">Add</button>
                        <button id="deleteUserRoleButton" class="toolbar-button" ng-click="deleteUserRole()">Delete</button>
                    </div>

                </div>

            </div>
            <div id="users-status-bar" class="status-bar rounded-border">
                <div style="position: absolute;left:300px;top:2px;right: 10px;">
                    <p align="right">User is dirty = {{user.isDirty}} Users is dirty = {{users.isDirty}}</p></div>
            </div>
        </div>
        <div id="roleTab">
            <div id="role-toolbar" class="ui-state-active ui-corner-all"
                 style="position:absolute;left:10px;right:10px;">
                <button id="addRoleButton" class="toolbar-button" ng-click="addRole()" style="margin-left: 5px;">Add</button>
                <button id="deleteRoleButton" class="toolbar-button" ng-click="deleteRole()">Delete</button>
                <button id="saveRoleButton" class="toolbar-button" ng-click="saveRoles()">Save</button>


            </div>
            <div id="role-listbox-div" class="listbox">
                <select id="role-list" ng-model="role"
                        ng-options="obj.name for obj in roles" size="10"
                        class="fill styled">
                </select>

            </div>
            <div id="role-detail" class="main-content">
                <div class="left-area">
                    Name:
                    <div><input type="text" class="fill rounded-border" ng-model="role.name"
                                ng-change="role.isDirty=true"></div>
                    <p></p>
                    Description:
                    <div>
                        <textarea type="text" class="fill rounded-border"
                                  ng-model="role.description" ng-change="role.isDirty=true"
                                  style="height: 100px;"></textarea>
                    </div>
                    <div class="right-area">
                    </div>

                </div>
            </div>
            <div id="roles-status-bar" class="status-bar rounded-border">
                <div style="position: absolute;left:300px;top:2px;right: 10px;">
                    <p align="right">Role is dirty = {{role.isDirty}} Roles is dirty = {{roles.isDirty}}</p></div>
            </div>
        </div>
        <div id="reportTab">
            <div id="report-toolbar" class="ui-state-active ui-corner-all"
                 style="position:absolute;left:10px;right:10px;">
                <button id="addReportButton" class="toolbar-button" ng-click="addReport()" style="margin-left: 5px;">Add</button>
                <button id="deleteReportButton" class="toolbar-button" ng-click="deleteRole()">Delete</button>
                <button id="saveReportButton" class="toolbar-button" ng-click="saveSelectedCompanyReports()">Save</button>


            </div>
            <div id="report-listbox-div" class="listbox">
                <select id="report-list" ng-model="report"
                        ng-options="obj.name for obj in reports" size="10"
                        class="fill styled">
                </select>

            </div>
            <div id="report-detail" class="main-content">
                <div class="left-area">
                    Name:
                    <div><input type="text" class="fill rounded-border" ng-model="report.name"
                                ng-change="report.isDirty=true"></div>
                    <p></p>
                    Description:
                    <div>
                        <textarea type="text" class="fill rounded-border"
                                  ng-model="report.description" ng-change="report.isDirty=true"
                                  style="height: 75px;"></textarea>
                    </div>
                    <p></p>
                    Report Url:
                    <div><input type="text" class="fill rounded-border" ng-model="report.reportUrl"
                                ng-change="report.isDirty=true"></div>


                </div>
                <div class="right-area">
                    Access Control:
                    <div id="report-roles-toolbar" class="rounded-border"
                            >
                        <select id="report-role-list" ng-model="reportRole"
                                ng-options="obj.name for obj in report.fullRoles" size="10"
                                class="fill styled-inner-border"
                                style="height: 95px;">
                        </select>

                        <button id="addReportRoleButton" class="toolbar-button" ng-click="addReportRole()" style="margin-left: 5px;">Add</button>
                        <button id="deleteReportRoleButton" class="toolbar-button" ng-click="deleteReportRole()">Delete</button>
                    </div>
                </div>
            </div>
            <div id="reports-status-bar" class="status-bar rounded-border">
                <div style="position: absolute;left:300px;top:2px;right: 10px;">
                    <p align="right">Report is dirty = {{report.isDirty}} Reports is dirty = {{reports.isDirty}}</p></div>
            </div>
        </div>

        <initialise comment="Initialise all the different controls that need the DOM to be available first">
            <delete-company-dialog></delete-company-dialog>
            <delete-role-dialog></delete-role-dialog>
            <delete-user-dialog></delete-user-dialog>
            <confirmation-dialog></confirmation-dialog>
            <add-user-role-dialog></add-user-role-dialog>
            <delete-user-role-dialog></delete-user-role-dialog>
            <add-report-role-dialog></add-report-role-dialog>

            <jq-button button-id="saveCompanyButton"></jq-button>
            <jq-button button-id="deleteCompanyButton"></jq-button>
            <jq-button button-id="addCompanyButton"></jq-button>
            <jq-button button-id="saveUserButton"></jq-button>
            <jq-button button-id="deleteUserButton"></jq-button>
            <jq-button button-id="addUserButton"></jq-button>
            <jq-button button-id="saveRoleButton"></jq-button>
            <jq-button button-id="deleteRoleButton"></jq-button>
            <jq-button button-id="addRoleButton"></jq-button>
            <jq-button button-id="addUserRoleButton"></jq-button>
            <jq-button button-id="deleteUserRoleButton"></jq-button>
            <jq-button button-id="logoutButton"></jq-button>
            <jq-button button-id="saveReportButton"></jq-button>
            <jq-button button-id="deleteReportButton"></jq-button>
            <jq-button button-id="addReportButton"></jq-button>
            <jq-button button-id="addReportRoleButton"></jq-button>
            <jq-button button-id="deleteReportRoleButton"></jq-button>
            <jq-tabs tabs-id="tabs" before-activate-callback="functionRetriever('tabSelect')"></jq-tabs>
        </initialise>

    </div>

</body>
</html>
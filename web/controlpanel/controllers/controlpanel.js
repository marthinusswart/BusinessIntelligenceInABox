/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */

var controlPanelModule = angular
    .module("controlPanel", ["jqComponents", "controlPanelDialogs", "ngCookies"]);

controlPanelModule.controller("controlPanelController",
    function ($scope, $http, $cookies)
    {
        /***************************************
         *                Variables
         ****************************************/
        $scope.deleteCompanyDialogId = "#deleteCompanyDialog";
        $scope.deleteUserDialogId = "#deleteUserDialog";
        $scope.deleteRoleDialogId = "#deleteRoleDialog";
        $scope.confirmationDialogId = "#confirmationDialog";
        $scope.addUserRoleDialog = "#addUserRoleDialog";
        $scope.deleteUserRoleDialog = "#deleteUserRoleDialog";
        $scope.addReportRoleDialog = "#addReportRoleDialog";
        $scope.deleteReportRoleDialog = "#deleteReportRoleDialog";
        $scope.tabsId = "#tabs";
        $scope.deletedCompanies = [];
        $scope.deletedUsers = [];
        $scope.deletedRoles = [];
        $scope.deletedReports = [];
        $scope.users = [];
        $scope.roles = [];
        $scope.companies = [];
        $scope.reports = [];
        $scope.currentCompany;
        $scope.confirmationmessage = "Confirm?";
        $scope.confirmationtitle = "Confirm";
        $scope.canNavigateOnDirty = false;
        $scope.newSelectedTabIndex = 1;
        $scope.company;
        $scope.user;
        $scope.role;
        $scope.report;
        $scope.sessionId = $cookies.sessionid;
        $scope.roleToAdd;
        $scope.userRole;
        $scope.reportRole;


        /*****************************************
         *          Functions
         ***************************************/

        /**************************************
         *        Add functions
         **************************************/
        $scope.addCompany = function ()
        {
            var company = new Company();
            company.name = "New";
            company.companyRegistrationNo = "0000";
            company.city = "City";
            company.country = "Country";
            company.isNew = true;
            company.isDirty = false;
            company.isDeleted = false;

            $scope.company = company;
            $scope.companies.push($scope.company);
        }

        $scope.addUser = function ()
        {

            var user = new User();
            user.firstname = "New";
            user.lastname = "New";
            user.email = "email@email.com";
            user.isNew = true;
            user.isDirty = false;
            user.isDeleted = false;

            $scope.user = user;
            $scope.users.push($scope.user);
        }

        $scope.addRole = function ()
        {

            var role = new Role();
            role.name = "New";
            role.description = "New";
            role.isNew = true;
            role.isDirty = false;
            role.isDeleted = false;

            $scope.role = role;
            $scope.roles.push($scope.role);
        }

        $scope.addReport = function ()
        {

            var report = new Report();
            report.name = "New";
            report.description = "New";
            report.reportUrl = "/newReport";
            report.isNew = true;
            report.isDirty = false;
            report.isDeleted = false;

            $scope.report = report;
            $scope.reports.push($scope.report);
        }

        /**************************************
         *        Delete functions
         **************************************/

        $scope.deleteCompany = function ()
        {
            $($scope.deleteCompanyDialogId).show();
            $($scope.deleteCompanyDialogId).dialog(
                {
                    resizable:false,
                    height:170,
                    modal:true,
                    buttons:{
                        Delete:function ()
                        {
                            $(this).dialog("close");
                            $scope.company.isDeleted = true;
                            $scope.deletedCompanies.push($scope.company);
                            $scope.removeFromArray($scope.company, $scope.companies);
                            $scope.company = $scope.companies[0];
                            $scope.$apply();
                        },
                        Cancel:function ()
                        {
                            $(this).dialog("close");
                        }
                    }
                }
            );

        }

        $scope.deleteUser = function ()
        {
            $($scope.deleteUserDialogId).show();
            $($scope.deleteUserDialogId).dialog(
                {
                    resizable:false,
                    height:170,
                    modal:true,
                    buttons:{
                        Delete:function ()
                        {
                            $(this).dialog("close");
                            $scope.user.isDeleted = true;
                            $scope.deletedUsers.push($scope.user);
                            $scope.removeFromArray($scope.user, $scope.users);
                            $scope.user = $scope.users[0];
                            $scope.$apply();
                        },
                        Cancel:function ()
                        {
                            $(this).dialog("close");
                        }
                    }
                }
            );

        }

        $scope.deleteRole = function ()
        {
            $($scope.deleteRoleDialogId).show();
            $($scope.deleteRoleDialogId).dialog(
                {
                    resizable:false,
                    height:170,
                    modal:true,
                    buttons:{
                        Delete:function ()
                        {
                            $(this).dialog("close");
                            $scope.role.isDeleted = true;
                            $scope.deletedRoles.push($scope.role);
                            $scope.removeFromArray($scope.role, $scope.roles);
                            $scope.role = $scope.roles[0];
                            $scope.$apply();
                        },
                        Cancel:function ()
                        {
                            $(this).dialog("close");
                        }
                    }
                }
            );

        }

        $scope.addUserRole = function ()
        {
            $scope.roleToAdd = $scope.roles[0];

            $($scope.addUserRoleDialog).show();
            $($scope.addUserRoleDialog).dialog(
                {
                    resizable:false,
                    height:250,
                    modal:true,
                    buttons:{
                        Add:function ()
                        {
                            $(this).dialog("close");
                            $scope.user.fullRoles.push($scope.roleToAdd);
                            $scope.userRole = $scope.user.fullRoles[0];
                            $scope.user.isDirty = true;
                            $scope.$apply();
                        },
                        Cancel:function ()
                        {
                            $(this).dialog("close");
                        }
                    }
                }
            );

        }

        $scope.deleteUserRole = function ()
        {
            $scope.roleToDelete = $scope.userRole;

            $($scope.deleteUserRoleDialog).show();
            $($scope.deleteUserRoleDialog).dialog(
                {
                    resizable:false,
                    height:200,
                    modal:true,
                    buttons:{
                        Delete:function ()
                        {
                            $(this).dialog("close");
                            $scope.removeFromArray($scope.userRole, $scope.user.fullRoles);
                            $scope.userRole = $scope.user.fullRoles[0];
                            $scope.user.isDirty = true;
                            $scope.$apply();
                        },
                        Cancel:function ()
                        {
                            $(this).dialog("close");
                        }
                    }
                }
            );

        }

        $scope.addReportRole = function ()
        {
            $scope.roleToAdd = $scope.roles[0];

            $($scope.addReportRoleDialog).show();
            $($scope.addReportRoleDialog).dialog(
                {
                    resizable:false,
                    height:250,
                    modal:true,
                    buttons:{
                        Add:function ()
                        {
                            $(this).dialog("close");
                            $scope.report.fullRoles.push($scope.roleToAdd);
                            $scope.reportRole = $scope.report.fullRoles[0];
                            $scope.report.isDirty = true;
                            $scope.$apply();
                        },
                        Cancel:function ()
                        {
                            $(this).dialog("close");
                        }
                    }
                }
            );

        }

        $scope.confirmTabNavigation = function (title, message)
        {
            $scope.confirmationmessage = message;
            $scope.confirmationtitle = title;
            $scope.$apply();

            $($scope.confirmationDialogId).show();
            $($scope.confirmationDialogId).dialog(
                {
                    resizable:false,
                    height:200,
                    width:350,
                    modal:true,
                    buttons:{
                        No:function ()
                        {
                            $scope.canNavigateOnDirty = false;
                            $(this).dialog("close");
                        },
                        Yes:function ()
                        {
                            $scope.canNavigateOnDirty = true;
                            $(this).dialog("close");
                            $($scope.tabsId).tabs("option", "active", $scope.newSelectedTabIndex);
                            $scope.canNavigateOnDirty = false;
                        }

                    }
                }
            );
        }

        $scope.logout = function ()
        {
            // clear the session id
            $cookies = sessionid = ""; //bug in angular JS
            // work around
            document.cookie = "sessionid=''; path=/";
            location.reload();
        }
        /*************************************
         *          REST Services
         *************************************/
        $scope.loadCompanies = function ()
        {
            $http.get("/rest/data/companies/load?sessionid=" + $scope.sessionId).success(
                function (reply)
                {
                    $scope.companies = reply;
                    $scope.company = $scope.companies[0];
                }
            ).error(
                function (e)
                {
                    console.error(e);
                }
            );

        }

        $scope.saveCompanies = function ()
        {
            if ($scope.deletedCompanies.length > 0)
            {
                // add the deleted companies to the list
                for (var i = 0; i < $scope.deletedCompanies.length; i++)
                {
                    $scope.companies.push($scope.deletedCompanies[i]);
                    $scope.deletedCompanies = [];
                }
            }

            $http.put("/rest/data/companies/save?sessionid=" + $scope.sessionId, $scope.companies).success(
                function (reply)
                {
                    $scope.companies = reply;
                    $scope.company = $scope.companies[0];
                }
            );
        }

        $scope.loadUsers = function (companyId)
        {
            $http.get("/rest/data/users/" + companyId + "?sessionid=" + $scope.sessionId).success(
                function (reply)
                {
                    $scope.users = reply;
                    $scope.user = $scope.users[0];
                    if ($scope.user != null)
                    {
                        $scope.userRole = $scope.user.fullRoles[0];
                    }

                }
            ).error(function (data, status, headers, config)
                {
                    console.error(data);
                });
        }

        $scope.saveSelectedCompanyUsers = function ()
        {
            $scope.saveUsers($scope.company.id.id);
        }

        $scope.saveUsers = function (companyId)
        {

            if ($scope.deletedUsers.length > 0)
            {
                console.info("adding deleted users");
                // add the deleted users to the list
                for (var i = 0; i < $scope.deletedUsers.length; i++)
                {
                    $scope.users.push($scope.deletedUsers[i]);
                    $scope.deletedUsers = [];
                }
            }

            $http.put("/rest/data/users/" + companyId + "?sessionid=" + $scope.sessionId, $scope.users).success(
                function (reply)
                {
                    $scope.users = reply;
                    $scope.user = $scope.users[0];

                    if ($scope.user != null)
                    {
                        $scope.userRole = $scope.user.fullRoles[0];
                    }
                }
            );
        }

        $scope.loadRoles = function ()
        {
            $http.get("/rest/data/roles/load?sessionid=" + $scope.sessionId).success(
                function (reply)
                {
                    $scope.roles = reply;
                    $scope.role = $scope.roles[0];
                }
            ).error(
                function (data, status, headers, config)
                {
                    console.error(data);
                }
            );

        }

        $scope.saveRoles = function ()
        {
            if ($scope.deletedRoles.length > 0)
            {
                // add the deleted companies to the list
                for (var i = 0; i < $scope.deletedRoles.length; i++)
                {
                    $scope.roles.push($scope.deletedRoles[i]);
                    $scope.deletedRoles = [];
                }
            }

            $http.put("/rest/data/roles/save?sessionid=" + $scope.sessionId, $scope.roles).success(
                function (reply)
                {
                    $scope.roles = reply;
                    $scope.role = $scope.roles[0];
                }
            ).error(
                function (e)
                {
                    console.error(e);
                }
            );
        }

        $scope.loadReports = function (companyId)
        {
            $http.get("/rest/data/reports/" + companyId + "?sessionid=" + $scope.sessionId).success(
                function (reply)
                {
                    $scope.reports = reply;
                    $scope.report = $scope.reports[0];
                    if ($scope.report != null)
                    {
                        $scope.reportRole = $scope.report.fullRoles[0];
                    }
                }
            ).error(function (data, status, headers, config)
                {
                    console.error(data);
                });
        }

        $scope.saveSelectedCompanyReports = function ()
        {
            $scope.saveReports($scope.company.id.id);
        }


        $scope.saveReports = function (companyId)
        {

            /*if ($scope.deletedUsers.length > 0)
             {
             console.info("adding deleted users");
             // add the deleted users to the list
             for (var i = 0; i < $scope.deletedUsers.length; i++)
             {
             $scope.users.push($scope.deletedUsers[i]);
             $scope.deletedUsers = [];
             }
             }  */

            $http.put("/rest/data/reports/" + companyId + "?sessionid=" + $scope.sessionId, $scope.reports).success(
                function (reply)
                {
                    $scope.reports = reply;
                    $scope.report = $scope.reports[0];

                    if ($scope.report != null)
                    {
                        $scope.reportRole = $scope.report.fullRoles[0];
                    }
                }
            );
        }


        /*****************************
         * Candidate to go into a service of some kind
         */
        $scope.removeFromArray = function (item, array)
        {
            for (var i = array.length - 1; i >= 0; i--)
            {
                if (array[i] == item)
                {
                    array.splice(i, 1);
                    break;
                }
            }
        }

        $scope.functionRetriever = function (functionToRetrieve)
        {

            if (functionToRetrieve == "tabSelect")
            {
                return $scope.tabSelect;
            }
            else
            {
                return function ()
                {
                };
            }
            ;
        }

        $scope.tabSelect = function (event, ui)
        {


            if (ui.newPanel.attr("id") == "userTab")
            {
                $scope.newSelectedTabIndex = 1;
                if ((ui.oldPanel.attr("id") == "companyTab") && ($scope.companies.isDirty) && (!$scope.canNavigateOnDirty))
                {

                    $scope.confirmTabNavigation("Lose Company Changes", "Some Company details changed, switching to another tab will lose the changes. Lose changes?");
                    /* always return false in this instance
                     * we are working async, so no blocking
                     * */
                    return false;
                }

                // Load all the users for this company
                $scope.loadUsers($scope.company.id.id);

            }
            else if (ui.newPanel.attr("id") == "roleTab")
            {
                $scope.newSelectedTabIndex = 2;
                if ((ui.oldPanel.attr("id") == "companyTab") && ($scope.companies.isDirty) && (!$scope.canNavigateOnDirty))
                {
                    $scope.confirmTabNavigation("Lose company changes", "Some company details changed, switching to another tab will lose changes. Lose changes?");
                    // always return false in this instance
                    return false;
                }

                // Load all the roles
                $scope.loadRoles();
            }
            else if (ui.newPanel.attr("id") == "companyTab")
            {
                $scope.newSelectedTabIndex = 0;
                $scope.loadCompanies();
            }
            else if (ui.newPanel.attr("id") == "reportTab")
            {
                $scope.newSelectedTabIndex = 3;
                if ((ui.oldPanel.attr("id") == "companyTab") && ($scope.companies.isDirty) && (!$scope.canNavigateOnDirty))
                {

                    $scope.confirmTabNavigation("Lose Company Changes", "Some Company details changed, switching to another tab will lose the changes. Lose changes?");
                    /* always return false in this instance
                     * we are working async, so no blocking
                     * */
                    return false;
                }

                // Load all the reports for this company
                $scope.loadReports($scope.company.id.id);

            }

            return true;
        }

        $scope.addDirtyWatchers = function ()
        {
            $scope.$watch("company.isDirty", function (oldValue, newValue)
            {

                if (oldValue == true)
                {
                    $scope.companies.isDirty = true;
                }
            });

            $scope.$watch("user.isDirty", function (oldValue, newValue)
            {

                if (oldValue == true)
                {
                    $scope.users.isDirty = true;
                }
            });

            $scope.$watch("role.isDirty", function (oldValue, newValue)
            {

                if (oldValue == true)
                {
                    $scope.roles.isDirty = true;
                }
            });

            $scope.$watch("report.isDirty", function (oldValue, newValue)
            {

                if (oldValue == true)
                {
                    $scope.reports.isDirty = true;
                }
            });

        }

        $scope.userChanged = function ()
        {
            $scope.userRole = $scope.user.fullRoles[0];
        }

        /**************************************
         *         Initialisation
         ***************************************/
        $scope.loadCompanies();
        $scope.loadRoles();
        $scope.addDirtyWatchers();


    }
);




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
        $scope.confirmationDialogId = "#confirmationDialog";
        $scope.tabsId = "#tabs";
        $scope.deletedCompanies = [];
        $scope.deletedUsers = [];
        $scope.users = [];
        $scope.roles = [];
        $scope.companies = [];
        $scope.currentCompany;
        $scope.confirmationmessage = "Confirm?";
        $scope.confirmationtitle = "Confirm";
        $scope.canNavigateOnDirty = false;
        $scope.newSelectedTabIndex = 1;
        $scope.company;
        $scope.user;
        $scope.sessionId = $cookies.sessionid;


        /*****************************************
         *          Functions
         ***************************************/
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

        $scope.addUser = function()
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
        /*************************************
         *               REST Services
         *************************************/
        $scope.loadCompanies = function ()
        {
            $http.get("/rest/data/companies/load?sessionid="+$scope.sessionId).success(
                function (reply)
                {
                    $scope.companies = reply;
                    $scope.company = $scope.companies[0];
                }
            ).error(
                function(e)
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

            $http.put("/rest/data/companies/save?sessionid="+$scope.sessionId, $scope.companies).success(
                function (reply)
                {
                    $scope.companies = reply;
                    $scope.company = $scope.companies[0];
                }
            );
        }

        $scope.loadUsers = function (companyId)
        {
            $http.get("/rest/data/users/"+companyId+"?sessionid="+$scope.sessionId).success(
                function (reply)
                {
                    $scope.users = reply;
                    $scope.user = $scope.users[0];
                }
            );
        }

        $scope.saveSelectedCompanyUsers = function()
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

            $http.put("/rest/data/users/"+companyId+"?sessionid="+$scope.sessionId, $scope.users).success(
                function (reply)
                {
                    $scope.users = reply;
                    $scope.user = $scope.users[0];
                }
            );
        }

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
                    // always return false in this instance
                    // we are working async, so no blocking
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
            }
            else if (ui.newPanel.attr("id") == "companyTab")
            {
                $scope.newSelectedTabIndex = 0;

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

        }

        /**************************************
         *         Initialisation
         ***************************************/
        $scope.loadCompanies();
        $scope.addDirtyWatchers();


    }
);




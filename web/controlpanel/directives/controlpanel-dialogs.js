/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/22
 * Time: 09:34
 * To change this template use File | Settings | File Templates.
 */

var controlPanelDialogs = angular.module("controlPanelDialogs", []);

controlPanelDialogs.directive("deleteCompanyDialog", function ()
    {
        return {
            restrict:'E',
            replace:true,
            templateUrl:"/controlpanel/views/delete-company-dialog.partial.html",
            link:function (scope, element, attrs)
            {
                // hide
                element.hide();

            }
        };
    }
);

controlPanelDialogs.directive("deleteRoleDialog", function ()
    {
        return {
            restrict:'E',
            replace:true,
            templateUrl:"/controlpanel/views/delete-role-dialog.partial.html",
            link:function (scope, element, attrs)
            {
                // hide
                element.hide();

            }
        };
    }
);

controlPanelDialogs.directive("deleteUserDialog", function ()
    {
        return {
            restrict:'E',
            replace:true,
            templateUrl:"/controlpanel/views/delete-user-dialog.partial.html",
            link:function (scope, element, attrs)
            {
                // hide
                element.hide();

            }
        };
    }
);

controlPanelDialogs.directive("confirmationDialog", function ()
    {
        return {
            restrict:'E',
            replace:true,
            templateUrl:"/controlpanel/views/confirmation-dialog.partial.html",
            link:function (scope, element, attrs)
            {
                // hide
                element.hide();

            }
        };
    }
);


controlPanelDialogs.directive("addUserRoleDialog", function ()
    {
        return {
            restrict:'E',
            replace:true,
            templateUrl:"/controlpanel/views/add-user-role-dialog.partial.html",
            link:function (scope, element, attrs)
            {
                // hide
                element.hide();

            }
        };
    }
);

controlPanelDialogs.directive("deleteUserRoleDialog", function ()
    {
        return {
            restrict:'E',
            replace:true,
            templateUrl:"/controlpanel/views/delete-user-role-dialog.partial.html",
            link:function (scope, element, attrs)
            {
                // hide
                element.hide();

            }
        };
    }
);

controlPanelDialogs.directive("addReportRoleDialog", function ()
    {
        return {
            restrict:'E',
            replace:true,
            templateUrl:"/controlpanel/views/add-report-role-dialog.partial.html",
            link:function (scope, element, attrs)
            {
                // hide
                element.hide();

            }
        };
    }
);

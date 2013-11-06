/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/11/04
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */

var designerModule = angular
    .module("designerModule", ["jqComponents", "ngCookies", "newReportWizardModule", "bibUnslider"]);

designerModule.controller("designerController",
    function ($scope, $http, $window, $cookies)
    {
        $scope.navigationPanel = {name:"Navigation", url:"/designer/views/designer-navigation.partial.html"};
        $scope.newReportWizardPanel = {name:"New Report Wizard", url:"/designer/views/new-report-wizard.partial.html"};
        $scope.sessionId = $cookies.sessionid;
        $scope.activeContent;

        $scope.newReport = function ()
        {
            $scope.activeContent = $scope.newReportWizardPanel;
        }

    });
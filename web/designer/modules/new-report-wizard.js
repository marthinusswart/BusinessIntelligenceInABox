/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/11/04
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */

var newReportWizardModule = angular
    .module("newReportWizardModule", ["jqComponents", "ngCookies"]);

newReportWizardModule.controller("newReportWizardController",
    function ($scope, $http, $window, $cookies)
    {
        $scope.selectedLayout = {};
    });

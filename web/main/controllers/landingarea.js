/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
var landingAreaModule = angular
    .module("landingAreaModule", ["jqComponents"])  ;

landingAreaModule.controller("landingAreaController",
    function($scope, $http)
    {
        $scope.loadReport = function(reportId)
        {
            console.info("Loading report " + reportId);
        }
    }
)   ;
/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
var landingAreaModule = angular
    .module("landingAreaModule", ["jqComponents", "ngCookies"])  ;

landingAreaModule.controller("landingAreaController",
    function($scope, $http, $window, $cookies, landingAreaService)
    {
        $scope.navigation = {name:"Navigation", url:"/main/views/landing-area-navigation.partial.html"};
        $scope.sessionId = $cookies.sessionid;
        $scope.reports = [];

        $scope.loadReport = function(reportId)
        {
            console.info("Loading report " + reportId);
        }

        $scope.uploadData = function()
        {
            $window.location.href = "/main/upload.jsp";
            //location.reload();
        }

        $scope.loadUserReports = function()
        {
            landingAreaService.loadUserReports($scope.sessionId, $scope.loadUserReportsCallback);

        }

        $scope.loadUserReportsCallback = function(reports)
        {
            $scope.reports = reports
        }

        $scope.loadUserReports();
    }
)   ;

landingAreaModule.service("landingAreaService",
    function($http)
    {
        /*************************************
         *          REST Services
         *************************************/
        this.loadUserReports = function (sessionId, callback)
        {
            $http.get("/rest/data/user_reports/" + sessionId).success(
                function (reply)
                {
                    callback(reply);
                }
            ).error(function (data, status, headers, config)
                {
                    console.error(data);
                });
        }

    }   );
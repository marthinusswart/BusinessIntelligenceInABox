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
        $scope.navigationPanel = {name:"Navigation", url:"/main/views/landing-area-navigation.partial.html"};
        $scope.manageDataPanel = {name:"Manage Data", url:"/main/views/manage-data.partial.html"};
        $scope.sessionId = $cookies.sessionid;
        $scope.reports = [];
        $scope.reportDataTypes = [];
        $scope.reportDataType = new ReportDataType();
        $scope.reportDataTypeData = [];
        $scope.activeContent;

        $scope.loadReport = function(reportId)
        {
            console.info("Loading report " + reportId);
        }

        $scope.uploadData = function()
        {
            $window.location.href = "/main/upload.jsp";
        }

        $scope.loadUserReports = function()
        {
            landingAreaService.loadUserReports($scope.sessionId, $scope.loadUserReportsCallback);

        }


        $scope.loadUserReportsCallback = function(reports)
        {
            $scope.reports = reports                             ;
        }

        $scope.manageData = function()
        {
            $scope.activeContent = $scope.manageDataPanel;
            $scope.loadCompanyReportDataTypes();
        }

        $scope.loadCompanyReportDataTypes = function()
        {
            landingAreaService.loadCompanyReportDataTypes($scope.sessionId, $scope.loadCompanyReportDataTypesCallback);
        }

        $scope.loadCompanyReportDataTypesCallback = function(reportDataTypes)
        {
            $scope.reportDataTypes = reportDataTypes;
            $scope.reportDataType.activeType = reportDataTypes[0];
            console.info("Report Types: " + reportDataTypes[0]);
        }

        $scope.loadReportDataTypeData = function()
        {
           console.info( $scope.reportDataType.activeType.name );
           landingAreaService.loadReportDataTypeData($scope.reportDataType.activeType.name, $scope.sessionId, $scope.loadReportDataTypeDataCallback);
        }

        $scope.loadReportDataTypeDataCallback = function(reportDataTypeData)
        {
            console.info("Report Type Data: " + reportDataTypeData);
            $scope.reportDataTypeData = reportDataTypeData;
        }


        /***************************
         *      Init functions
         ***************************/
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

        this.loadCompanyReportDataTypes = function (sessionId, callback)
        {
            $http.get("/rest/data/company_datatypes/" + sessionId).success(
                function (reply)
                {
                    callback(reply);
                }
            ).error(function (data, status, headers, config)
                {
                    console.error(data);
                });
        }

        this.loadReportDataTypeData = function (reportDataType, sessionId, callback)
        {
            $http.get("/rest/data/company_datatypedata/" + reportDataType + "?sessionid=" + sessionId).success(
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
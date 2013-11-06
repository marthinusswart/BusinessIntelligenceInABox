/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
var landingAreaModule = angular
    .module("landingAreaModule", ["jqComponents", "ngCookies", "ngGrid", "bibManageData"]);

landingAreaModule.controller("landingAreaController",
    function ($scope, $http, $window, $cookies, $manageData, landingAreaService)
    {
        $scope.navigationPanel = {name:"Navigation", url:"/main/views/landing-area-navigation.partial.html"};
        $scope.manageDataPanel = {name:"Manage Data", url:"/main/views/manage-data.partial.html"};
        $scope.sessionId = $cookies.sessionid;
        $scope.reports = [];
        $scope.reportDataTypes = new ReportDataTypeArray();
        $scope.reportDataType = new ReportDataType();
        $scope.reportDataTypeData = new ReportDataTypeData();
        $scope.activeContent;
        $scope.selectedItem;
        $scope.hoveredOverItem;
        $scope.reportDataTypeDetail = new ReportDataTypeDetail();

        $scope.testData = [
            {name:"Moroni", age:50},
            {name:"Tiancum", age:43},
            {name:"Jacob", age:27},
            {name:"Nephi", age:29},
            {name:"Enos", age:34}
        ];

        $scope.gridOptions = { data:'reportDataTypeDetail',
            showGroupPanel:true,
            multiSelect:false
        };

        $scope.loadReport = function (reportId)
        {
            console.info("Loading report " + reportId);
        }

        $scope.uploadData = function ()
        {
            $window.location.href = "/main/upload.jsp";
        }

        $scope.designReport = function ()
        {
            $window.location.href = "/designer";
        }

        $scope.loadUserReports = function ()
        {
            landingAreaService.loadUserReports($scope.sessionId, $scope.loadUserReportsCallback);

        }

        $scope.loadUserReportsCallback = function (reports)
        {
            $scope.reports = reports;
        }

        /*
         Load the manage data view
         */
        $scope.manageData = function ()
        {
            $scope.activeContent = $scope.manageDataPanel;
            $manageData.loadCompanyReportDataTypes($scope.sessionId, $scope.reportDataTypes, $scope.reportDataType);
        }

        /*****************************************************************
         Manage data functions, should refactor into its own controller
         *****************************************************************/
        $scope.loadReportDataTypeData = function ()
        {
            $manageData.loadReportDataTypeData($scope.sessionId, $scope.reportDataType.activeType.name, $scope.reportDataTypeData)
        }

        $scope.loadReportDataDetail = function ()
        {
            landingAreaService.loadReportDataTypeDetail($scope.selectedItem.id.id, $scope.sessionId, $scope.loadReportDataTypeDetailCallback);

            // show the details tab
            $("#tabs").tabs("option", "active", 1);
        }

        $scope.loadReportDataTypeDetailCallback = function (reportDataTypeDetail)
        {
            console.info("Report Type Detail: " + reportDataTypeDetail);
            $scope.reportDataTypeDetail = reportDataTypeDetail;


            // The object from the back end service is an anonymous object
            // we then embed the real object inside the anonymous object for later use
            reportDataTypeDetail.embeddedItem = new ReportDataTypeDataItem(reportDataTypeDetail)
            console.info(reportDataTypeDetail.embeddedItem.showMessage());


        }

        $scope.mouseOverGridItem = function (gridItem)
        {
            $scope.hoveredOverItem = gridItem;
        }

        $scope.mouseLeaveGridItem = function (gridItem)
        {
            $scope.hoveredOverItem = null;
        }

        $scope.getGridItemClass = function (gridItem)
        {

            if (gridItem === $scope.selectedItem)
            {
                return "rounded-border md-selected-data-grid-item";
            }
            else if (gridItem === $scope.hoveredOverItem)
            {
                return "rounded-border md-hover-data-grid-item";
            }
            else
            {
                return "rounded-border md-data-grid-item";
            }
        }

        $scope.loadSelectedGridItem = function (gridItem)
        {
            $scope.selectedItem = gridItem;
            // Clear detail
            $scope.reportDataTypeDetail = [];
        }

        /****************************
         Manage data functions - END
         ****************************/


        /***************************
         *      Init functions
         ***************************/
        $scope.loadUserReports();
        $manageData.setRESTProvider(landingAreaService);

    }
);

landingAreaModule.service("landingAreaService",
    function ($http)
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

        this.loadReportDataTypeDetail = function (reportDataTypeId, sessionId, callback)
        {
            $http.get("/rest/data/company_datatypedetail/" + reportDataTypeId + "?sessionid=" + sessionId).success(
                function (reply)
                {
                    callback(reply);
                }
            ).error(function (data, status, headers, config)
                {
                    console.error(data);
                });
        }

    });
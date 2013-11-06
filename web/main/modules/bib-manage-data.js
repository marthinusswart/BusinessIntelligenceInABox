/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/30
 * Time: 7:11 AM
 * To change this template use File | Settings | File Templates.
 */
(function (windows, angular)
{
    'use strict';


    var ManageData = angular.module("bibManageData", []).

        factory("$manageData", ['$rootScope', '$browser', function ()
    {


        // The manage data object
        ManageData = function ()
        {
            this.restProvider = {};
            this.reportDataTypes = {};
            this.reportDataTypeData = {};
            this.reportDataType = {};
            this.hoveredOverItem = {};

            this.setRESTProvider = function (restProvider)
            {
                this.restProvider = restProvider;
            }

            this.loadCompanyReportDataTypes = function (sessionId, reportDataTypes, reportDataType)
            {
                this.reportDataTypes = reportDataTypes;
                this.reportDataType = reportDataType;
                this.restProvider.loadCompanyReportDataTypes(sessionId, this.loadCompanyReportDataTypesCallback);
            }

            this.loadCompanyReportDataTypesCallback = function (reportDataTypes)
            {
                manageData.reportDataTypes.types = reportDataTypes;
                manageData.reportDataType.activeType = reportDataTypes[0];
            }

            this.loadReportDataTypeData = function (sessionId, activeTypeName, reportDataTypeData)
            {
                this.reportDataTypeData = reportDataTypeData;
                this.restProvider.loadReportDataTypeData(activeTypeName, sessionId, this.loadReportDataTypeDataCallback);

            }

            this.loadReportDataTypeDataCallback = function (reportDataTypeData)
            {

                manageData.reportDataTypeData.data = reportDataTypeData;

                for (var index in reportDataTypeData)
                {
                    // The object from the back end service is an anonymous object
                    // we then embed the real object inside the anonymous object for later use
                    reportDataTypeData[index].embeddedItem = new ReportDataTypeDataItem(reportDataTypeData[index])

                }

            }

        };

        var manageData = new ManageData();
        return manageData;
    }
    ]);
})(window, window.angular);

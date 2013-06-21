/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */

var controlPanelModule = angular
    .module("controlPanel", ["jqComponents"])  ;

controlPanelModule.controller("controlPanelController",
    function($scope, $http)
    {

        $scope.addCompany = function()
        {
            $scope.company = {"name": "New", "companyRegistrationNo": "0000", "city": "City", "country": "Country"};
            $scope.companies.push($scope.company);
        }

        $scope.loadCompanies = function()
        {
            $http.get("/rest/data/companies/load?email=marthinus.swart@intellibps.com").success(
                function(reply)
                {
                    $scope.companies = reply;
                    $scope.company = $scope.companies[0];
                }
            );
        }

        $scope.saveCompanies = function()
        {
            $http.put("/rest/data/companies/save?email=marthinus.swart@intellibps.com", $scope.companies).success(
                function(reply)
                {
                    //$scope.companies = reply;
                    //$scope.company = $scope.companies[0];
                }
            );
        }

        $scope.loadCompanies();
    }
)   ;

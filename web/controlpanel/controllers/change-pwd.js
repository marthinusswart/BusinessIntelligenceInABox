/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/28
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */

var changePasswordModule = angular
    .module("changePasswordModule", ["jqComponents", "ngCookies"]);

changePasswordModule.controller("changePasswordController",
    function ($scope, $http, $cookies)
    {
        $scope.sessionid = $cookies.sessionid;
        $scope.password = "";
        $scope.password2 = "";

        $scope.doCancel = function ()
        {
            $("#action").val("login");
            $("#password-form").submit();
        }

        $scope.doChange = function ()
        {
            alert("Checking passwords P1: " + $scope.password + " P2: " + $scope.password2);
            if (($scope.password != "") && ($scope.password == $scope.password2))
            {
                $("#action").val("newpwd");
                $("#password-form").submit();
            }
            else
            {
                alert("Not the same");

            }
        }
    }
);

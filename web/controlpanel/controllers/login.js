var loginModule = angular
    .module("loginModule", ["jqComponents"])  ;

loginModule.controller("loginController",
        function($scope, $http)
        {
            $scope.loginButtonTitle = "Login";
            $scope.email = "anonymous@anonymous.com";

            $scope.doLogin = function()
            {
                $("#action").val("login");
                $("#login-form").submit();
            }

            $scope.changePassword = function()
            {
                $("#action").val("changepwd");
                $("#login-form").submit();
            }
        }
)   ;

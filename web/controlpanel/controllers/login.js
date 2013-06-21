var loginModule = angular
    .module("loginModule", ["jqComponents"])  ;

loginModule.controller("loginController",
        function($scope, $http)
        {
            $scope.loginButtonTitle = "Login";
            $scope.email = "anonymous@anonymous.com";

            $scope.doLogin = function()
            {
                $("#login-form").submit();
            }
        }
)   ;

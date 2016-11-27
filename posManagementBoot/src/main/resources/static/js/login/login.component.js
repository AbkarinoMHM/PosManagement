'use strict';

// Register `login` component, along with its associated controller and template
angular.module('login').component('loginForm', {
    templateUrl: 'templates/login/login.template.html',
    controller: function LoginController($scope, $rootScope, $location, AuthenticationService) {
// reset login status
        AuthenticationService.ClearCredentials();
        $rootScope.loggedin = false;
        $scope.login = function () {
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.userEmail, $scope.password, function (response) {
                if (response.success) {
                    toastr.success('Welcome back ' + $scope.userEmail + "!", 'Log in succeeded.');
                    AuthenticationService.SetCredentials($scope.userEmail, $scope.password);
                    $rootScope.loggedin = true;
                    $location.path('/');
                } else {
                    $rootScope.loggedin = false;
                    toastr.error(response.message, 'Log in failed!');
                    //$scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
        }
    }
});
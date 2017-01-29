'use strict';

// Define the `posApp` module
angular.module('posApp', ['ngCookies',
    'ngMaterial', 'ngMessages', 'ngSanitize', 'ngRoute','ui.bootstrap', 'ui.grid',
    'ui.grid.selection', 'ui.grid','ui.grid.pagination', 'ui.grid.autoResize', 'ui.grid.pinning', 'ui.grid.exporter', 'login', 'region', 'project', 'user',
    'area','merchantBranch', 'vendorBranch', 'serviceCenter', 'technician', 'posType', 'posVendor', 'pos'
]).controller('mainController', function ($rootScope, $scope, $log, $http, $location, AuthenticationService) {

    $log.log("Globals:");
    $log.log($rootScope.globals);

    var self = this;

    self.settings = {};
    $scope.customerName = 'Customer';
    $scope.vendorName = 'Vendor';

    //send get request to the settings service
    $http.get('services/setting').then(function (response) {
        toastr.success('Application settings are loaded successfully.', 'Success');
        // Stored the returned data into scope
        self.settings = response.data;
        for (var i = 0; i < self.settings.length; i++) {
            switch (self.settings[i].settingName) {
                case 'customerName':
                    $rootScope.globals.customerName = self.settings[i].settingValue;
                    break;
                case 'vendorName':
                    $rootScope.globals.vendorName = self.settings[i].settingValue;
            }
        }

    }, function (data) {
        toastr.error('Failed to load application settings.', 'Error');
    });

    $scope.logout = function () {
        AuthenticationService.ClearCredentials();
        $location.path('#!/login');
    }
});


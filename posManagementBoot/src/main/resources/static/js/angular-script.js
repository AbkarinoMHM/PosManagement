// Application module
var crudApp = angular.module('crudApp', []);
crudApp.controller("DbController", ['$scope', '$http', function ($scope, $http) {

// Function to get employee details from the database
    getInfo();
    function getInfo() {
// Sending request to EmpDetails.php files
        $http.get('region').success(function (data) {
// Stored the returned data into scope
            $scope.details = data;
        });
    }

// Enabling show_form variable to enable Add employee button
    $scope.show_form = true;
// Function to add toggle behaviour to form
    $scope.formToggle = function () {
        $('#addRegionForm').slideToggle();
        $('#editForm').css('display', 'none');
    }

    $scope.insertInfo = function (info) {
        $http.post('region', {
            "regionName": info.regionName
        }).success(function (data) {
            getInfo();
            $('#addRegionForm').css('display', 'none');
        });
    }

    $scope.deleteInfo = function (info) {
        $http.delete('region/' + info.regionId).success(function (data) {
            getInfo();
        });
    }
    $scope.currentRegion = {};
    $scope.editInfo = function (info) {
        $scope.currentRegion = info;
        $('#addRegionForm').slideUp();
        $('#editForm').slideToggle();
    }
    $scope.UpdateInfo = function (info) {
        $http.put('region/' + info.regionId, {
            "regionName": info.regionName
        }).success(function (data) {
            $scope.show_form = true;
            getInfo();
        });
    }

    $scope.updateMsg = function (regionID) {
        $('#editForm').css('display', 'none');
    }
}]);
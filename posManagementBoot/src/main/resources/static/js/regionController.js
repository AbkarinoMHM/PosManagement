// Application module
angular.module('crudApp', ['ngMaterial'])
    .controller("regionController", regionController);

//Controller main
function regionController($scope, $http, $mdDialog) {

    // Function to get regions from the database
    getInfo();
    function getInfo() {
        // Sending request to region get service
        $http.get('../../services/region').success(function (data) {
            // Stored the returned data into scope
            $scope.details = data;
        });
    }

    // Enabling show_form variable to enable Add region button
    $scope.show_form = true;

    //Controller Entry point
    $scope.showAddForm = function (event) {
        $scope.regionInfo = {};
        $mdDialog.show({
            clickOutsideToClose: true,
            scope: $scope,
            preserveScope: true,
            templateUrl: '../region/templates/addForm.html',
            parent: angular.element(document.body),
            targetEvent: event,
            controller: regionController
        });
    };


    $scope.insertInfo = function (info) {

    }

    $scope.deleteInfo = function (info) {
        //show confirmatin dialog
        var confirm = $mdDialog.confirm()
            .title('Are you sure to delete this record?')
            .textContent('Record will be deleted permanently.')
            .ariaLabel('Delete Region')
            .targetEvent(event)
            .ok('Yes')
            .cancel('No');
        $mdDialog.show(confirm).then(function () {
            $http.delete('../../services/region/' + info.regionId).success(function (data) {
                getInfo();
            });
        }, function () {
            //delete cancelled
        });
    }

    $scope.currentRegion = {};
    $scope.editInfo = function (info) {
        $scope.currentRegion = info;
        $('#addRegionForm').slideUp();
        $('#editForm').slideToggle();
    }

    $scope.UpdateInfo = function (info) {
        $http.put('../../services/region/' + info.regionId, {
            "regionName": info.regionName
        }).success(function (data) {
            $scope.show_form = true;
            getInfo();
        });
    }

    //Dialog functions
    $scope.updateMsg = function (regionID) {
        $('#editForm').css('display', 'none');
    }

    $scope.hide = function () {
        $mdDialog.hide();
    };

    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    //Add region button pressed
    $scope.insertRegion = function (region) {
        $http.post('../../services/region', {
            "regionName": region.regionName
        }).success(function (data) {
            getInfo();
        });
        $mdDialog.hide();
    };
};
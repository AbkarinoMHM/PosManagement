'use strict';

// Register `phoneList` component, along with its associated controller and template
angular.module('region').component('regionList', {
    templateUrl: 'region/region.template.html',
    controller: function regionController($scope, $http, $mdDialog) {

        // Function to get regions from the database
        function getInfo() {
            // Sending request to region get service
            $http.get('services/region').success(function (data) {
                // Stored the returned data into scope
                $scope.details = data;
            });
        }

        //get regions
        getInfo();

        // Enabling show_form variable to enable Add region button
        $scope.show_form = true;

        //Show add form
        $scope.showAddForm = function (event) {
            $scope.regionInfo = {};
            $mdDialog.show({
                clickOutsideToClose: true,
                scope: $scope,
                preserveScope: true,
                templateUrl: 'region/addForm.template.html',
                parent: angular.element(document.body),
                targetEvent: event,
                controller: regionController
            });
        };

        //Show edit form
        $scope.currentRegion = {};
        $scope.showEditForm = function ($info) {
            $scope.currentRegion = $info;
            $mdDialog.show({
                clickOutsideToClose: true,
                scope: $scope,
                preserveScope: true,
                templateUrl: 'region/editForm.template.html',
                parent: angular.element(document.body),
                targetEvent: $info,
                controller: function () {
                    return self;
                },
                controllerAs: 'ctrl'
            });
        };

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
                $http.delete('services/region/' + info.regionId).success(function (data) {
                    getInfo();
                });
            }, function () {
                //delete cancelled
            });
        }

        $scope.hide = function () {
            $mdDialog.hide();
        };

        $scope.cancel = function () {
            $mdDialog.cancel();
        };

        //Add region button pressed
        $scope.insertRegion = function (region) {
            if (region.regionName) {
                $http.post('services/region', {
                    "regionName": region.regionName
                }).then(function (data) {
                    getInfo();
                },function (response) {
                    $mdDialog.show(
                        $mdDialog.alert()
                            .parent(angular.element(document.querySelector('#popupContainer')))
                            .clickOutsideToClose(true)
                            .title('Server error')
                            .htmlContent('<table>' +
                                '<tr><td><strong>Error </strong></td><td>'+ response.data.error +'</td></tr>' +
                                '<tr><td><strong>Exception </strong></td><td>'+ response.data.exception +'</td></tr>' +
                                '<tr><td><strong>Message </strong></td><td>'+ response.data.message +'</td></tr>' +
                                '<tr><td><strong>Path </strong></td><td>'+ response.data.path +'</td></tr>' +
                                '<tr><td><strong>Status </strong></td><td>'+ response.data.status +'</td></tr>' +
                                '<tr><td><strong>Timestamp </strong></td><td>'+ response.data.timestamp +'</td></tr>' +
                                '</table>')
                            .ariaLabel('Error')
                            .ok('Got it!')
                            .targetEvent(response)
                    );
                });
                $mdDialog.hide();
            }
        };

        //Update region button pressed
        $scope.updateRegion = function (region) {
            $http.put('services/region/' + region.regionId, {
                "regionName": region.regionName
            }).success(function (data) {
                getInfo();
            });
            $mdDialog.hide();
        };
    }
});

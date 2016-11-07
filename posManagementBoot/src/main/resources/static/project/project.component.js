'use strict';

// Register `phoneList` component, along with its associated controller and template
angular.module('project').component('projectsList', {
    templateUrl: 'project/project.template.html',
    controller: function projectController($scope, $http, $mdDialog) {

        // Function to get projects from the database
        function getInfo() {
            // Sending request to project get service
            $http.get('services/project').success(function (data) {
                // Stored the returned data into scope
                $scope.details = data;
            });
        }

        //get projects
        getInfo();

        // Enabling show_form variable to enable Add  button
        $scope.show_form = true;

        //Show add form
        $scope.showAddForm = function (event) {
            $scope.info = {};
            $mdDialog.show({
                clickOutsideToClose: true,
                scope: $scope,
                preserveScope: true,
                templateUrl: 'project/addForm.template.html',
                parent: angular.element(document.body),
                targetEvent: event,
                controller: projectController
            });
        };

        //Show edit form
        $scope.currentRecord = {};
        $scope.showEditForm = function ($info) {
            $scope.currentRecord = $info;
            $mdDialog.show({
                clickOutsideToClose: true,
                scope: $scope,
                preserveScope: true,
                templateUrl: 'project/editForm.template.html',
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
                .ariaLabel('Delete Project')
                .targetEvent(event)
                .ok('Yes')
                .cancel('No');
            $mdDialog.show(confirm).then(function () {
                $http.delete('services/project/' + info.projectId).success(function (data) {
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

        //Add  button pressed
        $scope.insertRecord = function (record) {
            if (record.projectName) {
                $http.post('services/project', {
                    "projectName": record.projectName
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

        //Update button pressed
        $scope.updateRecord = function (record) {
            $http.put('services/project/' + record.projectId, {
                "projectName": record.projectName
            }).success(function (data) {
                getInfo();
            });
            $mdDialog.hide();
        };
    }
});

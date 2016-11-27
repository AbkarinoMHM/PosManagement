/**
 * Created by saado on 11/18/2016.
 */
'use strict';

angular.module('posApp')
    .factory('HttpService',
        ['CommonService', '$http',
            function (CommonService, $http) {
                return {

                    httpGet: function (serviceUrl) {
                        // Sending request to project get service
                        return $http.get(serviceUrl + CommonService.generateTimePart(serviceUrl));
                    },
                    httpPost: function (serviceUrl, objectToPost) {
                        return $http.post(serviceUrl + CommonService.generateTimePart(serviceUrl), objectToPost);
                    },
                    httpPut: function (serviceUrl, objectToPut) {
                        return $http.put(serviceUrl + CommonService.generateTimePart(serviceUrl), objectToPut);
                    },
                    httpDelete: function (serviceUrl) {
                        return $http.delete(serviceUrl + CommonService.generateTimePart(serviceUrl));
                    }

                }
            }])

    .factory('DialogService',
        ['$mdDialog',
            function ($mdDialog) {
                return {
                    //return time to be added to a url in http request
                    showHttpErrorDialog: function (response) {
                        return $mdDialog.show(
                            $mdDialog.alert()
                                .parent(angular.element(document.querySelector('#popupContainer')))
                                .clickOutsideToClose(true)
                                .title('Server error')
                                .htmlContent('<table>' +
                                    '<tr><td><strong>Error </strong></td><td>' + response.data.error + '</td></tr>' +
                                    '<tr><td><strong>Exception </strong></td><td>' + response.data.exception + '</td></tr>' +
                                    '<tr><td><strong>Message </strong></td><td>' + response.data.message + '</td></tr>' +
                                    '<tr><td><strong>Path </strong></td><td>' + response.data.path + '</td></tr>' +
                                    '<tr><td><strong>Status </strong></td><td>' + response.data.status + '</td></tr>' +
                                    '<tr><td><strong>Timestamp </strong></td><td>' + response.data.timestamp + '</td></tr>' +
                                    '</table>')
                                .ariaLabel('Error')
                                .ok('Got it!')
                                .targetEvent(response)
                        );
                    },
                    showConfirmDialog: function (label, title, message) {
                        var confirm = $mdDialog.confirm()
                            .title(title)
                            .textContent(message)
                            .ariaLabel(label)
                            .targetEvent(event)
                            .ok('Yes')
                            .cancel('No');
                        return $mdDialog.show(confirm);

                    },
                    showTemplateDialog: function (addTemplateUrl, eventData, scope, controller) {
                        return $mdDialog.show({
                            clickOutsideToClose: true,
                            scope: scope,
                            preserveScope: true,
                            templateUrl: addTemplateUrl,
                            parent: angular.element(document.body),
                            targetEvent: eventData,
                            controller: controller,
                            controllerAs: 'ctrl'
                        });
                    },
                    hideDialog: function () {
                        return $mdDialog.hide();
                    },
                    cancelDialog: function () {
                        return $mdDialog.cancel();
                    },
                    toastrSuccess: function (moduleName, functionVerb) {
                        return toastr.success('A ' + moduleName + ' is ' + functionVerb + ' successfully.', 'Success');
                    },
                    toastrError: function (moduleName, functionVerb) {
                        toastr.error('Failed to ' + functionVerb + ' ' + moduleName + '!', 'Error');
                    },
                    toastrWarning: function (msg) {
                        toastr.warning(msg);
                    }

                }
            }])

    .factory('ButtonsSheetService', ['$mdBottomSheet',
        function ($mdBottomSheet) {
            return {
                showButtonsSheet: function (buttonsSheetTemplate, scope, sheetController) {
                    return $mdBottomSheet.show({
                        templateUrl: buttonsSheetTemplate,
                        scope: scope,
                        preserveScope: true,
                        controller: sheetController
                    });
                }
            }
        }

    ])
    .factory('CommonService',
        [
            function () {
                return {
                    //return time to be added to a url in http request
                    generateTimePart: function (serviceUrl) {
                        return (serviceUrl.indexOf('?') !== -1 ? '&etc=' : '?etc=') + +new Date().getTime();
                    },
                    calculateGridHeight:function (gridDataLength) {
                        var rowHeight = 30; // your row height
                        var headerHeight = 30; // your header height
                        return {
                            height: (gridDataLength * rowHeight + 2*rowHeight + headerHeight) + "px"
                        };
                    }
                }
            }]);
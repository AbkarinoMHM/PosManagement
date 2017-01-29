'use strict';

// Register `project` component, along with its associated controller and template
angular.module('technician').component('techniciansList', {
    templateUrl: 'templates/common/gridWithFilterAndActions.template.html',
    controller: function projectController($scope, $log, $mdBottomSheet, HttpService,
                                           DialogService, ButtonsSheetService, CommonService) {
        /*
         Initialization section
         */
        //component general settings
        var self = this;
        self.moduleName = 'Technician';
        self.baseServiceUrl = 'services/technician';
        self.baseTemplateUrl = 'templates/technician';
        self.commonTemplateUrl = 'templates/common';

        //Paganation settings
        var paginationOptions = {
            pageNumber: 1,
            pageSize: 20,
            sort: null
        };

        //filters
        $scope.searchTags = [];

        //buttons sheet items
        $scope.buttonSheetItems = [
            {name: 'Add New', icon: 'fa-plus'}
        ];
        //the current record to be edited
        $scope.currentRecord = {};
        //the selected record in the grid
        $scope.selectedRecord = {};
        /**************************************************************************************************************/
        /*
         Grid section
         */
        /**
         * Set grid options
         */
        $scope.gridOptions = {
            paginationPageSizes: [5, 10, 20, 50, 100,200],
            enableColumnMenus: true,
            enableGridMenu: true,
            useExternalPagination: true,
            enableSorting: true,
            enableFiltering: false,
            enableRowHeaderSelection: true,
            enableSelectAll: true,
            selectionRowHeaderWidth: 35,
            rowHeight: 30,
            multiSelect: true,
            paginationPageSize: paginationOptions.pageSize,
            exporterCsvFilename: self.moduleName + '.csv',
            exporterMenuPdf: false,
            exporterIsExcelCompatible: true,
            columnDefs: [
                {name: 'id', displayName: "ID", pinnedLeft: true, headerTooltip: "Filter Name: id"},
                {name: 'name', displayName: "Technician Name", headerTooltip: "Filter Name: name"},
                {name: 'title', displayName: "Technician Title", headerTooltip: "Filter Name: title"},
                {name: 'rate', displayName: "Technician Rate", headerTooltip: "Filter Name: rate"},
                {name: 'mobile', displayName: "Technician Mobile", headerTooltip: "Filter Name: mobile"},
                {name: 'address', displayName: "Technician Address", headerTooltip: "Filter Name: address"},
                {name: 'tel', displayName: "Technician Tel", headerTooltip: "Filter Name: tel"},
                {
                    name: 'Edit', maxWidth: 50, displayName: "", pinnedRight: true, enableSorting: false,
                    enableCellEdit: false, enableFiltering: false, enableColumnMenus: false,
                    cellTemplate: '<div><a href="" ng-click="grid.appScope.showEditForm(row.entity)" title="Edit"><span class="glyphicon glyphicon-edit"></span></a><a href="" ng-click="grid.appScope.deleteInfo(row.entity)" title="Delete"><span class="glyphicon glyphicon-trash"></span></a></div>'
                }],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged(
                    $scope,
                    function (newPage, pageSize) {
                        paginationOptions.pageNumber = newPage;
                        paginationOptions.pageSize = pageSize;
                        getPaginatedInfo(newPage, pageSize);
                    });
                gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                    $scope.selectedRecord = row;
                    $log.log("Selected row:");
                    $log.log($scope.selectedRecord);
                });
            }
        };

        /**
         * calculate grid height
         * @returns {*|{height}}
         */
        $scope.getTableHeight = function () {
            return CommonService.calculateGridHeight($scope.gridOptions.data.length);
        };

        /**************************************************************************************************************/
        /*
         Filtration section
         */
        /**
         * Filter is changed
         * @param chip
         */
        $scope.filterChanged = function (chip) {
            getPaginatedInfo(paginationOptions.pageNumber, paginationOptions.pageSize);
        };
        /**************************************************************************************************************/
        /*
         Buttons sheet section
         */
        /**
         * buttons sheet item clicked
         * @param $index
         */
        $scope.listItemClick = function ($index, $event) {
            var clickedItem = $scope.buttonSheetItems[$index];
            switch (clickedItem['name']) {
                case 'Add New':
                    $scope.showAddForm($event);
            }
            $mdBottomSheet.hide(clickedItem);
        };

        /**
         * Show buttons sheet
         */
        $scope.showGridBottomSheet = function () {
            ButtonsSheetService.showButtonsSheet(self.commonTemplateUrl + '/actionsSheet.template.html', $scope, function () {
                    return self;
                }
            );
        };

        /**************************************************************************************************************/
        /*
         Data services section
         */
        //get projects
        getPaginatedInfo(paginationOptions.pageNumber, paginationOptions.pageSize);
        /**
         * Get a page of records from DB
         * @param page page number
         * @param size items per page
         */
        function getPaginatedInfo(page, size) {
            //create the search string
            var searchString = '';
            for (var i = 0; i < $scope.searchTags.length; i++) {
                searchString += ',' + $scope.searchTags[i];
            }
            if (searchString.length > 0) {
                searchString = searchString.substr(1);
            }
            $log.log("searchString");
            $log.log(searchString);
            // Sending request to project get service
            HttpService.httpGet(self.baseServiceUrl + '/get?page=' + page + '&size=' + size + '&search=' + searchString).then(function (response) {
                // Stored the returned data into scope
                $scope.gridOptions.data = response.data.content;
                $scope.gridOptions.totalItems = response.data.totalElements;
            }, function (response) {
                if (response.status == 404) {//no projects found
                    $scope.gridOptions.data = [];
                    $scope.gridApi.core.refresh();
                    DialogService.toastrWarning("No Technician found!");
                } else {
                    DialogService.showHttpErrorDialog(response);
                    DialogService.toastrError(self.moduleName + ' list', 'load');
                }

            });
        }

        /**
         * Get all records from DB
         */
        function getInfo() {
            HttpService.httpGet(self.baseServiceUrl).then(function (response) {
                $scope.details = response.data;
            }, function (response) {
                DialogService.showHttpErrorDialog(response);
                DialogService.toastrError(self.moduleName + ' list', 'load');
            });
        }

        /**
         * Insert a new record into DB
         * @param record The record to be inserted
         */
        $scope.insertRecord = function (record) {
            if (record.name) {
                HttpService.httpPost(self.baseServiceUrl, {
                    "name": record.name,
                    "title": record.title,
                    "rate": record.rate,
                    "mobile": record.mobile,
                    "address": record.address,
                    "tel": record.tel
                }).then(function (data) {
                    DialogService.toastrSuccess(self.moduleName, 'added');
                    getPaginatedInfo(paginationOptions.pageNumber, paginationOptions.pageSize);
                }, function (response) {
                    DialogService.showHttpErrorDialog(response);
                    DialogService.toastrError(self.moduleName, 'add');
                });
                DialogService.hideDialog()
            }
        };

        /**
         * Update a record
         * @param record The record to be updated
         */
        $scope.updateRecord = function (record) {
            HttpService.httpPut(self.baseServiceUrl + "/" + record.id, {
                "name": record.name,
                "title": record.title,
                "rate": record.rate,
                "mobile": record.mobile,
                "address": record.address,
                "tel":record.tel
            }).then(function (response) {
                DialogService.toastrSuccess(self.moduleName, 'updated');
                getPaginatedInfo(paginationOptions.pageNumber, paginationOptions.pageSize);
            }, function (response) {
                DialogService.showHttpErrorDialog(response);
                DialogService.toastrError(self.moduleName, 'update');

            });
            DialogService.hideDialog()
        };

        /**
         * Delete a record from DB
         * @param info The record to be deleted
         */
        $scope.deleteInfo = function (info) {
            //show confirmatin dialog
            DialogService.showConfirmDialog('Delete ' + self.moduleName, 'Are you sure to delete this record?', 'Record will be deleted permanently.')
                .then(function (response) {
                    HttpService.httpDelete(self.baseServiceUrl + "/" + info.id).then(function (data) {
                        DialogService.toastrSuccess(self.moduleName, 'deleted');
                        var visibleRows = $scope.gridApi.core.getVisibleRows().length;
                        $log.log('Visible rows: ' + visibleRows);
                        if (visibleRows <= 1 && paginationOptions.pageNumber > 1) {
                            paginationOptions.pageNumber--;
                        }
                        getPaginatedInfo(paginationOptions.pageNumber, paginationOptions.pageSize);
                    }, function (response) {
                        DialogService.showHttpErrorDialog(response);
                        DialogService.toastrError(self.moduleName, 'delete');
                    });
                }, function () {
                    //delete cancelled
                });
        }

        /**************************************************************************************************************/
        /*
         Dialogs section
         */
        /**
         * Show add new record dialog
         * @param event
         */
        $scope.showAddForm = function (event) {
            $scope.info = {};
            DialogService.showTemplateDialog(self.baseTemplateUrl + '/addForm.template.html', event, $scope, function () {
                return self;
            });
        };

        /**
         * Show edit record dialog.
         * @param $info The record to be edited
         */
        $scope.showEditForm = function ($info) {
            $scope.currentRecord = $info;
            DialogService.showTemplateDialog(self.baseTemplateUrl + '/editForm.template.html', $info, $scope, function () {
                return self;
            });

        };

        /**
         * Hide the visible dialog
         */
        $scope.hide = function () {
            DialogService.hideDialog();
        };

        /**
         * Cancel and close dialog
         */
        $scope.cancel = function () {
            DialogService.cancelDialog();
        };

        /**************************************************************************************************************/
    }
});

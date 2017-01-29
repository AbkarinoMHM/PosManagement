'use strict';

angular.module('pos').component('posList', {
    templateUrl: 'templates/common/gridWithFilterAndActions.template.html',
    controller: function posTypeController($scope, $rootScope, $log, $mdBottomSheet, HttpService,
                                           DialogService, ButtonsSheetService, CommonService) {
        /*
         Initialization section
         */
        //component general settings
        var self = this;
        $scope.moduleName = 'Pos';
        self.moduleNamePlural = 'Pos'
        self.baseServiceUrl = 'services/pos';
        self.baseTemplateUrl = 'templates/pos';
        self.commonTemplateUrl = 'templates/common';
        self.posTypeServiceUrl = 'services/postype';
        self.posVendorServiceUrl = 'services/posvendor';
        self.projectsServiceUrl = 'services/project';
        self.vendorBranchServiceUrl = 'services/vendorbranch';

        //types
        self.types = null;
        self.typeSearchText = null;

        //vendors
        self.vendors = null;
        self.vendorSearchText = null;

        //projects
        self.projects = null;
        self.projectSearchText = null;

        //vendor branches
        self.branches = null;
        self.branchSearchText = null;


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
            paginationPageSizes: [5, 10, 20, 50, 100, 200],
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
            exporterCsvFilename: $scope.moduleName + '.csv',
            exporterMenuPdf: false,
            exporterIsExcelCompatible: true,
            enableHorizontalScrollbar: 1,
            columnDefs: [
                {name: 'id', displayName: "ID", pinnedLeft: true, headerTooltip: "Filter Name: id", minWidth: 50},
                {
                    name: 'serialNumber',
                    displayName: "Serial Number",
                    headerTooltip: "Filter Name: serialNumber",
                    pinnedLeft: true,
                    minWidth: 150
                },
                {name: 'status.name', displayName: "Status", headerTooltip: "Filter Name: status.name", minWidth: 150},
                {
                    name: 'condition.name',
                    displayName: "Condition",
                    headerTooltip: "Filter Name: condition.name",
                    minWidth: 150
                },
                {
                    name: 'vendorBranch.name',
                    displayName: $rootScope.globals.vendorName + ' branch',
                    headerTooltip: "Filter Name: vendorBranch.name",
                    minWidth: 150
                },
                {name: 'type.name', displayName: "Type", headerTooltip: "Filter Name: type.name", minWidth: 150},
                {name: 'vendor.name', displayName: "Vendor", headerTooltip: "Filter Name: vendor.name", minWidth: 150},
                {name: 'model', displayName: "Model", headerTooltip: "Filter Name: model", minWidth: 150},
                {name: 'made', displayName: "Made", headerTooltip: "Filter Name: made", minWidth: 150},
                {
                    name: 'partNumber',
                    displayName: "Part Number",
                    headerTooltip: "Filter Name: partNumber",
                    minWidth: 150
                },
                {
                    name: 'batchNumber',
                    displayName: "Batch Number",
                    headerTooltip: "Filter Name: batchNumber",
                    minWidth: 150
                },
                {name: 'file', displayName: "File Number", headerTooltip: "Filter Name: file", minWidth: 150},
                {
                    name: 'terminal',
                    displayName: "Terminal Number",
                    headerTooltip: "Filter Name: terminal",
                    minWidth: 150
                },
                {name: 'node', displayName: "Node Number", headerTooltip: "Filter Name: node", minWidth: 150},
                {name: 'tender', displayName: "Tender", headerTooltip: "Filter Name: tender", minWidth: 150},
                {
                    name: 'project.name',
                    displayName: "Project",
                    headerTooltip: "Filter Name: project.name",
                    minWidth: 150
                },
                {name: 'remarks', displayName: "Remarks", headerTooltip: "Filter Name: remarks", minWidth: 150},
                {
                    name: 'otherFeatures',
                    displayName: "Other Features",
                    headerTooltip: "Filter Name: otherFeatures",
                    minWidth: 150
                },
                {name: 'timestamp', displayName: "Created On", headerTooltip: "Filter Name: timestamp", minWidth: 150},
                {
                    name: 'Edit', maxWidth: 50, displayName: "", pinnedRight: true, enableSorting: false, minWidth: 50,
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
        //get Data
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
            HttpService.httpGet(self.baseServiceUrl + '/get?page=' + page + '&size=' + size + '&search=' + searchString).then(function (response) {
                $scope.gridOptions.data = response.data.content;
                $scope.gridOptions.totalItems = response.data.totalElements;
            }, function (response) {
                if (response.status == 404) {
                    $scope.gridOptions.data = [];
                    $scope.gridApi.core.refresh();
                    DialogService.toastrWarning("No " + self.moduleNamePlural + " found!");
                } else {
                    DialogService.showHttpErrorDialog(response);
                    DialogService.toastrError($scope.moduleName + ' list', 'load');
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
                DialogService.toastrError($scope.moduleName + ' list', 'load');
            });
        }

        self.getPosTypes = function (posTypeName) {
            //self.typeSearchText = null;
            HttpService.httpGet(self.posTypeServiceUrl + "?name=" + posTypeName).then(function (response) {
                $log.log(response.data);
                self.types = response.data;
                return response.data;
            }, function (response) {
                DialogService.showHttpErrorDialog(response);
                DialogService.toastrError('Pos Type' + ' list', 'load');
                return [];
            });
        }

        self.getPosVendors = function (posVendorName) {
            //self.vendorSearchText = null;
            HttpService.httpGet(self.posVendorServiceUrl + "?name=" + posVendorName).then(function (response) {
                $log.log(response.data);
                self.vendors = response.data;
                return response.data;
            }, function (response) {
                DialogService.showHttpErrorDialog(response);
                DialogService.toastrError('Pos Vendor' + ' list', 'load');
                return [];
            });
        }

        self.getProjects = function (projectName) {
            //self.projectSearchText = null;
            HttpService.httpGet(self.projectsServiceUrl + "?name=" + projectName).then(function (response) {
                $log.log(response.data);
                self.projects = response.data;
                return response.data;
            }, function (response) {
                DialogService.showHttpErrorDialog(response);
                DialogService.toastrError('Project' + ' list', 'load');
                return [];
            });
        }

        self.getVendorBranches = function (branchName) {
            //self.branchSearchText = null;
            HttpService.httpGet(self.vendorBranchServiceUrl + "?name=" + branchName).then(function (response) {
                $log.log(response.data);
                self.branches = response.data;
                return response.data;
            }, function (response) {
                DialogService.showHttpErrorDialog(response);
                DialogService.toastrError('Vendor branch' + ' list', 'load');
                return [];
            });
        }

        /**
         * Insert a new record into DB
         * @param record The record to be inserted
         */
        $scope.insertRecord = function (record) {
            if (record.name) {
                HttpService.httpPost(self.baseServiceUrl, {
                    "name": record.name
                }).then(function (data) {
                    DialogService.toastrSuccess($scope.moduleName, 'added');
                    getPaginatedInfo(paginationOptions.pageNumber, paginationOptions.pageSize);
                }, function (response) {
                    DialogService.showHttpErrorDialog(response);
                    DialogService.toastrError($scope.moduleName, 'add');
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
                "name": record.name
            }).then(function (response) {
                DialogService.toastrSuccess($scope.moduleName, 'updated');
                getPaginatedInfo(paginationOptions.pageNumber, paginationOptions.pageSize);
            }, function (response) {
                DialogService.showHttpErrorDialog(response);
                DialogService.toastrError($scope.moduleName, 'update');

            });
            DialogService.hideDialog()
        };

        /**
         * Delete a record from DB
         * @param info The record to be deleted
         */
        $scope.deleteInfo = function (info) {
            //show confirmatin dialog
            DialogService.showConfirmDialog('Delete ' + $scope.moduleName, 'Are you sure to delete this record?', 'Record will be deleted permanently.')
                .then(function (response) {
                    HttpService.httpDelete(self.baseServiceUrl + "/" + info.id).then(function (data) {
                        DialogService.toastrSuccess($scope.moduleName, 'deleted');
                        var visibleRows = $scope.gridApi.core.getVisibleRows().length;
                        $log.log('Visible rows: ' + visibleRows);
                        if (visibleRows <= 1 && paginationOptions.pageNumber > 1) {
                            paginationOptions.pageNumber--;
                        }
                        getPaginatedInfo(paginationOptions.pageNumber, paginationOptions.pageSize);
                    }, function (response) {
                        DialogService.showHttpErrorDialog(response);
                        DialogService.toastrError($scope.moduleName, 'delete');
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

'use strict';

// Register `project` component, along with its associated controller and template
angular.module('merchantBranch').component('merchantBranchList', {
    templateUrl: 'templates/common/gridWithFilterAndActions.template.html',
    controller: function projectController($scope, $log, $mdBottomSheet, HttpService,
                                           DialogService, ButtonsSheetService, CommonService) {
        /*
         Initialization section
         */
        //component general settings
        var self = this;
        self.moduleName = 'Branch';
        self.baseServiceUrl = 'services/merchantbranch';
        self.baseTemplateUrl = 'templates/merchantBranch';
        self.commonTemplateUrl = 'templates/common';
        self.regionServiceUrl = 'services/region';
        self.areaServiceUrl = 'services/area';


        //Paganation settings
        var paginationOptions = {
            pageNumber: 1,
            pageSize: 20,
            sort: null
        };

        //filters
        $scope.searchTags = [];

        //regions
        $scope.selectedRegion = null;
        self.regionSearchText = null;

        //areas
        self.selectedArea = null;
        self.areaSearchText = null;

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
            // gridMenuCustomItems: [
            //     {
            //         title: "Export Data",
            //         action: function(){
            //             //add your logic here
            //             alert('ok');
            //         }
            //     }
            // ],
            columnDefs: [
                {name: 'id', displayName: "ID", pinnedLeft: true, headerTooltip: "Filter: id"},
                {name: 'name', displayName: "Branch Name", headerTooltip: "Filter: name"},
                {name: 'address', displayName: "Branch Address", headerTooltip: "Filter: address"},
                {name: 'notes', displayName: "Branch Notes", headerTooltip: "Filter: notes"},
                {name: 'area.name', displayName: "Area Name", headerTooltip: "Filter: area.name"},
                {
                    name: 'area.region.name',
                    displayName: "Region Name",
                    headerTooltip: "Filter: area.region.name"
                },
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
        }
        ;


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
         * Filter is changes (added or removed)
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
        //get records by page and filter
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
                if (response.status == 404) {//nothing found
                    $scope.gridOptions.data = [];
                    $scope.gridApi.core.refresh();
                    DialogService.toastrWarning("No Branches found!");
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
            return HttpService.httpGet(self.baseServiceUrl + "?search=" + searchString).then(function (response) {
                    return response.data;
                }
            );
        }


        /**
         * Get regions with name like @regionName
         * @param regionName Part of the region name to look for.
         */
        $scope.getRegions = function (regionName) {
            self.areaSearchText = "";
            return HttpService.httpGet(self.regionServiceUrl + "?name=" + regionName).then(function (response) {
                return response.data;
            }, function (response) {
                DialogService.showHttpErrorDialog(response);
                DialogService.toastrError('Region' + ' list', 'load');
                return [];
            });
        }

        /**
         * Get areas with name like @areaName
         * @param regionName Part of the region name to look for.
         */
        $scope.getAreas = function (areaName) {
            if ($scope.selectedRegion != null) {
                return HttpService.httpGet(self.areaServiceUrl + "?name=" + areaName + "&region=" + $scope.selectedRegion.id).then(function (response) {
                    return response.data;
                }, function (response) {
                    DialogService.showHttpErrorDialog(response);
                    DialogService.toastrError('Area' + ' list', 'load');
                    return [];
                });
            }
        }

        /**
         * Insert a new record into DB
         * @param record The record to be inserted
         */
        $scope.insertRecord = function (record) {
            if (record.name
                && record.area) {
                HttpService.httpPost(self.baseServiceUrl, {
                    "name": record.name,
                    "address": record.address,
                    "notes": record.notes,
                    "area": record.area
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
                "address": record.address,
                "notes": record.notes,
                "area": record.area
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
            self.areaSearchText = null;
            self.regionSearchText = null;
            $scope.info = null;
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
            self.areaSearchText = $scope.currentRecord.area.areaName;
            self.regionSearchText = $scope.currentRecord.area.region.regionName;
            $scope.selectedRegion = $scope.currentRecord.area.region;
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

'use strict';

angular.module('myApp.view1', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/main_page', {
            templateUrl: 'app/main_page/index.html',
            controller: 'mainPageController'
        });
    }])
    
    .controller('mainPageController', ['$scope', '$rootScope', 'apiService', '$mdDialog', '$mdMedia', 'authService', 'cartService', 'logger',
        function ($scope, $rootScope, apiService, $mdDialog, $mdMedia, authService, cartService, logger) {

            $scope.allGoods = [];

            // object that represent request data to server
            $scope.filterDTO = {};
            $scope.filterDTO.usePagination = true;
            $scope.filterDTO.sortDESCbyCreatedDate = true;

            // pagination number of items per page
            $scope.itemsPerPage = 15;

            $scope.newUserAccountDTO = {};
            $scope.newUserAccountDTO.customer = {};
            $scope.newUserAccountDTO.customer.primaryAddress = {};

            $scope.showFilters = false;
            
            // user to login
            $scope.user = {};
            
            /**
             * make request to server with DTO filter
             * @param newPage pagination page
             */
            $scope.pageChanged = function (newPage) {
                var fromRecord = (newPage - 1) * $scope.itemsPerPage;
                var toRecord = newPage * $scope.itemsPerPage;

                var filterRequest = angular.copy($scope.filterDTO);

                filterRequest.recordFrom = fromRecord;
                filterRequest.recordTo = toRecord;

                if ($scope.filterDTO.fullTextSearchRequest && $scope.filterDTO.fullTextSearchRequest.length > 0) {
                    filterRequest.useFullTextSearch = true;
                } else {
                    filterRequest.useFullTextSearch = false;
                }

                $scope.customersPromise = apiService.getGoodsByFilter(filterRequest).then(function (e) {
                    $scope.allGoods = e.resultedObjects;
                    $scope.allGoodsCount = e.relevantObjectsNumber;
                    $scope.lastRequestCount = e.returnedObjectsNumber;
                });
            };
            $scope.pageChanged(1);

            /**
             * if we have byte[]
             * and we want to download in browser
             * @param data
             */
            $rootScope.downloadFile = function (data) {
                var arr = data.file;
                var byteArray = new Uint8Array(arr);
                var a = window.document.createElement('a');

                a.href = window.URL.createObjectURL(new Blob([byteArray], {type: data.type}));
                a.download = data.filename;

                // Append anchor to body.
                document.body.appendChild(a);
                a.click();

                // Remove anchor from body
                document.body.removeChild(a);
            };

            $scope.downloadFileExcel = function() {
                var objectSend = angular.copy($scope.filterDTO);
                objectSend.usePagination = false;
                logger.logSuccess("Файл скоро будет загружен. Это может занять несколько секунд");

                apiService.getGoodExcelByFilter(objectSend).then(function(data) {
                    $rootScope.downloadFile({
                        file: data, type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
                        filename: 'Товары от ' + $rootScope.printDate(new Date()) + '.xlsx'
                    })
                })
            };

            $rootScope.allCategories = [];
            apiService.getAllCategories().then(function (data) {
                $rootScope.allCategories = data;
            });


        
        }]);
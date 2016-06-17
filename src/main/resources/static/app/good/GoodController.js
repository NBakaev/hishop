angular.module('myApp.good', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/good/details/:id', {
                controller: 'GoodDetails',
                templateUrl: 'app/good/details.html'
            }).when('/category/all', {
            controller: 'CategoryAll',
            templateUrl: 'app/good/category_all.html'
        })
    }])
    .controller('GoodDetails', ['$scope', '$rootScope', 'apiService', '$mdDialog', '$mdMedia', 'authService', 'cartService', '$route', '$upload', '$timeout',
        function ($scope, $rootScope, apiService, $mdDialog, $mdMedia, authService, cartService, $route, $upload, $timeout) {

            var goodId = $route.current.params.id;
            $scope.good = {};

            apiService.getGoodById(goodId).then(function (data) {
                $scope.good = data;
            });

            $scope.updateGood = function (good) {
                apiService.updateGood(good).then(function (data) {
                })
            };


            $scope.upload = [];
            $scope.uploadRightAway = true;

            // does we upload all files
            // store to hide upload UI progress
            $scope.uploadedAll = false;

            $scope.hasUploader = function (index) {
                return $scope.upload[index] != null;
            };
            $scope.abort = function (index) {
                $scope.upload[index].abort();
                $scope.upload[index] = null;
            };

            $scope.onFileSelect = function ($files) {
                $scope.selectedFiles = [];
                $scope.progress = [];
                if ($scope.upload && $scope.upload.length > 0) {
                    for (var i = 0; i < $scope.upload.length; i++) {
                        if ($scope.upload[i] != null) {
                            $scope.upload[i].abort();
                        }
                    }
                }

                $scope.upload = [];
                $scope.uploadResult = [];
                $scope.selectedFiles = $files;
                $scope.dataUrls = [];

                for (var i = 0; i < $files.length; i++) {
                    var $file = $files[i];
                    if ($scope.fileReaderSupported && $file.type.indexOf('image') > -1) {
                        var fileReader = new FileReader();
                        fileReader.readAsDataURL($files[i]);
                        var loadFile = function (fileReader, index) {
                            fileReader.onload = function (e) {
                                $timeout(function () {
                                    $scope.dataUrls[index] = e.target.result;
                                });
                            }
                        }(fileReader, i);
                    }

                    $scope.progress[i] = -1;
                    if ($scope.uploadRightAway) {
                        $scope.start(i);
                    }
                }
            };

            $scope.start = function (index) {
                $scope.progress[index] = 0;
                $scope.errorMsg = null;
                if ($scope.howToSend === 1) {

                    fileReader.readAsArrayBuffer($scope.selectedFiles[index]);
                } else {

                    $scope.upload[index] = $upload.upload({
                        url: "/api/v1/storage/upload",
                        method: 'POST',
                        data: {
                            "Content-Type": $scope.selectedFiles[index].type === null || $scope.selectedFiles[index].type === '' ?
                                'application/octet-stream' : $scope.selectedFiles[index].type,
                            filename: $scope.selectedFiles[index].name
                        },
                        file: $scope.selectedFiles[index]
                    });
                    $scope.upload[index].then(function (response) {
                        $timeout(function () {
                            var url = response.data.publicUrl;

                            if (isUndefinedOrNullOrEmpty($scope.good.mainAvatarUrl)) {
                                $scope.good.mainAvatarUrl = url;
                            } else {
                                $scope.good.avatarUrl.push(url);
                            }

                            $scope.updateGood($scope.good);
                            $scope.uploadedAll = true;
                        });
                    }, function (response) {

                        if (response.status > 0) $scope.errorMsg = response.status + ': ' + response.data;
                    }, function (evt) {
                        // Math.min is to fix IE which reports 200% sometimes
                        $scope.progress[index] = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                    });
                }
            };

            $scope.dragOverClass = function ($event) {
                var items = $event.dataTransfer.items;
                var hasFile = false;
                if (items != null) {
                    for (var i = 0; i < items.length; i++) {
                        if (items[i].kind === 'file') {
                            hasFile = true;
                            break;
                        }
                    }
                } else {
                    hasFile = true;
                }

                return hasFile ? "dragover" : "dragover-err";
            };

        }])
    .controller('CategoryAll', ['$scope', '$rootScope', 'apiService', '$mdDialog', '$mdMedia', 'authService', 'cartService', '$route',
        function ($scope, $rootScope, apiService, $mdDialog, $mdMedia, authService, cartService, $route) {

            $scope.allCategories = [];
            $scope.newCategory = {};

            apiService.getAllCategories().then(function (data) {
                $scope.allCategories = data;
            });

            $scope.createNewCategory = function () {
                apiService.addNewCategory($scope.newCategory).then(function (data) {
                    $scope.allCategories.push(data);
                })
            };

            $scope.updateCategory = function (category) {
                apiService.updateCategory(category).then(function (data) {
                })
            };

        }]);

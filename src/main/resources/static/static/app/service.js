'use strict';
angular.module('myApp.services', ['ngRoute'])

    .service('cartService', ['$rootScope', 'apiService', '$mdDialog', '$mdMedia', 'localStorageService', '$mdToast',
        function ($rootScope, apiService, $mdDialog, $mdMedia, localStorageService, $mdToast) {
            $rootScope.allGoodsInCart = {};
            $rootScope.allGoodsInCart.totalPrice = 0;

            // good, number fields
            $rootScope.allGoodsInCart.goodList = [];

            $rootScope.addGoodToCart = function (good) {

                var modifyExistings = false;

                $rootScope.allGoodsInCart.goodList.forEach(function (goodOnCart) {
                    if (goodOnCart.good.id === good.id) {
                        goodOnCart.number++;
                        modifyExistings = true;
                    }
                });

                if (!modifyExistings) {
                    $rootScope.allGoodsInCart.goodList.push({good: good, number: 1});
                }

                $rootScope.allGoodsInCart.goodList.forEach(function (data) {
                    $rootScope.allGoodsInCart.totalPrice += (data.good.price * data.number)
                });

                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Товар: ' + good.name + " добавлен в корзину!")
                        .hideDelay(3000)
                );
            }

        }])

    .service('authService', ['$rootScope', 'apiService', '$mdDialog', '$mdMedia', 'localStorageService', '$http',
        function ($rootScope, apiService, $mdDialog, $mdMedia, localStorageService, $http) {
            var self = this;

            this.testUserAuthenticated = function () {
                var token = localStorageService.get('token');
                if (!isUndefinedOrNullOrEmpty(token)) {
                    $http.defaults.headers.common.Authorization = token;
                    return true;
                }
                return false;
            };

            this.authentificateUserWithLoginAndPassword = function (username, password) {
                localStorageService.set('userName', username);
                localStorageService.set('userPassword', password);

                var basicAuth = "Basic " + Base64.encode(username + ":" + password);
                localStorageService.set('token', basicAuth);
                self.testUserAuthenticated();
            };

            this.logOut = function () {
                localStorageService.remove('token');
            }
        }])

    .service('serverRequestService', ['$q', '$http', '$rootScope',
        function ($q, $http, $rootScope) {
            $http.defaults.headers.common["Accept"] = "application/json";
            $http.defaults.headers.common["Content-Type"] = "application/json";

            var baseUrl = '/api/v1';

            // count server connection number
            $rootScope.serversConnectionsActive = 0;

            this.get = function (relUrl, params) {
                $rootScope.serversConnectionsActive++;
                var promise = $http.get(baseUrl + "/" + relUrl).then(function (response) {
                    $rootScope.serversConnectionsActive--;
                    return response.data;
                });
                // Return the promise to the controller
                return promise;
            };

            this.put = function (relUrl, object) {
                $rootScope.serversConnectionsActive++;
                var promise = $http.put(baseUrl + "/" + relUrl, object).then(function (response) {
                    $rootScope.serversConnectionsActive--;
                    return response.data;
                });
                // Return the promise to the controller
                return promise;
            };

            this.post = function (relUrl, object, params) {
                var optionsParams = {};

                if (params) {
                    if (params.responseType) optionsParams.responseType = params.responseType;
                }

                if (optionsParams.responseType) {
                    var promise = $http.post(baseUrl + "/" + relUrl, object, optionsParams).then(function (response) {
                        $rootScope.serversConnectionsActive--;
                        return response.data;
                    });

                } else {
                    var promise = $http.post(baseUrl + "/" + relUrl, object).then(function (response) {
                        $rootScope.serversConnectionsActive--;
                        return response.data;
                    });
                }

                // Return the promise to the controller
                return promise;

            };

            this.delete = function (relUrl) {
                $rootScope.serversConnectionsActive++;
                var promise = $http.delete(baseUrl + "/" + relUrl).then(function (response) {
                    $rootScope.serversConnectionsActive--;
                    return response.data;
                });

                // Return the promise to the controller
                return promise;
            };
        }])

    .factory('logger', [
        function () {
            var logIt;
            toastr.options = {
                "closeButton": true,
                "positionClass": "toast-bottom-right",
                "timeOut": "3000"
            };
            logIt = function (message, type) {
                return toastr[type](message);
            };
            return {
                log: function (message) {
                    logIt(message, 'info');
                },
                logWarning: function (message) {
                    logIt(message, 'warning');
                },
                logSuccess: function (message) {
                    logIt(message, 'success');
                },
                logError: function (message) {
                    logIt(message, 'error');
                }
            };
        }
    ])

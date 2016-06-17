angular.module('myApp.api', ['ngRoute', 'myApp.services'])
    .service('apiService', ['serverRequestService', function (serverRequestService) {

        this.getAllGoods = function () {
            return serverRequestService.get('good');
        };

        this.updateGood = function (obj) {
            return serverRequestService.put('good', obj);
        };
        
        this.deleteGood = function (obj) {
            return serverRequestService.delete('good/' + obj.id);
        };

        this.addNewGood = function (obj) {
            return serverRequestService.post('good', obj);
        };

        this.getGoodById = function (id) {
            return serverRequestService.get('good/details/' + id);
        };

        this.getGoodsByFilter = function (filterDTO) {
            return serverRequestService.post('good/filter', filterDTO);
        };

        this.addNewUser = function (obj) {
            return serverRequestService.post('users', obj);
        };

        this.getMyAccount = function () {
            return serverRequestService.get('myaccount');
        };

        /**
         * Only admin allowed
         * @returns {*}
         */
        this.getAllAccount = function () {
            return serverRequestService.get('users');
        };

        // category
        this.getAllCategories = function () {
            return serverRequestService.get('good/category');
        };

        this.addNewCategory = function (obj) {
            return serverRequestService.post('good/category', obj);
        };

        this.updateCategory = function (obj) {
            return serverRequestService.put('good/category', obj);
        };

        this.deleteCategory = function (obj) {
            return serverRequestService.delete('good/category/' + obj.id);
        };

        /**
         * Only admin allowed
         * @returns {*}
         */
        this.getAllPurchases = function () {
            return serverRequestService.get('purchase');
        };

        this.updateMyAccount = function (obj) {
            return serverRequestService.put('myaccount', obj);
        };

        this.getMyPurchases = function () {
            return serverRequestService.get('purchase/my');
        };

        this.makePurchase = function (obj) {
            return serverRequestService.post('purchase', obj);
        };

        this.getGoodExcelByFilter = function(goodFilter) {
            return serverRequestService.post('good/excel/download', goodFilter, {responseType: 'arraybuffer'});
        };

    }])
    
    .factory('authHttpResponseInterceptor', ['$q', '$location', 'logger', 'localStorageService', function ($q, $location, logger, localStorageService) {
        return {

            response: function (response) {
                return response || $q.when(response);
            },
            responseError: function (rejection) {
                console.warn(rejection);

                if (rejection.status === 401) {
                    console.log("Response Error 401", rejection);

                    logger.logError('Ошибка авторизации!');
                    // TODO: login form
                    localStorageService.remove("token");
                    // $location.path('login/login').search('returnTo', $location.path());
                }

                if (rejection.status === 403) {
                    console.log("Response Error 403", rejection);
                    logger.logError("Доступ запрещен к данному объекту или действию Вам!")
                }

                if (rejection.status === 503 || rejection.status === -1) {
                    console.log("Response Error 503", rejection);
                    // $location.path('pages/503').search('returnTo', $location.path());
                }

                if (rejection.status === 500) {
                    // logger.logError("Ошибка запроса к серверу!" + "<br>" + rejection.data.exception + "<br>" + rejection.data.message + "<br>" + rejection.data.path)
                    logger.logError("Ошибка запроса к серверу!" + "<br>" + rejection.data.message + "<br>" + rejection.data.path)
                }

                if (rejection.status === 404) {
                    // logger.logWarning("Ошибка запроса к серверу!");
                }

                return $q.reject(rejection);
            }
        }
    }])

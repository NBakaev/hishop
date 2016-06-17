angular.module('myApp.account', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/account/my', {
                controller: 'MyAccountController',
                templateUrl: 'app/account/my.html'
            })
            .when('/account/all', {
                controller: 'AllAccountController',
                templateUrl: 'app/account/all.html'
            })
    }])
    .controller('MyAccountController', ['$scope', '$rootScope', 'apiService', '$mdDialog', '$mdMedia', 'authService', 'cartService', 'logger',
        function ($scope, $rootScope, apiService, $mdDialog, $mdMedia, authService, cartService, logger) {

            $scope.updateCurrentUser = function () {
                apiService.updateMyAccount($rootScope.currentUser).then(function (data) {
                    $scope.currentUser = data;
                    logger.logSuccess("Сохранено!");
                })
            };

        }])

    .controller('AllAccountController', ['$scope', '$rootScope', 'apiService', '$mdDialog', '$mdMedia', 'authService', 'cartService', 'logger',
        function ($scope, $rootScope, apiService, $mdDialog, $mdMedia, authService, cartService, logger) {

            $scope.allAccounts = [];

            apiService.getAllAccount().then(function (data) {
                $scope.allAccounts = data;
            })

        }]);

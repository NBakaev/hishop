angular.module('myApp.purchases', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/purchase/my', {
                controller: 'PurchaseController',
                templateUrl: 'app/purchase/my.html'
            })
            .when('/purchase/all', {
                controller: 'AllPurchasesController',
                templateUrl: 'app/purchase/all.html'
            })
    }])
    .controller('PurchaseController', ['$scope', '$rootScope', 'apiService',
        function ($scope, $rootScope, apiService) {

            $scope.allPurchases = [];

            apiService.getMyPurchases().then(function (data) {
                $scope.allPurchases = data;
            })
        }])

    .controller('AllPurchasesController', ['$scope', '$rootScope', 'apiService',
        function ($scope, $rootScope, apiService) {

            $scope.allPurchases = [];

            apiService.getAllPurchases().then(function (data) {
                $scope.allPurchases = data;
            })

        }]);
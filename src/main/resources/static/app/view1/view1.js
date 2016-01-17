'use strict';

angular.module('myApp.view1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'app/view1/view1.html',
    controller: 'View1Ctrl'
  });
}])

.controller('View1Ctrl', [ '$scope', '$rootScope', 'apiService',  function($scope, $rootScope, apiService) {

  $scope.allGoods = [];

  apiService.getAllGoods().then(function (data) {
    $scope.allGoods = data;
  })

}]);
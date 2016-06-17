'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'myApp.view1',
    'myApp.services',
    'myApp.api',
    'myApp.good',
    'myApp.purchases',
    'myApp.account',
    'ngMaterial',
    'angularUtils.directives.dirPagination',
    'LocalStorageModule',
    'angular-loading-bar',
    'angularFileUpload'

]).config(['$routeProvider', 'paginationTemplateProvider', '$httpProvider', function ($routeProvider, paginationTemplateProvider, $httpProvider) {
    $routeProvider.otherwise({redirectTo: '/main_page'});
    paginationTemplateProvider.setPath('bower_components/angularUtils-pagination/dirPagination.tpl.html');
    $httpProvider.interceptors.push('authHttpResponseInterceptor');
}]);
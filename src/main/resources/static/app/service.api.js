/**
 * Created by ya_000 on 1/17/2016.
 */

angular.module('myApp.api', ['ngRoute', 'myApp.services'])
    .service('apiService', ['serverRequestService', function (serverRequestService) {

        this.getAllGoods = function () {
            return serverRequestService.get('good');
        };

        this.updateGood = function (obj) {
            return serverRequestService.put('good', {});
        };

        this.deleteGood = function (obj) {
            return serverRequestService.delete('good/' + obj.id);
        };

        this.addNewGood = function (obj) {
            return serverRequestService.post('good', obj);
        };

    }]);
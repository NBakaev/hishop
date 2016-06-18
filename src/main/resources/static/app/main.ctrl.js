angular.module('myApp')
    .controller('AppCtrl', function ($scope, $rootScope, $timeout, $mdSidenav, $log, authService, apiService, $filter, logger) {
        $rootScope.currentUser = {};
        var isAuthTokenPresent = authService.testUserAuthenticated();

        if (isAuthTokenPresent) {
            apiService.getMyAccount().then(function (data) {
                $rootScope.currentUser = data;
            })
        }

        $rootScope.printDate = function (date) {
            if (isUndefinedOrNull(date)) return null;
            var format = "dd.MM.yyyy HH:mm";
            
            if (isUndefinedOrNullOrEmpty(format) || format === '') format = "medium";
            return $filter('date')(date, format);
        };

        $rootScope.makePurchase = function () {
          apiService.makePurchase($rootScope.allGoodsInCart).then(function (data) {
              logger.logSuccess("Успешная покупка. Подробности в email");
              $rootScope.allGoodsInCart.goodList = [];
          })
        };

    })

    .controller('LeftCtrl', function ($scope, $timeout, $mdSidenav, $log) {
        $scope.close = function () {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav('left').close()
                .then(function () {
                    $log.debug("close LEFT is done");
                });
        };
    })
    .controller('HeaderController', function ($scope, $timeout, $mdSidenav, $log, authService, $mdDialog, $rootScope, $mdMedia, apiService) {

        /**
         * does current user has some role
         * Usage STRING (one) role hasRole("ROLE_ADMIN")
         * Usage ARRAY (any) hasRole(["ROLE_ADMIN", "ROLE_USER"])
         * @param role
         * @returns {boolean}
         */
        $rootScope.hasRole = function (role) {
            if (typeof $rootScope.currentUser === 'undefined') return false;

            var rolesArray = [];

            if (typeof role === 'string') {
                rolesArray.push(role);
            } else if (typeof role === 'object') {
                rolesArray = role;
            }

            if (isUndefinedOrNullOrEmpty($rootScope.currentUser.roles)){
                return false;
            }

            var arrayLength = $rootScope.currentUser.roles.length;

            for (var i = 0; i < rolesArray.length; i++) {
                for (var j = 0; j < arrayLength; j++) {
                    if ($rootScope.currentUser.roles[j] === rolesArray[i]) {
                        return true;
                    }
                }
            }
        };
        
        $scope.logInTry = function () {
            authService.authentificateUserWithLoginAndPassword($scope.user.username, $scope.user.password);
            location.reload();
        };

        var originatorEv;
        $scope.openMenu = function($mdOpenMenu, ev) {
            originatorEv = ev;
            $mdOpenMenu(ev);
        };

        /**
         * Open dialog for new user registration
         */
        $scope.registrateNewUser = function () {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                templateUrl: 'app/templates/register.user.dialog.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: useFullScreen
            })
        };

        /**
         * Log in to user
         */
        $scope.logIn = function () {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                templateUrl: 'app/templates/login.user.dialog.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: useFullScreen
            })
        };

        /**
         * Exit from current user
         */
        $scope.logOut = function () {
            authService.logOut();
            location.reload();
        };

        /**
         * Register new user account API
         */
        $scope.createNewUserAccount = function () {
            var useraccount = angular.copy($scope.newUserAccountDTO);

            apiService.addNewUser(useraccount).then(function (data) {
                console.log(data);
                authService.authentificateUserWithLoginAndPassword(useraccount.username, useraccount.password);
				location.reload();
			});
        };
        
    });




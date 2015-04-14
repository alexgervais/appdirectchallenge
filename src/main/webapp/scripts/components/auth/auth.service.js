'use strict';

angular.module('appdirectchallengeApp')
    .factory('Auth', function Auth($rootScope, $state, $q, $translate, Principal, AuthServerProvider) {
        return {
            logout: function () {
                AuthServerProvider.logout();
                Principal.authenticate(null);
            },

            authorize: function(force) {
                return Principal.identity(force)
                    .then(function() {

                        if ($rootScope.toState.data.roles && $rootScope.toState.data.roles.length > 0) {
                            $state.go('accessdenied');
                        }
                    });
            }
        };
    });

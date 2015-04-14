'use strict';

angular.module('appdirectchallengeApp')
    .factory('AuthServerProvider', function loginService($http, localStorageService, Base64) {
        return {
            logout: function() {
                // logout from the server
                $http.post('api/logout').then(function() {
                    localStorageService.clearAll();
                });
            },
            getToken: function () {
                return localStorageService.get('token');
            },
            hasValidToken: function () {
                var token = this.getToken();
                return token && token.expires_at && token.expires_at > new Date().getTime();
            }
        };
    });

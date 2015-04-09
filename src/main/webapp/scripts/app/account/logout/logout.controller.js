'use strict';

angular.module('appdirectchallengeApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });

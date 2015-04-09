'use strict';

angular.module('appdirectchallengeApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });



'use strict';

angular.module('appdirectchallengeApp')
    .factory('Access', function ($resource) {
        return $resource('api/accesses/:id', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });

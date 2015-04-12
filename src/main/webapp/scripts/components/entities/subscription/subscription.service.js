'use strict';

angular.module('appdirectchallengeApp')
    .factory('Subscription', function ($resource) {
        return $resource('api/subscriptions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });

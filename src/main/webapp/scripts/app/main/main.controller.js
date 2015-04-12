'use strict';

angular.module('appdirectchallengeApp')
    .controller('MainController', function ($scope, Principal, Subscription, ParseLinks) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.subscriptions = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Subscription.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.subscriptions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
    });

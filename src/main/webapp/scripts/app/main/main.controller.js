'use strict';

angular.module('appdirectchallengeApp')
    .controller('MainController', function ($scope, Principal, Subscription, Access, ParseLinks) {
        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.subscriptions = [];
        $scope.accesses = [];
        $scope.subscriptionsPage = 1;
        $scope.accessesPage = 1;

        $scope.loadAllSubscriptions = function () {
            Subscription.query({page: $scope.subscriptionsPage, per_page: 20}, function (result, headers) {
                $scope.subscriptionLinks = ParseLinks.parse(headers('link'));
                $scope.subscriptions = result;
            });
        };

        $scope.loadAllAccesses = function () {
            Access.query({page: $scope.accessesPage, per_page: 20}, function (result, headers) {
                $scope.accesslinks = ParseLinks.parse(headers('link'));
                $scope.accesses = result;
            });
        };

        $scope.loadSubscriptionPage = function (page) {
            $scope.subscriptionsPage = page;
            $scope.loadAllSubscriptions();
        };

        $scope.loadAccessPage = function (page) {
            $scope.accessesPage = page;
            $scope.loadAllAccesses();
        };

        $scope.loadAll = function () {
            $scope.loadAllSubscriptions();
            $scope.loadAllAccesses();
        };

        $scope.loadAll();
    });

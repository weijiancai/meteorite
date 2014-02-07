var app = angular.module('app', ['metaui', 'ngResource', 'ngRoute']);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({templateUrl: "main.html", controller: 'MainCtl'})
}]);
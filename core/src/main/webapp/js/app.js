var app = angular.module('app', ['metaui', 'ngResource', 'ngRoute', 'angularBootstrapNavTree', 'ui.bootstrap']);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        /*when('/', {
            templateUrl: 'admin.html',
            controller: 'AdminCtl'
        }).*/
        when('/ftp', {
            templateUrl: 'ftp.html',
            controller: 'FtpCtl',
            controllerAs: 'ftp'
        }).
        otherwise({templateUrl: "main.html", controller: 'MainCtl'})
}]);
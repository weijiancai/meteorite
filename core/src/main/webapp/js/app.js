var app = angular.module('app', ['metaui', 'ngResource', 'ngRoute', 'angularBootstrapNavTree']);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/ftp', {
            templateUrl: 'ftp.html',
            controller: 'FtpCtl'
        }).
        otherwise({templateUrl: "main.html", controller: 'MainCtl'})
}]);
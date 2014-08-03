app.controller('FtpCtl', ['$scope', '$http', function($scope, $http) {
    $scope.myData = [];

    $scope.gridOptions = {
        data: 'myData',
        colDefs: [{field: 'displayName', displayName: 'displayName'}]
    };

    $http({url:'/tree', params: {path: '/he_gy'}}).success(function(data) {
        $scope.myData = data.children;
    });
}]);
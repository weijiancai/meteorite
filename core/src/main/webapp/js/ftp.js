app.controller('FtpCtl', ['$scope', '$http', '$locale', function($scope, $http, $locale) {
    $scope.myData = [];

    console.log($locale.id);

    $scope.gridOptions = {
        data: 'myData',
        columnDefs: [
            {field: 'displayName', displayName: '名称'},
            {field: 'lastModified', displayName: '最后修改时间', cellFilter: 'date:"yyyy-MM-dd HH:mm"'},
            {field: 'size', displayName: '大小', cellFilter: 'fileSize'},
            {field: 'type', displayName: '类型'}
        ]
    };

    $http({url:'/tree', params: {path: '/he_gy'}}).success(function(data) {
        $scope.myData = data.children;
    });
}]);
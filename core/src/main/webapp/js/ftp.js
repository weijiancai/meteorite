app.controller('FtpCtl', ['$scope', '$http', '$locale', function($scope, $http, $locale) {
    $scope.myData = [];

    console.log($locale.id);

    $scope.gridOptions = {
        data: 'myData',
        columnDefs: [
            {field: 'displayName', displayName: '名称'},
            {field: 'lastModified', displayName: '最后修改时间', cellFilter: 'date:"yyyy-MM-dd HH:mm"'},
            {field: 'size', displayName: '大小', cellFilter: 'fileSize'},
            {field: 'type', displayName: '类型'},
//            {field: '', displayName: '下载', cellTemplate: '<button type="button" class="btn btn-default" title="{{row.entity[\'id\']}}" ng-click="ftp.downFile(row.entity)"><span class="glyphicon glyphicon-download"></span></button>'}
            {field: '', displayName: '下载', cellTemplate: '<a href="/tree?down={{row.entity[\'id\']}}" class="btn btn-default"><span class="glyphicon glyphicon-download"></span></a>'}
        ]
    };

    $http({url:'/tree', params: {path: '/he_gy'}}).success(function(data) {
        $scope.myData = data.children;
    });

    this.downFile = function(row) {
        console.log(row);
        /*$http({url:'/tree', params: {down: row['id']}}).success(function() {
            alert("OK!");
        });*/
        $.post('/tree', {down: row['id']});
    };

    $scope.temp_text = '请输入内容！';
    this.storeFile = function() {
        /*$http({url:'/tree', method: 'post', params: {store: '/he_gy/temp_text.txt', text: $scope.temp_text}}).success(function() {

        });*/
        $.post('/tree', {store: '/he_gy/temp_text.txt', text: $scope.temp_text});
    }
}]);
app.controller('FtpCtl', ['$scope', '$http', '$locale', function($scope, $http, $locale) {
    $scope.myData = [];

    console.log($locale.id);

    $scope.gridOptions = {
        data: 'myData',
        columnDefs: [
            {field: 'displayName', displayName: '名称',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-switch="row.entity[\'type\']"><a href="javascript:void(0)" ng-click="ftp.goTo(row.entity[\'id\'])" ng-switch-when="目录">{{row.entity["displayName"]}}</a><span ng-switch-default="">{{row.entity["displayName"]}}</span></span></div>'},
            {field: 'lastModified', displayName: '最后修改时间', cellFilter: 'date:"yyyy-MM-dd HH:mm"'},
            {field: 'size', displayName: '大小', cellFilter: 'fileSize'},
            {field: 'type', displayName: '类型'},
//            {field: '', displayName: '下载', cellTemplate: '<button type="button" class="btn btn-default" title="{{row.entity[\'id\']}}" ng-click="ftp.downFile(row.entity)"><span class="glyphicon glyphicon-download"></span></button>'}
            {field: '', displayName: '下载', cellTemplate: '<a href="/tree?down={{row.entity[\'id\']}}" class="btn btn-default"><span class="glyphicon glyphicon-download"></span></a>' +
                '<a ng-click="ftp.deleteFile(row.entity[\'id\'])" href="javascript:void(0)" class="btn btn-default"><span class="icon-trash"></span></a>'}
        ]
    };

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
    };

    $scope.parents = ["/"];
    $scope.parent = "/";
    $scope.current = "/";

    this.goTo = function(path) {
        $scope.current = path;

        var array = path.split("/");
        array.shift();
        array.unshift("/");
        $scope.parents = array;
        console.log($scope.parents);
        $scope.parent = path.substring(0, path.lastIndexOf('/'));
        console.log($scope.parent);

        $http({url:'/tree', params: {path: path}}).success(function(data) {
            console.log(data);
            $scope.myData = data;
        });
    };

    this.goToParent = function() {
        this.goTo($scope.parent);
    };

    this.deleteFile = function(path) {
        var self = this;
        $http({url:'/tree', params: {delete: path}}).success(function() {
            self.refresh();
        });
    };

    this.refresh = function() {
        $http({url:'/tree', params: {refresh: $scope.parent}}).success(function(data) {
            $scope.myData = data;
        });
    };

    this.goTo('/');
}]);
app.controller('FtpCtl', ['$scope', '$http', '$locale', '$modal', 'Message', function($scope, $http, $locale, $modal, Message) {
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
            {field: '', displayName: '下载', cellTemplate: '<a href="javascript:void(0)" ng-click="ftp.downFile(row.entity)" class="btn btn-default"><span class="glyphicon glyphicon-download"></span></a>' +
                '<a ng-click="ftp.deleteFile(row.entity)" href="javascript:void(0)" class="btn btn-default"><span class="icon-trash"></span></a>'}
        ]
    };

    this.downFile = function(row) {
        if(row['type'] == '目录') {
            Message.alert('请选择文件下载！');
            return;
        }
        var progress = Message.progress("正在下载文件：");
        $.get('/tree', {down: row['id']}, function(data) {
            progress.close();
            if(data.url) {
                location.href = data.url;
            }
        });
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
        $scope.parent = path.substring(0, path.lastIndexOf('/'));

        $http({url:'/tree', params: {path: path}}).success(function(data) {
            $scope.myData = data;
        });
    };

    this.goToParent = function() {
        this.goTo($scope.parent);
    };

    this.deleteFile = function(rowData) {
        var self = this;
        if(rowData['type'] == '目录') {
            Message.confirm('您确定要删除整个目录吗？', function() {
                $http({url:'/tree', params: {delete: rowData['id']}}).success(function() {
                    self.refreshPath();
                });
            });
        }
    };

    this.refreshPath = function() {
        $http({url:'/tree', params: {refresh: $scope.current}}).success(function(data) {
            $scope.myData = data;
        });
    };

    this.goTo('/');

    // 文件上传
    var fileUploadDialog;

    this.openFileUploadDialog = function(templateUrl) {
        fileUploadDialog = $modal.open({
            templateUrl: templateUrl,
            size: 'lg',
            controller: FileUploadCtrl,
            resolve: {
                path: function () {
                    return $scope.current;
                }
            }
        });
    };


}]);

var FileUploadCtrl = function ($scope, path, $modalInstance) {
    $scope.initFileUploadDialog = function() {
        $('#drag-and-drop-zone').dmUploader({
            url: '/tree?upload=' + path,
            dataType: 'json',
//        allowedTypes: 'image/*',
            /*extFilter: 'jpg;png;gif',*/
            onInit: function(){
                console.log('onInit');
                $.danidemo.addLog('#demo-debug', 'default', 'Plugin initialized correctly');
            },
            onBeforeUpload: function(id){
                $.danidemo.addLog('#demo-debug', 'default', 'Starting the upload of #' + id);

                $.danidemo.updateFileStatus(id, 'default', 'Uploading...');
            },
            onNewFile: function(id, file){
                $.danidemo.addFile('#demo-files', id, file);
            },
            onComplete: function(){
                $.danidemo.addLog('#demo-debug', 'default', 'All pending tranfers completed');
            },
            onUploadProgress: function(id, percent){
                var percentStr = percent + '%';

                $.danidemo.updateFileProgress(id, percentStr);
            },
            onUploadSuccess: function(id, data){
                $.danidemo.addLog('#demo-debug', 'success', 'Upload of file #' + id + ' completed');

                $.danidemo.addLog('#demo-debug', 'info', 'Server Response for file #' + id + ': ' + JSON.stringify(data));

                $.danidemo.updateFileStatus(id, 'success', 'Upload Complete');

                $.danidemo.updateFileProgress(id, '100%');
            },
            onUploadError: function(id, message){
                $.danidemo.updateFileStatus(id, 'error', message);

                $.danidemo.addLog('#demo-debug', 'error', 'Failed to Upload file #' + id + ': ' + message);
            },
            onFileTypeError: function(file){
                $.danidemo.addLog('#demo-debug', 'error', 'File \'' + file.name + '\' cannot be added: must be an image');
            },
            onFileSizeError: function(file){
                $.danidemo.addLog('#demo-debug', 'error', 'File \'' + file.name + '\' cannot be added: size excess limit');
            },
            /*onFileExtError: function(file){
             $.danidemo.addLog('#demo-debug', 'error', 'File \'' + file.name + '\' has a Not Allowed Extension');
             },*/
            onFallbackMode: function(message){
                $.danidemo.addLog('#demo-debug', 'info', 'Browser not supported(do something else here!): ' + message);
            }
        });
    };

    $scope.upload = function() {
        console.log("upload.........");
    };

    $scope.open = function() {

    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
};
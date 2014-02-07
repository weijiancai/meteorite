app.controller('MainCtl', ['$scope', 'Layout', function($scope, Layout) {
//    $scope.layout = Layout.query();
    $scope.systemList = [
        { label : "元数据管理", id : "role1"},
        { label : "布局管理", id : "role1"}
    ];
    $scope.onTreeNodeClick = function(selectedNode) {
        alert(selectedNode);
    }
}]);
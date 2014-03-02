app.controller('MainCtl', ['$scope', '$resource', '$http', 'Layout', 'MUConfig', function($scope, $resource, $http, Layout, MUConfig) {
//    $scope.layout = Layout.query();
    $scope.systemList = [
        { label : "元数据管理", id : "role1"},
        { label : "布局管理", id : "role1"}
    ];
    /*$scope.onTreeNodeClick = function(selectedNode) {
        alert(selectedNode);
    };*/

    /*$scope.treeOption = {
        id: 'Tree1',
        nodeLabel: 'label',
        data: [
            { label : "元数据管理", id : "role1", children: [
                { label : "增加", id : "role1"},{ label : "修改", id : "role1"},{ label : "删除", id : "role1"}
            ]},
            { label : "布局管理", id : "role1"}
        ]
    };*/

    $http.post('/getSystemInfo').success(function(data) {
        $scope.layout = data.children;
    });
}]);

/*
app.controller('TreeCtl', function($scope) {
    alert('TreeCtl = ' + $scope.name);
});*/

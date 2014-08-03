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

    /*$scope.layout = [
        {label: '安全管理', children: []},
        {label: '基础数据', children: []},
        {label: '系统管理', children: []},
        {label: '国内进港', children: []},
        {label: '货包机', children: []},
        {label: '集控器材', children: []},
        {label: '收费与账单', children: []},
        {label: 'KPI管理', children: []},
        {label: '报文交换', children: []},
        {label: '实时统计', children: []},
        {label: '公用工具', children: []}
    ];*/

    $scope.treeOptions = {
        treeId: 'navTree',
        nodeLabel: 'label',
        data: [
            {label: '安全管理', children: []},
            {label: '基础数据', children: []},
            {label: '系统管理', children: []},
            {label: '国内进港', children: [
                {label: '文件操作', children: [
                    {label: '航班文件', children: []},
                    {label: '中转转港', children: []},
                    {label: '冷藏登记', children: []},
                    {label: '联程货物统计', children: []},
                    {label: '进港运单高级管理', children: []},
                    {label: '中转换单', children: []},
                    {label: '海关账册', children: []}
                ]},
                {label: '货物操作', children: []},
                {label: '提单办单', children: []},
                {label: '提货出库', children: []},
                {label: '相关操作', children: []}
            ]},
            {label: '货包机', children: []},
            {label: '集控器材', children: []},
            {label: '收费与账单', children: []},
            {label: 'KPI管理', children: []},
            {label: '报文交换', children: []},
            {label: '实时统计', children: []},
            {label: '公用工具', children: []}
        ]
    };

    $scope['dbTreeNodeLabelTemplate'] = function(node) {
        var str = '<div class="dbTree_node"><span class="dbTree_obj_name">' + node.displayName + '</span>';
        if(node['children'] && (node['objectType'] == 'TABLE' || node['objectType'] == 'VIEW')) {
            str += '<span class="dbTree_obj_count">(' + node.children.length + ')</span>';
        }
        if(node['comment']) {
            var comment = node['comment'].replace('<', '&lt;').replace('>', '&gt;');
            str += '<span class="dbTree_obj_comment" title="' + node['comment'] + '">- ' + comment + '</span>'
        }
        return  str + '</div>';
    };

    $scope['dbTreeNodeClick'] = function(node) {
        $scope.muFormOptions = {fields: node};
    };


    var treedata_avm = [
        {
            label: 'Animal',
            children: [
                {
                    label: 'Dog',
                    data: {
                        description: "man's best friend"
                    }
                },
                {
                    label: 'Cat',
                    data: {
                        description: "Felis catus"
                    }
                },
                {
                    label: 'Hippopotamus',
                    data: {
                        description: "hungry, hungry"
                    }
                },
                {
                    label: 'Chicken',
                    children: ['White Leghorn', 'Rhode Island Red', 'Jersey Giant']
                }
            ]
        }
    ];

    $scope.my_data = treedata_avm;
    $scope.my_tree = {};
}]);

/*
app.controller('TreeCtl', function($scope) {
    alert('TreeCtl = ' + $scope.name);
});*/

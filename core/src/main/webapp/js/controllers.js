app.controller('MainCtl', ['$scope', '$resource', 'Layout', 'MUConfig', function($scope, $resource, Layout, MUConfig) {
    $scope.layout = Layout.query();
    $scope.systemList = [
        { label : "元数据管理", id : "role1"},
        { label : "布局管理", id : "role1"}
    ];
    $scope.onTreeNodeClick = function(selectedNode) {
        alert(selectedNode);
    };


    $scope.metas = MUConfig.meta.query();

    $scope.treeOption = {
        id: 'Tree1',
        nodeLabel: 'label',
        data: [
            { label : "元数据管理", id : "role1", children: [
                { label : "增加", id : "role1"},{ label : "修改", id : "role1"},{ label : "删除", id : "role1"}
            ]},
            { label : "布局管理", id : "role1"}
        ]
    };

    $scope.gridOptions = {};

    /*MUConfig.getMeta('Meta', function(data) {
        $scope.colDefs = [];

        var options = data;
        for(var i = 0; i < options.fields.length; i++) {
            var obj = {};
            obj.field = options.fields[i].name;
            obj.displayName = options.fields[i].displayName;
            obj.dictId = options.fields[i].dictId;
            *//*if(options.fields[i].displayStyle == DS_COMBO_BOX) {
                obj.editableCellTemplate = '<select ng-class="col.index" ng-input="COL_FIELD" ng-model="COL_FIELD" ng-options="m.value as m.name for m in getDict(col.colDef.dictId)"></select>';
//                    obj.cellTemplate = '<span ng-cell-text>{{col.field + col.displayName + col.index + row.entity[col.field] + col.colDef.dictId}}</span>';
                obj.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{getDictDisplayName(col.colDef.dictId, row.entity[col.field])}}</span></div>';
            }*//*
            $scope.colDefs.push(obj);
        }

        $scope.gridOptions = {
            enableColumnResize: true,
            data: 'metas',
            columnDefs: 'colDefs'
        };
    });*/

    $scope.colDefs = [];

    $scope.gridOptions = {
        enableColumnResize: true,
        columnDefs: 'colDefs'
    };

    var Meta = $resource('/meta/:name', {userId:'@name'});
    var meta = Meta.get({name:'Meta'}, function() {
        $scope.colDefs = meta.fields;
    });

}]);

app.controller('TreeCtl', function($scope) {
    alert('TreeCtl = ' + $scope.name);
});
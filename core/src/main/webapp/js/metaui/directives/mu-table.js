metauiDirectives.directive('muTable', ['MUConfig', '$compile', function(MUConfig, $compile) {
    return {
        transclude: true,
        /*scope: {muTableOptions: '@'},*/
        template: '<div class="gridStyle" ng-grid="gridOptions" style="height: 300px;"></div>',
        controller: function($scope, $element, $attrs, $transclude) {
            /*var options = MUConfig.get($element.attr('mu-table'));*/
            $scope.mydata = [];
            $scope.colDefs = [];
            $scope.metas = [];
            /*$scope.getDictDisplayName = function(dictId, value) {
                return MUDict.getDisplayName(dictId, value);
            };
            $scope.getDict = function(dictId) {
                return MUDict.getDict(dictId);
            };*/

            /*for(var i = 0; i < options.fields.length; i++) {
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
            }*/
            $scope.gridOptions = {
                data: 'metas',
                columnDefs: 'colDefs',
                enableCellEditOnFocus: true
            };

            /*$scope.addRow = function() {
//                $scope.colDefs.push({field:'test', displayName: '测试'});
//                $scope.mydata.push({colCount: '3', labelGap: '5'});
                var aobj = {};
                for(var i = 0; i < options.fields.length; i++) {
                    aobj[options.fields[i].name] = options.fields[i].defaultValue;
                }
                *//**//*obj.colCount = '5';
                obj.labelGap = '5';*//**//*
                $scope.mydata.push(aobj);

                MUConfig.get('PreviewForm').fields.push(aobj);
            }*/


            MUConfig.getMeta('Meta', function(meta) {
                var cols = [];
                for(var i = 0; i < meta.fields.length; i++) {
                    var obj = {};
                    obj.field = meta.fields[i].name.substr(0, 1).toLowerCase() + meta.fields[i].name.substr(1);
                    obj.displayName = meta.fields[i].displayName;
                    obj.dictId = meta.fields[i].dictId;
                    /*if(options.fields[i].displayStyle == DS_COMBO_BOX) {
                     obj.editableCellTemplate = '<select ng-class="col.index" ng-input="COL_FIELD" ng-model="COL_FIELD" ng-options="m.value as m.name for m in getDict(col.colDef.dictId)"></select>';
                     //                    obj.cellTemplate = '<span ng-cell-text>{{col.field + col.displayName + col.index + row.entity[col.field] + col.colDef.dictId}}</span>';
                     obj.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{getDictDisplayName(col.colDef.dictId, row.entity[col.field])}}</span></div>';
                     }*/
                    cols.push(obj);
                }

                $scope.colDefs = cols;
                $scope.metas = MUConfig.meta.query();
            });
        }
    }
}]);
metauiDirectives.directive('muTable', ['MUDict', 'MUConfig', function(MUDict, MUConfig) {
    return {
        transclude: true,
        /*scope: {muTableOptions: '@'},*/
        template: '{{MUDict.getDisplayName("formLayoutType", "T")}}<div class="gridStyle" ng-grid="gridOptions" style="height: 300px;"></div>',
        controller: function($scope, $element, $attrs, $transclude) {
            var options = MUConfig.get($element.attr('mu-table'));
            $scope.mydata = [];
            $scope.colDefs = [];

            $scope.getDictDisplayName = function(dictId, value) {
                return MUDict.getDisplayName(dictId, value);
            };
            $scope.getDict = function(dictId) {
                return MUDict.getDict(dictId);
            };

            for(var i = 0; i < options.fields.length; i++) {
                var obj = {};
                obj.field = options.fields[i].name;
                obj.displayName = options.fields[i].displayName;
                obj.dictId = options.fields[i].dictId;
                if(options.fields[i].displayStyle == DS_COMBO_BOX) {
                    obj.editableCellTemplate = '<select ng-class="col.index" ng-input="COL_FIELD" ng-model="COL_FIELD" ng-options="m.value as m.name for m in getDict(col.colDef.dictId)"></select>';
//                    obj.cellTemplate = '<span ng-cell-text>{{col.field + col.displayName + col.index + row.entity[col.field] + col.colDef.dictId}}</span>';
                    obj.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{getDictDisplayName(col.colDef.dictId, row.entity[col.field])}}</span></div>';
                }
                $scope.colDefs.push(obj);
            }
            $scope.gridOptions = {
                data: 'mydata',
                columnDefs: 'colDefs',
                enableCellEditOnFocus: true
            };

            $scope.addRow = function() {
//                $scope.colDefs.push({field:'test', displayName: '测试'});
//                $scope.mydata.push({colCount: '3', labelGap: '5'});
                var aobj = {};
                for(var i = 0; i < options.fields.length; i++) {
                    aobj[options.fields[i].name] = options.fields[i].defaultValue;
                }
                /*obj.colCount = '5';
                obj.labelGap = '5';*/
                $scope.mydata.push(aobj);

                MUConfig.get('PreviewForm').fields.push(aobj);
            }
        }
    }
}]);
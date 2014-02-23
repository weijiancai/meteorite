metauiDirectives.directive('muTable', ['MUConfig', 'MUDict', '$compile', function(MUConfig, MUDict, $compile) {
    return {
        transclude: true,
        /*scope: {muTableOptions: '@'},*/
        /*scope: {},*/
        template: '<div class="gridStyle" ng-grid="gridOptions" style="height: 300px;"></div>',
        controller: function($scope, $element, $attrs, $transclude) {
            var metaName = $element.attr('mu-table');
//            $scope.mydata = [];
            $scope.colDefs = [];
            $scope.metas = [];
            $scope.meta = {};

            $scope.gridOptions = {
                data: 'metas',
                columnDefs: 'colDefs',
                enableCellEditOnFocus: true
            };

            $scope.addRow = function() {
                var meta = $scope.meta;
                var aobj = {};
                for(var i = 0; i < meta.fields.length; i++) {
                    aobj[meta.fields[i].name] = '';
                }

                $scope.metas.push(aobj);
            };

            $scope.getDictDisplayName = function(dictId, value) {
                return MUConfig.getDictDisplayName(dictId, value);
            };

            MUConfig.getMeta(metaName, function(meta) {
                $scope.meta = meta;

                var cols = [];
                for(var i = 0; i < meta.fields.length; i++) {
                    var obj = {};
                    obj.field = meta.fields[i].name.substr(0, 1).toLowerCase() + meta.fields[i].name.substr(1);
                    obj.displayName = meta.fields[i].displayName;
                    obj.dictId = meta.fields[i].dictId;

                    var dictId = obj.dictId;
                    if(meta.fields[i].dictId) {
                        $scope[dictId] = [];
                        MUConfig.getDict(dictId, function(data) {
                            $scope[data.id] = data.codeList;
                        });
                        obj.editableCellTemplate = '<select ng-class="col.index" ng-input="COL_FIELD" ng-model="COL_FIELD" ng-options="m.name as m.value for m in ' + dictId + '"></select>';
                        obj.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{getDictDisplayName(col.colDef.dictId, row.entity[col.field])}}</span></div>';
                    }
                    cols.push(obj);
                }

                $scope.colDefs = cols;
                $scope.metas = MUConfig.meta.query();
            });
        }
    }
}]);
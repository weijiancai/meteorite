metauiDirectives.directive('muTable', ['MUConfig', 'MUDict', '$compile', 'Meta', 'Dict', function(MUConfig, MUDict, $compile, Meta, Dict) {
    return {
        transclude: true,
        /*scope: {muTableOptions: '@'},*/
        /*scope: {},*/
        template: '<div class="gridStyle" ng-grid="gridOptions" style="height: 300px;"></div>',
        controller: function($scope, $element, $attrs, $transclude) {
            var metaCache = $scope.metaCache;
            var dictCache = $scope.dictCache;

            var metaName = $element.attr('mu-table');
            $scope.colDefs = [];
            $scope.metas = [];
            var meta = metaCache.get(metaName);

            $scope.gridOptions = {
                data: 'metas',
                columnDefs: 'colDefs',
                enableCellEditOnFocus: true
            };

            $scope.addRow = function() {
                var meta = $scope.meta;
                $scope.rowObj = new Meta();
                for(var i = 0; i < meta.fields.length; i++) {
                    $scope.rowObj[meta.fields[i].name] = '';
                }

                $scope.metas.push($scope.rowObj);
            };

            $scope.save = function() {
                $scope.rowObj.$save();
            };

            $scope.getDictDisplayName = function(dictId, value) {
                return MUConfig.getDictDisplayName(dictId, value);
            };

            /*MUConfig.getMeta(metaName, function(meta) {
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
            });*/

            if(meta) {
                initTable(meta);
            } else {
                Meta.get({name: metaName}, function(meta) {
                    metaCache.put(meta.name, meta);
                    initTable(meta);
                });
            }


            function initTable(meta) {
                var cols = [];
                for(var i = 0; i < meta.fields.length; i++) {
                    var obj = {};
                    obj.field = meta.fields[i].name.substr(0, 1).toLowerCase() + meta.fields[i].name.substr(1);
                    obj.displayName = meta.fields[i].displayName;
                    obj.dictId = meta.fields[i].dictId;

                    var dictId = obj.dictId;
                    if(meta.fields[i].dictId) {
                        Dict.get({id: dictId}, function(data) {
                            $scope.dictCache[data.id] = data;

                            for(var i = 0; i < data.codeList.length; i++) {
                                var code = data.codeList[i];
                                $scope.dictCache[data.id][code.name.toLowerCase()] = code.value;
                            }
                        });
                        obj.editableCellTemplate = '<select ng-class="col.index" ng-input="COL_FIELD" ng-model="COL_FIELD" ng-options="m.name.toLowerCase() as m.value for m in dictCache.' + dictId + '.codeList"></select>';
//                        obj.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{' + dictId + '["" + row.entity[col.field]]}}</span></div>';
//                        obj.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{dictCache.' + dictId + '["" + row.entity[col.field]]}}</span></div>';
                        obj.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{dictCache.getDisplayValue(col.colDef.dictId, row.entity[col.field])}}</span></div>';
                    }
                    cols.push(obj);
                }

                $scope.colDefs = cols;
                $scope.metas = MUConfig.meta.query();
            }
        }
    }
}]);
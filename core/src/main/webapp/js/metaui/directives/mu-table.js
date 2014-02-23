metauiDirectives.directive('muTable', ['MUConfig', 'MUDict', '$compile', function(MUConfig, MUDict, $compile) {
    return {
        transclude: true,
        /*scope: {muTableOptions: '@'},*/
        scope: {},
        template: '<div class="gridStyle" ng-grid="gridOptions" style="height: 300px;"></div>',
        controller: function($scope, $element, $attrs, $transclude) {
            var metaName = $element.attr('mu-table');
            $scope.mydata = [];
            $scope.colDefs = [];
            $scope.metas = MUConfig.meta.query();

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

            $scope.getDictDisplayName = function(dictId, value) {
                return MUDict.getDisplayName(dictId, value);
            };
            $scope.getDict = function(dictId) {
                return MUDict.getDict(dictId);
            };

//            $scope.dict = MUDict.getDict('com.meteorite.core.dict.EnumBoolean');

            MUConfig.getMeta(metaName, function(meta) {
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
                    /*if(meta.fields[i].dictId) {
                        obj.editableCellTemplate = '<select ng-class="col.index" ng-input="COL_FIELD" ng-model="COL_FIELD" ng-options="m.value as m.name for m in getDict(col.colDef.dictId)"></select>';
                        //                    obj.cellTemplate = '<span ng-cell-text>{{col.field + col.displayName + col.index + row.entity[col.field] + col.colDef.dictId}}</span>';
                        obj.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{getDictDisplayName(col.colDef.dictId, row.entity[col.field])}}</span></div>';
                    }*/
                    var dictId = obj.dictId;
                    if(meta.fields[i].dictId) {
                        /*$scope[dictId] = [
                            {name: 'true', value: '是'},
                            {name: 'false', value: '否'}
                        ];*/
                        $scope[dictId] = [];
                        MUConfig.getDict(dictId, function(data) {
                            $scope[data.id] = data.codeList;
                        });
//                        obj.editableCellTemplate = '<select ng-class="col.index" ng-input="COL_FIELD" ng-model="COL_FIELD" ng-options="m.name as m.value for m in ' + dictId + '"></select>';
                        //                    obj.cellTemplate = '<span ng-cell-text>{{col.field + col.displayName + col.index + row.entity[col.field] + col.colDef.dictId}}</span>';
                        obj.cellTemplate = '<div class="ngCellText" ng-class="col.colIndex()">' +
                            '<span mu-dict="' + dictId + '" is-display-text="true" data="row.entity[col.field]"></span></div>';
//                        obj.cellTemplate = '<select><option ng-repeat="o in ' + dictId + '" value="o.name">{{o.value}}</option></select>';
                    }
                    cols.push(obj);
                }

                $scope.colDefs = cols;
            });
        }
    }
}]);
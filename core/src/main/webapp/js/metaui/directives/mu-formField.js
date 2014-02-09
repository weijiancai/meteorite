metauiDirectives.directive('muFormField', function () {
    return {
        replace: true,
        templateUrl: 'js/templates/formFieldTpl.html'
    }
});

metauiDirectives.directive('configureGrid', [ 'JsRoutes', function (JsRoutes) {
    return {
        restrict: 'A',
        controller: function ($scope, $element, $attrs, $transclude) {
            var params = $scope.$parent.$eval($attrs['configureGrid']);
            $scope.gridId = params.id;
            var dataName = params.dataName;
            JsRoutes.Directives.getGridOptions.get({
                id: $scope.gridId
            }, function (options) {
                if (options && options.columns) {
                    $scope.columnDefs = options.columns;
                }
            })
            $scope.gridColumns = [];
            $scope.configureGridModalShown = false;
            $scope.$parent.configureGrid = function () {
                $scope.configureGridModalShown = true;
            }
            $scope.$watch('gridWidth', function (newV) {
                $scope.layoutPlugin1.updateGridLayout();
            });
            $scope.layoutPlugin1 = new ngGridLayoutPlugin();
            $scope.columnDefs = [ ];
            $scope.gridOptions = {
                data: dataName,
                columnDefs: 'columnDefs',
                plugins: [$scope.layoutPlugin1]
            }
            $scope.$on('ngGridEventColumns', function (newColumns) {
                var columns = newColumns.targetScope.columns;
                columns = columns.filter(function (col) {
                    return col.field !== '✔';
                })
                $scope.gridColumns = columns.map(function (col) {
                    return {
                        field: col.field,
                        displayName: col.displayName,
                        width: col.width
                    }
                });
                var total = 0;
                $scope.gridColumns.forEach(function (col) {
                    total += col.width;
                });
                $scope.gridWidth = total;
            });
            $scope.gridWidth = 500;
            $scope.$on('')
            $scope.getFields = function () {
                var data = $scope.$parent[dataName];
                if (data && data.length > 0) {
                    var keys = _.keys(data[0]);
                    return _.reject(keys, function (key) {
                        return _.find($scope.columnDefs, function (col) {
                            return col.field === key;
                        });
                    });
                }
                return [];
            }
            $scope.addColumn = function (field) {
                $scope.columnDefs.push({
                    field: field,
                    displayName: field,
                    width: 100
                });
            }
            $scope.removeColumn = function (col) {
                $scope.columnDefs = _.reject($scope.columnDefs, function (c) {
                    return c.field == col.field;
                });
            }
            $scope.$watch(dataName, function (data) {
                if (data.length > 0 && $scope.columnDefs.length == 0) {
                    $scope.getFields().forEach(function (f) {
                        $scope.addColumn(f);
                    });
                }
            })
            $scope.saveGridOptions = function () {
                JsRoutes.Directives.saveGridOptions.post({
                    id: $scope.gridId,
                    columns: $scope.gridColumns
                }, function () {
                    $scope.configureGridModalShown = false;
                }, null, {
                    postType: 'json'
                });
            }
        },
        scope: true,
        templateUrl: JsRoutes.Directives.configureGrid.link({format: 'html'})
    }
}])
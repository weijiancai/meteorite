
// Declare app level module which depends on filters, and services
var metauiDirectives = angular.module('metaui.directives', []);
var metauiServices = angular.module('metaui.services', []);
var metauiFilters = angular.module('metaui.filters', []);

// initialization of services into the main module
var metaui = angular.module('metaui', ['metaui.directives', 'metaui.services', 'metaui.filters', 'ngGrid', 'ngAnimate', 'ngSanitize']);

metaui.run(['$rootScope', function($rootScope) {
    // 元数据缓存
    $rootScope.metaCache = new ObjMap();
    // 数据字典缓存
    $rootScope.dictCache = new ObjMap();
    $rootScope.dictCache.getDisplayValue = function(dictId, data) {
        if(this[dictId]) {
            return this[dictId]['' + data];
        }

        return '';
    }
}]);
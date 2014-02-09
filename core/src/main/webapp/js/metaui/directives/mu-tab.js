metauiDirectives.directive('muTabs', function() {
    return {
        restrict: 'E',
        transclude: true,
        scope: {},
        controller: function($scope, $element) {
            var tabs = $scope.tabs = [];

            $scope.select = function(tab) {
                angular.forEach(tabs, function(tab) {
                    tab.selected = false;
                });
                tab.selected = true;
            };

            this.addTab = function(tab) {
                if(tabs.length == 0) $scope.select(tab);
                tabs.push(tab);
            };
        },
        template:
            '<div class="tabbable">' +
                '<ul class="nav nav-tabs">' +
                    '<li ng-repeat="tab in tabs" ng-class="{active:tab.selected}">' +
                        '<a href="" ng-click="select(tab)">{{tab.title}}</a> ' +
                    '</li>' +
                '</ul>' +
                '<div class="tab-content" ng-transclude></div>' +
            '</div>',
        replace: true
    }
}).directive('muTab', function() {
    return {
        require: '^muTabs',
        restrict: 'E',
        transclude: true,
        scope: {title: '@'},
        link: function(scope, element, attrs, tabsCtrl) {
            tabsCtrl.addTab(scope);
        },
        template:
            '<div class="tab-pane" ng-class="{active:selected}" ng-transclude></div>',
        replace: true
    }
});
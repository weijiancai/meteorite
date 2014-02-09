metauiDirectives.directive('muDict', ['MUDict', function(MUDict) {
    return {
        restrict: 'A'

    }
}]);

metauiDirectives.directive('muCurrentTime', function($timeout, $filter) {
    return function($scope, $element, $attrs) {
        var format = 'yyyy-MM-dd HH:mm:ss', timeoutId;

        function updateTime() {
            $element.text($filter('date')(new Date(), format));
        }

        $scope.$watch($attrs.muCurrentTime, function(value) {
            format = value;
            updateTime();
        });

        function updateLater() {
            timeoutId = $timeout(function() {
                updateTime();
                updateLater();
            }, 1000);
        }

        $element.bind('$destroy', function() {
           $timeout.cancel(timeoutId);
        });

        updateLater();
    }
});

metauiDirectives.directive('ququ', function() {
    return {
        restrict: 'E',
        transclude: true,
        scope: {},
        replace: true,
        template:
            '<div><img ng-click="info()" width="{{size}}" src="http://image.sjrjy.com/201011/291354164ea84578066309.jpg"></div>',
        controller: function($scope, $attrs, $element) {
            $scope.size = $attrs.size;

            $scope.info = function() {
                alert('size:' + $scope.size + ', src:' + $($element).find('img').attr('src'));
            }
        }
    }
});
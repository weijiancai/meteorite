metauiDirectives.directive('muDict', function($resource, $http) {
    return {
        restrict: 'A',
        replace: true,
        controller: function($scope, $element, $attrs) {
            $scope.isDisplayText = $attrs['isDisplayText'];
            $scope.codeList = [];

            /*$resource('/dict/:id', {id: '@id'}, function(res) {
                $scope.codeList = res.codeList;
            }).get({id: $attrs['muDict']});*/

            var dictId = $attrs['muDict'];
            $http({url: '/dict', method: "get", params: {id: dictId}}).success(function(data) {
                $scope.codeList = data.codeList;
            });
        },
        link: function(scope, element, attrs) {

        },
        template:
            '<div><div ng-show="isDisplayText"></div>' +
            '<select ng-show="!isDisplayText"><option ng-repeat="code in codeList" value="{{code.name}}">{{code.value}}</option></select></div>'
    }
});

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
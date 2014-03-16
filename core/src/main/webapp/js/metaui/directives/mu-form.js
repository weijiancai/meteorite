metauiDirectives.directive('muForm', ['MUConfig', function (MUConfig) {
    return {
        transclude: true,
        templateUrl: 'js/metaui/templates/formTpl.html',
        controller: function($scope, $element, $attrs, $transclude) {

//            alert($element.attr('mu-Form'));
//            $scope.muFormOptions = $.extend(defaults, MUConfig.get($element.attr('mu-Form')));
            var formName = $attrs['muForm'];
//            alert(formName);
//            var formConfig = MUConfig.get(formName);
//            MUConfig.addFormConfig(formName, formConfig);
//            $scope[$element.attr('mu-Form') + 'FormOption'] = $.extend(defaults, formConfig);

//            var muFormOptions = $scope[$element.attr('mu-Form') + 'FormOption'];
//            $scope.muFormOptions = MUConfig.get(formName);
            var form = new M.Form($scope.muFormOptions);

            /*var fields = $scope.muFormOptions.fields;
            for(var i = 0; i < fields.length; i++) {
                $scope.muForm[fields[i].name] = fields[i].defaultValue || '';
//                $scope.muForm.width = fields[i].width || 180;
//                $scope.muForm.labalGap = fields[i].labelGap || 5;
//                $scope.muForm.fieldGap = fields[i].fieldGap || 15;
            }*/

            $scope.muForm = {};
            $scope.trs = form.getTrs();
//            alert($scope.trs.length);
//            $scope.trs = muFormOptions.getTrs();
//            $scope.trs = [];

//            $scope.layout($scope.muFormOptions);
//            $scope.formField = $scope.muFormOptions.fields;
            $scope.$watch('muFormOptions.fields', function() {
//                alert("change......")
//                $scope.layout($scope.muFormOptions);
            });


            $scope.$watch('muFormOptions', function() {
                var form = new M.Form($scope.muFormOptions);
                $scope.formConfig = form.config;
                var fields = form.config.fields;
                for(var i = 0; i < fields.length; i++) {
                    $scope.muForm[fields[i].name] = fields[i].defaultValue || '';
//                $scope.muForm.width = fields[i].width || 180;
//                $scope.muForm.labalGap = fields[i].labelGap || 5;
//                $scope.muForm.fieldGap = fields[i].fieldGap || 15;
                }
                $scope.trs = form.getTrs();
            });

            $scope.updateForm = function() {
                $scope.muFormOptions.layoutType = 'T';
                $scope.trs = form.getTrs();
//                alert($scope.trs.length);
//                $scope.$apply($scope.trs);
                $scope.trs.push({"displayStyle":"10"});
            };

            /*$scope.getFormConfig = function() {
                return muFormOptions;
            }*/
        }
    };
}]);
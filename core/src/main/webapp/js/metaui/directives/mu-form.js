metauiDirectives.directive('muForm', ['MUConfig', function (MUConfig) {
    return {
        transclude: true,
        templateUrl: 'js/templates/formTpl.html',
        controller: function($scope, $element, $attrs, $transclude) {
            var defaults = {
                colCount: 3,
                labelGap: 5,
                fieldGap: 15,
                layoutType: 'T', // 0 TABLE布局， 1 流式布局
                fields: []
            };

//            alert($element.attr('mu-Form'));
//            $scope.muFormOptions = $.extend(defaults, MUConfig.get($element.attr('mu-Form')));
            var formName = $attrs['muForm'];
//            alert(formName);
//            var formConfig = MUConfig.get(formName);
//            MUConfig.addFormConfig(formName, formConfig);
//            $scope[$element.attr('mu-Form') + 'FormOption'] = $.extend(defaults, formConfig);

//            var muFormOptions = $scope[$element.attr('mu-Form') + 'FormOption'];
            $scope.muFormOptions = MUConfig.get(formName);
            $scope.muForm = {};

            var fields = $scope.muFormOptions.fields;
            for(var i = 0; i < fields.length; i++) {
                $scope.muForm[fields[i].name] = fields[i].defaultValue || '';
//                $scope.muForm.width = fields[i].width || 180;
//                $scope.muForm.labalGap = fields[i].labelGap || 5;
//                $scope.muForm.fieldGap = fields[i].fieldGap || 15;
            }

            $scope.trs = new TableLayout($scope.muFormOptions).getTrs();
//            alert($scope.trs.length);
//            $scope.trs = muFormOptions.getTrs();
//            $scope.trs = [];

            $scope.layout = function(option) {
                var trs = [];
                // 如果fields的第一个元素数组
                if($.isArray(option.fields[0])) {

                } else {
                    var tds = [];
                    for(var i = 0; i < option.fields.length; i++) {
                        var field = option.fields[i];

                        if(field.isSingleLine && tds.length > 0) {
                            trs.push(tds);
                            tds = [];
                        }

                        if(field.displayStyle == 4) {
                            tds.push({label: ''});
                        } else {
                            tds.push({label: field.displayName});
                        }

//                tds.push({gap: 'width:' + (field.labelGap ? field.labelGap : 5) + 'px;'});
                        var fieldStyle = '';
                        if(field.height) {
                            fieldStyle += 'height:' + field.height + 'px;';
                        }
                        fieldStyle += 'margin-left:' + (field.labelGap ? field.labelGap : 5) + 'px;';
                        if((i + 1) % option.colCount > 0 && !field.isSingleLine) {
                            fieldStyle += 'margin-right:' + (field.fieldGap ? field.fieldGap : 15) + 'px;';
                        }
                        /*tds.push({style: fieldStyle});*/

                        var fieldTd = {};
                        // 显示风格
                        fieldTd.displayStyle = field.displayStyle || 10;
                        // 默认值
                        fieldTd.defaultValue = field.defaultValue;
                        // 数据字典
                        if(field.displayStyle == DS_COMBO_BOX) {
                            fieldTd.dict = dictList[field.dictId];
                        }
                        // 宽度
                        fieldTd.width = field.width || 180;
                        // Label Gap
                        fieldTd.labelGap = field.labalGap || 5;
                        // Field Gap
                        fieldTd.fieldGap = field.fieldGap || 15;
                        // 名称
                        fieldTd.name = field.name;
                        if(field.displayStyle == 4) {
                            fieldTd.displayName = field.displayName;
                        }
                        if(field.isSingleLine && option.colCount > 1) {
                            fieldTd.colspan = option.colCount * 2 - 1;
                            if(option.colCount == 2) {
                                fieldStyle += 'width: 96%;';
                            } else if(option.colCount == 3) {
                                fieldStyle += 'width: 98%;';
                            } else {
                                fieldStyle += 'width: 98%;';
                            }

                        } else {
                            fieldStyle += 'width:' + (field.width || 180) + 'px;';
                        }
                        fieldTd.style = fieldStyle;
                        tds.push(fieldTd);

                        if(option.colCount == 1 || (i + 1) % option.colCount == 0 || field.isSingleLine || field.isLineEnd) {
                            trs.push(tds);
                            tds = [];
                        }
                    }
                    if(tds.length > 0) {
                        trs.push(tds);
                    }
                }

                return trs;
            }

            $scope.layout($scope.muFormOptions);
            $scope.formField = $scope.muFormOptions.fields;
            $scope.$watch('muFormOptions.fields', function() {
//                alert("change......")
                $scope.layout($scope.muFormOptions);
            });

            $scope.updateForm = function() {
                $scope.muFormOptions.layoutType = 'T';
                $scope.trs = new TableLayout($scope.muFormOptions).getTrs();
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
/**
 * MetaUI函数库
 *
 * @author wei_jc
 */
(function(window) {
    var M = function() {

    };

    M.Tree = function(options) {
        var defaults = {
            treeId: 'muTree',
            nodeId: 'id',
            nodeLabel: 'label',
            nodeChildren: 'children',
            nodeLabelTemplate: '',
            onNodeClick: '',
            data: []
        };

        var self = this;
        self.config = $.extend(defaults, options);


    };

    M.Form = function(options) {
        var defaults = {
            colCount: 3,
            labelGap: 5,
            fieldGap: 15,
            layoutType: 'T', // 0 TABLE布局， 1 流式布局
            fields: []
        };

        var self = this;
        self.config = $.extend(defaults, options);

        var fields = self.config.fields;
        if(!$.isArray(fields)) {
            var temps = [];
            for(var key in fields) {
                if(fields.hasOwnProperty(key)) {
                    if($.isArray(fields[key])) continue;
                    var f = new M.FormField({name: key, displayName: key, defaultValue: fields[key]});
                    temps.push(f);
                }
            }
            self.config.fields = temps;
        }

        this.getTrs = function() {
            var config = self.config;
            var fields = self.config.fields;
            var trs = [];
            var tds = [];

            for(var i = 0; i < fields.length; i++) {
                var field = fields[i];

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
                if((i + 1) % config.colCount > 0 && !field.isSingleLine) {
                    fieldStyle += 'margin-right:' + (field.fieldGap ? field.fieldGap : 15) + 'px;';
                }
                /*tds.push({style: fieldStyle});*/

                var fieldTd = {};
                // 显示风格
                fieldTd.displayStyle = field.displayStyle || 10;
                // 默认值
                fieldTd.defaultValue = field.defaultValue;
                // 数据字典
                /*if(field.displayStyle == DS_COMBO_BOX) {
                    fieldTd.dict = dictList[field.dictId];
                }*/
                // 宽度
                fieldTd.width = field.width;
                // 名称
                fieldTd.name = field.name;
                if(field.displayStyle == 4) {
                    fieldTd.displayName = field.displayName;
                }
                if(field.isSingleLine && config.colCount > 1) {
                    fieldTd.colspan = config.colCount * 2 - 1;
                    if(config.colCount == 2) {
                        fieldStyle += 'width: 96%;';
                    } else if(config.colCount == 3) {
                        fieldStyle += 'width: 98%;';
                    } else {
                        fieldStyle += 'width: 98%;';
                    }

                } else {
                    fieldStyle += 'width:' + (field.width || 180) + 'px;';
                }
                fieldTd.style = fieldStyle;
                tds.push(fieldTd);

                if(config.colCount == 1 || (i + 1) % config.colCount == 0 || field.isSingleLine || field.isLineEnd) {
                    trs.push(tds);
                    tds = [];
                }
            }

            if(tds.length > 0) {
                trs.push(tds);
            }

            return trs;
        }
    };

    M.FormField = function(field) {
        this.id = field['id'] || '';
        this.name = field['name'];
        this.colName = field['colName'];
        this.displayName = field['displayName'];
        this.isSingleLine = field['isSingleLine'];
        this.isDisplay = field['display'] || true;
        this.width = field['width'] || 180;
        this.height = field['height'];
        this.displayStyle = field['displayStyle'];
        this.isValid = field['valid'];
        this.sortNum = field['sortNum'];
        this.dataType = field['dataType'];
        this.dictList = field['dictList'];
        this.queryMode = field['queryMode'];
        this.readonly = field['readonly'];
        this.required = field['required'];
        this.styleClass = field['styleClass'];
        this.defaultValue = field['defaultValue'];
        this.colspan = field['colspan'];
    };

    window.M = M;
})(window);

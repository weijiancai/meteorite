metauiServices.factory('MUConfig', ['$resource', '$http', function($resource, $http) {
    var formConfigCache = {};
    var metaCache = new ObjMap();
    var dictCache = new ObjMap();
    var viewCache = [];
    var layoutList = [];
    var propMap = new ObjMap();

    for(var i = 0; i < layoutList.length; i++) {
        var props = layoutList[i].props;
        if(props) {
            for(var j = 0; j < props.length; j++) {
                propMap.push(props[j].id, props[j]);
            }
        }
    }


    return {
        get: function(name) {
            if(!name) return null;

            var config = muConfig[name];
            if(!config) {
               config =  muConfig[name] = {fields: []};
            }
            return config;
        },
        getFormConfig: function(name) {
            return formConfigCache[name];
        },

        /**
         * 根据ID查找数据字典
         *
         * @param id
         * @param onSuccess
         */
        getDict: function(id, onSuccess) {
            var obj = dictCache.get(id);
            if(obj) {
                onSuccess(obj);
            } else {
                $http({url:"/dict", method: "get", params:{id: id}}).success(function(data) {
                    dictCache.put(id, data);
                    onSuccess(data);
                });
            }
        },

        /**
         * 获得字典显示名
         *
         * @param dictId
         * @param value
         * @returns {*}
         */
        getDictDisplayName: function (dictId, value) {
            var codeList = dictCache.get(dictId).codeList;
            for (var i = 0; i < codeList.length; i++) {
                if (codeList[i].name.toLowerCase() == ('' + value).toLowerCase()) {
                    return codeList[i].value;
                }
            }
            return "Not Found";
        },

        /**
         * 根据名称查找元数据
         * @param name
         * @param onSuccess
         */
        getMeta: function(name, onSuccess) {
            var obj = metaCache.get(name);
            if(obj) {
                onSuccess(obj);
            } else {
                $http({url:"/meta", method: "post", params:{name: name}}).success(function(data) {
                    metaCache.put(name, data);
                    onSuccess(data);
                });
            }
        },
        getMetaField: function(id) {
            for(var i = 0; i < metaCache.length; i++) {
                var fields = metaCache[i]['fields'];
                for(var j = 0; j < fields.length; j++) {
                    if(fields[j]['id'] == id) {
                        return fields[j];
                    }
                }
            }

            return null;
        },
        getView: function(viewId) {
            return Utils.getValue(viewCache, 'id', viewId);
        },
        getLayout: function(layoutId) {
            return Utils.getTreeValue(layoutList, 'id', layoutId);
        },
        getLayoutPropery: function(propId) {
            return propMap.get(propId);
        },
        meta: $resource('/meta/:name', {name: '@name'})
    };
}]);

metauiServices.factory('Meta', ['$resource', function($resource) {
    return $resource('/meta/:name', {}, {
        update: {
            method: 'PUT'
        }
    });
}]);

metauiServices.factory('Dict', ['$resource', function($resource) {
    return $resource('/dict/:id', {}, {
        update: {
            method: 'PUT'
        }
    });
}]);

metauiServices.factory('Phone', ['$resource',
    function($resource){
        return $resource('/meta/:name', {}, {
            query: {method:'GET', params:{}, isArray:true}
        });
    }
]);

metauiServices.factory('Layout', ['$resource', function($resource) {
    return $resource('/layout/:id');
}]);

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
                $http({url:"/dict", method: "post", params:{id: id}}).success(function(data) {
                    dictCache.put(id, data);
                    onSuccess(data);
                });
            }
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

    function _getMeta(name) {
        for(var i = 0; i < metaCache.length; i++) {
            if(metaCache[i]['name'] == name) {
                return metaCache[i];
            }
        }

        return null;
    }
}]);

metauiServices.factory('Meta', ['$resource', function($resource) {
    var metaCache = new ObjMap();

    /*return {
        getMeta: function(name) {
            return metaCache.get(name);
        },
        reqMeta: function() {
            return $http({url: '/layout', method: 'POST'}).then(function(data) {
                alert(data);
                return data;
            });
        },
        setMeta: function(name, meta) {
            metaCache.put(name, meta);
        }
    };*/

    metaCache.query = function() {
        return $resource('/meta').query();
    };

    return metaCache;
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

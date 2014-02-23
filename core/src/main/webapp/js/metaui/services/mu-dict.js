metauiServices.factory('MUDict', function($resource) {
    var dictCache = new ObjMap();

    return {
        getDict: function(dictId) {
            var obj = dictCache.get('' + dictId);
            if(obj) {
                return obj;
            } else {
                $resource('/dict/:id', {id: '@id'}, function(res) {
                    dictCache.put('' + dictId, res.data);
                }).get({id: dictId});

                obj = {codeList: []};
                dictCache.put('' + dictId, obj);
                return obj;
            }
        },
        getDisplayName: function(dictId, value) {
            var list = dictCache.get('' + dictId);
            if(list) {
                for(var i = 0; i < list.length; i++) {
                    if(list[i].value == value) {
                        return list[i].name;
                    }
                }
            }
            return null;
        }
    }
});

metauiServices.factory('decorator', ['AfterMethod', function(afterMethod) {
    function _createWrapper(fn) {
        var newFn = function() {
            var orig = fn.apply(fn, arguments);
            afterMethod.apply(fn, arguments);
            return orig;
        };

        newFn.toString = function() {
            return fn.toString();
        };

        return newFn();
    }

    // Decorator Method
    return function(scope, functions) {
        for(var i = 0; i < functions.length; i++) {
            var fName = functions[i];
            if(typeof scope[fName] == 'function') {
                scope[fName] = _createWrapper(scope[fName]);
            }

        }
        return scope;
    }
}]);
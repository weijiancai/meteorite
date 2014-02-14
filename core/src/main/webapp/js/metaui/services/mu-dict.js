metauiServices.factory('MUDict', function($resource) {
    var dictCache = new ObjMap();

    return {
        getDict: function(dictId) {
            return $resource('/dict/:id', {id: '@id'}, function(res) {
                dictCache.put('' + dictId, res.data);
            }).get(dictId);
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
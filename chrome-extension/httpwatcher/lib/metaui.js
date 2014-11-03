var MU = {
    Rest: {
        post: function(url, model, callback) {
            $.ajax({
                type: 'POST',
                url: url,
                data: JSON.stringify(model), // '{"name":"' + model.name + '"}',
                dataType: 'json',
                processData: false,
                contentType: 'application/json',
                success: callback,
                error: function(req, status, ex) {},
                timeout:60000
            });
        },
        put: function(url, model, callback) {
            $.ajax({
                type: 'PUT',
                url: url,
                data: JSON.stringify(model), // '{"name":"' + model.name + '"}',
                dataType: 'json',
                processData: false,
                contentType: 'application/json',
                success: callback,
                error: function(req, status, ex) {
                    alert(status);
                },
                timeout:60000
            });
        },
        find: function(url, model, callback) {
            $.ajax({
                type: 'GET',
                url: url,
                contentType: 'application/json',
                success: callback,
                dataType: 'json',
                error: function(req, status, ex) {},
                timeout:60000
            });
        },
        remove: function(url, model, callback) {
            $.ajax({
                type: 'DELETE',
                url: url,
                contentType: 'application/json',
                dataType: 'json',
                success: callback,
                error: function(req, status, ex) {},
                timeout:60000
            });
        }
    },
    Meta: function(metaName) {
        this.add = function(param, callback) {
            MU.Rest.put("/meta/" + metaName, param, callback);
        }
    },
    Util: {
        String: {
            endsWith: function() {

            },
            /** 将字符串反序排列 **/
            reserve: function(str) {
                var temp = "";
                for (var i = str.length - 1; i >= 0; i--) {
                    temp = temp.concat(str.charAt(i));
                }
                return temp;
            }
        }
    },
    tree: {
        DomainTree: function() {
            var domains = [];
            var map = new MU.ObjMap();

            this.addDomain = function(newDomain) {
                var temp =  newDomain;
                for(var i = 0; i < domains.length; i++) {
                    var domain = domains[i];
                }
            };

            function DomainTreeNode(domain) {
                this.domain = domain;
                this.cookies = [];
                this.children = [];
            }
        }
    }
};

/**
 * 对象Map，使用JS中的Object构造的Map对象
 *
 * @constructor
 */
MU.ObjMap = function () {
    this._obj = {};
    this._keys = [];

    /**
     * 将key，value放入对象Map中
     *
     * @param key
     * @param value
     */
    this.put = function (key, value) {
        this._keys.push(key);
        this._obj[key] = value;
    };

    /**
     * 根据key值，获得对象map中对应的value值
     *
     * @param key
     * @returns {*}
     */
    this.get = function (key) {
        return this._obj[key];
    };

    /**
     * 获得对象map中的所有key值
     *
     * @returns {Array}
     */
    this.keys = function () {
        return this._keys;
    };

    /**
     * 获得对象map中的所有value值
     *
     * @returns {Array}
     */
    this.values = function () {
        var list = [];
        for (var i = 0; i < this._keys.length; i++) {
            list.push(this._obj[this._keys[i]]);
        }

        return list;
    };
};

(function ($) {
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);
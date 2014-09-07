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
    }
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
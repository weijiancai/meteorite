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

// ======================================日期处理相关函数=========================================================
MU.UDate = {
    /**
     * 日期计算
     * @param field  日期类型 s 秒，m 分，h 小时，d 天，w 周，M 月，q 季度，y 年
     * @param number 要增加的数字
     * @param date   日期
     */
    dateAdd: function(field, number, date) {
        var dtTmp = date ? date : new Date();
        switch (field) {
            case 's' :return new Date(Date.parse(dtTmp) + (1000 * number));
            case 'm' :return new Date(Date.parse(dtTmp) + (60000 * number));
            case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * number));
            case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * number));
            case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * number));
            case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
            case 'M' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
            case 'y' :return new Date((dtTmp.getFullYear() + number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
        }
    },
    /**
     * 根据格式化样式将日期格式化为字符串，并可以进行日期字段的加减操作
     * 注意：目前格式化样式的要求是：yyyy 四位年，MM 两位月，dd 两位天，hh 两位24制小时，mm 两位分，ss 两位秒
     *
     * @param _date  要格式化的日期
     * @param _style 格式化样式, CN, 格式化为中文样式
     * @param field  日期字段类型
     * @param number 要增加或减少的数字
     * @return str 格式化后的日期字符串
     */
    format: function(_date, _style, field, number) {
        if('CN' == _style) {
            return this.formatToCn(_date);
        }
        var style = _style ? _style : 'yyyy-MM-dd';
        var date = _date ? _date : new Date();
        if(field && number) {
            date = S.dateAdd(field, number, date);
        }
        var year, month, d, h, m, s;
        year = date.getFullYear();
        month = (month = date.getMonth() + 1) < 10 ? '0' + month : month;
        d = (d = date.getDate()) < 10 ? '0' + d : d;
        h = (h = date.getHours()) < 10 ? '0' + h : h;
        m = (m = date.getMinutes()) < 10 ? '0' + m : m;
        s = (s = date.getSeconds()) < 10 ? '0' + s : s;

        return style.replace('yyyy', year).replace('MM', month).replace('dd', d).replace('hh', h).replace('mm', m).replace('ss', s);
    },
    /**
     * 格式化为中文显示的日期字符串
     *
     * @param _date {String | Date}
     * @returns {string}
     */
    formatToCn: function(_date) {
        if(!_date) return;

        if(typeof(_date) == 'string') {
            _date = toDate(_date);
        }

        var date = new Date();
        var curYear = date.getFullYear();
        var curMonth = date.getMonth() + 1;
        var curDay = date.getDate();
        var curWeek = parseInt(curDay / 7);

        var year = _date.getFullYear();
        var month = _date.getMonth() + 1;
        var day = _date.getDate();
        var hour = _date.getHours();
        var week = parseInt(day / 7);
        var dayWeek = _date.getDay();


        var hourStr = "早上";
        if(hour == 12) {
            hourStr = "中午";
        } else if(hour >= 13 && hour <= 18) {
            hourStr = "下午";
        } else if(hour > 18 && hour <= 23) {
            hourStr = "晚上 ";
        }

        var CN_NUM = ["一", "二", "三","四", "五", "六","七", "八", "九","十", "十一", "十二"];

        var result = '';
        if(year == curYear) { // 同年
            if(month == curMonth) { // 同月
                if(curDay == day) { // 同日
                    result += "今天";
                } else if(curDay - day == 1) {
                    result += "昨天";
                }else if(curWeek == week) {
                    result += "周" + CN_NUM[dayWeek - 1];
                } else if(curWeek - week == 1) {
                    result += "上周" + CN_NUM[dayWeek - 1];
                }
            } else { // 不同月
                result += (month + 1) + "月" + date + "日 ";
            }
        } else { // 不同年
            result += this.format(_date);
        }
        result += " " + hourStr + " " + this.format(_date, "HH:mm");

        return result;
    },
    /**
     * 将字符串解析为Date类型
     * @param dateStr 要解析的日期字符串
     * @param _style  格式化样式
     */
    toDate: function(dateStr, _style) {
        var style = _style ? _style : 'yyyy-MM-dd';
        switch (style) {
            case 'yyyy-MM-dd' :
                return new Date(dateStr.substr(0,4), dateStr.substr(5,2)-1, dateStr.substr(8));
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
/**
 * 文件大小过滤器
 */
metauiFilters.filter('fileSize', function() {
    var kb = 1024;
    var mb = 1024 * 1024;
    var gb = 1024 * 1024 * 1024;
    var tb = 1024 * 1024 * 1024 * 1024;
    return function(input) {
        var number = parseInt(input) + 0.0;
        var result;
        if(number > tb) {
            result = (number / tb).toFixed(2) + ' TB';
        } else if(number > gb) {
            result = (number / gb).toFixed(2) + ' GB';
        } else if(number > mb) {
            result = (number / mb).toFixed(2) + ' MB';
        } else {
            result = (number / kb).toFixed(2) + ' KB';
        }

        return result.replace('.00', '');
    };
});
var requestTable =  $('#example').DataTable({
    columns: [
        {
            "class":          'details-control',
            "orderable":      false,
            "data":           null,
            "defaultContent": ''
        },
        {data: 'requestId', title: 'requestId'},
        {data: 'url', title: 'url'},
        {data: 'method', title: 'method'},
        {data: 'frameId', title: 'frameId'},
        {data: 'parentFrameId', title: 'parentFrameId'},
        //{data: 'requestBody', title: 'requestBody'},
        {data: 'tabId', title: 'tabId'},
        {data: 'type', title: 'type'},
        {data: 'timeStamp', title: 'timeStamp'}
    ]
});

$('#example tbody').on('click', 'td.details-control', function () {
    var tr = $(this).closest('tr');
    var row = requestTable.row( tr );

    if ( row.child.isShown() ) {
        // This row is already open - close it
        row.child.hide();
        tr.removeClass('shown');
    } else {
        // Open this row
        row.child( format(row.data()) ).show();
        tr.addClass('shown');
    }
});

function format ( data ) {
    var detailTpl = $('#detail_tpl').clone().show();
    // 设置请求头
    detailTpl.find('#requestHeaders').DataTable({
        searching: false,
        lengthChange: false,
        paginate: false,
        info: false,
        columns: [
            {data: 'name'},
            {data: 'value'}
        ],
        data: cache[data['requestId']]['requestHeaders']
    });
    // 设置响应头
    detailTpl.find('#responseHeaders').DataTable({
        searching: false,
        lengthChange: false,
        paginate: false,
        info: false,
        columns: [
            {data: 'name'},
            {data: 'value'}
        ],
        data: cache[data['requestId']]['responseHeaders']
    });
    // 设置请求Cookie
    detailTpl.find('#requestCookies').DataTable({
        searching: false,
        lengthChange: false,
        paginate: false,
        info: false,
        columns: [
            {data: 'name'},
            {data: 'value'}
        ],
        data: getRequestCookies(data['requestId'])
    });
    // 设置响应Cookie
    detailTpl.find('#responseCookies').DataTable({
        searching: false,
        lengthChange: false,
        paginate: false,
        info: false,
        columns: [
            {data: 'name'},
            {data: 'value'},
            {data: 'path', defaultContent: ''},
            {data: 'domain', defaultContent: ''}
        ],
        data: getResponseCookie(data['requestId'])
    });
    // 设置请求字符串
    detailTpl.find('#queryStrings').DataTable({
        searching: false,
        lengthChange: false,
        paginate: false,
        info: false,
        columns: [
            {data: 'name'},
            {data: 'value'}
        ],
        data: getQueryString(data['requestId'])
    });
    // 设置Post Data
    detailTpl.find('#postData').DataTable({
        searching: false,
        lengthChange: false,
        paginate: false,
        info: false,
        columns: [
            {data: 'name'},
            {data: 'value'}
        ],
        data: getPostData(data['requestId'])
    });
    chrome.tabs.get(data['tabId'], function (tab) {
        console.log(tab);
    });
    // 设置内容
    detailTpl.find('#responseContent').val();

    return detailTpl.html();
}

// 从请求头中获取Cookie信息
function getRequestCookies(requestId) {
    var result = [];
    var requestHeaders = cache[requestId]['requestHeaders'];
    if(requestHeaders) {
        for(var i = 0; i < requestHeaders.length; i++) {
            var header = requestHeaders[i];
            if(header.name == 'Cookie') {
                var value = header.value;
                var list = value.split(";");
                for(var j = 0; j < list.length; j++) {
                    var map = list[j].split('=');
                    result.push({name: map[0], value: map[1]});
                }
            }
        }
    }
    return result;
}
// 请响应头中取Cookie信息
function getResponseCookie(requestId) {
    var result = [];
    var responseHeaders = cache[requestId]['responseHeaders'];
    for(var i = 0; i < responseHeaders.length; i++) {
        var header = responseHeaders[i];
        if(header.name == 'Set-Cookie') {
            var value = header.value;
            var object = {};

            var list = value.split(";");
            for(var j = 0; j < list.length; j++) {
                var map = list[j].split('=');
                var key = $.trim(map[0]);
                if(key == 'path') {
                    object.path = map[1];
                } else if(key == 'domain') {
                    object.domain = map[1];
                } else {
                    object.name = map[0];
                    object.value = map[1];
                }
            }

            result.push(object);
        }

    }
    return result;
}
// 获得Query String
function getQueryString(requestId) {
    var result = [];
    var url = cache[requestId]['url'];
    var idx = url.indexOf('?');
    if(idx >= 0) {
        url = url.substr(idx + 1);
        var list = url.split("&");
        for(var j = 0; j < list.length; j++) {
            var map = list[j].split('=');
            result.push({name: map[0], value: map[1]});
        }
    }

    return result;
}
// 获得Post Data
function getPostData(requestId) {
    var result = [];
    var requestBody = cache[requestId]['requestBody'];
    if(requestBody && requestBody['formData']) {
        var formData = requestBody['formData'];
        for(var key in formData) {
            if(formData.hasOwnProperty(key)) {
                result.push({name: key, value: formData[key].join(',')});
            }
        }
    }

    return result;
}

// http request cache
var cache = {};

chrome.webRequest.onBeforeRequest.addListener (
    function(details) {
        //requestTable.row.add(details).draw();
        //console.log("request......");
        //console.log(details);
        if(details.type == 'main_frame' || details.type == 'sub_frame' || details.type == 'object' || details.type == 'xmlhttprequest') {
            cache[details['requestId']] = details;
        }
        return true;
    },
    {urls:["<all_urls>"]},  //监听所有的url,你也可以通过*来匹配。
    ["blocking", "requestBody"]
);

chrome.webRequest.onBeforeSendHeaders.addListener(
    function(details) {
        var obj = cache[details['requestId']];
        if(obj) {
            $.extend(obj, details);
        }

        return {requestHeaders: details.requestHeaders};
    },
    {urls: ["<all_urls>"]},
    ["blocking", "requestHeaders"]
);

/*chrome.webRequest.onHeadersReceived.addListener(
    function(details) {
        console.log(details);
        return details;
    },
    {urls: ["<all_urls>"]},
    ["blocking", "responseHeaders"]
);*/

/*chrome.webRequest.onResponseStarted.addListener(
    function(details) {
        $.extend(cache[details['requestId']], details);
        console.log(cache[details['requestId']]);
        // 新增表格行
        requestTable.row.add(details).draw();

        return details;
    },
    {urls: ["<all_urls>"]},
    ["responseHeaders"]
);*/

chrome.webRequest.onCompleted.addListener(
    function(details) {
        var obj = cache[details['requestId']];
        if(obj) {
            $.extend(obj, details);
            //console.log(cache[details['requestId']]);
            // 新增表格行
            requestTable.row.add(details).draw();
        }

        return details;
    },
    {urls: ["<all_urls>"]},
    ["responseHeaders"]
);

// Cookies
// 启动监听
var ce = new CE();
ce.cookies.load();
// 添加domain监听
ce.cookies.addDomainChangeListener(function() {
    var $domainList = $('#domainList').html('');
    ce.cookies.getDomains().forEach(function(domain) {
        $domainList.append('<li class="list-group-item"><span class="badge">' + ce.cookies.getCookiesByDomain(domain).length + '</span>' + domain + '</li>');
    });
});


function CE() {
    this.cookies = new CE.CookieCache();
}

if (!chrome.cookies) {
    chrome.cookies = chrome.experimental.cookies;
}

CE.CookieCache = function() {
    var self = this;
    var isOnload = false;
    this.cookies_ = {};

    this.reset = function() {
        this.cookies_ = {};
    };

    this.add = function(cookie) {
        var domain = cookie.domain;
        if (!this.cookies_[domain]) {
            this.cookies_[domain] = [];
            if(!isOnload) {
                $(this).trigger('domainChangeEvent');
            }
        }
        this.cookies_[domain].push(cookie);
    };

    this.remove = function(cookie) {
        var domain = cookie.domain;
        if (this.cookies_[domain]) {
            // 从chrome中移除cookie
            removeCookie(cookie);
            // 从缓存中移除cookie
            var i = 0;
            while (i < this.cookies_[domain].length) {
                if (cookieMatch(this.cookies_[domain][i], cookie)) {
                    this.cookies_[domain].splice(i, 1);
                } else {
                    i++;
                }
            }
            if (this.cookies_[domain].length == 0) {
                delete this.cookies_[domain];
                $(this).trigger('domainChangeEvent');
            }
        }
    };

    function removeCookie(cookie) {
        var url = "http" + (cookie.secure ? "s" : "") + "://" + cookie.domain + cookie.path;
        chrome.cookies.remove({"url": url, "name": cookie.name});
    }

    // Returns a sorted list of cookie domains that match |filter|. If |filter| is
    //  null, returns all domains.
    this.getDomains = function(filter) {
        var result = [];
        sortedKeys(this.cookies_).forEach(function(domain) {
            if (!filter || domain.indexOf(filter) != -1) {
                result.push(domain);
            }
        });
        return result;
    };

    this.getCookiesByDomain = function(domain) {
        return this.cookies_[domain];
    };

    this.load = function() {
        isOnload = true;
        chrome.cookies.getAll({}, function(cookies) {
            self.startListening();
            for (var i in cookies) {
                self.add(cookies[i]);
            }
            $(self).trigger('domainChangeEvent');
            isOnload = false;
        });
    };

    this.addDomainChangeListener = function(listener) {
        $(this).bind('domainChangeEvent', listener);
    };

    this.startListening = function() {
        chrome.cookies.onChanged.addListener(listener);
    };

    this.stopListening = function() {
        chrome.cookies.onChanged.removeListener(listener);
    };

    function listener(info) {
        self.remove(info.cookie);
        if (!info.removed) {
            self.add(info.cookie);
        }
    }

    // Compares cookies for "key" (name, domain, etc.) equality, but not "value" equality.
    function cookieMatch(c1, c2) {
        return (c1.name == c2.name) && (c1.domain == c2.domain) &&
            (c1.hostOnly == c2.hostOnly) && (c1.path == c2.path) &&
            (c1.secure == c2.secure) && (c1.httpOnly == c2.httpOnly) &&
            (c1.session == c2.session) && (c1.storeId == c2.storeId);
    }

    // Returns an array of sorted keys from an associative array.
    function sortedKeys(array) {
        var keys = [];
        for (var i in array) {
            keys.push(i);
        }
        keys.sort(function(a, b) {
            return MU.Util.String.reserve(a) - MU.Util.String.reserve(b);
        });
        return keys;
    }
};

var domainTree = {
    domain: '.baidu.com',
    children: [
        {domain: 'www.baidu.com', children: []}
    ]
};
<#-- @ftlvariable name="project" type="com.meteorite.core.project.ProjectDefine" -->
<!DOCTYPE html>
<!--HTML5 doctype-->
<html>

<head>
    <script>
        (function () {
            if ("-ms-user-select" in document.documentElement.style && navigator.userAgent.match(/IEMobile\/10\.0/)) {
                var msViewportStyle = document.createElement("style");
                msViewportStyle.appendChild(
                        document.createTextNode("@-ms-viewport{width:auto!important}")
                );
                document.getElementsByTagName("head")[0].appendChild(msViewportStyle);
            }
        })();
    </script>

    <title>${project.name}</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="stylesheet" type="text/css" href="css/icons.css"/>

    <!--
  <link rel="stylesheet" type="text/css" href="css/af.ui.css" title="default" />
-->
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
    <link rel="stylesheet" type="text/css" href="css/appframework.css"/>
    <link rel="stylesheet" type="text/css" href="css/lists.css"/>
    <link rel="stylesheet" type="text/css" href="css/forms.css"/>
    <link rel="stylesheet" type="text/css" href="css/buttons.css"/>
    <link rel="stylesheet" type="text/css" href="css/badges.css"/>
    <link rel="stylesheet" type="text/css" href="css/grid.css"/>

    <link rel="stylesheet" type="text/css" href="css/android.css"/>
    <link rel="stylesheet" type="text/css" href="css/win8.css"/>
    <link rel="stylesheet" type="text/css" href="css/bb.css"/>
    <link rel="stylesheet" type="text/css" href="css/ios.css"/>
    <link rel="stylesheet" type="text/css" href="css/ios7.css"/>
    <link rel="stylesheet" type="text/css" href="css/tizen.css"/>

    <link rel="stylesheet" type="text/css" href="plugins/css/af.actionsheet.css"/>
    <link rel="stylesheet" type="text/css" href="plugins/css/af.popup.css"/>
    <link rel="stylesheet" type="text/css" href="plugins/css/af.scroller.css"/>
    <link rel="stylesheet" type="text/css" href="plugins/css/af.selectBox.css"/>
    <!-- uncomment for intel.xdk apps
    <script type="text/javascript" charset="utf-8" src="intelxdk.js"></script>
    <script type="text/javascript" charset="utf-8" src="xhr.js"></script>
    -->
    <script type="text/javascript" charset="utf-8" src="./appframework.js"></script>
    <!-- include af.desktopBrowsers.js on desktop browsers only -->
    <script>
        function loadedPanel(what) {
            //We are going to set the badge as the number of li elements inside the target
            $.ui.updateBadge("#aflink", $("#af").find("li").length);
        }


        function unloadedPanel(what) {
            console.log("unloaded " + what.id);
        }

        if (!((window.DocumentTouch && document instanceof DocumentTouch) || 'ontouchstart' in window)) {
            var script = document.createElement("script");
            script.src = "plugins/af.desktopBrowsers.js";
            var tag = $("head").append(script);
            //$.os.desktop=true;
        }

        //  $.feat.nativeTouchScroll=true;
    </script>

    <script type="text/javascript" charset="utf-8" src="./plugins/af.actionsheet.js"></script>
    <script type="text/javascript" charset="utf-8" src="./plugins/af.css3animate.js"></script>
    <script type="text/javascript" charset="utf-8" src="./plugins/af.passwordBox.js"></script>
    <script type="text/javascript" charset="utf-8" src="./plugins/af.scroller.js"></script>
    <script type="text/javascript" charset="utf-8" src="./plugins/af.selectBox.js"></script>
    <script type="text/javascript" charset="utf-8" src="./plugins/af.touchEvents.js"></script>
    <script type="text/javascript" charset="utf-8" src="./plugins/af.touchLayer.js"></script>
    <script type="text/javascript" charset="utf-8" src="./plugins/af.popup.js"></script>

    <script type="text/javascript" charset="utf-8" src="./ui/appframework.ui.js"></script>
    <!-- <script type="text/javascript" charset="utf-8" src="./ui/transitions/all.js"></script> -->
    <script type="text/javascript" charset="utf-8" src="./ui/transitions/fade.js"></script>
    <script type="text/javascript" charset="utf-8" src="./ui/transitions/flip.js"></script>
    <script type="text/javascript" charset="utf-8" src="./ui/transitions/pop.js"></script>
    <script type="text/javascript" charset="utf-8" src="./ui/transitions/slide.js"></script>
    <script type="text/javascript" charset="utf-8" src="./ui/transitions/slideDown.js"></script>
    <script type="text/javascript" charset="utf-8" src="./ui/transitions/slideUp.js"></script>
    <script type="text/javascript" charset="utf-8" src="./plugins/af.slidemenu.js"></script>

    <script type="text/javascript">
        /* This function runs once the page is loaded, but intel.xdk is not yet active */
        //$.ui.animateHeaders=false;
        var search = document.location.search.toLowerCase().replace("?", "");
        //if(!search)
        $.ui.useOSThemes = true;
        if (search.length > 0) //Android fix has too many buggy issues on iOS - can't preview with $.os.android
        {

            $.ui.useOSThemes = true;
            if (search == "win8")
                $.os.ie = true;
            $.ui.ready(function () {
                $("#afui").get(0).className = search;
            });
        }

        var webRoot = "./";
        // $.os.android=true;
        //$.ui.autoLaunch = false;
        $.ui.openLinksNewTab = false;
        $.ui.splitview = false;


        $(document).ready(function () {
            // $.ui.launch();

        });

        /* This code is used to run as soon as intel.xdk activates */
        var onDeviceReady = function () {
            intel.xdk.device.setRotateOrientation("portrait");
            intel.xdk.device.setAutoRotate(false);
            webRoot = intel.xdk.webRoot + "";
            //hide splash screen
            intel.xdk.device.hideSplashScreen();
            $.ui.blockPageScroll(); //block the page from scrolling at the header/footer
        };
        document.addEventListener("intel.xdk.device.ready", onDeviceReady, false);

        function showHide(obj, objToHide) {
            var el = $("#" + objToHide)[0];

            if (obj.className == "expanded") {
                obj.className = "collapsed";
            } else {
                obj.className = "expanded";
            }
            $(el).toggle();

        }


        if ($.os.android || $.os.ie || search == "android") {
            $.ui.ready(function () {
                $("#main .list").append("<li><a id='toggleAndroidTheme'>Toggle Theme Color</a></li>");
                var $el = $("#afui");
                $("#toggleAndroidTheme").bind("click", function (e) {
                    if ($el.hasClass("light"))
                        $el.removeClass("light");
                    else
                        $el.addClass("light");
                });
            });
        }
    </script>
</head>
<body>
<div id="afui">
    <!-- this is the splashscreen you see. -->
    <div id="splashscreen" class='ui-loader heavy'>
        ${project.name}
        <br>
        <br> <span class='ui-icon ui-icon-loading spin'></span>
        <h1>正在启动......</h1>
    </div>

    <div id="header">
        <a id="backButton" href="javascript:;" class="button">返回</a>
        <h1 id="pageTitle">${project.name}</h1>
        <a id='menubadge' onclick='af.ui.toggleSideMenu()' class='menuButton'></a>
    </div>

    <div id="content">
        <div title='Welcome' id="main" class="panel" selected="true" data-load="loadedPanel" data-unload="unloadedPanel" data-tab="navbar_home" data-nav="main_nav">
            <h2 class='expanded' onclick='showHide(this,"main_info");'>Welcome</h2>

            <p id='main_info'>Welcome to the kitchen sink demo for App Framework. Here you will find samples of how to
                use the App Framework libraries. Please select an option from below.</p>

        </div>

    <!-- 二级菜单内容 -->
    <#list project.getNavMenusByLevel(1) as navMenu>
        <div title="${navMenu.displayName}" id="${navMenu.name}" class="panel">
            <ul class="list">
            <#list navMenu.children as subMenu>
                <li>
                    <a href="#${subMenu.name}">${subMenu.displayName}</a>
                </li>
            </#list>
            </ul>
        </div>
    </#list>

    <!-- 三级级菜单内容 -->
    <#list project.getNavMenusByLevel(1) as navMenu>
        <#list navMenu.children as subMenu>
        <div title="${subMenu.displayName}" id="${subMenu.name}" class="panel">
            <ul class="list">
                <#list subMenu.children as threeMenu>
                    <li>
                        <a href="#">${threeMenu.displayName}</a>
                    </li>
                </#list>
            </ul>
        </div>
        </#list>
    </#list>
    </div>

    <!-- Slide Menu -->
    <#--<nav id="main_nav">
        <div class='title'>Home</div>
    <#list project.getNavMenusByLevel(1) as navMenu>
        <ul class="list">
            <li>
                <a href="#${navMenu.name}" class="">${navMenu.displayName}</a>
            </li>
        </ul>
    </#list>
        </div>
    </nav>-->

    <div id="navbar">
    <#list project.getNavMenusByLevel(1) as navMenu>
        <a href="#${navMenu.name}">${navMenu.displayName}</a>
    </#list>
        <#--<a href="#main/1/3" id='navbar_home' class='icon home'>home <span class='af-badge lr'>3</span></a>
        <a href="#af" id='navbar_js' class="icon target">selectors</a>
        <a href="#afuidemo" id='navbar_ui' class="icon picture">ui</a>
        <a href="#afweb" id='navbar_plugins' class="icon key">plugins</a>-->
    </div>
</div>
</body>
</html>
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
    <link rel="stylesheet" type="text/css" href="../../css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="iconfont/iconfont.css"/>

    <link rel="stylesheet" type="text/css" href="css/mu-mobile.css"/>

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
        <a href="#${navMenu.name}" class="${navMenu.icon}">${navMenu.displayName}</a>
    </#list>
        <#--<a href="#main/1/3" id='navbar_home' class='icon home'>home <span class='af-badge lr'>3</span></a>
        <a href="#af" id='navbar_js' class="icon target">selectors</a>
        <a href="#afuidemo" id='navbar_ui' class="icon picture">ui</a>
        <a href="#afweb" id='navbar_plugins' class="icon key">plugins</a>-->
    </div>
</div>
</body>
</html>
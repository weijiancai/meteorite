<%@ page import="com.meteorite.core.config.PathManager" %>
<%@ page import="java.io.File" %>
<%@ page import="com.meteorite.core.util.UFile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">

    <link href="css/bootstrap.css" type="text/css" rel="stylesheet">
    <link href="css/font-awesome.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="js/lib/jquery-2.1.1.js"></script>
    <script type="text/javascript" src="js/lib/bootstrap/bootstrap.js"></script>
    <script type="text/javascript" src="js/lib/yoxview/yoxview-init.js"></script>
    <script type="text/javascript">
        $(function() {
            $(".thumbnails").yoxview({
                lang: "zh-cn",
                backgroundColor: 'Blue',
                playDelay: 5000,
                autoPlay: true
            });
        });
    </script>
</head>
<body>
    <div class="container-fluid">
        <div class="thumbnails yoxview">
            <%
                File dir = PathManager.getWebPath("resource/airport");
                // 截图
                File screenshotDir = new File(dir, "screenshot");
                for (File file : UFile.getAllImages(screenshotDir)) {
                    String path = file.getAbsolutePath().substring(screenshotDir.getAbsolutePath().length()).replace('\\', '/');
            %>
                <a href="resource/airport/screenshot/<%= path%>"  style="width: 200px;height: 300px;">
                    <img src="resource/airport/screenshot/<%= path%>" title="<%= file.getName()%>" class="img-thumbnail"/>
                </a>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>

<%@ page import="java.io.File" %>
<%@ page import="com.meteorite.core.config.PathManager" %>
<%@ page import="com.meteorite.core.util.UImage" %>
<%@ page import="com.meteorite.core.util.UString" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String password = request.getParameter("pwd");
  if (UString.isEmpty(password) || !"20140521".equals(password)) {
    return;
  }
%>
<html>
<head>
    <title></title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">

    <link href="css/bootstrap.css" type="text/css" rel="stylesheet">
  <script type="text/javascript" src="js/lib/yoxview/yoxview-init.js"></script>
  <script type="text/javascript">
    $(function() {
      $("#thumbnails").yoxview({
        lang: "zh-cn",
        backgroundColor: 'Blue',
        playDelay: 5000,
        autoPlay: true
      });
    });
  </script>
</head>
<body>
<div id="thumbnails" class="thumbnails yoxview">
  <%
    File dir = PathManager.getWebPath("xuanxuan");
    /*File smallDir = new File(dir, "small");
    if (!smallDir.exists()) {
      smallDir.mkdirs();
    }*/
    for (File file : dir.listFiles()) {
      // 写入小图片
      /*File targetFile = new File(smallDir, file.getName());
      if (!targetFile.exists()) {
        UImage.zoomImage(file, 102, 102, targetFile);
      }*/
  %>
  <a href="xuanxuan/<%= file.getName()%>">
    <img src="xuanxuan/<%= file.getName()%>" title="<%= file.getName()%>" class="img-thumbnail" style="width: 140px;height: 140px;"/>
  </a>
  <%
    }
  %>
</div>

</body>
</html>

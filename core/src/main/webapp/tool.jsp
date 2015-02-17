<%@ page import="java.io.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.prefs.Preferences" %>

<%!
    static final String PREFS_NODE = "com.wjc.tools";
    static final String PREFS_TOMCAT_HOME = "tomcat_home";

    public class FileUpload {
        private HttpServletRequest request;
        private File tmpFile;
        private File uploadDir;
        private String fileName;

        public FileUpload(HttpServletRequest request) throws IOException {
            this.request = request;

            String contentType = request.getContentType();
            if ((null == contentType) || (!contentType.toLowerCase(Locale.ENGLISH).startsWith("multipart/"))) {
                return;
            }

            System.out.println(getFileSize());
            parse();
        }

        private void parse() throws IOException {
            writeTmpFile();

            int n;
            //read the temp file.
            RandomAccessFile random = new RandomAccessFile(tmpFile, "r");
            RandomAccessFile random2 = null;
            try {
                String line;
//                String fileName = null;
                while (true) {
                    line = random.readLine();
                    /*System.out.println(line);
                    System.out.println(new String(line.getBytes(), "GBK"));
                    int idx = line.indexOf("filename=\"");
                    if (idx >= 0) {
                        int start = idx + "filename=\"".length();
                        fileName = line.substring(start, line.indexOf("\"", start));
                        System.out.println("fileName = " + fileName);
                    }
                    System.out.println(line.length());*/
                    if (line.length() == 0) {
                        break;
                    }
                }
                if (fileName == null) {
                    throw new RuntimeException("文件名不存在！");
                }

                File realFile = new File(uploadDir, fileName);
                random2 = new RandomAccessFile(realFile, "rw");

                // 开始位置
                long startPosition = random.getFilePointer();
                System.out.println("开始位置：" + startPosition);
                // 结束位置
                //locate the end position of the content.Count backwards 6 lines.
                random.seek(random.length());
                long endPosition = random.getFilePointer();
                long mark = endPosition;
                int j = 1;
                while ((mark >= 0) && (j <= 2)) {
                    mark--;
                    random.seek(mark);
                    n = random.readByte();
                    if (n == '\n') {
                        endPosition = random.getFilePointer();
                        j++;
                    }
                }
                System.out.println("结束位置：" + endPosition);
                System.out.println("文件大小：" + (endPosition - startPosition));
                //locate to the begin of content.Count for 4 lines's end position.
                random.seek(startPosition);
                long startPoint = random.getFilePointer();
                //read the real content and write it to the realFile.
                while (startPoint < endPosition - 1) {
                    n = random.readByte();
                    random2.write(n);
                    startPoint = random.getFilePointer();
                }
                random2.close();
                random.close();
                //delete the temp file.
//                tmpFile.delete();
            } finally {
                if (random2 != null) {
                    random2.close();
                }
                random.close();
            }

            System.out.print("File upload success!");
        }

        // 写入临时文件
        private void writeTmpFile() throws IOException {
            uploadDir = new File(request.getSession().getServletContext().getRealPath("/"), "upload");
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            tmpFile = new File(uploadDir, request.getRequestedSessionId());

            InputStream in = request.getInputStream();
            FileOutputStream fos = new FileOutputStream(tmpFile);
//            StringBuilder sb = new StringBuilder();
            String firstLine = null;
            boolean isDataStart = false;
            byte[] b = new byte[1024];
            int n, lineCount = 0;

            while ((n = in.read(b)) != -1) {
                if(n == '\n') {
                    if(!isDataStart) {
                        String line = new String(b);
                        if(lineCount == 0) {
                            firstLine = line;
                        }
                        lineCount++;

                        int idx = line.indexOf("filename=\"");
                        if (idx >= 0) {
                            int start = idx + "filename=\"".length();
                            fileName = line.substring(start, line.indexOf("\"", start));
                            System.out.println("fileName = " + fileName);
                        }

                        // 是否数据开始
                        if(line.length() == 0) {
                            isDataStart = true;
                        }
                    } else {
                        if(new String(b).equals(firstLine)) {
                            break;
                        }
                        fos.write(b);
                    }
                    b = new byte[1024];
                }
            }

            fos.close();
            in.close();
        }

        public long getFileSize() {
            return Long.parseLong(request.getHeader("Content-length"));
        }
    }
%>
<%
    request.setCharacterEncoding("UTF-8");
    String file = request.getParameter("file");
    System.out.println(file);
    System.out.println(request.getContentType());
//    new FileUpload(request);
    String contentType = request.getContentType();
    if ((null != contentType) && (contentType.toLowerCase(Locale.ENGLISH).startsWith("multipart/"))) {
        File uploadDir = new File(request.getSession().getServletContext().getRealPath("/"), "upload");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        File tmpFile = new File(uploadDir, request.getRequestedSessionId());

        InputStream in = request.getInputStream();
        FileOutputStream fos = new FileOutputStream(tmpFile);
//            StringBuilder sb = new StringBuilder();
        String firstLine = null;
        boolean isDataStart = false;
        byte[] b = new byte[1024];
        int n, lineCount = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        byte[] data = null;

        while (true) {
            n = in.read();
            if (n == -1) {
                break;
            }
            bos.write(n);

            if(n == '\n') {
                if(!isDataStart) {
                    String line = bos.toString("UTF-8").trim();
                    if(lineCount == 0) {
                        firstLine = line;
                    }
                    lineCount++;

                    int idx = line.indexOf("filename=\"");
                    if (idx >= 0) {
                        int start = idx + "filename=\"".length();
                        String fileName = line.substring(start, line.indexOf("\"", start));
                        System.out.println("fileName = " + fileName);
                        fos = new FileOutputStream(new File(uploadDir, fileName));
                    }

                    // 是否数据开始
                    if(line.length() == 0) {
                        isDataStart = true;
                    }
                } else {
                    if(bos.toString().contains(firstLine)) {
                        break;
                    }
                    if(data == null) {
                        data = bos.toByteArray();
                    } else {
                        fos.write(data);
                        data = bos.toByteArray();
                    }
                }
                bos.reset();
            }
        }

        // 写入最后数据，去掉换行
        if (data != null && data.length > 2) {
            if(data[data.length - 1] == '\n' && data[data.length - 2] == '\r') {
                fos.write(data, 0, data.length - 2);
            } else if(data[data.length - 1] == '\n') {
                fos.write(data, 0, data.length - 1);
            }
        }

        fos.close();
        in.close();
    }


    if (file != null) {
//        uploadFile(request);
//        new FileUpload(request);
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>工具页面</title>
</head>
<body>
<form method="post" enctype="multipart/form-data">
    <label>文件上传
        <input name="file" type="file">
    </label>
    <button type="submit">上传</button>
</form>
<ul>
    <%
        File uploadDir = new File(request.getSession().getServletContext().getRealPath("/"), "upload");
        System.out.println(uploadDir);
        if (uploadDir.exists()) {
            for (File aFile : uploadDir.listFiles()) {
    %>
    <li><%= aFile.getName() %> <span><%= aFile.length() %></span></li>
    <%
            }
        }
    %>
</ul>
<hr/>

<form>
    <label>tomcat目录<input type="text" name="tomcatDir"></label>
    <button type="submit">设置</button>
</form>
<ul style="width: 300px;">
<%
    Preferences preferences = Preferences.userRoot();

    String tomcatDirStr = request.getParameter("tomcatDir");
    // 保存到首选项
    if (tomcatDirStr != null) {
        preferences.put(PREFS_TOMCAT_HOME, tomcatDirStr);
    } else {
        // 从首选项中取出tomcat主目录
        tomcatDirStr = preferences.get(PREFS_TOMCAT_HOME, null);
    }

    System.out.println(tomcatDirStr);
    if (tomcatDirStr != null) {
        File tomcatDir = new File(tomcatDirStr);
        if (tomcatDir.exists()) {
            File logDir = new File(tomcatDir, "logs");
            for (File aFile : logDir.listFiles()) {
%>
                <li><a href="?method=openFile&file=<%= aFile.getAbsoluteFile() %>"><%= aFile.getName() %></a> <span><%= aFile.length() %></span></li>
<%
            }
        }
    }
%>
</ul>

<%
    String method = request.getParameter("method");
    String openFile = "";
    StringBuilder fileContent = new StringBuilder();
    if ("openFile".equals(method)) {
        openFile = request.getParameter("file");
        File currentFile = new File(openFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(currentFile)));
        String line;
        while ((line = br.readLine()) != null) {
            fileContent.append(line).append("\r\n");
        }
        br.close();
    }
%>
<div>
    <h1><%= openFile %></h1>
    <textarea rows="30" style="width: 100%;"><%= fileContent %></textarea>
</div>

<script type="text/javascript">

</script>
</body>
</html>

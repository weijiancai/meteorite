package com.meteorite.core.web.rest;

import com.meteorite.core.datasource.ftp.FtpDataSource;
import com.meteorite.core.util.UString;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Tree Rest
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TreeRest extends BaseRest {
    private static final Logger log = Logger.getLogger(TreeRest.class);

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse res) {
        try {
            FtpDataSource ds = FtpDataSource.getInstance("115.29.163.55", "wei_jc", "wjcectong2013#");
            ds.load();

            if (req.getRequestURI().endsWith("/tree")) {
                String path = req.getParameter("path");
                if (UString.isEmpty(path)) {
                    path = "/";
                }
                String down = req.getParameter("down");
                String store = req.getParameter("store");
                String delete = req.getParameter("delete");
                String refresh = req.getParameter("refresh");
                String uploadStr = req.getParameter("upload");
                if (UString.isNotEmpty(down)) {
                    res.setContentType("application/octet-stream");
                    res.setHeader("Content-disposition", "attachment;filename=" + UString.getLastName(down, "/"));
                    ServletOutputStream os = res.getOutputStream();
                    ds.write(down, os);
                    os.flush();
                    os.close();
                } else if (UString.isNotEmpty(store)) {
                    String text = req.getParameter("text");
                    ds.store(store, new ByteArrayInputStream(text.getBytes("UTF-8")));
                } else if (UString.isNotEmpty(delete)) {
                    ds.delete(delete);
                } else if (UString.isNotEmpty(refresh)) {
                    writeJsonObject(res, ds.getChildren(path));
                } else if (UString.isNotEmpty(uploadStr)) {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);

                    List<FileItem> items = upload.parseRequest(req);// 上传文件解析
                    for (FileItem item : items) {
                        if (item.isFormField()) {// 判断是文件还是文本信息
                            System.out.println("表单参数名:" + item.getFieldName() + "，表单参数值:" + item.getString("UTF-8"));
                        } else {
                            if (item.getName() != null && !item.getName().equals("")) {// 判断是否选择了文件
                                System.out.println("上传文件的大小:" + item.getSize());
                                System.out.println("上传文件的类型:" + item.getContentType());
                                // item.getName()返回上传文件在客户端的完整路径名称
                                System.out.println("上传文件的名称:" + item.getName());
                                // 此时文件暂存在服务器的内存当中

                                File tempFile = new File(item.getName());// 构造临时对象
//                                File file = new File(sc.getRealPath("/") + savePath, tempFile.getName());
                                // 获取根目录对应的真实物理路径
//                                item.write(file);// 保存文件在服务器的物理磁盘中
                                ds.store(uploadStr + "/" + item.getName(), item.getInputStream());
                                req.setAttribute("upload.message", "上传文件成功！");// 返回上传结果
                            } else {
                                req.setAttribute("upload.message", "没有选择上传文件！");
                            }
                        }
                    }
                } else {
                    writeJsonObject(res, ds.getChildren(path));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }


        try {

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("upload.message", "上传文件失败！");
        }
    }
}

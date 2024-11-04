package com.zs.webserver;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
public class FileUploadServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "upload_dir"; // 上传目录

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置上传目录
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdir(); // 如果目录不存在，创建目录
        }

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        File uploadedFile = new File(uploadDir, fileName);
                        item.write(uploadedFile); // 将文件写入服务器
                    }
                }
                response.getWriter().println("文件上传成功");
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("文件上传失败: " + e.getMessage());
            } finally {
                response.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有来源，解决CORS问题
            }
        }
    }
}


package com.zs.webserver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@WebServlet("/download")
public class FileDownloadServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "upload_dir"; // 文件存储的目录

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("filename"); // 获取请求参数中的文件名
        File file = new File(UPLOAD_DIRECTORY, fileName); // 创建文件对象

        // 检查文件是否存在
        if (!file.exists() || !file.isFile()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 设置404状态码
            response.getWriter().write("文件未找到");
            return;
        }

        // 设置响应头
        response.setContentType("application/octet-stream"); // 指定响应内容类型
        String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20"); // 对文件名进行编码
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\""); // 提供下载的文件名
        response.setContentLength((int) file.length()); // 设置响应体的长度

        // 写入文件到响应流
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096]; // 创建缓冲区
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead); // 将文件内容写入响应
            }
        }
    }
}

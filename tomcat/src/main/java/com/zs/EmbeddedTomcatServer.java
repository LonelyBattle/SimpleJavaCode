package com.zs;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import static java.nio.file.Paths.*;

public class EmbeddedTomcatServer {

    public static void main(String[] args) throws LifecycleException, ServletException {
        // 创建Tomcat实例
        Tomcat tomcat = new Tomcat();

        // 设置Tomcat的端口号
        tomcat.setPort(8080);

        // 获取临时目录
        String contextPath = "";

        String docBase = System.getProperty("java.io.tmpdir");
        System.out.println("Temporary Directory: " + docBase);

        // 检查并创建临时目录
        File tmpDir = new File(docBase);
        if (!tmpDir.exists() && !tmpDir.mkdirs()) {
            throw new RuntimeException("Failed to create temporary directory: " + docBase);
        }
         //获取resources目录的实际路径
        //URL resourceUrl = EmbeddedTomcatServer.class.getClassLoader().getResource("");
        //if (resourceUrl == null) {
        //    throw new RuntimeException("Cannot find the resources directory.");
        //}
        //String docBase = get(resourceUrl.getPath()).toString();

        // 创建并配置Tomcat的上下文
        Context context = tomcat.addContext(contextPath, docBase);

        // 创建并注册Servlet
        Tomcat.addServlet(context, "helloServlet", new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                resp.setContentType("text/html");
                resp.getWriter().println("<h1>Hello, Embedded Tomcat!</h1>");
            }
        });

        // 将Servlet映射到路径
        context.addServletMappingDecoded("/", "helloServlet");

        // 启动Tomcat服务器
        tomcat.start();

        // 让Tomcat保持运行
        tomcat.getServer().await();
    }
}

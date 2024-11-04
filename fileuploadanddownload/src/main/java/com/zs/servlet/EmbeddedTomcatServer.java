package com.zs.webserver;

import java.io.File;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcatServer {

    public static void main(String[] args) throws LifecycleException, ServletException {
        // 创建Tomcat实例
        Tomcat tomcat = new Tomcat();

        // 设置Tomcat的端口号
        tomcat.setPort(8080);

        // 获取临时目录
        String contextPath = "/demo";

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

        //// 创建并注册Servlet
        //Tomcat.addServlet(context, "helloServlet", new HttpServlet() {
        //    @Override
        //    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //        System.out.println("req.getClass():" + req.getClass());//RequestFacade
        //        System.out.println("resp.getClass():" + resp.getClass());//ResponseFacade
        //        System.out.println("req.getSession():" + req.getSession());
        //        System.out.println("req.getContextPath():" + req.getContextPath());
        //        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("");
        //        //requestDispatcher.forward(req, resp);
        //
        //        //resp.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有来源
        //        //resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        //        //resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        //        //resp.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有来源
        //
        //        resp.setContentType("text/html");
        //        resp.getWriter().println("<h1>Hello, Embedded Tomcat!</h1>");
        //    }
        //});
        //
        //// 将Servlet映射到路径
        //context.addServletMappingDecoded("/hello", "helloServlet");

        Tomcat.addServlet(context, "uploadServlet", new FileUploadServlet());
        context.addServletMappingDecoded("/upload", "uploadServlet");

        Tomcat.addServlet(context, "downloadServlet", new FileDownloadServlet());
        context.addServletMappingDecoded("/download", "downloadServlet");



        // 启动Tomcat服务器
        tomcat.start();

        // 让Tomcat保持运行
        tomcat.getServer().await();
    }
}

package com.zs;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmbeddedJetty {
    public static void main(String[] args) throws Exception {
        // 创建Jetty服务器并指定端口号
        Server server = new Server(8080);

        // 创建ServletContextHandler, 将其绑定到Server
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // 添加一个简单的Servlet
        context.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                resp.getWriter().println("Hello, Embedded Jetty!");
            }
        }), "/hello");

        // 启动服务器
        server.start();
        server.join();
    }
}
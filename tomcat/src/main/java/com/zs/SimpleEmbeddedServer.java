package com.zs.webserver;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleEmbeddedServer {
    // 使用java嵌入式Http服务器开发
    public static void main(String[] args) throws IOException {
        // 创建一个嵌入式 HTTP 服务器，监听端口 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        System.out.println("server.getClass():" + server.getClass());

        // 设置处理器，映射到根路径 "/"
        server.createContext("/", new ServletHandler());

        // 启动服务器
        server.start();
        System.out.println("服务器已启动，访问地址：http://localhost:8080");
    }

    // 模拟 Servlet 请求处理
    static class ServletHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // 获取请求方法
            String requestMethod = exchange.getRequestMethod();
            System.out.println("exchange.getClass():" + exchange.getClass());
            // 设置响应头
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, 0);
            
            // 获取输出流，生成响应
            try (OutputStream os = exchange.getResponseBody()) {
                String response;
                if ("GET".equalsIgnoreCase(requestMethod)) {
                    response = "<html><body><h1>Hello, World!</h1><p>这是一个使用嵌入式服务器的简单Web程序。</p></body></html>";
                } else {
                    response = "<html><body><h1>仅支持GET请求</h1></body></html>";
                }
                os.write(response.getBytes());
            }
        }
    }
}

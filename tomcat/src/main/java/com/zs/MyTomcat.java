package com.zs;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: LonelyBattle
 * @create: 2024-10-27 23:02
 */
public class MyTomcat {
    public static void main(String[] args) throws IOException {
        // 不同浏览器对相应内容严格检测，需要设置相应内容
        ServerSocket serverSocket = new ServerSocket(9999);
        while(!serverSocket.isClosed()) {
            System.out.println("start listening");
            Socket acceptSocket = serverSocket.accept();
            System.out.println("write data");
            OutputStream outputStream = acceptSocket.getOutputStream();
            // 写入 HTTP 响应头
            String response = "<html><head></head><body>hello,this is my tomcat</body></html>";
            outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
            outputStream.write("Content-Type: text/html; charset=UTF-8\r\n".getBytes());
            outputStream.write(("Content-Length: " + response.length() + "\r\n").getBytes());
            outputStream.write("Connection: close\r\n".getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write(response.getBytes());//分隔头部和内容
            outputStream.write(response.getBytes());
            outputStream.close();
            acceptSocket.close();
        }
        serverSocket.close();
    }
}

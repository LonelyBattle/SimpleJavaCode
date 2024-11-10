package com.zs;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class QQMailSender {

    public static void main(String[] args) {
        // QQ邮箱A的账号和授权码
        final String fromEmail = QQMailInfo.fromEmail; // 你的QQ邮箱A
        final String authCode = QQMailInfo.authCode; // QQ邮箱A的授权码

        // 收件人邮箱B
        String toEmail = QQMailInfo.toEmail; // QQ邮箱B

        // 邮件服务器的配置信息
        Properties props = new Properties();
        /**
         * SMTP服务器连接信息
         * 126——smtp.126.com
         * 163——smtp.163.com
         * qq——smtp.qq.com"
         */
        props.put("mail.smtp.host", "smtp.qq.com"); // QQ邮箱SMTP服务器
        props.put("mail.smtp.port", "465"); // QQ邮箱SMTP服务器端口（SSL）
        props.put("mail.smtp.auth", "true"); // 开启认证
        props.put("mail.smtp.ssl.enable", "true"); // 使用SSL连接
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // 获取Session对象
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, authCode);
            }
        });

        try {
            // 创建邮件内容
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail)); // 发件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail)); // 收件人
            message.setSubject("测试邮件"); // 邮件标题
            message.setText("这是一封通过Java程序发送的测试邮件！"); // 邮件内容

            // 发送邮件
            Transport.send(message);
            System.out.println("邮件发送成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

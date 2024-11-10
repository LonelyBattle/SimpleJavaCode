package com.zs;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;

public class QQMailSenderWithAttachment {

    public static void main(String[] args) {
        // QQ邮箱A的账号和授权码
        final String fromEmail = QQMailInfo.fromEmail; // 你的QQ邮箱A
        final String authCode = QQMailInfo.authCode; // QQ邮箱A的授权码

        // 收件人邮箱B
        String toEmail = QQMailInfo.toEmail; // QQ邮箱B

        // 附件的文件路径
        String filePath = "C:\\Users\\iii\\Pictures\\Camera Roll\\x"; // 替换为你的附件路径

        // 邮件服务器的配置信息
        Properties props = new Properties();
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
            // 创建邮件对象
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail)); // 发件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail)); // 收件人
            message.setSubject("测试邮件（含附件）"); // 邮件标题

            // 创建邮件内容
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("这是通过Java程序发送的邮件，包含附件！"); // 邮件文本内容

            // 创建附件部分
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath)); // 添加附件

            // 创建容器类，将文本和附件组合在一起
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart); // 添加文本部分
            multipart.addBodyPart(attachmentPart); // 添加附件部分

            // 将组合内容设置为邮件内容
            message.setContent(multipart);

            // 发送邮件
            Transport.send(message);
            System.out.println("邮件发送成功，包含附件！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

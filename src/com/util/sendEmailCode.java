package com.util;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * 使用SMTP协议发送电子邮件
 */  
public class sendEmailCode {  
    // 邮件发送协议
    private final static String PROTOCOL = "smtp";  
   
    // SMTP邮件服务器  
    private final static String HOST = "smtp.163.com";
   
    // SMTP邮件服务器默认端口  
    private final static String PORT = "25";
   
    // 是否要求身份认证  
    private final static String IS_AUTH = "true";  
   
    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）  
    private final static String IS_ENABLED_DEBUG_MOD = "true";  
   
    // 发件人  
    private static String from = "17797754554@163.com";
   
    // 收件人  
    private static String to = "1163840865@qq.com";
   
    // 初始化连接邮件服务器的会话信息  
    private static Properties props = null;
    static {
        props = new Properties();
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
    }

    /**
     * 发送简单的文本邮件
     */
    public static boolean sendTextEmail(String to,int code,String title) throws Exception {
        try {
            // 创建Session实例对象
            Session session1 = Session.getDefaultInstance(props);
            // 创建MimeMessage实例对象
            MimeMessage message = new MimeMessage(session1);
            // 设置发件人
            message.setFrom(new InternetAddress(from));
            // 设置邮件主题
            message.setSubject(title);
            // 设置收件人
            message.setRecipient(RecipientType.TO, new InternetAddress(to));
            // 设置发送时间
            message.setSentDate(new Date());
            // 设置纯文本内容为邮件正文
            message.setText("您的验证码是："+code+"!验证码有效期是10分钟，过期后请重新获取！");
            // 保存并生成最终的邮件内容
            message.saveChanges();

            // 获得Transport实例对象
            Transport transport = session1.getTransport();
            // 打开连接，邮件服务器
            transport.connect("754825001@qq.com", "lervkwqfzzhibedf");
            // 将message对象传递给transport对象，将邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            // 关闭连接
            transport.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        sendHtmlEmail("754825001@qq.com", 88888,"测试");
    }

    /**
     * 发送简单的html邮件
     */
    public static boolean sendHtmlEmail(String to,int code,String title) throws Exception {
        // 创建Session实例对象
        Session session1 = Session.getInstance(props, new MyAuthenticator());

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session1);
        // 设置邮件主题
        message.setSubject(title);
        // 设置发送人
        message.setFrom(new InternetAddress(from));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置收件人
        message.setRecipients(RecipientType.TO, InternetAddress.parse(to));
        // 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk
        message.setContent("<div style='width: 600px;margin: 0 auto'><h3 style='color:#003E64; text-align:center; '>内燃机注册验证码</h3><p style=''>尊敬的用户您好：</p><p style='text-indent: 2em'>您在注册内燃机账号，此次的验证码是："+code+",有效期10分钟!如果过期请重新获取。</p><p style='text-align: right; color:#003E64; font-size: 20px;'>中国内燃机学会</p></div>","text/html;charset=utf-8");

        //设置自定义发件人昵称
        String nick="";
        try {
            nick=javax.mail.internet.MimeUtility.encodeText(title);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        message.setFrom(new InternetAddress(nick+" <"+from+">"));
        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 发送邮件
        try {
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 向邮件服务器提交认证信息
     */
    static class MyAuthenticator extends Authenticator {

        private String username = "17797754554@163.com";

        private String password = "ruoshui0118";

        public MyAuthenticator() {
            super();
        }

        public MyAuthenticator(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
}
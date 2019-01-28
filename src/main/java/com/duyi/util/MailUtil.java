package com.duyi.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class MailUtil {

    private static Logger logger = Logger.getLogger(MailUtil.class);

    /**
     * 进行base64加密，防止中文乱码
     */
    private static String changeEncode(String str) {
        try {
            str = MimeUtility.encodeText(new String(str.getBytes(), "UTF-8"), "UTF-8", "B"); // "B"代表Base64
        } catch (UnsupportedEncodingException e) {
//            logger.error(e);
        }
        return str;
    }

    public static boolean sendMail(String toUser, String subject, String content) {
        long begin = System.currentTimeMillis();
        MailThread mailThread = new MailThread(toUser, subject, content);
        try {
            mailThread.run();
            System.out.println(System.currentTimeMillis() - begin);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean realSendMail(String toUser, String subject, String content){
        logger.info("开始发送邮件");
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.duyiedu.com");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.socketFactory.port","465");
        properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getInstance(properties);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);

        try {
            // 发件人
            Address address = new InternetAddress("duyioa@duyi-inc.com");
            message.setFrom(address);
            // 收件人
            Address toAddress = new InternetAddress(toUser);
            message.setRecipient(MimeMessage.RecipientType.TO, toAddress); // 设置收件人,并设置其接收类型为TO

            // 主题message.setSubject(changeEncode(emailInfo.getSubject()));
            message.setSubject(subject);
            // 时间
            message.setSentDate(new Date());

            Multipart multipart = new MimeMultipart();

            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(content, "text/html; charset=utf-8");
            multipart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            message.setContent(multipart);
            message.saveChanges();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        }

        try {
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.duyiedu.com", "duyioa@duyi-inc.com", "Dy123456");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (Exception e) {
            logger.error(e);
//            e.printStackTrace();
            return false;
        }

        return true;
    }



    public static class MailThread implements Runnable{
        private String toUser;
        private String subject;
        private String content;

        public MailThread(String toUser, String subject, String content) {
            this.toUser = toUser;
            this.subject = subject;
            this.content = content;
        }

        @Override
        public void run() {
            realSendMail(this.toUser, this.subject, this.content);
        }
    }

    public static void main(String[] args) {
        MailUtil.sendMail("982294498@qq.com", "这是主体", "还有中文");
    }

}

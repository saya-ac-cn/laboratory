package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * @Title: MailService
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-01 21:33
 * @Description:
 */
@Service("mailService")
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * @param to 发给谁
     * @param subject 主题
     * @param content 正文
     * @return Boolean
     */
    public Boolean sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(from, "Saya.ac.cn-实验室中心"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            CurrentLineInfo.printCurrentLineInfo("发送邮件时发生异常",e, MailService.class);
        } catch (UnsupportedEncodingException e) {
            CurrentLineInfo.printCurrentLineInfo("发送邮件时，发送者地址异常",e, MailService.class);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("发送邮件时发生异常",e, MailService.class);
        }
        return false;
    }

}

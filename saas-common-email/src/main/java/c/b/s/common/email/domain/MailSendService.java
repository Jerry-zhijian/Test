package c.b.s.common.email.domain;

import c.b.s.common.fileupload.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Objects;

/**
 * Created by guiqingqing on 2018/3/23.
 */
@Service
public class MailSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSendService.class);

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private FileService fileService;

    public void sendResetPasswordEmail(String templatePath, String toMail, String ... params) throws Exception {

        String template = fileService.downloadEmailTemplate(templatePath);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setFrom("support@zhiniutech.com.cn");
        messageHelper.setTo(toMail);
        messageHelper.setSubject("智牛平台密码找回");
        messageHelper.setText(String.format(template, params), true);
        mailSender.send(message);
    }

    public void sendCreateTaskMessage(String templatePath, String toMail, String subject,String ... params) throws Exception {

        String template = fileService.downloadEmailTemplate(templatePath);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setFrom("support@zhiniutech.com.cn");
        messageHelper.setTo(toMail);
        messageHelper.setSubject(subject);
        messageHelper.setText(String.format(template, params), true);
        mailSender.send(message);
    }

    public void sendMail(String toMail,String subject,String body) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        messageHelper.setFrom("support@zhiniutech.com.cn");
        messageHelper.setTo(toMail);
        messageHelper.setSubject(subject);
        messageHelper.setText(body, true);
        mailSender.send(message);
    }

    public void sendMail(String templatePath, String toMail, String[] cc, String subject, String ... params) throws Exception {
        String template = fileService.downloadEmailTemplate(templatePath);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setFrom("support@zhiniutech.com.cn");
        messageHelper.setTo(toMail);
        if (Objects.nonNull(cc) && cc.length > 0) {
            messageHelper.setCc(cc);
        }
        messageHelper.setSubject(subject);
        messageHelper.setText(String.format(template, params), true);
        mailSender.send(message);
    }
}
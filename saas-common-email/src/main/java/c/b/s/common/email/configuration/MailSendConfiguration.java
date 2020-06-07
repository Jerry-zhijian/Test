package c.b.s.common.email.configuration;

import c.b.s.common.email.domain.MailSendService;
import c.b.s.common.util.ConfigUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@DependsOn("springContextUtil")
public class MailSendConfiguration {

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        String host = ConfigUtil.getValue("email.host", "smtp.partner.outlook.cn");
        int port = ConfigUtil.getIntValue("email.port", 587);
        String userName = ConfigUtil.getValue("email.userName", "support@zhiniutech.com.cn");
        String password = ConfigUtil.getValue("email.password", "qwerty@123456");

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(props);
        mailSender.setUsername(userName);
        mailSender.setPassword(password);
        return mailSender;
    }

    @Bean
    public MailSendService mailSendService() {
        return new MailSendService();
    }
}
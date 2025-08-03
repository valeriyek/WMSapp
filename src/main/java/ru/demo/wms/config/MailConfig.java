package ru.demo.wms.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("your.smtp.host"); // Указать адрес SMTP-сервера
        mailSender.setPort(587); // Указать порт SMTP-сервера
        mailSender.setUsername("your.username"); // Указать имя пользователя SMTP
        mailSender.setPassword("your.password"); // Указать пароль SMTP

        // Настроить свойства JavaMailSender
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp"); // Указать протокол передачи — SMTP
        props.put("mail.smtp.auth", "true"); // Включить аутентификацию
        props.put("mail.smtp.starttls.enable", "true"); // Включить поддержку STARTTLS
        props.put("mail.debug", "true"); // Включить режим отладки для логирования

        return mailSender;
    }
}

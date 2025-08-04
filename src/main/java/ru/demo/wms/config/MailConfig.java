package ru.demo.wms.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Конфигурация почтового клиента для отправки email-сообщений через SMTP.
 */
@Configuration
public class MailConfig {

    /**
     * Создаёт и настраивает бин {@link JavaMailSender} для работы с почтой.
     * <p>
     * Настройки включают хост, порт, учетные данные и свойства SMTP-протокола.
     *
     * @return настроенный {@link JavaMailSender}
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("your.smtp.host"); // Указать адрес SMTP-сервера
        mailSender.setPort(587); // Указать порт SMTP-сервера
        mailSender.setUsername("your.username"); // Указать имя пользователя SMTP
        mailSender.setPassword("your.password"); // Указать пароль SMTP

        // Настроить свойства JavaMailSender
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}

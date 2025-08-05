package ru.demo.wms.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Утилитный класс для отправки электронной почты с помощью JavaMailSender.
 * Поддерживает отправку писем с вложениями и без них, включая HTML-сообщения, CC и BCC.
 */
@Component
public class MailUtil {

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Отправляет письмо с возможностью указания CC, BCC, темы, текста и вложения.
	 *
	 * @param to     получатель
	 * @param cc     список адресов в копии (может быть null)
	 * @param bcc    список скрытых адресов (может быть null)
	 * @param subject тема письма
	 * @param text   текст письма (поддерживается HTML)
	 * @param file   вложение (может быть null)
	 * @return true, если письмо отправлено успешно; false — при ошибке
	 */
	public boolean sendEmail(
			String to,
			String[] cc,
			String[] bcc,
			String subject,
			String text,
			MultipartFile file
	) {
		boolean flag = false;
		try {
			// Создание MIME-сообщения
			MimeMessage message = mailSender.createMimeMessage();

			// Настройка помощника с возможностью вложений
			MimeMessageHelper helper = new MimeMessageHelper(message, file != null);

			helper.setTo(to);

			if (cc != null && cc.length > 0) {
				helper.setCc(cc);
			}

			if (bcc != null && bcc.length > 0) {
				helper.setBcc(bcc);
			}

			helper.setSubject(subject);
			helper.setText(text, true); // true — включение HTML

			if (file != null) {
				helper.addAttachment(file.getOriginalFilename(), file);
			}

			// Отправка письма
			mailSender.send(message);
			flag = true;

		} catch (Exception e) {
			e.printStackTrace(); // Лучше заменить на логгер в реальных приложениях
		}

		return flag;
	}

	/**
	 * Упрощённая версия метода отправки письма без вложения, CC и BCC.
	 *
	 * @param to      получатель
	 * @param subject тема письма
	 * @param text    текст письма (поддерживается HTML)
	 * @return true, если письмо отправлено успешно; false — при ошибке
	 */
	public boolean sendEmail(String to, String subject, String text) {
		return sendEmail(to, null, null, subject, text, null);
	}
}

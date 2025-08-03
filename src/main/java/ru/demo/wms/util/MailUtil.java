package ru.demo.wms.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MailUtil {

	@Autowired
	private JavaMailSender mailSender;

	/***
	 * This method is used to send email with below details 
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param text
	 * @param file
	 * @return
	 */
	public boolean sendEmail(
			String to,
			String[] cc,
			String[] bcc,
			String subject,
			String text,
			MultipartFile file
			) 
	{
		boolean flag = false;
		try {
			//1. create one empty email (mime message)
			MimeMessage message = mailSender.createMimeMessage();

			//2. fill details
			MimeMessageHelper helper = new MimeMessageHelper(message, file!=null);

			helper.setTo(to);
			if(cc!=null && cc.length>0)
				helper.setCc(cc);
			if(bcc!=null && bcc.length>0)
				helper.setBcc(bcc);

			helper.setSubject(subject);
			helper.setText(text,true);


			if(file!=null)
				helper.addAttachment(file.getOriginalFilename(), file);


			mailSender.send(message);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	/***
	 * This method is used to send email with below details 
	 * @param to
	 * @param subject
	 * @param text
	 * @return
	 */
	public boolean sendEmail(
			String to,
			String subject,
			String text) 
	{
		return sendEmail(to, null, null, subject, text, null);
	}
	
}

/*
Класс MailUtil предоставляет утилиту для отправки электронной почты в Spring-приложении, используя JavaMailSender. Этот класс предлагает два основных метода: один для отправки электронной почты с возможностью добавления вложения (MultipartFile) и другой для отправки простого текстового сообщения без вложения.

Методы MailUtil:
sendEmail с вложением:
Этот метод предназначен для отправки электронного сообщения с дополнительными параметрами, такими как адреса CC (копия) и BCC (скрытая копия), тема письма, текст и возможность прикрепить файл как вложение. Этот вариант подходит для более сложных сообщений с дополнительными требованиями к отправке.

sendEmail без вложения:
Упрощенный метод, который обеспечивает отправку электронного сообщения только с основной информацией: адресат, тема и текст письма. Этот метод используется для простых уведомлений или информационных сообщений, где не требуется добавление вложений.

Ключевые моменты реализации:
Использование MimeMessage:
Для создания электронного письма используется объект MimeMessage, который позволяет настроить различные аспекты сообщения, включая вложения.

MimeMessageHelper для настройки сообщения:
Чтобы упростить процесс настройки MimeMessage, используется MimeMessageHelper. Этот помощник позволяет легко задать получателей, тему, текст сообщения и добавить вложения.

Поддержка HTML в содержимом сообщения:
Параметр true в методе helper.setText(text,true); указывает, что текст сообщения интерпретируется как HTML, что позволяет форматировать содержимое письма.

Добавление вложения:
Если в метод передается не null в параметре file, MimeMessageHelper добавляет файл в сообщение как вложение с использованием оригинального имени файла.

Рекомендации по использованию:
Безопасность и конфиденциальность:
Необходимо соблюдать осторожность при отправке конфиденциальной информации, особенно при указании адресов CC и BCC.

Управление исключениями:
В текущей реализации все исключения перехватываются и выводятся в консоль. В продакшен-среде рекомендуется предусмотреть более продуманную обработку исключений, например, логирование или оповещение администратора.

Конфигурация JavaMailSender:
Для работы MailUtil необходимо правильно настроить JavaMailSender в контексте Spring-приложения, включая параметры сервера SMTP.

Этот утилитный класс является полезным инструментом для реализации функциональности по отправке электронной почты в Spring-приложениях, обеспечивая гибкость и масштабируемость для различных потребностей в коммуникациях.
*/
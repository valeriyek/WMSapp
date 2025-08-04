package ru.demo.wms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Конфигурационный класс приложения.
 * <p>
 * Определяет бин {@link BCryptPasswordEncoder}, используемый для хэширования паролей.
 */
@Configuration
public class AppConfig {

	/**
	 * Создаёт и регистрирует бин {@link BCryptPasswordEncoder}.
	 * <p>
	 * Используется для безопасного шифрования паролей с помощью алгоритма BCrypt.
	 *
	 * @return экземпляр {@link BCryptPasswordEncoder}
	 */
	@Bean
	public BCryptPasswordEncoder pwdEnc() {
		return new BCryptPasswordEncoder();
	}

}

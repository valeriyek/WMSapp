package ru.demo.wms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Конфигурация безопасности приложения с использованием Spring Security.
 * <p>
 * Определяет правила авторизации, формы входа и выхода, а также обработку ошибок.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Настраивает источник аутентификации — сервис загрузки пользователей и кодировщик паролей.
	 *
	 * @param auth билдер менеджера аутентификации
	 * @throws Exception в случае ошибки конфигурации
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	/**
	 * Определяет правила авторизации, конфигурацию форм логина и выхода,
	 * а также страницы по умолчанию и при ошибках.
	 *
	 * @param http объект конфигурации HTTP безопасности
	 * @throws Exception в случае ошибки конфигурации
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// Публичные маршруты (доступны всем без авторизации)
				.antMatchers("/rest/api/**", "/user/login", "/user/showForgot", "/user/reGenNewPwd").permitAll()
				.antMatchers("/user/showUserActiveOtp", "/user/doUserActiveOtp").permitAll()

				// Доступны пользователям с ролями ADMIN или APPUSER
				.antMatchers("/uom/**", "/st/**", "/om/**", "/part/**", "/wh/**").hasAnyAuthority("ADMIN", "APPUSER")
				// Доступны только пользователям с ролью APPUSER
				.antMatchers("/po/**", "/grn/**", "/sale/**", "/shiping/**").hasAuthority("APPUSER")
				// Регистрация новых пользователей доступна только ADMIN
				.antMatchers("/user/register", "/user/create").hasAuthority("ADMIN")

				// Все остальные запросы требуют аутентификации
				.anyRequest().authenticated()

				// Конфигурация формы логина
				.and()
				.formLogin()
				.loginPage("/user/login") // Страница входа (GET)
				.loginProcessingUrl("/login") // URL для обработки логина (POST)
				.defaultSuccessUrl("/user/setup", true) // Страница после успешного входа
				.failureUrl("/user/login?error") // Страница при ошибке входа

				// Конфигурация выхода из системы
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL выхода
				.logoutSuccessUrl("/user/login?logout") // Перенаправление после выхода

				// Обработка ошибок доступа
				.and()
				.exceptionHandling()
				.accessDeniedPage("/user/denied"); // Страница при отказе в доступе
	}

}

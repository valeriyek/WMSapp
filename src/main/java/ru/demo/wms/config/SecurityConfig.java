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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Настроить авторизацию
		http.authorizeRequests()
				.antMatchers("/rest/api/**", "/user/login", "/user/showForgot", "/user/reGenNewPwd").permitAll()
				.antMatchers("/user/showUserActiveOtp", "/user/doUserActiveOtp").permitAll()
				.antMatchers("/uom/**", "/st/**", "/om/**", "/part/**", "/wh/**").hasAnyAuthority("ADMIN", "APPUSER")
				.antMatchers("/po/**", "/grn/**", "/sale/**", "/shiping/**").hasAuthority("APPUSER")
				.antMatchers("/user/register", "/user/create").hasAuthority("ADMIN")
				.anyRequest().authenticated()

				// Настроить параметры входа
				.and()
				.formLogin()
				.loginPage("/user/login") // Задать страницу входа (GET)
				.loginProcessingUrl("/login") // Задать URL для обработки логина (POST)
				.defaultSuccessUrl("/user/setup", true) // Задать страницу после успешного входа
				.failureUrl("/user/login?error") // Задать URL при ошибке входа

				// Настроить параметры выхода
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Задать URL выхода
				.logoutSuccessUrl("/user/login?logout") // Задать страницу после выхода

				// Настроить обработку ошибок
				.and()
				.exceptionHandling()
				.accessDeniedPage("/user/denied"); // Задать страницу при отказе в доступе
	}
}

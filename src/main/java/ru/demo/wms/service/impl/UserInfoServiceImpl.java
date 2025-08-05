package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.consts.UserMode;
import ru.demo.wms.model.UserInfo;
import ru.demo.wms.repo.UserInfoRepository;
import ru.demo.wms.service.IUserInfoService;

/**
 * Сервис, реализующий бизнес-логику по работе с пользователями.
 * Также реализует интерфейс Spring Security {@link UserDetailsService}
 * для обеспечения авторизации и аутентификации.
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService, UserDetailsService {

	@Autowired
	private UserInfoRepository repo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Сохраняет нового пользователя с хэшированием пароля.
	 *
	 * @param ui объект пользователя
	 * @return ID сохраненного пользователя
	 */
	@Override
	public Integer saveUserInfo(UserInfo ui) {
		String encPwd = passwordEncoder.encode(ui.getPassword());
		ui.setPassword(encPwd);
		return repo.save(ui).getId();
	}

	/**
	 * Возвращает список всех пользователей.
	 *
	 * @return список {@link UserInfo}
	 */
	@Override
	public List<UserInfo> getAllUserInfos() {
		return repo.findAll();
	}

	/**
	 * Загружает пользователя по email для Spring Security.
	 * Если пользователь не найден или отключён, выбрасывается исключение.
	 *
	 * @param username email пользователя
	 * @return объект {@link UserDetails}
	 * @throws UsernameNotFoundException если пользователь не найден или отключён
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return repo.findByEmail(username)
				.filter(user -> user.getMode().equals(UserMode.ENABLED))
				.map(user -> new User(
						user.getEmail(),
						user.getPassword(),
						user.getRoles().stream()
								.map(role -> new SimpleGrantedAuthority(role.getRole().name()))
								.collect(Collectors.toSet())
				))
				.orElseThrow(() -> new UsernameNotFoundException("User not exist"));
	}

	/**
	 * Возвращает пользователя по email, если существует.
	 *
	 * @param email email
	 * @return {@link Optional} с пользователем
	 */
	@Override
	public Optional<UserInfo> getOneUserInfoByEmail(String email) {
		return repo.findByEmail(email);
	}

	/**
	 * Обновляет статус пользователя (ENABLED / DISABLED).
	 *
	 * @param id   идентификатор пользователя
	 * @param mode новый статус
	 */
	@Override
	@Transactional
	public void updateUserStatus(Integer id, UserMode mode) {
		repo.updateUserStatus(id, mode);
	}

	/**
	 * Обновляет пароль пользователя.
	 * Пароль не шифруется — предполагается, что шифрование выполнено заранее.
	 *
	 * @param email    email пользователя
	 * @param password новый пароль
	 */
	@Override
	@Transactional
	public void updateUserPassword(String email, String password) {
		repo.updateUserPassword(email, password);
	}

	/**
	 * Проверяет, существует ли пользователь с указанным email.
	 *
	 * @param email email
	 * @return true, если пользователь существует
	 */
	@Override
	public boolean isUserEmail(String email) {
		return repo.existsByEmail(email);
	}
}

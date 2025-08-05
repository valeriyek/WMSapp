package ru.demo.wms.runner;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ru.demo.wms.consts.UserMode;
import ru.demo.wms.model.UserInfo;
import ru.demo.wms.repo.RoleRepository;
import ru.demo.wms.repo.UserInfoRepository;
import ru.demo.wms.service.IUserInfoService;

/**
 * Компонент инициализации пользовательских данных при запуске приложения.
 * <p>
 * Этот раннер предназначен для автоматического создания мастер-пользователя
 * с заданным email и набором ролей, если такой пользователь ещё не существует.
 * Используется для первичной настройки приложения.
 * </p>
 *
 * <p>Запускается после {@link RoleRepositoryRunner} благодаря {@code @Order(20)}.</p>
 */
@Component
@Order(20)
public class UserMasterDataRunner implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(UserMasterDataRunner.class);

	@Autowired
	private IUserInfoService service;

	@Autowired
	private UserInfoRepository repo;

	@Autowired
	private RoleRepository roleRepo;

	/**
	 * Создаёт мастер-пользователя при первом запуске приложения, если он отсутствует в БД.
	 *
	 * @param args аргументы командной строки (не используются)
	 */
	@Override
	public void run(String... args) {
		String email = "example@gmail.com";

		LOG.info("▶ Проверка наличия мастер-пользователя с email: {}", email);
		if (!repo.existsByEmail(email)) {
			UserInfo user = new UserInfo();
			user.setName("newuser");
			user.setEmail(email);
			user.setPassword("example");
			user.setMode(UserMode.ENABLED);
			user.setRoles(
					roleRepo.findAll()
							.stream()
							.collect(Collectors.toSet())
			);

			service.saveUserInfo(user);
			LOG.info("Мастер-пользователь с email '{}' успешно создан и сохранён.", email);
		} else {
			LOG.info("Мастер-пользователь с email '{}' уже существует. Создание не требуется.", email);
		}
	}
}

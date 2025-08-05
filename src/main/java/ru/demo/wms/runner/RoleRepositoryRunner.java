package ru.demo.wms.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ru.demo.wms.consts.RoleType;
import ru.demo.wms.model.Role;
import ru.demo.wms.repo.RoleRepository;

/**
 * Компонент инициализации данных ролей при запуске приложения.
 * <p>
 * Реализует интерфейс {@link CommandLineRunner}, что позволяет выполнять
 * кастомную логику сразу после инициализации Spring Boot-приложения.
 * </p>
 *
 * <p>
 * Класс предназначен для автоматического заполнения таблицы ролей
 * начальными значениями из перечисления {@link RoleType}, если они отсутствуют.
 * </p>
 *
 * <p>
 * Запускается с порядком {@code @Order(10)}, что позволяет задать приоритет
 * выполнения среди других раннеров.
 * </p>
 */
@Component
@Order(10)
public class RoleRepositoryRunner implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(RoleRepositoryRunner.class);

	@Autowired
	private RoleRepository repo;

	/**
	 * Выполняет проверку наличия ролей в БД и при необходимости вставляет недостающие.
	 *
	 * @param args аргументы командной строки (не используются)
	 */
	@Override
	public void run(String... args) {
		LOG.info("▶ Инициализация ролей в таблице ROLE начата...");
		RoleType[] roleTypes = RoleType.values();

		for (RoleType rt : roleTypes) {
			if (!repo.existsByRole(rt)) {
				Role role = new Role();
				role.setRole(rt);
				repo.save(role);
				LOG.info("Роль '{}' добавлена в таблицу ROLE.", rt.name());
			} else {
				LOG.info("Роль '{}' уже существует в таблице ROLE.", rt.name());
			}
		}
		LOG.info("✔ Инициализация ролей завершена.");
	}
}

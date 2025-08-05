package ru.demo.wms.util;

import java.util.Set;
import java.util.stream.Collectors;

import ru.demo.wms.model.Role;

/**
 * Утилитный класс для работы с ролями пользователя.
 */
public final class UserInfoUtil {

	private UserInfoUtil() {
		// Приватный конструктор — запрещаем создание экземпляров
	}

	/**
	 * Преобразует множество ролей в множество их строковых представлений.
	 *
	 * @param roles Set<Role> — роли пользователя
	 * @return Set<String> — названия ролей
	 */
	public static Set<String> getRolesAsString(Set<Role> roles) {
		return roles.stream()
				.map(role -> role.getRole().name())
				.collect(Collectors.toSet());
	}
}

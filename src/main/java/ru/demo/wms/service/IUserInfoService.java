package ru.demo.wms.service;

import java.util.List;
import java.util.Optional;

import ru.demo.wms.consts.UserMode;
import ru.demo.wms.model.UserInfo;

/**
 * Сервисный интерфейс для управления пользователями (UserInfo) в системе.
 * Предоставляет методы для CRUD-операций, проверки email, управления статусом и смены пароля.
 */
public interface IUserInfoService {

	/**
	 * Сохраняет нового пользователя или обновляет существующего.
	 *
	 * @param ui объект пользователя
	 * @return ID сохранённого пользователя
	 */
	Integer saveUserInfo(UserInfo ui);

	/**
	 * Возвращает список всех пользователей в системе.
	 *
	 * @return список UserInfo
	 */
	List<UserInfo> getAllUserInfos();

	/**
	 * Возвращает информацию о пользователе по его email.
	 *
	 * @param email адрес электронной почты
	 * @return Optional с UserInfo, если найден
	 */
	Optional<UserInfo> getOneUserInfoByEmail(String email);

	/**
	 * Обновляет статус пользователя (например, ACTIVE / INACTIVE).
	 *
	 * @param id ID пользователя
	 * @param mode новый режим пользователя
	 */
	void updateUserStatus(Integer id, UserMode mode);

	/**
	 * Обновляет пароль пользователя по email.
	 * Предполагается, что пароль будет предварительно зашифрован.
	 *
	 * @param email email пользователя
	 * @param password новый пароль
	 */
	void updateUserPassword(String email, String password);

	/**
	 * Проверяет, существует ли пользователь с указанным email.
	 *
	 * @param email адрес электронной почты
	 * @return true, если пользователь существует
	 */
	boolean isUserEmail(String email);
}

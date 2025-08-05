package ru.demo.wms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.consts.UserMode;
import ru.demo.wms.model.UserInfo;

/**
 * Репозиторий Spring Data JPA для сущности {@link UserInfo}.
 * Предоставляет базовые и специализированные методы управления пользователями.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	/**
	 * Проверяет, существует ли пользователь с указанным email.
	 *
	 * @param email адрес электронной почты
	 * @return true, если пользователь существует, иначе false
	 */
	Boolean existsByEmail(String email);

	/**
	 * Ищет пользователя по email.
	 *
	 * @param email адрес электронной почты
	 * @return Optional с найденным {@link UserInfo}, если существует
	 */
	Optional<UserInfo> findByEmail(String email);

	/**
	 * Обновляет статус (mode) пользователя по его идентификатору.
	 *
	 * @param id   идентификатор пользователя
	 * @param mode новый статус пользователя
	 */
	@Modifying
	@Query("UPDATE UserInfo SET mode=:mode WHERE id=:id")
	void updateUserStatus(Integer id, UserMode mode);

	/**
	 * Обновляет пароль пользователя по его email.
	 *
	 * @param email    адрес электронной почты
	 * @param password новый пароль (в зашифрованном виде)
	 */
	@Modifying
	@Query("UPDATE UserInfo SET password=:password WHERE email=:email")
	void updateUserPassword(String email, String password);
}

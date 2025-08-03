package ru.demo.wms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.consts.UserMode;
import ru.demo.wms.model.UserInfo;

public interface UserInfoRepository 
	extends JpaRepository<UserInfo, Integer> {

	Boolean existsByEmail(String email);
	Optional<UserInfo> findByEmail(String email);
	
	@Modifying
	@Query("UPDATE UserInfo SET mode=:mode WHERE id=:id")
	void updateUserStatus(Integer id,UserMode mode);
	
	@Modifying
	@Query("UPDATE UserInfo SET password=:password WHERE email=:email")
	void updateUserPassword(String email,String password);

}
/*

Этот интерфейс UserInfoRepository служит репозиторием Spring Data JPA для сущности UserInfo, предоставляя инструментарий для управления информацией о пользователях в системе. Он наследует JpaRepository, обеспечивая стандартный набор операций CRUD для объектов UserInfo, а также включает специализированные методы для выполнения конкретных задач, связанных с управлением пользователями.

Специализированные методы:
existsByEmail: Проверяет существование пользователя с заданным email. Этот метод полезен для валидации уникальности электронной почты при регистрации нового пользователя или обновлении информации о существующем пользователе.

findByEmail: Возвращает объект Optional<UserInfo> для пользователя с заданным email. Использование Optional помогает безопасно обрабатывать случаи, когда пользователь с указанным email не найден.

updateUserStatus: Обновляет статус пользователя (mode) по его идентификатору (id). Метод использует перечисление UserMode для обозначения нового статуса, что обеспечивает типобезопасность и упрощает понимание кода.

updateUserPassword: Обновляет пароль пользователя по его email. Этот метод может быть использован для функциональности восстановления пароля или изменения пароля пользователем.

Основные характеристики и преимущества:
Автоматическая реализация: Благодаря наследованию от JpaRepository, Spring Data JPA автоматически предоставляет реализацию для большинства методов, что значительно сокращает количество ручной работы и упрощает разработку.

Интеграция с Spring Framework: Репозитории Spring Data JPA легко интегрируются с другими компонентами Spring, обеспечивая единообразную и мощную платформу для разработки приложений.

Гибкость в определении запросов: Способность определять пользовательские запросы с помощью аннотации @Query и выполнять модифицирующие операции с @Modifying позволяет реализовывать сложные бизнес-логики непосредственно в репозитории.

Типобезопасность: Использование типов и перечислений, например UserMode, улучшает читаемость кода, обеспечивает его корректность и упрощает поддержку.

UserInfoRepository играет ключевую роль в управлении данными пользователя, предоставляя разработчикам удобные и эффективные инструменты для реализации функций аутентификации, авторизации и управления профилями пользователей.
*/
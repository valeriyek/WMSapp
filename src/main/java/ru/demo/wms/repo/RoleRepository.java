package ru.demo.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.demo.wms.consts.RoleType;
import ru.demo.wms.model.Role;

/**
 * Репозиторий для работы с ролями пользователей ({@link Role}).
 * Предоставляет стандартные операции CRUD и метод проверки существования роли.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

	/**
	 * Проверить, существует ли роль с указанным типом.
	 *
	 * @param role тип роли (enum {@link RoleType})
	 * @return true, если роль существует, иначе false
	 */
	Boolean existsByRole(RoleType role);
}

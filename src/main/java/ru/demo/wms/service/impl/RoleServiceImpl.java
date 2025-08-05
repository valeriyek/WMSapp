package ru.demo.wms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.model.Role;
import ru.demo.wms.repo.RoleRepository;
import ru.demo.wms.service.IRoleService;

/**
 * Сервис для работы с ролями пользователей.
 * Реализует бизнес-логику, связанную с получением ролей и их преобразованием.
 */
@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleRepository repo;

	/**
	 * Получает все роли из базы данных и возвращает их в виде отображения:
	 * ключ — ID роли, значение — имя роли (enum.name()).
	 *
	 * @return карта ID → Название роли
	 */
	@Override
	public Map<Integer, String> getRolesMap() {
		List<Role> roleList = repo.findAll();
		Map<Integer, String> map = new HashMap<>();
		for (Role role : roleList) {
			map.put(role.getId(), role.getRole().name());
		}
		return map;
	}
}

package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.model.Role;
import ru.demo.wms.repo.RoleRepository;
import ru.demo.wms.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleRepository repo;

	public Map<Integer, String> getRolesMap() {
		List<Role> roleList = repo.findAll();
		Map<Integer,String> map = new HashedMap<>();
		for(Role role:roleList) {
			map.put(role.getId(), role.getRole().name());
		}
		return map;
	}

}
/*
Класс RoleServiceImpl реализует интерфейс IRoleService, обеспечивая функциональность для работы с ролями (Role) в приложении. Этот сервис взаимодействует с репозиторием RoleRepository для выполнения операций над ролями и предоставляет дополнительную логику преобразования списка ролей в карту для удобства использования в других частях приложения.

Реализация
Внедрение зависимости RoleRepository: Используется Spring @Autowired для внедрения зависимости, что позволяет сервису взаимодействовать с базой данных ролей.

Метод getRolesMap: Извлекает все роли из базы данных с помощью repo.findAll() и преобразует их в Map<Integer, String>, где ключом является идентификатор роли, а значением - название роли. Это может быть особенно полезно для отображения списка ролей в пользовательских интерфейсах, где необходимо представить роли в форме выбора или фильтра.

Возможные улучшения
Использование Java 8 Stream API: Преобразование списка в карту может быть выполнено более элегантно и эффективно с использованием Stream API и методов коллекций Java 8. Это улучшит читаемость и сократит количество кода.

java
Copy code
public Map<Integer, String> getRolesMap() {
    return repo.findAll().stream()
            .collect(Collectors.toMap(Role::getId, role -> role.getRole().name()));
}
Исправление типа карты: В приведенном примере используется HashedMap из Apache Commons Collections. В стандартной библиотеке Java уже есть HashMap, который является частью Java Collections Framework. Если нет специальных требований, предпочтительнее использовать стандартные классы Java, чтобы сократить зависимости и упростить код.

java
Copy code
Map<Integer, String> map = new HashMap<>();
Обработка исключений: Если в будущем методы репозитория будут включать операции, которые могут генерировать исключения (например, при доступе к базе данных), рассмотрите возможность добавления обработки исключений в сервисе для гарантирования корректной обработки ошибок и предоставления информативных сообщений для других компонентов приложения.

Логирование: Добавление логирования к операциям сервиса может помочь в отладке и обеспечить аудит действий приложения. Это особенно важно для операций, которые изменяют состояние данных или имеют значительное влияние на работу приложения.

RoleServiceImpl играет важную роль в управлении доступом к функциям приложения на основе ролей пользователей. Применение предложенных улучшений может сделать сервис более надежным, гибким и легко поддерживаемым.
*/
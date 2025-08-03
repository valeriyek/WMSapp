package ru.demo.wms.runner;
/*

Класс RoleRepositoryRunner в Spring Boot предназначен для выполнения инициализирующих действий с базой данных при запуске приложения. Он реализует интерфейс CommandLineRunner, который позволяет включить код, выполняемый сразу после полной инициализации контекста приложения. В этом случае, он используется для заполнения таблицы ролей (Role) начальными данными.

Ключевые аспекты реализации:
Аннотация @Component: Делает класс RoleRepositoryRunner управляемым компонентом Spring, позволяя фреймворку обнаруживать его и вызывать метод run автоматически при запуске.

Аннотация @Order(10): Определяет порядок, в котором должны выполняться различные реализации CommandLineRunner и ApplicationRunner. Число в аннотации указывает на приоритет выполнения, где более низкое число означает более высокий приоритет. В данном случае, @Order(10) указывает, что этот раннер должен быть запущен с приоритетом 10.

Внедрение зависимости RoleRepository: Используется для взаимодействия с таблицей ролей в базе данных. @Autowired автоматически внедряет инстанс RoleRepository в RoleRepositoryRunner, делая возможным сохранение новых ролей в базу данных.

Метод run: Является точкой входа для выполнения кода при старте. В этом методе происходит итерация по всем значениям перечисления RoleType, и для каждого значения проверяется его наличие в базе данных. Если роль отсутствует, она создается и сохраняется с использованием RoleRepository.

Логирование: Использование логгера (Logger) для вывода информационных сообщений о выполнении операций, что помогает в отладке и мониторинге процесса инициализации данных.

Пример использования:
Этот подход часто применяется в приложениях для выполнения предварительной настройки или инициализации базы данных начальными значениями, такими как роли пользователей, типы товаров или любые другие справочные данные, необходимые для работы приложения. Это особенно удобно для разработки и тестирования, когда требуется автоматически подготовить окружение приложения к работе.
*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ru.demo.wms.consts.RoleType;
import ru.demo.wms.model.Role;
import ru.demo.wms.repo.RoleRepository;


@Component
@Order(10)
public class RoleRepositoryRunner implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(RoleRepositoryRunner.class);
	
	@Autowired
	private RoleRepository repo;
	
	public void run(String... args) throws Exception {
		
		RoleType[] roleType = RoleType.values();
		
		for (RoleType rt : roleType) {
			if(!repo.existsByRole(rt)) {
				Role role = new Role();
				role.setRole(rt);
				repo.save(role);
				LOG.info(rt.name() + " (ROLE DATA) IS INSERTED INTO ROLE TABLE");
			}
		}
		
	}

}

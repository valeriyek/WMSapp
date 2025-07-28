package ru.demo.wms.runner;
/*

Класс UserMasterDataRunner предназначен для автоматической инициализации базы данных пользовательскими данными при
запуске Spring Boot приложения. Использование этого класса гарантирует, что в базе данных будет существовать
хотя бы один пользователь с определенным набором ролей, что может быть особенно полезно для начальной настройки системы,
тестирования или демонстрационных целей.

Основные характеристики и функциональность:
Аннотация @Component: Указывает Spring на то, что класс является
компонентом и должен быть автоматически обнаружен во время сканирования класспаса.

Аннотация @Order(20): Определяет порядок выполнения для классов,
реализующих интерфейс CommandLineRunner или ApplicationRunner. Значение 20 указывает,
что этот раннер должен выполняться после тех, у которых порядок выполнения ниже (например, после раннеров с @Order(10)).

Автовнедрение зависимостей: Используются аннотации @Autowired для внедрения сервиса
IUserInfoService, репозитория UserInfoRepository, и репозитория RoleRepository.
Это позволяет использовать методы этих компонентов для создания и сохранения пользовательских данных в базе данных.

Проверка существования пользователя: Прежде чем создать и сохранить
нового пользователя, проверяется, существует ли уже пользователь с определенным email.
Это предотвращает создание дубликатов пользователей при каждом запуске приложения.

Создание и сохранение пользователя: Если пользователь с указанным email не существует,
создается новый объект UserInfo с заданными параметрами, включая набор ролей, извлекаемых
из репозитория RoleRepository. После этого пользователь сохраняется в базе данных с помощью сервиса IUserInfoService.

Применение:
Этот класс может быть использован для создания "мастер" аккаунта
администратора или основного пользователя при первом запуске приложения.
Такой подход обеспечивает наличие в системе учетной записи с высоким уровнем доступа
для управления настройками, пользователями и другими важными аспектами системы сразу
после ее развертывания или в процессе разработки.
*/
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ru.demo.wms.consts.UserMode;
import ru.demo.wms.model.UserInfo;
import ru.demo.wms.repo.RoleRepository;
import ru.demo.wms.repo.UserInfoRepository;
import ru.demo.wms.service.IUserInfoService;

@Component
@Order(20)
public class UserMasterDataRunner implements CommandLineRunner {

	@Autowired
	private IUserInfoService service;
	
	@Autowired
	private UserInfoRepository repo;
	
	@Autowired
	private RoleRepository roleRepo;

	public void run(String... args) throws Exception {
		if(!repo.existsByEmail("example@gmail.com"))
		{
			UserInfo user = new UserInfo();

			user.setName("newuser");
			user.setEmail("example@gmail.com");

			user.setPassword("example");
			user.setMode(UserMode.ENABLED);

			user.setRoles(
					roleRepo.findAll()
					.stream()
					.collect(Collectors.toSet())
					);

			service.saveUserInfo(user);
		}
	}

}

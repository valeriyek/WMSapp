package ru.demo.wms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.exception.DataNotFoundException;
import ru.demo.wms.model.Grn;
import ru.demo.wms.repo.GrnDtlReposiory;
import ru.demo.wms.repo.GrnReposiory;
import ru.demo.wms.service.IGrnService;

@Service
public class GrnServiceImpl implements IGrnService {

	@Autowired
	private GrnReposiory repo;
	
	@Autowired
	private GrnDtlReposiory dtlRepo;
	
	public Integer saveGrn(Grn grn) {
		return repo.save(grn).getId();
	}

	public List<Grn> fetchAllGrns() {
		return repo.findAll();
	}

	public Grn getOneGrn(Integer id) {
		return repo.findById(id)
				.orElseThrow(
						()->new DataNotFoundException("GRN NOT EXIST")
						);
	}
	
	@Transactional
	public void updateGrnDtlStatus(Integer id, String status) {
		dtlRepo.updateGrnDtlStatus(id, status);
	}

}

/*
Класс GrnServiceImpl реализует интерфейс IGrnService, предоставляя функциональность для управления данными о поступлениях товаров (Goods Receipt Note, GRN) в приложении. Давайте рассмотрим ключевые аспекты этой реализации и потенциальные направления для улучшения.

Внедрение зависимостей
GrnReposiory и GrnDtlReposiory: Используются для взаимодействия с базой данных. Аннотация @Autowired обеспечивает автоматическое внедрение этих зависимостей, позволяя работать с основными данными GRN и их деталями.
Методы сервиса
saveGrn: Сохраняет информацию о GRN в базе данных и возвращает идентификатор сохраненной записи. Использует метод save репозитория.

fetchAllGrns: Возвращает список всех записей GRN из базы данных. Использует метод findAll репозитория.

getOneGrn: Возвращает одну запись GRN по идентификатору. Если запись не найдена, генерируется исключение DataNotFoundException. Это обеспечивает явную обработку ситуации отсутствия данных.

updateGrnDtlStatus: Обновляет статус деталей GRN. Метод помечен аннотацией @Transactional, что указывает на необходимость выполнения в контексте транзакции. Это особенно важно, поскольку обновление может затрагивать множество записей.

Рекомендации по улучшению
Использование DTO: Вместо прямой передачи сущностей между слоями приложения может быть полезно использование DTO (Data Transfer Objects). Это улучшит безопасность, позволит избежать случайной модификации данных и упростит изменение архитектуры в будущем.

Логирование: Добавление логирования операций может помочь в отладке и мониторинге выполнения операций, особенно при обработке исключений и выполнении ключевых бизнес-операций.

Обработка исключений: Создание специализированных классов исключений для разных сценариев ошибок может улучшить обработку ошибок и предоставить более детальную информацию об ошибках клиентам API.

Валидация данных: Добавление валидации входных данных на уровне сервиса может предотвратить сохранение некорректных данных в базу и упростить обработку ошибок.

Более гранулярные транзакции: Вместо использования @Transactional на уровне метода для всех операций обновления может быть полезно более точно управлять транзакциями, особенно в случаях, когда одна операция включает несколько шагов, требующих разных стратегий обработки ошибок и восстановления.

Класс GrnServiceImpl служит важной частью бизнес-логики приложения, обеспечивая управление данными о поступлениях товаров. Применение предложенных улучшений может сделать сервис более надежным, безопасным и удобным в использовании.
*/
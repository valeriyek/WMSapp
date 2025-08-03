package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.ShipmentTypeNotFoundException;
import ru.demo.wms.model.ShipmentType;
import ru.demo.wms.repo.ShipmentTypeRepository;
import ru.demo.wms.service.IShipmentTypeService;
import ru.demo.wms.util.MyAppUtil;

@Service
public class ShipmentTypeServiceImpl 
implements IShipmentTypeService
{
@Autowired
	private MyAppUtil myAppUtil;
	@Autowired
	private ShipmentTypeRepository repo;

	public Integer saveShipmentType(ShipmentType st) {
		st = repo.save(st);
		return st.getId();
	}

	public List<ShipmentType> getAllShipmentTypes() {
		List<ShipmentType> list = repo.findAll();
		return list;
	}


	
	public void deleteShipmentType(Integer id) {
		repo.delete(getShipmentType(id));
	}

	public ShipmentType getShipmentType(Integer id) {
		return repo.findById(id)
				.orElseThrow(
						()->new ShipmentTypeNotFoundException(
								"ShipmentType '"+id+"' Not Exist")
						);
	}

	public void updateShipmentType(ShipmentType st) {

		repo.save(st);
	}

	public boolean isShipmentTypeCodeExist(String code) {
		/*
		Integer count = repo.getShipmentTypeCodeCount(code);
		boolean isExist = count > 0 ? true: false;
		return isExist;*/
		//return repo.getShipmentTypeCodeCount(code) > 0 ? true: false;
		return repo.getShipmentTypeCodeCount(code) > 0 ;
	}

	public boolean isShipmentTypeCodeExistForEdit(String code,Integer id) {
		return repo.getShipmentTypeCodeCountForEdit(code, id) > 0;
	}
	
	public List<Object[]> getShipmentTypeModeAndCount() {
		return repo.getShipmentTypeModeAndCount();
	}
	
	public Map<Integer, String> getShipmentIdAndCodeByEnable(String enable) {
		List<Object[]> list =  repo.getShipmentIdAndCodeByEnable(enable);
		return myAppUtil.convertListToMap(list);
	}
}


/*
Класс ShipmentTypeServiceImpl предоставляет реализацию интерфейса IShipmentTypeService, обеспечивая управление типами отправлений (ShipmentType) в системе. Эта реализация включает в себя операции CRUD, проверку уникальности кодов типов отправлений, а также агрегацию данных для отчетов и интеграцию. Давайте рассмотрим основные аспекты и потенциальные направления для улучшения:

Основные функции:
CRUD операции: Класс реализует основные операции управления данными типов отправлений, такие как создание, обновление, удаление и получение типов отправлений.
Валидация кода: Методы isShipmentTypeCodeExist и isShipmentTypeCodeExistForEdit обеспечивают проверку уникальности кода типа отправления при создании и редактировании записей.
Агрегация данных: Метод getShipmentTypeModeAndCount собирает статистические данные о количестве типов отправлений по их режимам, что может быть полезно для анализа и отчетности.
Фильтрация по статусу: Метод getShipmentIdAndCodeByEnable предоставляет список типов отправлений, активированных или деактивированных, в зависимости от параметра enable.
Возможные улучшения:
Обработка исключений: Применение специализированных исключений, как ShipmentTypeNotFoundException, улучшает обработку ошибок и предоставляет более четкие сообщения об ошибках пользователям или другим компонентам системы.
Логирование: Добавление логирования операций может помочь в отладке и мониторинге системы, особенно в случаях исключений или нештатных ситуаций.
Транзакционность: Для методов, изменяющих состояние данных (например, updateShipmentType), рассмотрите использование аннотации @Transactional, чтобы обеспечить атомарность операций в рамках одной транзакции.
DTO и маппинг: Использование DTO (Data Transfer Objects) может помочь в разделении слоев логики и данных, а также упростить изменения в модели данных, не затрагивая API.
Валидация входных данных: Реализация валидации на уровне сервиса для проверки входных данных перед выполнением операций с данными может предотвратить возможные ошибки и улучшить надежность системы.
Использование Optional в репозитории: Вместо прямой проверки opt.isEmpty() рекомендуется использовать метод orElseThrow(), что делает код более читабельным и компактным.
ShipmentTypeServiceImpl играет ключевую роль в управлении типами отправлений в логистической системе, обеспечивая необходимую функциональность для поддержки бизнес-процессов. Применение предложенных улучшений может повысить качество кода, облегчить его поддержку и улучшить пользовательский опыт.
*/
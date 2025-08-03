package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.UomNotFoundException;
import ru.demo.wms.model.Uom;
import ru.demo.wms.repo.UomRepository;
import ru.demo.wms.service.IUomService;
import ru.demo.wms.util.MyAppUtil;

@Service
public class UomServiceImpl implements IUomService {
@Autowired
	private MyAppUtil myAppUtil;
	@Autowired
	private UomRepository repo;
	public Integer saveUom(Uom uom) {
		uom = repo.save(uom);
		return uom.getId();
	}

	public void updateUom(Uom uom) {
		if(uom.getId() == null || !repo.existsById(uom.getId())) {
			throw new UomNotFoundException(
					"Uom '"+(uom.getId()==null?"id":uom.getId())+"' not exist for update!");
		} else {
			repo.save(uom);
		}
		
	}

	public void deleteUom(Integer id) {
		repo.delete(getOneUom(id));
	}

	public Uom getOneUom(Integer id) {
		return repo.findById(id)
				.orElseThrow(
						()->new UomNotFoundException(
								"Uom '"+id+"' Not exist")
						);
	}

	public List<Uom> getAllUoms() {
		return repo.findAll();
	}

	public boolean isUomModelExist(String uomModel) {
		return repo.getUomModelCount(uomModel)>0;
	}

	public boolean isUomModelExistForEdit(String uomModel, Integer id) {
		return repo.getUomModelCountForEdit(uomModel, id)>0;
	}

	public Map<Integer, String> getUomIdAndModel() {
		List<Object[]> list =  repo.getUomIdAndModel();
		return myAppUtil.convertListToMap(list);
		
	}

}
/*
Класс UomServiceImpl реализует интерфейс IUomService, предоставляя логику для управления единицами измерения (UOM) в системе. Этот сервис включает операции CRUD для работы с объектами Uom, а также проверку на уникальность и получение агрегированных данных. Давайте разберем основные аспекты этой реализации и предложим улучшения.

Основные функции:
CRUD операции: Предоставляются методы для создания (saveUom), обновления (updateUom), удаления (deleteUom), и получения (getOneUom, getAllUoms) единиц измерения.
Проверка на уникальность: Методы isUomModelExist и isUomModelExistForEdit проверяют, существует ли уже единица измерения с заданной моделью, что важно для обеспечения уникальности данных.
Агрегация данных: Метод getUomIdAndModel извлекает данные о всех единицах измерения для последующего использования, например, в пользовательских интерфейсах.
Возможные улучшения:
Обработка исключений: Использование специализированных исключений, таких как UomNotFoundException, помогает в более точной обработке ошибочных ситуаций. Возможно, стоит рассмотреть создание иерархии исключений для различных типов ошибок в приложении.
Логирование: Добавление логирования к ключевым операциям может улучшить отслеживание работы приложения и упростить диагностику проблем. Рекомендуется использовать различные уровни логирования (INFO, DEBUG, ERROR) для разных ситуаций.
Транзакционность: Обеспечение транзакционности операций, влияющих на данные, может предотвратить неконсистентность данных в случае возникновения ошибок.
Валидация данных: Внедрение логики валидации на уровне сервиса может помочь предотвратить добавление некорректных данных в систему и улучшить качество пользовательского опыта.
Использование DTO и маппинга: Применение DTO (Data Transfer Objects) и библиотек маппинга (например, ModelMapper или MapStruct) может облегчить преобразование данных между слоями и улучшить архитектурную чистоту приложения.
UomServiceImpl играет ключевую роль в управлении единицами измерения в системе, обеспечивая необходимые операции для поддержки бизнес-процессов. Применение предложенных улучшений может повысить надежность, удобство поддержки и масштабируемость сервиса.
*/
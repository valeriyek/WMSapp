package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.WhUserTypeNotFound;
import ru.demo.wms.model.WhUserType;
import ru.demo.wms.repo.WhUserTypeRepository;
import ru.demo.wms.service.IWhUserTypeService;
import ru.demo.wms.util.MyAppUtil;

@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService{
@Autowired
	private MyAppUtil myAppUtil;
	@Autowired
	private WhUserTypeRepository repo;
	
	public Integer saveWhUserType(WhUserType whut) {
		return repo.save(whut).getId();
	}

	public List<WhUserType> getAllWhUserTypes() {
		return repo.findAll();
	}

	public void deleteWhUserType(Integer id) {
		repo.delete(getOneWhUserType(id));
	}

	@Override
	public WhUserType getOneWhUserType(Integer id) {
		
		return repo.findById(id).orElseThrow(
				()-> new WhUserTypeNotFound("WhUserType '"+id+"'  Not Exit")
		
				);
	}

	@Override
	public void updateWhUserType(WhUserType whut) {
		if(whut.getId()==null || !repo.existsById(whut.getId()))
			throw new  WhUserTypeNotFound("WhUserType   '"+whut.getId()+"' Not Exist");
		repo.save(whut).getId();
		
	}
 

	@Override
	public boolean isWhUserTypeCodeExit(String code) {
		
		return repo.getWhUserTypeuserCodeCount(code)>0;
	}

	@Override
	public boolean isWhUserTypeCodeExitForEdit(String code, Integer id) {
		
		return repo.getWhUserTypeuserCodeCountForEdit(code, id)>0;
	}


	@Override
	public boolean getWhUserTypeuserEmailCount(String email) {
		
		return repo.getWhUserTypeuserEmailCount(email)>0;
	}

	@Override
	public boolean getWhUserTypeuserEmailCountForEdit(String email, Integer id) {
		
		return repo.getWhUserTypeuserEmailCountForEdit(email, id)>0;
	}

	@Override
	public boolean getWhUserTypeuserIdNumCount(String idnum) {
		
		return repo.getWhUserTypeuserIdNumCount(idnum)>0;
	}

	@Override
	public boolean getWhUserTypeuserIdNumCountForEdit(String idnum, Integer id) {
		return repo.getWhUserTypeuserIdNumCountForEdit(idnum, id)>0;
	}

	@Override
	public List<Object[]> getWhUserTypUserIDAndCount() {
		
		return repo.getWhUserTypUserIDAndCount();
	}
	
	public Map<Integer, String> getWhUserIdAndCodeByType(String type) {
		List<Object[]> list = repo.getWhUserIdAndCodeByType(type);
		return myAppUtil.convertListToMap(list);
	}

}
/*
Класс WhUserTypeServiceImpl предлагает комплексное решение для управления типами пользователей склада (WhUserType) в системе, обеспечивая ряд операций CRUD, проверку уникальности данных и агрегацию информации для аналитических целей. Рассмотрим ключевые моменты и возможные улучшения:

Ключевые функции:
CRUD операции: Реализованы методы для создания (saveWhUserType), получения (getOneWhUserType, getAllWhUserTypes), обновления (updateWhUserType), и удаления (deleteWhUserType) типов пользователей склада.
Проверка уникальности: Методы, такие как isWhUserTypeCodeExit и getWhUserTypeuserEmailCount, позволяют проверить уникальность кодов и электронных почт пользователей, что важно для предотвращения дублирования данных.
Агрегация данных: getWhUserTypUserIDAndCount собирает и агрегирует информацию о типах пользователей, что может быть полезно для отчетов и анализа данных.
Фильтрация по типу: getWhUserIdAndCodeByType предоставляет способ получения данных о пользователях, фильтруя их по типу.
Возможные улучшения:
Обработка исключений: Использование специализированных исключений (например, WhUserTypeNotFound) улучшает управление ошибками. Рассмотрите возможность расширения иерархии исключений для обработки различных ошибочных сценариев.
Логирование: Добавление логирования для ключевых операций поможет в отладке и мониторинге системы. Это особенно важно для операций, влияющих на данные, и сценариев исключений.
Валидация данных: Включение валидации для входящих данных перед их обработкой может предотвратить некорректное использование API и улучшить качество данных.
DTO и маппинг: Рассмотрите возможность использования DTO (Data Transfer Objects) для разграничения слоев представления и данных. Это облегчит изменения в модели данных и интерфейсе пользователя.
Транзакционность: Для методов, изменяющих данные (например, updateWhUserType), убедитесь, что они выполняются в рамках транзакции, чтобы гарантировать целостность данных.
WhUserTypeServiceImpl играет ключевую роль в управлении типами пользователей склада, обеспечивая гибкость и расширяемость системы через модульные и взаимосвязанные операции. Применение улучшений поможет сделать систему более надежной, безопасной и удобной в обслуживании.
*/
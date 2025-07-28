package ru.demo.wms.service;

import java.util.List;
import java.util.Map;

import ru.demo.wms.model.WhUserType;

public interface IWhUserTypeService {  

	Integer saveWhUserType(WhUserType whut);
	List<WhUserType> getAllWhUserTypes();
	void deleteWhUserType(Integer id);
	WhUserType getOneWhUserType(Integer id);
	void updateWhUserType(WhUserType whut);

	boolean isWhUserTypeCodeExit(String code);
	boolean isWhUserTypeCodeExitForEdit(String code,Integer id); 

	boolean getWhUserTypeuserEmailCount(String email);
	boolean getWhUserTypeuserEmailCountForEdit(String email, Integer id);

	boolean getWhUserTypeuserIdNumCount(String idnum);	
	boolean getWhUserTypeuserIdNumCountForEdit(String idnum, Integer id);

	List<Object[]> getWhUserTypUserIDAndCount();

	//for integration
	Map<Integer,String> getWhUserIdAndCodeByType(String type);
	
}
/*
Интерфейс IWhUserTypeService определяет сервисный слой для управления типами пользователей склада (WhUserType) в системе управления запасами или логистикой. Этот интерфейс предлагает комплексный набор методов для создания, обновления, удаления и получения информации о типах пользователей склада, а также для выполнения специфических проверок и интеграции с другими модулями системы.

Основные методы интерфейса:
saveWhUserType(WhUserType whut):
Сохраняет новый тип пользователя склада или обновляет существующий. Возвращает идентификатор сохраненного или обновленного объекта.

getAllWhUserTypes():
Возвращает список всех типов пользователей склада, зарегистрированных в системе.

deleteWhUserType(Integer id):
Удаляет тип пользователя склада по его идентификатору.

getOneWhUserType(Integer id):
Возвращает детальную информацию о конкретном типе пользователя склада по его идентификатору.

updateWhUserType(WhUserType whut):
Обновляет информацию о типе пользователя склада.

isWhUserTypeCodeExit(String code) и isWhUserTypeCodeExitForEdit(String code, Integer id):
Проверяют, существует ли уже тип пользователя склада с указанным кодом, что важно для предотвращения дублирования данных при создании или обновлении записей.

getWhUserTypeuserEmailCount(String email) и getWhUserTypeuserEmailCountForEdit(String email, Integer id):
Проверяют, существует ли уже тип пользователя склада с указанным адресом электронной почты.

getWhUserTypeuserIdNumCount(String idnum) и getWhUserTypeuserIdNumCountForEdit(String idnum, Integer id):
Проверяют, существует ли уже тип пользователя склада с указанным идентификационным номером.

getWhUserTypUserIDAndCount():
Возвращает агрегированные данные по типам пользователей склада для аналитических отчетов и визуализаций.

getWhUserIdAndCodeByType(String type):
Предоставляет карту идентификаторов и кодов типов пользователей склада, фильтруемую по определенному типу, что может быть использовано для интеграции с другими модулями системы.

Рекомендации для реализации:
Валидация данных: Перед сохранением или обновлением информации о типах пользователей склада важно провести тщательную проверку входных данных для обеспечения их корректности и соответствия бизнес-правилам.

Обработка исключений: При разработке реализации сервиса следует предусмотреть корректную обработку возможных исключений, например, при попытке удалить несуществующий тип пользователя склада.

Оптимизация запросов: Для методов, возвращающих списки или агрегированные данные, важно обеспечить эффективность выполнения запросов к базе данных.

Безопасность: Необходимо обеспечить защиту данных о типах пользователей склада, особенно если они содержат чувствительную информацию.

Реализация IWhUserTypeService играет важную роль в обеспечении гибкого и эффективного управления типами пользователей склада, позволяя системе адаптироваться к разнообразным бизнес-процессам и операционным потребностям.
*/
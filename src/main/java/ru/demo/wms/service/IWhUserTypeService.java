package ru.demo.wms.service;

import java.util.List;
import java.util.Map;

import ru.demo.wms.model.WhUserType;

/**
 * Сервисный интерфейс для управления типами пользователей склада (WhUserType).
 * Предоставляет методы для CRUD-операций, валидации уникальности, агрегации и интеграции.
 */
public interface IWhUserTypeService {

	/**
	 * Сохраняет нового пользователя склада.
	 *
	 * @param whut объект WhUserType
	 * @return ID сохранённой записи
	 */
	Integer saveWhUserType(WhUserType whut);

	/**
	 * Возвращает все записи типов пользователей склада.
	 *
	 * @return список WhUserType
	 */
	List<WhUserType> getAllWhUserTypes();

	/**
	 * Удаляет пользователя склада по ID.
	 *
	 * @param id идентификатор записи
	 */
	void deleteWhUserType(Integer id);

	/**
	 * Возвращает одного пользователя склада по ID.
	 *
	 * @param id идентификатор записи
	 * @return объект WhUserType
	 */
	WhUserType getOneWhUserType(Integer id);

	/**
	 * Обновляет существующую запись типа пользователя склада.
	 *
	 * @param whut обновлённый объект WhUserType
	 */
	void updateWhUserType(WhUserType whut);

	/**
	 * Проверяет, существует ли запись с таким кодом.
	 *
	 * @param code проверяемый код
	 * @return true, если код уже используется
	 */
	boolean isWhUserTypeCodeExit(String code);

	/**
	 * Проверяет, существует ли запись с таким кодом, исключая текущий ID (для редактирования).
	 *
	 * @param code код для проверки
	 * @param id исключаемый ID
	 * @return true, если дублирование найдено
	 */
	boolean isWhUserTypeCodeExitForEdit(String code, Integer id);

	/**
	 * Проверяет, существует ли пользователь с указанным email.
	 *
	 * @param email адрес email
	 * @return true, если найдено совпадение
	 */
	boolean getWhUserTypeuserEmailCount(String email);

	/**
	 * Проверяет email при редактировании (исключает текущий ID).
	 *
	 * @param email проверяемый email
	 * @param id исключаемый ID
	 * @return true, если найден дубликат
	 */
	boolean getWhUserTypeuserEmailCountForEdit(String email, Integer id);

	/**
	 * Проверяет, существует ли пользователь с указанным ID-номером.
	 *
	 * @param idnum идентификационный номер
	 * @return true, если найден дубликат
	 */
	boolean getWhUserTypeuserIdNumCount(String idnum);

	/**
	 * Проверяет ID-номер при редактировании (исключает текущий ID).
	 *
	 * @param idnum проверяемый номер
	 * @param id исключаемый ID
	 * @return true, если найден дубликат
	 */
	boolean getWhUserTypeuserIdNumCountForEdit(String idnum, Integer id);

	/**
	 * Возвращает статистику количества пользователей по их типу (например, ADMIN, VENDOR).
	 *
	 * @return список массивов [тип, количество]
	 */
	List<Object[]> getWhUserTypUserIDAndCount();

	/**
	 * Возвращает карту [ID пользователя → Код] для заданного типа (например, "VENDOR").
	 * Используется для интеграции с другими модулями.
	 *
	 * @param type тип пользователя склада
	 * @return Map ID → Код
	 */
	Map<Integer, String> getWhUserIdAndCodeByType(String type);
}

package ru.demo.wms.service;

import java.util.List;
import java.util.Map;

import ru.demo.wms.model.ShipmentType;

/**
 * Сервисный интерфейс для управления типами отгрузок в системе WMS.
 * Предоставляет методы для создания, обновления, удаления и получения информации,
 * а также проверки уникальности и получения агрегированных данных.
 */
public interface IShipmentTypeService {

	/**
	 * Сохраняет новый тип отгрузки и возвращает его ID.
	 *
	 * @param st объект ShipmentType для сохранения
	 * @return идентификатор сохранённого объекта
	 */
	Integer saveShipmentType(ShipmentType st);

	/**
	 * Обновляет существующий тип отгрузки.
	 *
	 * @param st объект ShipmentType с новыми данными
	 */
	void updateShipmentType(ShipmentType st);

	/**
	 * Удаляет тип отгрузки по его ID.
	 *
	 * @param id идентификатор удаляемого типа отгрузки
	 */
	void deleteShipmentType(Integer id);

	/**
	 * Возвращает список всех типов отгрузок.
	 *
	 * @return список объектов ShipmentType
	 */
	List<ShipmentType> getAllShipmentTypes();

	/**
	 * Возвращает один тип отгрузки по его ID.
	 *
	 * @param id идентификатор типа отгрузки
	 * @return объект ShipmentType
	 */
	ShipmentType getShipmentType(Integer id);

	/**
	 * Проверяет, существует ли тип отгрузки с заданным кодом.
	 *
	 * @param code код типа отгрузки
	 * @return true, если код уже существует, иначе false
	 */
	boolean isShipmentTypeCodeExist(String code);

	/**
	 * Проверяет уникальность кода типа отгрузки при редактировании.
	 * Исключает текущий ID из проверки.
	 *
	 * @param code код для проверки
	 * @param id   идентификатор текущего объекта
	 * @return true, если такой код уже используется, иначе false
	 */
	boolean isShipmentTypeCodeExistForEdit(String code, Integer id);

	/**
	 * Возвращает статистику типов отгрузки по режимам.
	 * Каждый элемент содержит:
	 * [0] - режим (String), [1] - количество (Long).
	 *
	 * @return список массивов с режимом и количеством
	 */
	List<Object[]> getShipmentTypeModeAndCount();

	/**
	 * Возвращает карту [ID, Код типа отгрузки] для заданного признака активности.
	 *
	 * @param enable "YES" или "NO" в зависимости от статуса активности
	 * @return карта с ID и кодами
	 */
	Map<Integer, String> getShipmentIdAndCodeByEnable(String enable);
}

package ru.demo.wms.service;

import java.util.List;
import java.util.Map;

import ru.demo.wms.model.Uom;

/**
 * Сервисный интерфейс для управления единицами измерения (UOM — Unit of Measurement).
 * Предоставляет методы для CRUD-операций, проверки уникальности модели и получения справочных данных.
 */
public interface IUomService {

	/**
	 * Сохраняет новую единицу измерения.
	 *
	 * @param uom объект единицы измерения
	 * @return идентификатор сохранённого объекта
	 */
	Integer saveUom(Uom uom);

	/**
	 * Обновляет существующую единицу измерения.
	 *
	 * @param uom объект с обновлёнными данными
	 */
	void updateUom(Uom uom);

	/**
	 * Удаляет единицу измерения по её ID.
	 *
	 * @param id идентификатор удаляемой единицы
	 */
	void deleteUom(Integer id);

	/**
	 * Возвращает одну единицу измерения по ID.
	 *
	 * @param id идентификатор
	 * @return объект единицы измерения
	 */
	Uom getOneUom(Integer id);

	/**
	 * Возвращает список всех единиц измерения.
	 *
	 * @return список объектов Uom
	 */
	List<Uom> getAllUoms();

	/**
	 * Проверяет, существует ли единица измерения с указанной моделью.
	 *
	 * @param uomModel модель (название) для проверки
	 * @return true, если модель уже существует, иначе false
	 */
	boolean isUomModelExist(String uomModel);

	/**
	 * Проверяет уникальность модели при редактировании, исключая текущую запись.
	 *
	 * @param uomModel модель (название)
	 * @param id ID текущей записи, которую следует исключить из проверки
	 * @return true, если модель уже используется, иначе false
	 */
	boolean isUomModelExistForEdit(String uomModel, Integer id);

	/**
	 * Возвращает карту, где ключ — ID, значение — модель единицы измерения.
	 * Удобно для выпадающих списков в UI.
	 *
	 * @return карта [ID, Название модели]
	 */
	Map<Integer, String> getUomIdAndModel();
}

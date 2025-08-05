package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.ShipmentType;

/**
 * Репозиторий для работы с сущностью {@link ShipmentType}.
 * Предоставляет CRUD-операции и специализированные методы
 * для аналитики и валидации.
 */
public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Integer> {

	/**
	 * Проверка уникальности кода при создании.
	 *
	 * @param code код типа отправки
	 * @return количество найденных записей с таким кодом
	 */
	@Query("SELECT count(shipCode) FROM ShipmentType WHERE shipCode=:code")
	Integer getShipmentTypeCodeCount(String code);

	/**
	 * Проверка уникальности кода при редактировании (исключая текущую запись).
	 *
	 * @param code код типа отправки
	 * @param id   идентификатор редактируемой записи
	 * @return количество найденных записей с таким кодом (кроме текущей)
	 */
	@Query("SELECT count(shipCode) FROM ShipmentType WHERE shipCode=:code AND id!=:id")
	Integer getShipmentTypeCodeCountForEdit(String code, Integer id);

	/**
	 * Получение количества типов отправки, сгруппированных по режиму (shipMode).
	 * Используется, например, для построения диаграмм.
	 *
	 * @return список массивов: [shipMode, count]
	 */
	@Query("SELECT shipMode, count(shipMode) FROM ShipmentType GROUP BY shipMode")
	List<Object[]> getShipmentTypeModeAndCount();

	/**
	 * Получение id и кода отправки по признаку активности.
	 * Применяется для динамических выпадающих списков.
	 *
	 * @param enable значение поля enbleShip
	 * @return список массивов: [id, shipCode]
	 */
	@Query("SELECT id, shipCode FROM ShipmentType WHERE enbleShip=:enable")
	List<Object[]> getShipmentIdAndCodeByEnable(String enable);
}

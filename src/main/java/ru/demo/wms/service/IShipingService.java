package ru.demo.wms.service;

import java.util.List;

import ru.demo.wms.model.Shiping;

/**
 * Интерфейс IShipingService описывает контракт сервисного слоя
 * для управления операциями доставки (отгрузки) в системе WMS.
 */
public interface IShipingService {

	/**
	 * Сохраняет новую запись о доставке и возвращает её ID.
	 *
	 * @param shiping объект доставки
	 * @return идентификатор сохранённой записи
	 */
	Integer saveShiping(Shiping shiping);

	/**
	 * Возвращает список всех доставок, зарегистрированных в системе.
	 *
	 * @return список объектов Shiping
	 */
	List<Shiping> getAllShiping();

	/**
	 * Возвращает одну доставку по её ID.
	 *
	 * @param id идентификатор доставки
	 * @return объект Shiping
	 */
	Shiping getOneShiping(Integer id);

	/**
	 * Обновляет статус определённой детали доставки.
	 * Используется, например, для пометки как "отгружено", "в пути", "доставлено".
	 *
	 * @param id идентификатор детали доставки
	 * @param status новый статус (строкой)
	 */
	void updateShipingDtlStatus(Integer id, String status);
}

package ru.demo.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import ru.demo.wms.model.SaleOrder;
import ru.demo.wms.model.SaleOrderDetails;

/**
 * Интерфейс ISaleOrderService описывает контракт сервисного слоя
 * для управления заказами на продажу и их деталями в системе WMS.
 */
public interface ISaleOrderService {

	/**
	 * Сохраняет новый заказ на продажу и возвращает его ID.
	 *
	 * @param saleOrder объект заказа на продажу
	 * @return идентификатор созданного заказа
	 */
	Integer saveSaleOrder(SaleOrder saleOrder);

	/**
	 * Возвращает список всех заказов на продажу.
	 *
	 * @return список заказов
	 */
	List<SaleOrder> getAllSaleOrder();

	/**
	 * Возвращает заказ на продажу по ID.
	 *
	 * @param id идентификатор заказа
	 * @return объект SaleOrder
	 */
	SaleOrder getOneSaleOrder(Integer id);

	/**
	 * Проверяет уникальность кода заказа.
	 *
	 * @param code код заказа
	 * @return true, если код уже существует
	 */
	boolean validateOrderCode(String code);

	/**
	 * Проверяет уникальность кода заказа при редактировании.
	 *
	 * @param code код заказа
	 * @param id идентификатор редактируемого заказа
	 * @return true, если код уже используется другим заказом
	 */
	boolean validateOrderCodeAndId(String code, Integer id);

	/**
	 * Сохраняет деталь к заказу на продажу и возвращает её ID.
	 *
	 * @param saleOrderDetails объект детали заказа
	 * @return идентификатор созданной детали
	 */
	Integer savePurchaseDetails(SaleOrderDetails saleOrderDetails);

	/**
	 * Возвращает все детали для указанного заказа на продажу.
	 *
	 * @param id идентификатор заказа
	 * @return список деталей заказа
	 */
	List<SaleOrderDetails> getSaleDtlsBySaleOrderId(Integer id);

	/**
	 * Удаляет одну деталь заказа по ID.
	 *
	 * @param detailId идентификатор детали
	 */
	void deleteSaleDetails(Integer detailId);

	/**
	 * Получает текущий статус заказа на продажу.
	 *
	 * @param soId идентификатор заказа
	 * @return строковое представление статуса
	 */
	String getCurrentStatusOfSaleOrder(Integer soId);

	/**
	 * Обновляет статус указанного заказа на продажу.
	 *
	 * @param soId идентификатор заказа
	 * @param newStatus новый статус
	 */
	void updateSaleOrderStatus(Integer soId, String newStatus);

	/**
	 * Возвращает количество деталей для конкретного заказа на продажу.
	 *
	 * @param soId идентификатор заказа
	 * @return количество деталей
	 */
	Integer getSaleDtlsCountBySaleOrderId(Integer soId);

	/**
	 * Получает деталь заказа по ID детали и ID заказа.
	 *
	 * @param partId идентификатор товара (детали)
	 * @param soId идентификатор заказа
	 * @return найденная деталь (если есть)
	 */
	Optional<SaleOrderDetails> getSaleDetailByPartIdAndSaleOrderId(Integer partId, Integer soId);

	/**
	 * Обновляет количество конкретной детали заказа по её ID.
	 *
	 * @param newQty новое количество
	 * @param dtlId идентификатор детали
	 * @return количество обновленных записей (1 или 0)
	 */
	Integer updateSaleOrderDetailQtyByDetailId(Integer newQty, Integer dtlId);

	/**
	 * Возвращает карту заказов на продажу по определенному статусу.
	 * Используется, например, для формирования выпадающих списков.
	 *
	 * @param status статус заказа
	 * @return карта [ID заказа → код заказа]
	 */
	Map<Integer, String> findSaleOrderIdAndCodeByStatus(String status);
}

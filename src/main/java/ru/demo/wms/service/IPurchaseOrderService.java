package ru.demo.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import ru.demo.wms.model.PurchaseDtl;
import ru.demo.wms.model.PurchaseOrder;

/**
 * Сервисный интерфейс для управления заказами на покупку (PurchaseOrder)
 * и их деталями (PurchaseDtl) в системе WMS.
 *
 * Обеспечивает полный цикл работы с заказами: создание, валидация,
 * управление статусами, получение агрегированных данных.
 */
public interface IPurchaseOrderService {

	/**
	 * Сохраняет новый заказ на покупку.
	 *
	 * @param po объект заказа на покупку.
	 * @return ID сохраненного заказа.
	 */
	Integer savePurchaseOrder(PurchaseOrder po);

	/**
	 * Получает заказ по ID.
	 *
	 * @param id идентификатор заказа.
	 * @return объект PurchaseOrder.
	 */
	PurchaseOrder getOnePurchaseOrder(Integer id);

	/**
	 * Возвращает список всех заказов на покупку.
	 *
	 * @return список заказов.
	 */
	List<PurchaseOrder> getAllPurchaseOrders();

	/**
	 * Проверяет, существует ли заказ с данным кодом.
	 *
	 * @param code код заказа.
	 * @return true, если существует.
	 */
	boolean isPurchaseOrderCodeExist(String code);

	/**
	 * Проверяет наличие кода заказа с исключением по ID (для редактирования).
	 *
	 * @param code код заказа.
	 * @param id ID исключаемого заказа.
	 * @return true, если такой код уже есть.
	 */
	boolean isPurchaseOrderCodeExistForEdit(String code, Integer id);

	/**
	 * Сохраняет деталь к заказу на покупку.
	 *
	 * @param pdtl объект детали.
	 * @return ID сохранённой детали.
	 */
	Integer savePurchaseDtl(PurchaseDtl pdtl);

	/**
	 * Возвращает список деталей по ID заказа.
	 *
	 * @param id ID заказа на покупку.
	 * @return список деталей.
	 */
	List<PurchaseDtl> getPurchaseDtlsByPoId(Integer id);

	/**
	 * Удаляет деталь по её ID.
	 *
	 * @param dtlId ID детали.
	 */
	void deletePurchaseDtl(Integer dtlId);

	/**
	 * Получает текущий статус заказа.
	 *
	 * @param poId ID заказа.
	 * @return строковое значение статуса.
	 */
	String getCurrentStatusOfPo(Integer poId);

	/**
	 * Обновляет статус заказа.
	 *
	 * @param poId ID заказа.
	 * @param newStatus новый статус.
	 */
	void updatePoStatus(Integer poId, String newStatus);

	/**
	 * Возвращает количество деталей для указанного заказа.
	 *
	 * @param poId ID заказа.
	 * @return количество деталей.
	 */
	Integer getPurchaseDtlsCountByPoId(Integer poId);

	/**
	 * Получает деталь заказа по ID товара и ID заказа.
	 *
	 * @param partId ID компонента.
	 * @param poId ID заказа.
	 * @return Optional с найденной деталью или пустой.
	 */
	Optional<PurchaseDtl> getPurchaseDtlByPartIdAndPoId(Integer partId, Integer poId);

	/**
	 * Обновляет количество в детали заказа.
	 *
	 * @param newQty новое количество.
	 * @param dtlId ID детали.
	 * @return количество обновлённых записей (обычно 1).
	 */
	Integer updatePurchaseDtlQtyByDtlId(Integer newQty, Integer dtlId);

	/**
	 * Возвращает карту ID и кодов заказов по статусу.
	 * Удобно для фильтрации или выпадающих списков.
	 *
	 * @param status статус заказа.
	 * @return Map ID → Код заказа.
	 */
	Map<Integer, String> getPoIdAndCodesByStatus(String status);
}

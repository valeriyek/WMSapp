package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.PurchaseOrder;

/**
 * Репозиторий для работы с заказами на покупку ({@link PurchaseOrder}).
 * Предоставляет базовые CRUD-операции и дополнительные методы запросов.
 */
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

	/**
	 * Проверить, существует ли заказ с указанным кодом.
	 *
	 * @param orderCode код заказа
	 * @return количество совпадений
	 */
	@Query("SELECT count(orderCode) FROM PurchaseOrder WHERE orderCode = :orderCode")
	Integer getOrderCodeCount(String orderCode);

	/**
	 * Проверка уникальности кода заказа при редактировании (исключая текущий ID).
	 *
	 * @param orderCode код заказа
	 * @param id        ID текущего заказа
	 * @return количество совпадений (должно быть 0 для уникальности)
	 */
	@Query("SELECT count(orderCode) FROM PurchaseOrder WHERE orderCode = :orderCode AND id != :id")
	Integer getOrderCodeCountForEdit(String orderCode, Integer id);

	/**
	 * Получить текущий статус заказа по его ID.
	 *
	 * @param poId ID заказа
	 * @return статус заказа
	 */
	@Query("SELECT status FROM PurchaseOrder WHERE id = :poId")
	String getCurrentStatusOfPo(Integer poId);

	/**
	 * Обновить статус заказа по его ID.
	 *
	 * @param poId      ID заказа
	 * @param newStatus новый статус
	 */
	@Modifying
	@Query("UPDATE PurchaseOrder SET status = :newStatus WHERE id = :poId")
	void updatePoStatus(Integer poId, String newStatus);

	/**
	 * Получить список ID и кодов заказов с указанным статусом.
	 *
	 * @param status статус заказа
	 * @return список массивов [id, orderCode]
	 */
	@Query("SELECT id, orderCode FROM PurchaseOrder WHERE status = :status")
	List<Object[]> getPoIdAndCodesByStatus(String status);
}

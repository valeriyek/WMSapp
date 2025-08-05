package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.demo.wms.model.SaleOrder;

/**
 * Репозиторий для работы с заказами на продажу ({@link SaleOrder}).
 * Предоставляет CRUD-операции и дополнительные методы для проверки и обновления заказов.
 */
@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, Integer> {

	/**
	 * Проверка уникальности кода заказа при создании.
	 *
	 * @param code код заказа
	 * @return количество совпадений
	 */
	@Query("SELECT count(orderCode) FROM SaleOrder WHERE orderCode = :code")
	Integer validateOrderCode(String code);

	/**
	 * Проверка уникальности кода заказа при редактировании (исключая текущий ID).
	 *
	 * @param code код заказа
	 * @param id   ID текущего заказа
	 * @return количество совпадений (исключая переданный ID)
	 */
	@Query("SELECT count(orderCode) FROM SaleOrder WHERE orderCode = :code AND id != :id")
	Integer validateOrderCodeAndId(String code, Integer id);

	/**
	 * Получение текущего статуса заказа.
	 *
	 * @param soId идентификатор заказа
	 * @return статус заказа
	 */
	@Query("SELECT status FROM SaleOrder WHERE id = :soId")
	String getCurrentStatusOfSaleOrder(Integer soId);

	/**
	 * Обновление статуса заказа на продажу.
	 *
	 * @param soId      идентификатор заказа
	 * @param newStatus новый статус
	 */
	@Modifying
	@Query("UPDATE SaleOrder SET status = :newStatus WHERE id = :soId")
	void updateSaleOrderStatus(Integer soId, String newStatus);

	/**
	 * Получение списка ID и кодов заказов по статусу.
	 *
	 * @param status статус заказа
	 * @return список массивов [id, orderCode]
	 */
	@Query("SELECT id, orderCode FROM SaleOrder WHERE status = :status")
	List<Object[]> findSaleOrderIdAndCodeByStatus(String status);
}

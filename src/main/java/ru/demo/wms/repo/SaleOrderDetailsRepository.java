package ru.demo.wms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.demo.wms.model.SaleOrderDetails;

/**
 * Репозиторий для доступа к деталям заказов на продажу ({@link SaleOrderDetails}).
 * Предоставляет стандартные CRUD-операции и специализированные методы выборки и обновления.
 */
@Repository
public interface SaleOrderDetailsRepository extends JpaRepository<SaleOrderDetails, Integer> {

	/**
	 * Получить список деталей по ID заказа на продажу.
	 *
	 * @param poId идентификатор заказа
	 * @return список {@link SaleOrderDetails}, связанных с заказом
	 */
	@Query("SELECT s FROM SaleOrderDetails s JOIN s.saleOrder so WHERE so.id = :poId")
	List<SaleOrderDetails> getSaleDtlsBySaleOrderId(Integer poId);

	/**
	 * Получить количество деталей по ID заказа на продажу.
	 *
	 * @param soId идентификатор заказа
	 * @return количество строк с деталями
	 */
	@Query("SELECT COUNT(s) FROM SaleOrderDetails s JOIN s.saleOrder so WHERE so.id = :soId")
	Integer getSaleDtlsCountBySaleOrderId(Integer soId);

	/**
	 * Найти деталь по ID части и ID заказа.
	 *
	 * @param partId идентификатор части
	 * @param soId идентификатор заказа
	 * @return деталь {@link SaleOrderDetails}, если найдена
	 */
	@Query("SELECT s FROM SaleOrderDetails s JOIN s.part p JOIN s.saleOrder so WHERE p.id = :partId AND so.id = :soId")
	Optional<SaleOrderDetails> getSaleDetailByPartIdAndSaleOrderId(Integer partId, Integer soId);

	/**
	 * Увеличить количество в детали заказа на продажу.
	 *
	 * @param newQty количество для прибавления
	 * @param dtlId идентификатор детали
	 * @return количество обновлённых записей (1 — успешно)
	 */
	@Modifying
	@Query("UPDATE SaleOrderDetails SET qty = qty + :newQty WHERE id = :dtlId")
	Integer updateSaleOrderDetailQtyByDetailId(Integer newQty, Integer dtlId);
}

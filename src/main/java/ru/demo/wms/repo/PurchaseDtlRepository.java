package ru.demo.wms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.PurchaseDtl;

/**
 * Репозиторий для работы с деталями заказов на покупку ({@link PurchaseDtl}).
 * Предоставляет базовые CRUD-операции и дополнительные методы выборки и обновления.
 */
public interface PurchaseDtlRepository extends JpaRepository<PurchaseDtl, Integer> {

	/**
	 * Получить все детали по ID заказа на покупку.
	 *
	 * @param poId ID заказа на покупку
	 * @return список деталей {@link PurchaseDtl}
	 */
	@Query("SELECT pdtl FROM PurchaseDtl pdtl JOIN pdtl.po as purchaseOrder WHERE purchaseOrder.id = :poId")
	List<PurchaseDtl> getPurchaseDtlsByPoId(Integer poId);

	/**
	 * Получить количество деталей по ID заказа.
	 *
	 * @param poId ID заказа на покупку
	 * @return количество деталей
	 */
	@Query("SELECT count(pdtl) FROM PurchaseDtl pdtl JOIN pdtl.po as purchaseOrder WHERE purchaseOrder.id = :poId")
	Integer getPurchaseDtlsCountByPoId(Integer poId);

	/**
	 * Найти деталь по ID части и ID заказа.
	 *
	 * @param partId ID части
	 * @param poId   ID заказа
	 * @return {@link Optional} детали покупки
	 */
	@Query("SELECT dtl FROM PurchaseDtl dtl JOIN dtl.part as part JOIN dtl.po as po WHERE part.id = :partId AND po.id = :poId")
	Optional<PurchaseDtl> getPurchaseDtlByPartIdAndPoId(Integer partId, Integer poId);

	/**
	 * Увеличить количество по ID детали.
	 *
	 * @param newQty количество для добавления
	 * @param dtlId  ID детали
	 * @return количество обновлённых записей
	 */
	@Modifying
	@Query("UPDATE PurchaseDtl SET qty = qty + :newQty WHERE id = :dtlId")
	Integer updatePurchaseDtlQtyByDtlId(Integer newQty, Integer dtlId);
}

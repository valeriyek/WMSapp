package ru.demo.wms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.demo.wms.model.SaleOrderDetails;

@Repository
public interface SaleOrderDetailsRepository extends JpaRepository<SaleOrderDetails, Integer> {

	@Query("select saleOrderDetails from SaleOrderDetails saleOrderDetails JOIN saleOrderDetails.saleOrder as saleOrder where saleOrder.id=:poId")
	public List<SaleOrderDetails> getSaleDtlsBySaleOrderId(Integer poId);


	@Query("SELECT count(saleOrderDetails) FROM SaleOrderDetails saleOrderDetails JOIN saleOrderDetails.saleOrder as saleOrder WHERE saleOrder.id=:soId")
	public Integer getSaleDtlsCountBySaleOrderId(Integer soId);

	@Query("SELECT saleOrderDetails FROM SaleOrderDetails  saleOrderDetails JOIN saleOrderDetails.part as part JOIN saleOrderDetails.saleOrder  as saleOrder WHERE part.id=:partId and saleOrder.id=:soId")
	public Optional<SaleOrderDetails> getSaleDetailByPartIdAndSaleOrderId(Integer partId, Integer soId);

	@Modifying
	@Query("UPDATE SaleOrderDetails SET qty = qty + :newQty WHERE id=:dtlId")
	public Integer updateSaleOrderDetailQtyByDetailId(Integer newQty, Integer dtlId);

}
/*
Этот интерфейс SaleOrderDetailsRepository определяет репозиторий Spring Data JPA для сущности SaleOrderDetails. Он наследует JpaRepository, обеспечивая базовые операции CRUD для объектов SaleOrderDetails, и дополнительно включает пользовательские методы для выполнения специализированных запросов и операций, связанных с деталями заказов на продажу.

Пользовательские методы:
getSaleDtlsBySaleOrderId: Возвращает список всех деталей заказа на продажу (SaleOrderDetails) для заданного идентификатора заказа (poId). Этот метод удобен для получения всех деталей, связанных с конкретным заказом на продажу.

getSaleDtlsCountBySaleOrderId: Считает количество деталей заказа на продажу для заданного идентификатора заказа (soId). Этот метод может использоваться для проверки наличия деталей в заказе на продажу или для получения общего количества деталей для отчетов и анализа.

getSaleDetailByPartIdAndSaleOrderId: Извлекает конкретную деталь заказа на продажу по идентификатору части (partId) и идентификатору заказа (soId). Возвращаемый тип Optional<SaleOrderDetails> позволяет безопасно обрабатывать случаи, когда деталь не найдена.

updateSaleOrderDetailQtyByDetailId: Обновляет количество (qty) для детали заказа на продажу, увеличивая его на заданное значение (newQty), по идентификатору детали (dtlId). Метод применяется для корректировки количества товара в деталях заказа, например, при изменении заказа клиентом или при возврате товара.

Особенности и преимущества:
Использование JPQL: Все пользовательские методы используют JPQL для формулировки запросов, что позволяет работать с объектной моделью, а не с прямыми запросами к базе данных.

Аннотация @Query: Определяет JPQL запросы прямо в аннотациях, предоставляя гибкость для создания запросов, которые не поддерживаются стандартными методами Spring Data JPA.

Аннотация @Modifying: Указывает, что метод выполняет модификацию данных (например, обновление), что требует выполнения в транзакционном контексте.

Безопасность типов: Использование Optional для методов, которые могут не найти запрашиваемые данные, помогает избежать NullPointerException и обеспечивает более чистый и безопасный код.

Эти методы обеспечивают не только базовую поддержку CRUD операций для объектов SaleOrderDetails, но и расширенные возможности для управления данными деталей заказов на продажу, включая получение, подсчет и обновление этих деталей, что является ключевым для бизнес-логики приложения, связанного с продажами и управлением заказами.
*/
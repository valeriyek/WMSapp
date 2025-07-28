package ru.demo.wms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.PurchaseDtl;

public interface PurchaseDtlRepository 
	extends JpaRepository<PurchaseDtl, Integer> {

	@Query("SELECT pdtl FROM PurchaseDtl pdtl JOIN pdtl.po as purchaseOrder WHERE purchaseOrder.id=:poId")
	public List<PurchaseDtl> getPurchaseDtlsByPoId(Integer poId);
	
	
	@Query("SELECT count(pdtl) FROM PurchaseDtl pdtl JOIN pdtl.po as purchaseOrder WHERE purchaseOrder.id=:poId")
	public Integer getPurchaseDtlsCountByPoId(Integer poId);
	
	@Query("SELECT dtl FROM PurchaseDtl  dtl JOIN dtl.part as part JOIN dtl.po  as po WHERE part.id=:partId and po.id=:poId")
	public Optional<PurchaseDtl> getPurchaseDtlByPartIdAndPoId(Integer partId, Integer poId);
	
	@Modifying
	@Query("UPDATE PurchaseDtl SET qty = qty + :newQty WHERE id=:dtlId")
	public Integer updatePurchaseDtlQtyByDtlId(Integer newQty,Integer dtlId);
}
/*


Этот интерфейс PurchaseDtlRepository представляет собой репозиторий Spring Data JPA для сущности PurchaseDtl. Он расширяет JpaRepository, предоставляя базовый набор операций CRUD для объектов PurchaseDtl, а также включает в себя дополнительные методы, определенные для выполнения конкретных запросов, связанных с деталями покупки.

Определенные методы:
getPurchaseDtlsByPoId: Извлекает все детали покупки (PurchaseDtl) для заданного идентификатора заказа на покупку (poId). Этот метод возвращает список объектов PurchaseDtl, связанных с определенным заказом.

getPurchaseDtlsCountByPoId: Возвращает количество деталей покупки, связанных с конкретным заказом на покупку, по его идентификатору (poId). Этот метод полезен для подсчета общего количества деталей, связанных с заказом.

getPurchaseDtlByPartIdAndPoId: Находит конкретную деталь покупки (PurchaseDtl) по идентификатору части (partId) и идентификатору заказа на покупку (poId). Этот метод возвращает Optional<PurchaseDtl>, что позволяет безопасно обрабатывать ситуации, когда деталь не найдена.

updatePurchaseDtlQtyByDtlId: Обновляет количество (qty) для детали покупки (PurchaseDtl) по её идентификатору (dtlId). Метод предназначен для изменения количества конкретной детали, что может быть необходимо при корректировке заказов.

Особенности:
Использование JPQL: Все пользовательские методы определены с использованием Java Persistence Query Language (JPQL), что позволяет формулировать запросы к объектной модели приложения.

Аннотация @Query: Предоставляет возможность напрямую определить запрос JPQL в аннотации, что обеспечивает большую гибкость при создании запросов, не поддерживаемых стандартными методами Spring Data JPA.

Аннотация @Modifying: Используется в методе updatePurchaseDtlQtyByDtlId для указания, что запрос должен быть выполнен как модифицирующий, изменяющий данные в базе данных.

Преимущества:
Уменьшение количества шаблонного кода: Наследование JpaRepository позволяет избежать написания стандартных методов доступа к данным.

Гибкость в выполнении запросов: Определение пользовательских запросов через аннотацию @Query дает возможность выполнить практически любой запрос к базе данных, используя объектную модель.

Простота в обновлении данных: Использование @Modifying в сочетании с @Query упрощает обновление данных в базе без необходимости извлекать объект, модифицировать его и сохранять обратно.

Этот репозиторий является ключевым компонентом для работы с деталями покупок в системе управления логистикой, обеспечивая эффективный и гибкий доступ к данным.
*/
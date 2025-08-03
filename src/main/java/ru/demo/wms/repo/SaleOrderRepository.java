package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.demo.wms.model.SaleOrder;

@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, Integer> {



	@Query("SELECT count(orderCode) FROM SaleOrder WHERE orderCode=:code")
	Integer validateOrderCode(String code);

	@Query("SELECT count(orderCode) FROM SaleOrder WHERE orderCode=:code AND id!=:id")
	Integer validateOrderCodeAndId(String code, Integer id);


	@Query("SELECT status FROM SaleOrder WHERE id=:soId")
	String getCurrentStatusOfSaleOrder(Integer soId);

	@Modifying
	@Query("UPDATE SaleOrder SET status=:newStatus WHERE id=:soId")
	void updateSaleOrderStatus(Integer soId, String newStatus);


	@Query("SELECT id,orderCode FROM SaleOrder WHERE status=:status")
	List<Object[]> findSaleOrderIdAndCodeByStatus(String status);

}
/*Этот интерфейс SaleOrderRepository является репозиторием Spring Data JPA для сущности SaleOrder
и предназначен для управления заказами на продажу в системе. Он расширяет интерфейс JpaRepository,
добавляя к базовым операциям CRUD специфические методы, которые обеспечивают дополнительную функциональность
для работы с заказами на продажу.

Специфические методы:
validateOrderCode: Проверяет, существует ли заказ на продажу с заданным кодом (code). Этот метод используется для проверки уникальности кода заказа при создании нового заказа, чтобы избежать дублирования.

validateOrderCodeAndId: Аналогичен методу validateOrderCode, но принимает дополнительный параметр id для исключения текущего заказа из проверки. Это полезно при редактировании существующего заказа, позволяя изменить его данные, не нарушая уникальности кода среди других заказов.

getCurrentStatusOfSaleOrder: Возвращает текущий статус заказа на продажу по его идентификатору (soId). Этот метод может быть использован для проверки статуса заказа перед выполнением определённых операций, зависящих от статуса.

updateSaleOrderStatus: Обновляет статус заказа на продажу по его идентификатору (soId). Использование аннотации @Modifying указывает, что метод изменяет данные, и для его выполнения могут потребоваться дополнительные настройки транзакций.

findSaleOrderIdAndCodeByStatus: Извлекает идентификаторы и коды заказов на продажу, которые соответствуют определенному статусу (status). Этот метод полезен для интеграции или создания отчетов, где требуется отобразить информацию о заказах определенного статуса.

Основные характеристики и преимущества:
Прямая интеграция с Spring Framework: Все репозитории Spring Data JPA тесно интегрированы с другими частями Spring, обеспечивая удобную работу с транзакциями, кешированием и безопасностью.

Автоматическая реализация репозитория: Spring Data JPA автоматически создает реализацию репозитория во время выполнения, что значительно сокращает количество шаблонного кода.

Гибкость в определении запросов: Аннотация @Query позволяет определять пользовательские запросы JPQL или SQL напрямую в интерфейсе репозитория, обеспечивая высокую степень гибкости для выполнения сложных запросов.

Безопасность типов и удобство использования: Использование Spring Data JPA способствует соблюдению принципов безопасности типов и упрощает работу с данными за счет встроенных методов и возможностей расширения.

SaleOrderRepository обеспечивает мощный и гибкий инструмент для работы с заказами на продажу, позволяя разработчикам сосредоточиться на бизнес-логике, а не на реализации механизмов доступа к данным.*/
package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer>
{


	@Query("SELECT count(orderCode) FROM PurchaseOrder WHERE orderCode=:orderCode")
	Integer getOrderCodeCount(String orderCode);
	

	@Query("SELECT count(orderCode) FROM PurchaseOrder WHERE orderCode=:orderCode AND id!=:id")
	Integer getOrderCodeCountForEdit(String orderCode,Integer id);
	

	@Query("SELECT status FROM PurchaseOrder WHERE id=:poId")
	String getCurrentStatusOfPo(Integer poId);
	
	@Modifying
	@Query("UPDATE PurchaseOrder SET status=:newStatus WHERE id=:poId")
	void updatePoStatus(Integer poId,String newStatus);
	
	@Query("SELECT id,orderCode FROM PurchaseOrder WHERE status=:status")
	List<Object[]> getPoIdAndCodesByStatus(String status);
	
}
/*
Этот интерфейс PurchaseOrderRepository представляет собой репозиторий Spring Data JPA для сущности PurchaseOrder. Он предлагает стандартные операции CRUD, предоставляемые его расширением JpaRepository, а также включает дополнительные пользовательские методы для выполнения специфических операций, связанных с заказами на покупку.

Пользовательские методы:
getOrderCodeCount: Проверяет, существует ли уже заказ на покупку с заданным кодом orderCode. Этот метод полезен при добавлении нового заказа на покупку для обеспечения уникальности его кода.

getOrderCodeCountForEdit: Аналогично getOrderCodeCount, но используется при редактировании существующего заказа на покупку, исключая из проверки текущий заказ по id. Это позволяет обновлять заказ, не изменяя его код, и гарантирует, что код остается уникальным среди всех других заказов.

getCurrentStatusOfPo: Возвращает текущий статус заказа на покупку по его идентификатору poId. Этот метод может быть использован для проверки статуса заказа перед выполнением действий, зависящих от статуса.

updatePoStatus: Обновляет статус заказа на покупку PurchaseOrder с заданным идентификатором poId на новый статус newStatus. Метод модифицирует данные в базе, что указывается аннотацией @Modifying.

getPoIdAndCodesByStatus: Извлекает идентификаторы и коды заказов на покупку, которые соответствуют определенному статусу status. Это может быть полезно для отображения списков заказов в зависимости от их статуса.

Особенности и преимущества:
Использование JPQL: Методы определены с использованием Java Persistence Query Language (JPQL), что позволяет формулировать запросы, ориентированные на объектную модель, а не на конкретную структуру базы данных.

Аннотация @Query: Определяет пользовательские запросы JPQL непосредственно в аннотациях, обеспечивая гибкость в создании запросов, которые не поддерживаются стандартными методами Spring Data JPA.

Аннотация @Modifying: Используется для указания, что запрос должен быть выполнен как модифицирующий операцию, изменяющую данные в базе данных, что особенно важно для методов обновления.

Уменьшение количества шаблонного кода: Наследование JpaRepository избавляет от необходимости реализации многих стандартных методов доступа к данным, что упрощает разработку.

Гибкость в выполнении запросов: Создание пользовательских запросов с помощью @Query дает возможность выполнить практически любой запрос к базе данных, используя объектную модель, что облегчает выполнение специфических для бизнеса операций.

Этот репозиторий является ключевым компонентом для управления заказами на покупку в системе, обеспечивая эффективное взаимодействие с базой данных и поддерживая бизнес-логику, связанную с уникальностью кодов заказов, их статусами и выборкой по различным критериям.
*/
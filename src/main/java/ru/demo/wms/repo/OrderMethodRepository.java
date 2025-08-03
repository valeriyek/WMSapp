package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.OrderMethod;

public interface OrderMethodRepository 
extends JpaRepository<OrderMethod, Integer> {

	@Query("SELECT COUNT(orderCode) FROM OrderMethod WHERE orderCode=:code")
	Integer isOrderMethodCodeExist(String code);

	@Query("SELECT COUNT(orderCode) FROM OrderMethod WHERE orderCode=:code AND id!=:id")
	Integer isOrderMethodCodeExistForEdit(String code, Integer id);

	@Query("SELECT orderMode, COUNT(orderMode) FROM OrderMethod GROUP BY orderMode")
	List<Object[]> getOrderMethodModeAndCount();

	@Query("SELECT id, orderCode FROM OrderMethod ")
	List<Object[]> getOrderMethodIdAndCode();
	
	
}
/*
Этот интерфейс OrderMethodRepository является репозиторием Spring Data JPA для сущности OrderMethod и расширяет JpaRepository, предоставляя базовый набор операций CRUD и методов для работы с данными. Кроме стандартных операций, в OrderMethodRepository определено несколько специфических методов с использованием аннотации @Query для выполнения конкретных запросов к базе данных.

Определенные методы:
isOrderMethodCodeExist: Проверяет, существует ли уже метод заказа с данным кодом orderCode в базе данных. Этот метод полезен при добавлении нового метода заказа для обеспечения уникальности кода.

isOrderMethodCodeExistForEdit: Похож на isOrderMethodCodeExist, но используется при редактировании существующего метода заказа, исключая из проверки метод с заданным идентификатором id. Это позволяет обновлять данные метода заказа, не изменяя его код, и обеспечивает, что код остается уникальным среди всех других методов.

getOrderMethodModeAndCount: Для отчетов и аналитики, этот метод возвращает количество методов заказа, сгруппированных по режиму заказа (orderMode). Это может быть полезно для визуализации распределения методов заказа, например, в виде диаграммы.

getOrderMethodIdAndCode: Возвращает идентификатор и код каждого метода заказа, что может быть использовано для интеграционных целей, например, при связывании различных частей системы или при экспорте данных.

Основные характеристики:
Использование JPQL: Все методы определены с помощью Java Persistence Query Language (JPQL), что позволяет формулировать запросы к объектной модели, не привязываясь к конкретной структуре базы данных.

Аннотация @Query: Специфицирует запросы JPQL непосредственно в аннотации, что обеспечивает читаемость и упрощает поддержку кода.

Преимущества:
Гибкость: Пользовательские запросы позволяют реализовать сложную логику выборки и обновления данных, которая может быть неосуществима с помощью стандартных методов Spring Data.

Простота интеграции: Методы, такие как getOrderMethodIdAndCode, облегчают интеграцию данных между различными частями приложения или с внешними системами.

Улучшенная поддержка отчетности и аналитики: Методы, предназначенные для генерации данных для отчетов и аналитики (например, getOrderMethodModeAndCount), облегчают создание информативных представлений данных, что важно для принятия обоснованных бизнес-решений.
*/
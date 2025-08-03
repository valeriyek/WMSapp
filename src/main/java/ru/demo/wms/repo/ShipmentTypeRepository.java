package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.ShipmentType;

public interface ShipmentTypeRepository
	extends JpaRepository<ShipmentType, Integer>
{

	//Register check
	@Query("SELECT count(shipCode) from ShipmentType where shipCode=:code")
	Integer getShipmentTypeCodeCount(String code);
	
	//Edit check
	@Query("SELECT count(shipCode) from ShipmentType where shipCode=:code and id!=:id")
	Integer getShipmentTypeCodeCountForEdit(String code,Integer id);
	
	//For Charts Data
	@Query("SELECT shipMode, count(shipMode) FROM ShipmentType GROUP BY shipMode")
	List<Object[]> getShipmentTypeModeAndCount();
	
	//for Dynamic DropDown
	@Query("SELECT id, shipCode FROM ShipmentType WHERE enbleShip=:enable")
	List<Object[]> getShipmentIdAndCodeByEnable(String enable);
	
	
}
/*
Этот интерфейс ShipmentTypeRepository служит репозиторием Spring Data JPA для сущности ShipmentType и предлагает базовые операции CRUD, а также несколько специализированных методов для выполнения конкретных бизнес-задач. Эти методы расширяют стандартные возможности, предоставляемые JpaRepository, обеспечивая гибкость и мощные инструменты для управления типами отправлений.

Специализированные методы:
getShipmentTypeCodeCount: Проверяет количество типов отправлений с заданным кодом (code). Этот метод используется для проверки уникальности кода типа отправления при его создании.

getShipmentTypeCodeCountForEdit: Аналогичен предыдущему методу, но применяется при редактировании существующего типа отправления для обеспечения уникальности кода, исключая текущий редактируемый тип из проверки по id.

getShipmentTypeModeAndCount: Собирает данные для генерации диаграмм и отчетов, возвращая количество типов отправлений, сгруппированных по режиму отправления (shipMode).

getShipmentIdAndCodeByEnable: Возвращает идентификаторы и коды типов отправлений, которые активированы (enable). Этот метод может быть использован для заполнения динамических выпадающих списков в пользовательском интерфейсе.

Ключевые особенности и преимущества:
Гибкость запросов: Использование аннотации @Query позволяет определить пользовательские запросы на JPQL, обеспечивая точный и гибкий доступ к данным.

Минимизация шаблонного кода: Наследование от JpaRepository уменьшает необходимость в ручном написании кода для стандартных операций с данными, таких как сохранение, поиск, обновление и удаление объектов.

Удобство проверок уникальности: Методы getShipmentTypeCodeCount и getShipmentTypeCodeCountForEdit облегчают реализацию логики проверки уникальности, что является общей задачей при работе с формами регистрации и редактирования.

Поддержка сложных сценариев отображения данных: Метод getShipmentTypeModeAndCount предоставляет данные, необходимые для визуализации статистики типов отправлений, что может быть полезно для аналитических целей и отчетности.

Динамическое формирование интерфейса: Метод getShipmentIdAndCodeByEnable упрощает создание динамических выпадающих списков на основе данных о доступных типах отправлений, что повышает удобство работы пользователя с системой.

ShipmentTypeRepository является важным компонентом системы управления логистикой, обеспечивая эффективное и гибкое взаимодействие с данными о типах отправлений.
*/
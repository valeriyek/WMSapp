package ru.demo.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.demo.wms.model.ShipingDtl;

@Repository
public interface ShipingDetailRepository extends JpaRepository<ShipingDtl, Integer> {

	@Modifying
	@Query("UPDATE ShipingDtl SET status=:status WHERE id=:id")
	void updateShipingDtlStatus(Integer id, String status);

}
/*
Этот интерфейс ShipingDetailRepository представляет собой репозиторий Spring Data JPA для сущности ShipingDtl, предназначенный для управления деталями отправки товаров. Он расширяет JpaRepository, предоставляя базовые операции CRUD для объектов ShipingDtl, и включает пользовательский метод для обновления статуса отправки.

Метод:
updateShipingDtlStatus: Этот метод предназначен для обновления статуса отправки (ShipingDtl) по его идентификатору (id). Аннотация @Modifying указывает, что метод изменяет данные, что требует выполнения в контексте транзакции. Параметр status определяет новое значение статуса для отправки.
Особенности и преимущества использования:
Автоматическая реализация репозитория: Spring Data JPA обеспечивает автоматическую реализацию методов репозитория, что значительно сокращает количество необходимого для написания шаблонного кода.

Интеграция с Spring Framework: Репозитории Spring Data JPA тесно интегрированы с другими компонентами Spring, такими как транзакции и безопасность, предоставляя разработчикам удобные и мощные инструменты для работы с данными.

Упрощение работы с данными: Использование аннотаций, таких как @Query и @Modifying, позволяет определять сложные запросы и операции обновления непосредственно в интерфейсе репозитория, уменьшая необходимость в написании и поддержке дополнительного кода для доступа к базе данных.

Гибкость в определении запросов: @Query дает возможность использовать JPQL (Java Persistence Query Language) для формулировки запросов, что обеспечивает более высокую степень гибкости и контроля над операциями с данными по сравнению с стандартными методами репозитория.

Использование ShipingDetailRepository облегчает разработку приложений, связанных с логистикой и отправкой товаров, предоставляя эффективные и простые в использовании инструменты для управления статусами отправки и обработки данных о отправках.
*/
package ru.demo.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.demo.wms.model.Shiping;

/**
 * Репозиторий для работы с сущностью {@link Shiping}.
 * Предоставляет стандартные CRUD-операции и интеграцию с Spring Data JPA.
 */
@Repository
public interface ShipingRepository extends JpaRepository<Shiping, Integer> {
    // Дополнительные методы можно определить при необходимости
}

package ru.demo.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.demo.wms.model.Grn;

/**
 * Репозиторий для работы с сущностью Grn.
 * <p>
 * Наследует базовые CRUD-операции от JpaRepository:
 * сохранение, удаление, поиск по ID, получение всех записей и т.д.
 * </p>
 */
public interface GrnRepository extends JpaRepository<Grn, Integer> {
}

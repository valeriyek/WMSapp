package ru.demo.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.demo.wms.model.ShipingDtl;

/**
 * Репозиторий для работы с деталями отправки {@link ShipingDtl}.
 * Расширяет JpaRepository и содержит пользовательский метод обновления статуса.
 */
@Repository
public interface ShipingDetailRepository extends JpaRepository<ShipingDtl, Integer> {

	/**
	 * Обновляет статус детали отправки по идентификатору.
	 *
	 * @param id     идентификатор записи
	 * @param status новый статус
	 */
	@Modifying
	@Query("UPDATE ShipingDtl SET status = :status WHERE id = :id")
	void updateShipingDtlStatus(Integer id, String status);
}

package ru.demo.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.model.GrnDtl;

/**
 * Репозиторий для работы с сущностью {@link GrnDtl}.
 * <p>Расширяет {@link JpaRepository} и предоставляет базовые и пользовательские операции
 * для управления деталями GRN (Goods Receipt Note).</p>
 */
public interface GrnDtlRepository extends JpaRepository<GrnDtl, Integer> {

	/**
	 * Обновляет статус записи GRN по её идентификатору.
	 *
	 * @param id     идентификатор детали GRN
	 * @param status новый статус, который будет установлен
	 */
	@Modifying
	@Transactional
	@Query("UPDATE GrnDtl SET status = :status WHERE id = :id")
	void updateGrnDtlStatus(Integer id, String status);
}

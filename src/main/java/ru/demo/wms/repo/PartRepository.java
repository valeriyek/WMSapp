package ru.demo.wms.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.demo.wms.model.Part;

/**
 * Репозиторий для работы с сущностью {@link Part}.
 *
 * Предоставляет стандартные CRUD-операции и дополнительный метод
 * для выборки ID и кода деталей.
 */
public interface PartRepository extends JpaRepository<Part, Integer> {

	/**
	 * Возвращает список массивов, содержащих ID и код каждой детали.
	 *
	 * @return список массивов вида [id, partCode]
	 */
	@Query("SELECT id, partCode FROM Part")
	List<Object[]> getPartIdAndCode();
}

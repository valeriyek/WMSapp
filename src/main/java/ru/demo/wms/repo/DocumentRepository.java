package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.Document;

/**
 * Репозиторий для работы с сущностью {@link Document}.
 * <p>Предоставляет базовые CRUD-операции, а также расширенные запросы к таблице документов.</p>
 *
 * <p>Наследует {@link JpaRepository}, что позволяет использовать готовые методы для поиска, сохранения,
 * удаления, пагинации и сортировки сущностей Document.</p>
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {

	/**
	 * Пользовательский JPQL-запрос для выборки только идентификаторов и названий документов.
	 *
	 * @return список массивов объектов, содержащих {@code docId} и {@code docName} каждого документа.
	 */
	@Query("SELECT docId, docName FROM Document")
	List<Object[]> getDocumentIdAndName();

}

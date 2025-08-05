package ru.demo.wms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.model.Document;
import ru.demo.wms.repo.DocumentRepository;
import ru.demo.wms.service.IDocumentService;

/**
 * Сервисный класс, реализующий бизнес-логику управления документами.
 * <p>
 * Предоставляет методы для сохранения, удаления и получения документов из базы данных.
 */
@Service
public class DocumentServiceImpl implements IDocumentService {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentServiceImpl.class);

	@Autowired
	private DocumentRepository repo;

	/**
	 * Сохраняет документ в базе данных.
	 *
	 * @param doc объект {@link Document} для сохранения
	 */
	@Override
	public void saveDocument(Document doc) {
		LOG.info("Сохраняется документ: {}", doc.getDocName());
		repo.save(doc);
		LOG.info("Документ успешно сохранён.");
	}

	/**
	 * Возвращает список ID и имён всех документов.
	 * Используется, например, для выпадающих списков.
	 *
	 * @return список массивов объектов, содержащих ID и имя документа
	 */
	@Override
	public List<Object[]> getDocumentIdAndName() {
		LOG.info("Получение ID и имён всех документов");
		return repo.getDocumentIdAndName();
	}

	/**
	 * Удаляет документ по ID.
	 *
	 * @param id идентификатор документа
	 * @throws RuntimeException если документ не существует
	 */
	@Override
	public void deleteDocumentById(Long id) {
		LOG.info("Попытка удалить документ с ID = {}", id);
		if (repo.existsById(id)) {
			repo.deleteById(id);
			LOG.info("Документ с ID = {} успешно удалён.", id);
		} else {
			LOG.warn("Документ с ID = {} не существует.", id);
			throw new RuntimeException("Документ с ID " + id + " не существует.");
		}
	}

	/**
	 * Возвращает документ по ID.
	 *
	 * @param id идентификатор документа
	 * @return найденный объект {@link Document}
	 * @throws RuntimeException если документ не найден
	 */
	@Override
	public Document getDocumentById(Long id) {
		LOG.info("Поиск документа по ID = {}", id);
		return repo.findById(id)
				.orElseThrow(() -> {
					LOG.error("Документ с ID = {} не найден.", id);
					return new RuntimeException("Документ с ID " + id + " не найден.");
				});
	}
}

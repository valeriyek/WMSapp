package ru.demo.wms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.exception.DataNotFoundException;
import ru.demo.wms.model.Grn;
import ru.demo.wms.repo.GrnDtlRepository;
import ru.demo.wms.repo.GrnRepository;
import ru.demo.wms.service.IGrnService;

/**
 * Сервисный класс, реализующий бизнес-логику для работы с поступлениями товаров (GRN).
 * <p>
 * Обеспечивает операции по сохранению, получению и обновлению записей о поступлениях.
 */
@Service
public class GrnServiceImpl implements IGrnService {

	private static final Logger LOG = LoggerFactory.getLogger(GrnServiceImpl.class);

	@Autowired
	private GrnRepository repo;

	@Autowired
	private GrnDtlRepository dtlRepo;

	/**
	 * Сохраняет новую запись GRN в базу данных.
	 *
	 * @param grn объект поступления
	 * @return идентификатор сохранённой записи
	 */
	@Override
	public Integer saveGrn(Grn grn) {
		LOG.info("Сохраняется новая запись GRN");
		Integer id = repo.save(grn).getId();
		LOG.debug("GRN сохранён с ID: {}", id);
		return id;
	}

	/**
	 * Возвращает список всех поступлений товаров (GRN).
	 *
	 * @return список объектов {@link Grn}
	 */
	@Override
	public List<Grn> fetchAllGrns() {
		LOG.info("Получение списка всех GRN");
		return repo.findAll();
	}

	/**
	 * Возвращает одну запись GRN по её идентификатору.
	 *
	 * @param id идентификатор GRN
	 * @return объект {@link Grn}
	 * @throws DataNotFoundException если GRN не найден
	 */
	@Override
	public Grn getOneGrn(Integer id) {
		LOG.info("Поиск GRN с ID: {}", id);
		return repo.findById(id)
				.orElseThrow(() -> {
					LOG.error("GRN с ID {} не найден", id);
					return new DataNotFoundException("GRN с ID " + id + " не найден");
				});
	}

	/**
	 * Обновляет статус деталей GRN по ID.
	 * <p>
	 * Метод выполняется в рамках транзакции.
	 *
	 * @param id     идентификатор GRN
	 * @param status новый статус
	 */
	@Override
	@Transactional
	public void updateGrnDtlStatus(Integer id, String status) {
		LOG.info("Обновление статуса деталей GRN. ID: {}, статус: {}", id, status);
		dtlRepo.updateGrnDtlStatus(id, status);
		LOG.debug("Статус деталей GRN успешно обновлён.");
	}
}

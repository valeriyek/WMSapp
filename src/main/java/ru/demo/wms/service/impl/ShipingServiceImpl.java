package ru.demo.wms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.exception.ShipingNotFoundException;
import ru.demo.wms.model.Shiping;
import ru.demo.wms.repo.ShipingDetailRepository;
import ru.demo.wms.repo.ShipingRepository;
import ru.demo.wms.service.IShipingService;

/**
 * Сервис для управления отправками (Shiping).
 * Реализует бизнес-логику, связанную с созданием, получением и обновлением отправок и их деталей.
 */
@Service
public class ShipingServiceImpl implements IShipingService {

	@Autowired
	private ShipingRepository repository;

	@Autowired
	private ShipingDetailRepository dtlRepository;

	/**
	 * Сохраняет новую отправку в базу данных.
	 *
	 * @param shiping объект отправки
	 * @return ID сохранённой отправки
	 */
	@Override
	public Integer saveShiping(Shiping shiping) {
		return repository.save(shiping).getId();
	}

	/**
	 * Получает список всех отправок.
	 *
	 * @return список отправок
	 */
	@Override
	public List<Shiping> getAllShiping() {
		return repository.findAll();
	}

	/**
	 * Получает одну отправку по её ID.
	 *
	 * @param id идентификатор отправки
	 * @return объект отправки
	 * @throws ShipingNotFoundException если отправка с указанным ID не найдена
	 */
	@Override
	public Shiping getOneShiping(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ShipingNotFoundException("Shiping Not Exist: ID = " + id));
	}

	/**
	 * Обновляет статус детали отправки по её ID.
	 * Метод помечен как транзакционный для обеспечения атомарности обновления.
	 *
	 * @param id     идентификатор детали отправки
	 * @param status новый статус
	 */
	@Override
	@Transactional
	public void updateShipingDtlStatus(Integer id, String status) {
		dtlRepository.updateShipingDtlStatus(id, status);
	}
}

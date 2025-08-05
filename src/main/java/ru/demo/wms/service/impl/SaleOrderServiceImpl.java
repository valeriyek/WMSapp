package ru.demo.wms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.exception.SaleOrderNotFoundException;
import ru.demo.wms.model.SaleOrder;
import ru.demo.wms.model.SaleOrderDetails;
import ru.demo.wms.repo.SaleOrderDetailsRepository;
import ru.demo.wms.repo.SaleOrderRepository;
import ru.demo.wms.service.ISaleOrderService;

/**
 * Реализация сервиса управления заказами на продажу.
 * Предоставляет функциональность по созданию, чтению, обновлению, удалению
 * и управлению статусами заказов и их деталей.
 */
@Service
public class SaleOrderServiceImpl implements ISaleOrderService {

	@Autowired
	private SaleOrderRepository repository;

	@Autowired
	private SaleOrderDetailsRepository saleRepository;

	/**
	 * Сохраняет новый заказ на продажу.
	 *
	 * @param saleOrder объект заказа
	 * @return ID сохранённого заказа
	 */
	@Override
	public Integer saveSaleOrder(SaleOrder saleOrder) {
		return repository.save(saleOrder).getId();
	}

	/**
	 * Возвращает список всех заказов на продажу.
	 *
	 * @return список заказов
	 */
	@Override
	public List<SaleOrder> getAllSaleOrder() {
		return repository.findAll();
	}

	/**
	 * Получает заказ по ID.
	 *
	 * @param id идентификатор заказа
	 * @return объект заказа
	 * @throws SaleOrderNotFoundException если заказ не найден
	 */
	@Override
	public SaleOrder getOneSaleOrder(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new SaleOrderNotFoundException("Sale Order not found"));
	}

	/**
	 * Проверяет, существует ли заказ с указанным кодом.
	 *
	 * @param code код заказа
	 * @return true, если заказ существует
	 */
	@Override
	public boolean validateOrderCode(String code) {
		return repository.validateOrderCode(code) > 0;
	}

	/**
	 * Проверяет, существует ли заказ с таким кодом, исключая заказ с указанным ID.
	 * Используется при редактировании.
	 *
	 * @param code код заказа
	 * @param id   ID текущего редактируемого заказа
	 * @return true, если другой заказ с таким кодом существует
	 */
	@Override
	public boolean validateOrderCodeAndId(String code, Integer id) {
		return repository.validateOrderCodeAndId(code, id) > 0;
	}

	/**
	 * Сохраняет деталь заказа на продажу.
	 *
	 * @param saleOrderDetails объект детали
	 * @return ID сохранённой детали
	 */
	@Override
	public Integer savePurchaseDetails(SaleOrderDetails saleOrderDetails) {
		return saleRepository.save(saleOrderDetails).getId();
	}

	/**
	 * Возвращает все детали для указанного заказа.
	 *
	 * @param id ID заказа
	 * @return список деталей
	 */
	@Override
	public List<SaleOrderDetails> getSaleDtlsBySaleOrderId(Integer id) {
		return saleRepository.getSaleDtlsBySaleOrderId(id);
	}

	/**
	 * Удаляет деталь по ID, если она существует.
	 *
	 * @param detailId ID детали
	 */
	@Override
	public void deleteSaleDetails(Integer detailId) {
		if (saleRepository.existsById(detailId)) {
			saleRepository.deleteById(detailId);
		}
	}

	/**
	 * Получает текущий статус заказа.
	 *
	 * @param soId ID заказа
	 * @return строка со статусом
	 */
	@Override
	public String getCurrentStatusOfSaleOrder(Integer soId) {
		return repository.getCurrentStatusOfSaleOrder(soId);
	}

	/**
	 * Обновляет статус заказа. Выполняется в рамках транзакции.
	 *
	 * @param soId      ID заказа
	 * @param newStatus новый статус
	 */
	@Override
	@Transactional
	public void updateSaleOrderStatus(Integer soId, String newStatus) {
		repository.updateSaleOrderStatus(soId, newStatus);
	}

	/**
	 * Возвращает количество деталей, связанных с заказом.
	 *
	 * @param soId ID заказа
	 * @return количество деталей
	 */
	@Override
	public Integer getSaleDtlsCountBySaleOrderId(Integer soId) {
		return saleRepository.getSaleDtlsCountBySaleOrderId(soId);
	}

	/**
	 * Получает деталь заказа по ID детали и ID заказа.
	 *
	 * @param partId ID запчасти
	 * @param soId   ID заказа
	 * @return Optional с найденной деталью
	 */
	@Override
	public Optional<SaleOrderDetails> getSaleDetailByPartIdAndSaleOrderId(Integer partId, Integer soId) {
		return saleRepository.getSaleDetailByPartIdAndSaleOrderId(partId, soId);
	}

	/**
	 * Обновляет количество в детали заказа. Выполняется в рамках транзакции.
	 *
	 * @param newQty новое количество
	 * @param dtlId  ID детали
	 * @return количество обновлённых записей (обычно 1)
	 */
	@Override
	@Transactional
	public Integer updateSaleOrderDetailQtyByDetailId(Integer newQty, Integer dtlId) {
		return saleRepository.updateSaleOrderDetailQtyByDetailId(newQty, dtlId);
	}

	/**
	 * Возвращает карту ID → код заказа по заданному статусу.
	 *
	 * @param status статус заказа
	 * @return карта ID заказа и его кода
	 */
	@Override
	public Map<Integer, String> findSaleOrderIdAndCodeByStatus(String status) {
		List<Object[]> list = repository.findSaleOrderIdAndCodeByStatus(status);
		Map<Integer, String> map = new HashMap<>();
		for (Object[] obj : list) {
			map.put((Integer) obj[0], (String) obj[1]);
		}
		return map;
	}
}

package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.model.PurchaseDtl;
import ru.demo.wms.model.PurchaseOrder;
import ru.demo.wms.repo.PurchaseDtlRepository;
import ru.demo.wms.repo.PurchaseOrderRepository;
import ru.demo.wms.service.IPurchaseOrderService;
import ru.demo.wms.util.MyAppUtil;

/**
 * Сервис для управления заказами на покупку (PurchaseOrder)
 * и их деталями (PurchaseDtl).
 */
@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private MyAppUtil myAppUtil;

	@Autowired
	private PurchaseOrderRepository repo;

	@Autowired
	private PurchaseDtlRepository dtlRepo;

	/**
	 * Сохраняет новый заказ на покупку.
	 *
	 * @param po объект PurchaseOrder
	 * @return ID сохраненного заказа
	 */
	@Override
	public Integer savePurchaseOrder(PurchaseOrder po) {
		return repo.save(po).getId();
	}

	/**
	 * Возвращает заказ на покупку по ID.
	 *
	 * @param id идентификатор заказа
	 * @return объект PurchaseOrder
	 * @throws RuntimeException если заказ не найден
	 */
	@Override
	public PurchaseOrder getOnePurchaseOrder(Integer id) {
		return repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Purchase Order NOT FOUND"));
	}

	/**
	 * Возвращает список всех заказов на покупку.
	 *
	 * @return список заказов
	 */
	@Override
	public List<PurchaseOrder> getAllPurchaseOrders() {
		return repo.findAll();
	}

	/**
	 * Проверяет, существует ли заказ с таким кодом.
	 *
	 * @param code код заказа
	 * @return true, если существует
	 */
	@Override
	public boolean isPurchaseOrderCodeExist(String code) {
		return repo.getOrderCodeCount(code) > 0;
	}

	/**
	 * Проверяет, существует ли код заказа (исключая текущий ID).
	 *
	 * @param code код заказа
	 * @param id идентификатор, который следует исключить
	 * @return true, если код уже используется
	 */
	@Override
	public boolean isPurchaseOrderCodeExistForEdit(String code, Integer id) {
		return repo.getOrderCodeCountForEdit(code, id) > 0;
	}

	/**
	 * Сохраняет деталь заказа.
	 *
	 * @param pdtl объект детали заказа
	 * @return ID сохраненной детали
	 */
	public Integer savePurchaseDtl(PurchaseDtl pdtl) {
		return dtlRepo.save(pdtl).getId();
	}

	/**
	 * Получает все детали заказа по ID заказа.
	 *
	 * @param id ID заказа
	 * @return список деталей
	 */
	public List<PurchaseDtl> getPurchaseDtlsByPoId(Integer id) {
		return dtlRepo.getPurchaseDtlsByPoId(id);
	}

	/**
	 * Удаляет деталь заказа по ID, если она существует.
	 *
	 * @param dtlId ID детали заказа
	 */
	public void deletePurchaseDtl(Integer dtlId) {
		if (dtlRepo.existsById(dtlId)) {
			dtlRepo.deleteById(dtlId);
		}
	}

	/**
	 * Получает текущий статус заказа по его ID.
	 *
	 * @param poId ID заказа
	 * @return текущий статус
	 */
	public String getCurrentStatusOfPo(Integer poId) {
		return repo.getCurrentStatusOfPo(poId);
	}

	/**
	 * Обновляет статус заказа.
	 *
	 * @param poId ID заказа
	 * @param newStatus новый статус
	 */
	@Transactional
	public void updatePoStatus(Integer poId, String newStatus) {
		repo.updatePoStatus(poId, newStatus);
	}

	/**
	 * Возвращает количество деталей заказа по ID заказа.
	 *
	 * @param poId ID заказа
	 * @return количество деталей
	 */
	public Integer getPurchaseDtlsCountByPoId(Integer poId) {
		return dtlRepo.getPurchaseDtlsCountByPoId(poId);
	}

	/**
	 * Возвращает деталь заказа по ID детали и ID заказа.
	 *
	 * @param partId ID детали
	 * @param poId ID заказа
	 * @return Optional объекта PurchaseDtl
	 */
	public Optional<PurchaseDtl> getPurchaseDtlByPartIdAndPoId(Integer partId, Integer poId) {
		return dtlRepo.getPurchaseDtlByPartIdAndPoId(partId, poId);
	}

	/**
	 * Обновляет количество в детали заказа.
	 *
	 * @param newQty новое количество
	 * @param dtlId ID детали заказа
	 * @return количество обновленных записей
	 */
	@Transactional
	public Integer updatePurchaseDtlQtyByDtlId(Integer newQty, Integer dtlId) {
		return dtlRepo.updatePurchaseDtlQtyByDtlId(newQty, dtlId);
	}

	/**
	 * Возвращает карту заказов по статусу.
	 *
	 * @param status статус заказа
	 * @return карта ID → код заказа
	 */
	public Map<Integer, String> getPoIdAndCodesByStatus(String status) {
		List<Object[]> list = repo.getPoIdAndCodesByStatus(status);
		return myAppUtil.convertListToMap(list);
	}
}

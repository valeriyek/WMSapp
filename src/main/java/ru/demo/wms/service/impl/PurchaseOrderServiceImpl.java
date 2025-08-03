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

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {
@Autowired
	private MyAppUtil myAppUtil;
	@Autowired
	private PurchaseOrderRepository repo;
	
	@Autowired
	private PurchaseDtlRepository dtlRepo;

	@Override
	public Integer savePurchaseOrder(PurchaseOrder po) {
		return repo.save(po).getId();
	}

	@Override
	public PurchaseOrder getOnePurchaseOrder(Integer id) {
		return repo.findById(id).orElseThrow(()->new RuntimeException("Purchase Order NOT FOUND"));
	}

	@Override
	public List<PurchaseOrder> getAllPurchaseOrders() {
		return repo.findAll();
	}

	@Override
	public boolean isPurchaseOrderCodeExist(String code) {
		return repo.getOrderCodeCount(code)>0;
	}

	@Override
	public boolean isPurchaseOrderCodeExistForEdit(String code, Integer id) {
		return repo.getOrderCodeCountForEdit(code, id)>0;
	}

	public Integer savePurchaseDtl(PurchaseDtl pdtl) {
		return dtlRepo.save(pdtl).getId();
	}
	
	public List<PurchaseDtl> getPurchaseDtlsByPoId(Integer id) {
		return dtlRepo.getPurchaseDtlsByPoId(id);
	}
	
	public void deletePurchaseDtl(Integer dtlId) {
		if(dtlRepo.existsById(dtlId)) {
			dtlRepo.deleteById(dtlId);
		}
	}
	
	public String getCurrentStatusOfPo(Integer poId) {
		return repo.getCurrentStatusOfPo(poId);
	}
	
	@Transactional
	public void updatePoStatus(Integer poId, String newStatus) {
		repo.updatePoStatus(poId, newStatus);
	}
	
	public Integer getPurchaseDtlsCountByPoId(Integer poId) {
		return dtlRepo.getPurchaseDtlsCountByPoId(poId);
	}
	
	public Optional<PurchaseDtl> getPurchaseDtlByPartIdAndPoId(Integer partId, Integer poId) {
		return dtlRepo.getPurchaseDtlByPartIdAndPoId(partId, poId);
	}
	@Transactional
	public Integer updatePurchaseDtlQtyByDtlId(Integer newQty, Integer dtlId) {
		return dtlRepo.updatePurchaseDtlQtyByDtlId(newQty, dtlId);
	}
	
	public Map<Integer, String> getPoIdAndCodesByStatus(String status) {
		List<Object[]> list = repo.getPoIdAndCodesByStatus(status);
		return myAppUtil.convertListToMap(list);
	}

}
/*Класс PurchaseOrderServiceImpl реализует интерфейс IPurchaseOrderService, предоставляя комплексные сервисы для управления заказами на покупку (PurchaseOrder) и деталями заказа (PurchaseDtl). Эта реализация включает в себя взаимодействие с двумя репозиториями: PurchaseOrderRepository и PurchaseDtlRepository, что демонстрирует сложную логику взаимодействия с данными в приложении. Рассмотрим ключевые аспекты этого класса и потенциальные улучшения:

Внедрение зависимостей
Используются @Autowired для внедрения репозиториев, позволяя легко взаимодействовать с базой данных для выполнения операций над заказами на покупку и их деталями.

Методы сервиса
CRUD операции: Реализованы методы для создания, получения и получения всех заказов на покупку. Включена также проверка на существование кода заказа на покупку, что важно для поддержания уникальности кодов.
Управление деталями заказа: Реализованы методы для работы с деталями заказа, включая создание, удаление и получение деталей по идентификатору заказа.
Обновление статуса заказа и деталей заказа: Реализованы методы для обновления статуса заказа и количества в деталях заказа, демонстрируя использование аннотации @Transactional для обеспечения целостности данных при выполнении этих операций.
Рекомендации по улучшению
Использование специализированных исключений: Вместо RuntimeException лучше использовать специализированные исключения, такие как PurchaseOrderNotFoundException, что улучшит обработку ошибок и информативность сообщений об ошибках.
Логирование: Добавление логирования выполнения операций поможет в отладке и мониторинге сервиса. Желательно использовать разные уровни логирования (DEBUG, INFO, ERROR) для различных сценариев.
Валидация данных: Прежде чем выполнять операции с данными, стоит добавить валидацию входящих данных на уровне сервиса, чтобы избежать сохранения некорректных данных.
Оптимизация работы с базой данных: В некоторых сценариях может быть полезно рассмотреть возможность оптимизации запросов к базе данных, особенно если приложение сталкивается с проблемами производительности из-за большого объема данных или сложности запросов.
Использование DTO и маппинга: Рассмотрите возможность применения DTO (Data Transfer Objects) для слоя сервисов, чтобы отделять внутреннюю модель данных приложения от данных, передаваемых в запросах и ответах API.
PurchaseOrderServiceImpl обеспечивает фундаментальные операции для управления заказами на покупку и их деталями в приложении. Применение предложенных улучшений может сделать сервис более надежным, удобным в обслуживании и готовым к масштабированию.*/
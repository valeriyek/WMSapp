package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.exception.SaleOrderNotFoundException;
import ru.demo.wms.model.SaleOrder;
import ru.demo.wms.model.SaleOrderDetails;
import ru.demo.wms.repo.SaleOrderDetailsRepository;
import ru.demo.wms.repo.SaleOrderRepository;
import ru.demo.wms.service.ISaleOrderService;

@Service
public class SaleOrderServiceImpl implements ISaleOrderService {

	@Autowired
	private SaleOrderRepository repository;

	@Autowired
	private SaleOrderDetailsRepository saleRepository;


	@Override
	public Integer saveSaleOrder(SaleOrder saleOrder) {
		return repository.save(saleOrder).getId();
	}

	@Override
	public List<SaleOrder> getAllSaleOrder() {
		return repository.findAll();
	}

	@Override
	public SaleOrder getOneSaleOrder(Integer id) {

		return repository.findById(id).orElseThrow(() -> new SaleOrderNotFoundException("Sale Order Exit"));
	}


	@Override
	public boolean validateOrderCode(String code) {
		return repository.validateOrderCode(code) > 0;
	}

	@Override
	public boolean validateOrderCodeAndId(String code, Integer id) {
		return repository.validateOrderCodeAndId(code, id) > 0;
	}


	@Override
	public Integer savePurchaseDetails(SaleOrderDetails saleOrderDetails) {
		return saleRepository.save(saleOrderDetails).getId();
	}

	@Override
	public List<SaleOrderDetails> getSaleDtlsBySaleOrderId(Integer id) {
		return saleRepository.getSaleDtlsBySaleOrderId(id);
	}


	@Override
	public void deleteSaleDetails(Integer detailId) {
		if (saleRepository.existsById(detailId)) {
			saleRepository.deleteById(detailId);
		}
	}

	@Override
	public String getCurrentStatusOfSaleOrder(Integer soId) {
		return repository.getCurrentStatusOfSaleOrder(soId);
	}

	@Override
	@Transactional
	public void updateSaleOrderStatus(Integer soId, String newStatus) {
		repository.updateSaleOrderStatus(soId, newStatus);
	}

	@Override
	public Integer getSaleDtlsCountBySaleOrderId(Integer soId) {
		return saleRepository.getSaleDtlsCountBySaleOrderId(soId);
	}


	@Override
	public Optional<SaleOrderDetails> getSaleDetailByPartIdAndSaleOrderId(Integer partId, Integer soId) {
		return saleRepository.getSaleDetailByPartIdAndSaleOrderId(partId, soId);
	}

	@Transactional
	@Override
	public Integer updateSaleOrderDetailQtyByDetailId(Integer newQty, Integer dtlId) {
		return saleRepository.updateSaleOrderDetailQtyByDetailId(newQty, dtlId);
	}

	@Override
	public Map<Integer, String> findSaleOrderIdAndCodeByStatus(String status) {
		List<Object[]> list = repository.findSaleOrderIdAndCodeByStatus(status);
		Map<Integer, String> map = new HashedMap<>();
		for (Object[] obj : list) {
			map.put((Integer) obj[0], (String) obj[1]);

		}
		return map;
	}
}
/*Класс SaleOrderServiceImpl обеспечивает реализацию бизнес-логики для управления заказами на продажу (SaleOrder) и связанными с ними деталями (SaleOrderDetails). В нем используются два репозитория: SaleOrderRepository для работы с заказами на продажу и SaleOrderDetailsRepository для управления деталями заказов. Рассмотрим ключевые аспекты этого класса и предложим возможные улучшения.

Ключевые функции
CRUD операции: Класс предоставляет методы для создания, чтения (включая чтение всех записей и чтение по ID), обновления и удаления заказов на продажу и их деталей.
Валидация кода заказа: Методы validateOrderCode и validateOrderCodeAndId позволяют проверить уникальность кода заказа, что важно для поддержания целостности данных.
Управление статусом заказа: Методы getCurrentStatusOfSaleOrder и updateSaleOrderStatus предоставляют возможность получить текущий статус заказа и обновить его, соответственно.
Управление деталями заказа: Включает функции для работы с деталями заказа, такие как добавление новых деталей, получение списка деталей по ID заказа и удаление деталей.
Возможные улучшения
Исключения: Вместо использования общего RuntimeException рекомендуется создать и использовать более специфические исключения (например, SaleOrderNotFoundException), что позволит более точно обрабатывать ошибки и предоставлять пользователю более информативные сообщения об ошибках.
Транзакционность: Правильное использование аннотации @Transactional на методах, изменяющих состояние данных, гарантирует корректное выполнение операций в рамках одной транзакции и помогает предотвратить возможные проблемы с целостностью данных.
Логирование: Добавление логирования операций может помочь в отладке и мониторинге выполнения операций, а также в диагностировании возможных проблем.
Валидация входных данных: Добавление слоя валидации входных данных на уровне сервиса поможет предотвратить сохранение некорректных данных и улучшит надежность приложения.
Использование DTO: Для улучшения безопасности и гибкости приложения рассмотрите возможность использования DTO (Data Transfer Objects) для передачи данных между слоями приложения, минимизируя прямую зависимость API от модели базы данных.
SaleOrderServiceImpl играет важную роль в управлении процессами продаж в приложении, обеспечивая необходимый уровень абстракции между базой данных и пользовательским интерфейсом. Применение предложенных улучшений может повысить надежность, производительность и удобство поддержки данного сервиса.*/
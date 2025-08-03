package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.OrderMethodNotFound;
import ru.demo.wms.model.OrderMethod;
import ru.demo.wms.repo.OrderMethodRepository;
import ru.demo.wms.service.IOrderMethodService;
import ru.demo.wms.util.MyAppUtil;

@Service
public class OrderMethodServiceImpl implements IOrderMethodService {

    @Autowired
	private MyAppUtil myAppUtil;
    
	@Autowired
	private OrderMethodRepository repo;

	public Integer saveOrderMethod(OrderMethod om) {
		return repo.save(om).getId();
	}


	public void updateOrderMethod(OrderMethod om) {
		if(om.getId()==null || !repo.existsById(om.getId()))
			throw new OrderMethodNotFound("Order Method Not Exist");
		repo.save(om).getId();
	}


	public void deleteOrderMethod(Integer id) {
		repo.delete(getOneOrderMethod(id));
	}


	public OrderMethod getOneOrderMethod(Integer id) {
		return repo.findById(id).orElseThrow(
				()->new OrderMethodNotFound("Order Method Not Exist")
				);
	}


	public List<OrderMethod> getAllOrderMethods() {
		return repo.findAll();
	}


	public boolean isOrderMethodCodeExist(String code) {
		return repo.isOrderMethodCodeExist(code) > 0;
	}


	public boolean isOrderMethodCodeExistForEdit(String code, Integer id) {
		return repo.isOrderMethodCodeExistForEdit(code,id) > 0;
	}
	
	public List<Object[]> getOrderMethodModeAndCount() {
		return repo.getOrderMethodModeAndCount();
	}
	
	public Map<Integer, String> getOrderMethodIdAndCode() {
		List<Object[]> list = repo.getOrderMethodIdAndCode();
		return myAppUtil.convertListToMap(list);
	}

}

/*
Класс OrderMethodServiceImpl реализует интерфейс IOrderMethodService, обеспечивая логику для работы с методами заказа (OrderMethod) в приложении. Этот сервисный слой взаимодействует с репозиторием OrderMethodRepository для выполнения операций CRUD и предоставляет дополнительную бизнес-логику, такую как проверка существования кода метода заказа. Давайте разберем ключевые моменты реализации и возможные улучшения:

Внедрение зависимостей
OrderMethodRepository: Внедрение зависимости через аннотацию @Autowired позволяет сервису взаимодействовать с базой данных для выполнения операций над объектами OrderMethod.
Методы сервиса
CRUD операции: Реализованы стандартные операции создания, чтения, обновления и удаления методов заказа.

Проверка существования кода метода заказа: Реализованы методы isOrderMethodCodeExist и isOrderMethodCodeExistForEdit для проверки уникальности кода метода заказа, что важно для предотвращения дублирования данных.

Агрегация данных: Метод getOrderMethodModeAndCount собирает данные для отчетов и аналитики, возвращая количество методов заказа по каждому режиму.

Преобразование данных для UI: Метод getOrderMethodIdAndCode возвращает данные в формате, удобном для отображения в пользовательском интерфейсе, используя вспомогательный метод MyAppUtil.convertListToMap.

Потенциальные улучшения
Обработка исключений: Хорошей практикой является создание иерархии исключений приложения и использование более специфичных исключений вместо RuntimeException. Это упрощает обработку ошибок и предоставление более информативных ответов пользователю.

Валидация данных: Добавление слоя валидации для проверки входных данных перед выполнением операций с базой данных может предотвратить возникновение ошибок и улучшить качество данных.

Транзакционность: Убедитесь, что операции, изменяющие состояние данных и потенциально влияющие на целостность данных, выполняются в контексте транзакции.

Логирование: Включение детализированного логирования операций может облегчить отладку и мониторинг приложения. Рекомендуется использовать разные уровни логирования (DEBUG, INFO, ERROR) для различных сценариев.

Использование DTO: Рассмотрите возможность использования объектов передачи данных (DTO) для обработки запросов и ответов API, чтобы отделить внешний контракт API от внутренней модели данных.

OrderMethodServiceImpl является важным компонентом в архитектуре приложения, обеспечивая бизнес-логику управления методами заказа. Применение предложенных улучшений может сделать сервис более надежным, безопасным и удобным для дальнейшей разработки и поддержки.
*/
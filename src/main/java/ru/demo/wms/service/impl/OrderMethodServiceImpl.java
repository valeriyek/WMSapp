package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.OrderMethodNotFound;
import ru.demo.wms.model.OrderMethod;
import ru.demo.wms.repo.OrderMethodRepository;
import ru.demo.wms.service.IOrderMethodService;
import ru.demo.wms.util.MyAppUtil;

/**
 * Сервисный класс для управления методами заказа (OrderMethod).
 * Реализует бизнес-логику, включая CRUD-операции, валидации и преобразования данных для UI.
 */
@Service
public class OrderMethodServiceImpl implements IOrderMethodService {

	private static final Logger LOG = LoggerFactory.getLogger(OrderMethodServiceImpl.class);

	@Autowired
	private MyAppUtil myAppUtil;

	@Autowired
	private OrderMethodRepository repo;

	/**
	 * Сохраняет новый метод заказа.
	 *
	 * @param om объект OrderMethod
	 * @return ID сохраненного метода
	 */
	@Override
	public Integer saveOrderMethod(OrderMethod om) {
		LOG.info("Сохранение нового метода заказа");
		return repo.save(om).getId();
	}

	/**
	 * Обновляет существующий метод заказа.
	 *
	 * @param om объект OrderMethod
	 * @throws OrderMethodNotFound если ID отсутствует или метод не существует
	 */
	@Override
	public void updateOrderMethod(OrderMethod om) {
		LOG.info("Обновление метода заказа с ID: {}", om.getId());
		if (om.getId() == null || !repo.existsById(om.getId())) {
			LOG.warn("Метод заказа не существует: ID={}", om.getId());
			throw new OrderMethodNotFound("Метод заказа не существует");
		}
		repo.save(om);
	}

	/**
	 * Удаляет метод заказа по ID.
	 *
	 * @param id идентификатор метода
	 * @throws OrderMethodNotFound если метод не найден
	 */
	@Override
	public void deleteOrderMethod(Integer id) {
		LOG.info("Удаление метода заказа ID: {}", id);
		repo.delete(getOneOrderMethod(id)); // выбросит исключение, если не найден
	}

	/**
	 * Получает метод заказа по ID.
	 *
	 * @param id идентификатор
	 * @return объект OrderMethod
	 * @throws OrderMethodNotFound если не найден
	 */
	@Override
	public OrderMethod getOneOrderMethod(Integer id) {
		LOG.info("Поиск метода заказа ID: {}", id);
		return repo.findById(id)
				.orElseThrow(() -> new OrderMethodNotFound("Метод заказа не существует"));
	}

	/**
	 * Возвращает все методы заказа.
	 *
	 * @return список OrderMethod
	 */
	@Override
	public List<OrderMethod> getAllOrderMethods() {
		LOG.info("Получение всех методов заказа");
		return repo.findAll();
	}

	/**
	 * Проверяет существование метода по коду (для создания).
	 *
	 * @param code код метода
	 * @return true, если существует
	 */
	@Override
	public boolean isOrderMethodCodeExist(String code) {
		LOG.debug("Проверка существования метода заказа по коду: {}", code);
		return repo.isOrderMethodCodeExist(code) > 0;
	}

	/**
	 * Проверяет существование метода по коду (для обновления).
	 *
	 * @param code код метода
	 * @param id   ID текущего метода
	 * @return true, если существует другой метод с таким кодом
	 */
	@Override
	public boolean isOrderMethodCodeExistForEdit(String code, Integer id) {
		LOG.debug("Проверка кода на уникальность при редактировании. Код: {}, ID: {}", code, id);
		return repo.isOrderMethodCodeExistForEdit(code, id) > 0;
	}

	/**
	 * Возвращает агрегированные данные: режим метода заказа и количество.
	 *
	 * @return список массивов [mode, count]
	 */
	@Override
	public List<Object[]> getOrderMethodModeAndCount() {
		LOG.info("Получение статистики: режим метода и количество");
		return repo.getOrderMethodModeAndCount();
	}

	/**
	 * Возвращает Map с ID и кодами методов заказа.
	 * Используется, например, для выпадающих списков на UI.
	 *
	 * @return Map<ID, Code>
	 */
	@Override
	public Map<Integer, String> getOrderMethodIdAndCode() {
		LOG.info("Формирование карты ID и кодов методов заказа");
		List<Object[]> list = repo.getOrderMethodIdAndCode();
		return myAppUtil.convertListToMap(list);
	}
}

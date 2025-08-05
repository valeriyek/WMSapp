package ru.demo.wms.service;

import java.util.List;
import java.util.Map;
import ru.demo.wms.model.OrderMethod;

/**
 * Интерфейс для управления методами заказа в системе WMS.
 * Предоставляет операции создания, обновления, удаления и выборки,
 * а также вспомогательные проверки и агрегирующие запросы.
 */
public interface IOrderMethodService {

	/**
	 * Сохраняет новый метод заказа.
	 *
	 * @param om объект OrderMethod.
	 * @return ID сохранённой записи.
	 */
	Integer saveOrderMethod(OrderMethod om);

	/**
	 * Обновляет существующий метод заказа.
	 *
	 * @param om объект OrderMethod с обновлёнными данными.
	 */
	void updateOrderMethod(OrderMethod om);

	/**
	 * Удаляет метод заказа по ID.
	 *
	 * @param id идентификатор метода заказа.
	 */
	void deleteOrderMethod(Integer id);

	/**
	 * Возвращает один метод заказа по ID.
	 *
	 * @param id идентификатор.
	 * @return объект OrderMethod.
	 */
	OrderMethod getOneOrderMethod(Integer id);

	/**
	 * Возвращает список всех методов заказа.
	 *
	 * @return список OrderMethod.
	 */
	List<OrderMethod> getAllOrderMethods();

	/**
	 * Проверяет, существует ли метод заказа с заданным кодом.
	 *
	 * @param code код метода заказа.
	 * @return true, если такой код уже существует.
	 */
	boolean isOrderMethodCodeExist(String code);

	/**
	 * Проверяет наличие метода заказа с таким кодом, исключая запись с указанным ID.
	 * Используется при редактировании.
	 *
	 * @param code код метода заказа.
	 * @param id ID текущей записи.
	 * @return true, если другой метод с таким кодом уже существует.
	 */
	boolean isOrderMethodCodeExistForEdit(String code, Integer id);

	/**
	 * Возвращает список пар [режим, количество методов].
	 * Используется, например, для построения диаграмм.
	 *
	 * @return список массивов: [0] - режим (String), [1] - количество (Long).
	 */
	List<Object[]> getOrderMethodModeAndCount();

	/**
	 * Возвращает карту [ID метода → Код метода].
	 * Удобно для использования в выпадающих списках.
	 *
	 * @return Map с ID и кодами методов.
	 */
	Map<Integer, String> getOrderMethodIdAndCode();
}

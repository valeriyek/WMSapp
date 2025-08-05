package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.OrderMethod;

/**
 * Репозиторий для работы с сущностью {@link OrderMethod}.
 * <p>
 * Предоставляет стандартные CRUD-операции, а также
 * кастомные методы для проверки уникальности кода,
 * выборки агрегированных данных и представлений для отображения.
 */
public interface OrderMethodRepository extends JpaRepository<OrderMethod, Integer> {

	/**
	 * Проверка существования метода заказа по коду (для создания).
	 *
	 * @param code код метода заказа
	 * @return количество записей с указанным кодом
	 */
	@Query("SELECT COUNT(orderCode) FROM OrderMethod WHERE orderCode=:code")
	Integer isOrderMethodCodeExist(String code);

	/**
	 * Проверка уникальности кода метода заказа при редактировании.
	 *
	 * @param code код метода заказа
	 * @param id   идентификатор редактируемой записи
	 * @return количество других записей с таким же кодом
	 */
	@Query("SELECT COUNT(orderCode) FROM OrderMethod WHERE orderCode=:code AND id!=:id")
	Integer isOrderMethodCodeExistForEdit(String code, Integer id);

	/**
	 * Получение количества методов заказа по каждому режиму.
	 * Используется для построения отчетов и графиков.
	 *
	 * @return список пар [режим заказа, количество]
	 */
	@Query("SELECT orderMode, COUNT(orderMode) FROM OrderMethod GROUP BY orderMode")
	List<Object[]> getOrderMethodModeAndCount();

	/**
	 * Получение всех методов заказа (id + code).
	 * Применяется для выпадающих списков, ссылок и отображения в UI.
	 *
	 * @return список пар [id, код метода заказа]
	 */
	@Query("SELECT id, orderCode FROM OrderMethod")
	List<Object[]> getOrderMethodIdAndCode();
}

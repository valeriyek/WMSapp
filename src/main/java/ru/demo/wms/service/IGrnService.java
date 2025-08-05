package ru.demo.wms.service;

import java.util.List;
import ru.demo.wms.model.Grn;

/**
 * Интерфейс IGrnService определяет контракт для работы с грузовыми накладными (GRN).
 * Используется для создания, получения и обновления информации о поставках.
 */
public interface IGrnService {

	/**
	 * Сохраняет новую грузовую накладную в систему.
	 *
	 * @param grn объект Grn, содержащий информацию о поступлении товара.
	 * @return ID сохранённой накладной.
	 */
	Integer saveGrn(Grn grn);

	/**
	 * Возвращает список всех накладных, зарегистрированных в системе.
	 *
	 * @return список объектов Grn.
	 */
	List<Grn> fetchAllGrns();

	/**
	 * Возвращает одну грузовую накладную по её ID.
	 *
	 * @param id идентификатор накладной.
	 * @return объект Grn.
	 */
	Grn getOneGrn(Integer id);

	/**
	 * Обновляет статус детали накладной (например, "принято", "проверено").
	 *
	 * @param id ID детали накладной.
	 * @param status новый статус.
	 */
	void updateGrnDtlStatus(Integer id, String status);
}

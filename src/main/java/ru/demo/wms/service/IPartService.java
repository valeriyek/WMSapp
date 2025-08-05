package ru.demo.wms.service;

import java.util.List;
import java.util.Map;
import ru.demo.wms.model.Part;

/**
 * Сервисный интерфейс для управления запчастями/компонентами (Part)
 * в системе WMS. Включает базовые CRUD-операции и утилитарные методы
 * для отображения данных в пользовательском интерфейсе.
 */
public interface IPartService {

	/**
	 * Сохраняет новую запчасть.
	 *
	 * @param part объект Part для сохранения.
	 * @return ID сохранённой запчасти.
	 */
	Integer savePart(Part part);

	/**
	 * Обновляет существующую запчасть.
	 *
	 * @param part объект Part с обновлёнными данными.
	 */
	void updatePart(Part part);

	/**
	 * Удаляет запчасть по ID.
	 *
	 * @param id идентификатор запчасти.
	 */
	void deletePart(Integer id);

	/**
	 * Возвращает одну запчасть по ID.
	 *
	 * @param id идентификатор.
	 * @return объект Part.
	 */
	Part getOnePart(Integer id);

	/**
	 * Возвращает список всех запчастей.
	 *
	 * @return список объектов Part.
	 */
	List<Part> getAllParts();

	/**
	 * Возвращает карту [ID → Код запчасти].
	 * Удобно для отображения в выпадающих списках.
	 *
	 * @return Map с ID и кодами запчастей.
	 */
	Map<Integer, String> getPartIdAndCode();
}

package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.PartNotFoundException;
import ru.demo.wms.model.Part;
import ru.demo.wms.repo.PartRepository;
import ru.demo.wms.service.IPartService;
import ru.demo.wms.util.MyAppUtil;

/**
 * Реализация сервиса IPartService.
 * Отвечает за бизнес-логику, связанную с управлением деталями (Part).
 */
@Service
public class PartServiceImpl implements IPartService {

	@Autowired
	private MyAppUtil myAppUtil;

	@Autowired
	private PartRepository repo;

	/**
	 * Сохраняет новую деталь в базе данных.
	 *
	 * @param part объект детали
	 * @return ID сохранённой детали
	 */
	@Override
	public Integer savePart(Part part) {
		return repo.save(part).getId();
	}

	/**
	 * Обновляет существующую деталь.
	 *
	 * @param part объект детали с обновлёнными данными
	 */
	@Override
	public void updatePart(Part part) {
		repo.save(part);
	}

	/**
	 * Удаляет деталь по её идентификатору.
	 *
	 * @param id идентификатор детали
	 * @throws PartNotFoundException если деталь не найдена
	 */
	@Override
	public void deletePart(Integer id) {
		repo.delete(getOnePart(id));
	}

	/**
	 * Получает одну деталь по ID.
	 *
	 * @param id идентификатор детали
	 * @return объект Part
	 * @throws PartNotFoundException если деталь не существует
	 */
	@Override
	public Part getOnePart(Integer id) {
		return repo.findById(id)
				.orElseThrow(() -> new PartNotFoundException("Деталь с ID " + id + " не найдена"));
	}

	/**
	 * Возвращает список всех деталей.
	 *
	 * @return список объектов Part
	 */
	@Override
	public List<Part> getAllParts() {
		return repo.findAll();
	}

	/**
	 * Возвращает карту идентификатор → код детали.
	 * Удобно для формирования UI-элементов, например, выпадающих списков.
	 *
	 * @return Map, где ключ — ID детали, значение — код
	 */
	@Override
	public Map<Integer, String> getPartIdAndCode() {
		List<Object[]> list = repo.getPartIdAndCode();
		return myAppUtil.convertListToMap(list);
	}
}

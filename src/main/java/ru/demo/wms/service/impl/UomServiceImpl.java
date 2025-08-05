package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.UomNotFoundException;
import ru.demo.wms.model.Uom;
import ru.demo.wms.repo.UomRepository;
import ru.demo.wms.service.IUomService;
import ru.demo.wms.util.MyAppUtil;

/**
 * Сервис для работы с единицами измерения (UOM).
 * Предоставляет CRUD-функции, валидацию и выборку для отображения в интерфейсе.
 */
@Service
public class UomServiceImpl implements IUomService {

	@Autowired
	private MyAppUtil myAppUtil;

	@Autowired
	private UomRepository repo;

	/**
	 * Сохраняет новую единицу измерения и возвращает её ID.
	 *
	 * @param uom объект Uom
	 * @return ID сохранённого объекта
	 */
	@Override
	public Integer saveUom(Uom uom) {
		return repo.save(uom).getId();
	}

	/**
	 * Обновляет существующую единицу измерения.
	 * Если объект не найден — выбрасывается исключение.
	 *
	 * @param uom объект Uom для обновления
	 * @throws UomNotFoundException если объект не существует
	 */
	@Override
	public void updateUom(Uom uom) {
		if (uom.getId() == null || !repo.existsById(uom.getId())) {
			throw new UomNotFoundException(
					"Uom '" + (uom.getId() == null ? "id" : uom.getId()) + "' not exist for update!");
		}
		repo.save(uom);
	}

	/**
	 * Удаляет единицу измерения по ID.
	 *
	 * @param id идентификатор UOM
	 */
	@Override
	public void deleteUom(Integer id) {
		repo.delete(getOneUom(id));
	}

	/**
	 * Возвращает одну единицу измерения по ID.
	 *
	 * @param id идентификатор
	 * @return объект Uom
	 * @throws UomNotFoundException если не найден
	 */
	@Override
	public Uom getOneUom(Integer id) {
		return repo.findById(id)
				.orElseThrow(() -> new UomNotFoundException("Uom '" + id + "' Not exist"));
	}

	/**
	 * Возвращает список всех единиц измерения.
	 *
	 * @return список объектов Uom
	 */
	@Override
	public List<Uom> getAllUoms() {
		return repo.findAll();
	}

	/**
	 * Проверяет, существует ли модель единицы измерения.
	 *
	 * @param uomModel модель
	 * @return true, если такая модель уже есть
	 */
	@Override
	public boolean isUomModelExist(String uomModel) {
		return repo.getUomModelCount(uomModel) > 0;
	}

	/**
	 * Проверяет наличие модели единицы измерения, исключая текущий ID (для редактирования).
	 *
	 * @param uomModel модель
	 * @param id       идентификатор текущего объекта
	 * @return true, если дубликат существует
	 */
	@Override
	public boolean isUomModelExistForEdit(String uomModel, Integer id) {
		return repo.getUomModelCountForEdit(uomModel, id) > 0;
	}

	/**
	 * Возвращает карту ID → модель единицы измерения.
	 * Используется, например, для выпадающих списков.
	 *
	 * @return карта ID и моделей
	 */
	@Override
	public Map<Integer, String> getUomIdAndModel() {
		List<Object[]> list = repo.getUomIdAndModel();
		return myAppUtil.convertListToMap(list);
	}
}

package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.ShipmentTypeNotFoundException;
import ru.demo.wms.model.ShipmentType;
import ru.demo.wms.repo.ShipmentTypeRepository;
import ru.demo.wms.service.IShipmentTypeService;
import ru.demo.wms.util.MyAppUtil;

/**
 * Реализация сервиса управления типами отправлений.
 * Обеспечивает CRUD-операции и дополнительные проверки/выборки.
 */
@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {

	@Autowired
	private MyAppUtil myAppUtil;

	@Autowired
	private ShipmentTypeRepository repo;

	/**
	 * Сохраняет новый тип отправления и возвращает его ID.
	 *
	 * @param st объект ShipmentType
	 * @return ID сохранённого объекта
	 */
	@Override
	public Integer saveShipmentType(ShipmentType st) {
		return repo.save(st).getId();
	}

	/**
	 * Возвращает список всех типов отправлений.
	 *
	 * @return список ShipmentType
	 */
	@Override
	public List<ShipmentType> getAllShipmentTypes() {
		return repo.findAll();
	}

	/**
	 * Удаляет тип отправления по его ID.
	 *
	 * @param id идентификатор типа
	 */
	@Override
	public void deleteShipmentType(Integer id) {
		repo.delete(getShipmentType(id));
	}

	/**
	 * Получает тип отправления по ID.
	 *
	 * @param id идентификатор
	 * @return объект ShipmentType
	 * @throws ShipmentTypeNotFoundException если объект не найден
	 */
	@Override
	public ShipmentType getShipmentType(Integer id) {
		return repo.findById(id)
				.orElseThrow(() -> new ShipmentTypeNotFoundException("ShipmentType '" + id + "' Not Exist"));
	}

	/**
	 * Обновляет существующий тип отправления.
	 *
	 * @param st объект ShipmentType
	 */
	@Override
	public void updateShipmentType(ShipmentType st) {
		repo.save(st);
	}

	/**
	 * Проверяет, существует ли тип отправления с указанным кодом.
	 *
	 * @param code код типа отправления
	 * @return true, если код уже существует
	 */
	@Override
	public boolean isShipmentTypeCodeExist(String code) {
		return repo.getShipmentTypeCodeCount(code) > 0;
	}

	/**
	 * Проверяет наличие другого объекта с тем же кодом (для редактирования).
	 *
	 * @param code код
	 * @param id   ID текущего объекта
	 * @return true, если найден другой объект с таким же кодом
	 */
	@Override
	public boolean isShipmentTypeCodeExistForEdit(String code, Integer id) {
		return repo.getShipmentTypeCodeCountForEdit(code, id) > 0;
	}

	/**
	 * Возвращает список режимов отправки и количества по каждому.
	 *
	 * @return список массивов [mode, count]
	 */
	@Override
	public List<Object[]> getShipmentTypeModeAndCount() {
		return repo.getShipmentTypeModeAndCount();
	}

	/**
	 * Возвращает карту ID → код типа отправления по признаку включённости.
	 *
	 * @param enable статус активности ("Y"/"N")
	 * @return карта ID и кодов
	 */
	@Override
	public Map<Integer, String> getShipmentIdAndCodeByEnable(String enable) {
		List<Object[]> list = repo.getShipmentIdAndCodeByEnable(enable);
		return myAppUtil.convertListToMap(list);
	}
}

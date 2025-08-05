package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.WhUserTypeNotFound;
import ru.demo.wms.model.WhUserType;
import ru.demo.wms.repo.WhUserTypeRepository;
import ru.demo.wms.service.IWhUserTypeService;
import ru.demo.wms.util.MyAppUtil;

/**
 * Сервис для управления типами пользователей склада (WhUserType).
 * Предоставляет реализацию CRUD-операций и валидации уникальности по коду, email и ID.
 */
@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService {

	@Autowired
	private MyAppUtil myAppUtil;

	@Autowired
	private WhUserTypeRepository repo;

	/**
	 * Сохраняет новый тип пользователя склада.
	 *
	 * @param whut объект типа WhUserType
	 * @return ID сохраненного объекта
	 */
	@Override
	public Integer saveWhUserType(WhUserType whut) {
		return repo.save(whut).getId();
	}

	/**
	 * Получает все типы пользователей склада.
	 *
	 * @return список всех WhUserType
	 */
	@Override
	public List<WhUserType> getAllWhUserTypes() {
		return repo.findAll();
	}

	/**
	 * Удаляет тип пользователя склада по ID.
	 * Если объект не найден — выбрасывает исключение.
	 *
	 * @param id идентификатор
	 */
	@Override
	public void deleteWhUserType(Integer id) {
		repo.delete(getOneWhUserType(id));
	}

	/**
	 * Получает один тип пользователя склада по ID.
	 *
	 * @param id идентификатор
	 * @return объект WhUserType
	 * @throws WhUserTypeNotFound если объект не найден
	 */
	@Override
	public WhUserType getOneWhUserType(Integer id) {
		return repo.findById(id).orElseThrow(
				() -> new WhUserTypeNotFound("WhUserType '" + id + "' Not Exist")
		);
	}

	/**
	 * Обновляет тип пользователя склада.
	 * Если объект не существует — выбрасывается исключение.
	 *
	 * @param whut обновлённый объект
	 */
	@Override
	public void updateWhUserType(WhUserType whut) {
		if (whut.getId() == null || !repo.existsById(whut.getId())) {
			throw new WhUserTypeNotFound("WhUserType '" + whut.getId() + "' Not Exist");
		}
		repo.save(whut);
	}

	/**
	 * Проверяет, существует ли тип пользователя с указанным кодом.
	 *
	 * @param code код пользователя
	 * @return true, если существует
	 */
	@Override
	public boolean isWhUserTypeCodeExit(String code) {
		return repo.getWhUserTypeuserCodeCount(code) > 0;
	}

	/**
	 * Проверяет, существует ли другой тип пользователя с таким же кодом, исключая текущий ID.
	 *
	 * @param code код
	 * @param id   ID текущего объекта
	 * @return true, если существует
	 */
	@Override
	public boolean isWhUserTypeCodeExitForEdit(String code, Integer id) {
		return repo.getWhUserTypeuserCodeCountForEdit(code, id) > 0;
	}

	/**
	 * Проверяет, существует ли пользователь с указанным email.
	 *
	 * @param email email
	 * @return true, если существует
	 */
	@Override
	public boolean getWhUserTypeuserEmailCount(String email) {
		return repo.getWhUserTypeuserEmailCount(email) > 0;
	}

	/**
	 * Проверяет, существует ли другой пользователь с тем же email (для редактирования).
	 *
	 * @param email email
	 * @param id    ID текущего пользователя
	 * @return true, если существует
	 */
	@Override
	public boolean getWhUserTypeuserEmailCountForEdit(String email, Integer id) {
		return repo.getWhUserTypeuserEmailCountForEdit(email, id) > 0;
	}

	/**
	 * Проверяет, существует ли пользователь с указанным идентификационным номером.
	 *
	 * @param idnum идентификационный номер
	 * @return true, если существует
	 */
	@Override
	public boolean getWhUserTypeuserIdNumCount(String idnum) {
		return repo.getWhUserTypeuserIdNumCount(idnum) > 0;
	}

	/**
	 * Проверяет, существует ли другой пользователь с тем же идентификационным номером.
	 *
	 * @param idnum идентификационный номер
	 * @param id    ID текущего пользователя
	 * @return true, если существует
	 */
	@Override
	public boolean getWhUserTypeuserIdNumCountForEdit(String idnum, Integer id) {
		return repo.getWhUserTypeuserIdNumCountForEdit(idnum, id) > 0;
	}

	/**
	 * Получает агрегированные данные: ID пользователя и количество.
	 *
	 * @return список массивов объектов с агрегированной информацией
	 */
	@Override
	public List<Object[]> getWhUserTypUserIDAndCount() {
		return repo.getWhUserTypUserIDAndCount();
	}

	/**
	 * Получает карту ID → код пользователя, отфильтрованных по типу (Customer/Supplier).
	 *
	 * @param type тип пользователя (например, "Customer", "Supplier")
	 * @return карта ID и кодов
	 */
	@Override
	public Map<Integer, String> getWhUserIdAndCodeByType(String type) {
		List<Object[]> list = repo.getWhUserIdAndCodeByType(type);
		return myAppUtil.convertListToMap(list);
	}
}

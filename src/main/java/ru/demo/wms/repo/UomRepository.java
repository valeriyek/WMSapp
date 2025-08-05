package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.Uom;

/**
 * Репозиторий для работы с сущностью {@link Uom} (единица измерения).
 * Расширяет {@link JpaRepository} и предоставляет базовые и пользовательские методы.
 */
public interface UomRepository extends JpaRepository<Uom, Integer> {

	/**
	 * Проверяет количество единиц измерения с указанной моделью.
	 * Применяется для валидации уникальности при создании.
	 *
	 * @param uomModel модель единицы измерения
	 * @return количество совпадений
	 */
	@Query("SELECT count(uomModel) FROM Uom WHERE uomModel=:uomModel")
	Integer getUomModelCount(String uomModel);

	/**
	 * Проверяет количество единиц измерения с указанной моделью,
	 * исключая текущую запись (по id). Используется при редактировании.
	 *
	 * @param uomModel модель единицы измерения
	 * @param id       идентификатор текущей записи
	 * @return количество совпадений
	 */
	@Query("SELECT count(uomModel) FROM Uom WHERE uomModel=:uomModel and id!=:id")
	Integer getUomModelCountForEdit(String uomModel, Integer id);

	/**
	 * Возвращает список всех единиц измерения в виде пар: [id, uomModel].
	 * Полезно для формирования выпадающих списков и других интерфейсов.
	 *
	 * @return список массивов [id, uomModel]
	 */
	@Query("SELECT id, uomModel FROM Uom")
	List<Object[]> getUomIdAndModel();
}

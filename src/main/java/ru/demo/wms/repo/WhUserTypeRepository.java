package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.WhUserType;

/**
 * Репозиторий Spring Data JPA для сущности {@link WhUserType}.
 * Предоставляет базовые и специализированные методы для управления пользователями склада.
 */
public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {

	/**
	 * Проверяет количество пользователей с заданным кодом.
	 *
	 * @param code код пользователя
	 * @return количество совпадений
	 */
	@Query("SELECT count(userCode) from WhUserType where userCode=:code")
	Integer getWhUserTypeuserCodeCount(String code);

	/**
	 * Проверяет количество пользователей с заданным кодом, исключая текущую запись по id.
	 *
	 * @param code код пользователя
	 * @param id   идентификатор исключаемой записи
	 * @return количество совпадений
	 */
	@Query("SELECT count(userCode) from WhUserType where userCode=:code and id!=:id")
	Integer getWhUserTypeuserCodeCountForEdit(String code, Integer id);

	/**
	 * Проверяет количество пользователей с заданной электронной почтой.
	 *
	 * @param email email пользователя
	 * @return количество совпадений
	 */
	@Query("SELECT count(userEmail) from WhUserType where userEmail=:email")
	Integer getWhUserTypeuserEmailCount(String email);

	/**
	 * Проверяет количество пользователей с заданным email, исключая запись по id.
	 *
	 * @param email email пользователя
	 * @param id    идентификатор исключаемой записи
	 * @return количество совпадений
	 */
	@Query("SELECT count(userCode) from WhUserType where userCode=:email and id!=:id")
	Integer getWhUserTypeuserEmailCountForEdit(String email, Integer id);

	/**
	 * Проверяет количество пользователей с заданным идентификационным номером.
	 *
	 * @param idnum идентификационный номер пользователя
	 * @return количество совпадений
	 */
	@Query("SELECT count(userIdNum) from WhUserType where userIdNum=:idnum")
	Integer getWhUserTypeuserIdNumCount(String idnum);

	/**
	 * Проверяет количество пользователей с заданным идентификационным номером, исключая запись по id.
	 *
	 * @param idnum идентификационный номер
	 * @param id    идентификатор исключаемой записи
	 * @return количество совпадений
	 */
	@Query("SELECT count(userIdNum) from WhUserType where userIdNum=:idnum and id!=:id")
	Integer getWhUserTypeuserIdNumCountForEdit(String idnum, Integer id);

	/**
	 * Возвращает количество пользователей, сгруппированных по типу идентификатора (userIdType).
	 *
	 * @return список массивов вида [userIdType, count]
	 */
	@Query("SELECT userIdType, count(userIdType) FROM WhUserType GROUP BY userIdType")
	List<Object[]> getWhUserTypUserIDAndCount();

	/**
	 * Возвращает идентификаторы и коды пользователей по заданному типу пользователя.
	 *
	 * @param type тип пользователя (userType)
	 * @return список массивов вида [id, userCode]
	 */
	@Query("SELECT id, userCode FROM WhUserType WHERE userType=:type")
	List<Object[]> getWhUserIdAndCodeByType(String type);
}

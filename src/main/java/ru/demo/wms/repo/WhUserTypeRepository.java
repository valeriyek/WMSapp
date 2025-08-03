package ru.demo.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.demo.wms.model.WhUserType;

public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {


	@Query("SELECT count(userCode) from WhUserType where userCode=:code")
	Integer getWhUserTypeuserCodeCount(String code);

	@Query("SELECT count(userCode) from WhUserType where userCode=:code and id!=:id")
	Integer getWhUserTypeuserCodeCountForEdit(String code, Integer id);

	@Query("SELECT count(userEmail) from WhUserType where userEmail=:email")
	Integer getWhUserTypeuserEmailCount(String email);

	@Query("SELECT count(userCode) from WhUserType where userCode=:email and id!=:id")
	Integer getWhUserTypeuserEmailCountForEdit(String email, Integer id);

	@Query("SELECT count(userIdNum) from WhUserType where userIdNum=:idnum")
	Integer getWhUserTypeuserIdNumCount(String idnum);

	@Query("SELECT count(userIdNum) from WhUserType where userIdNum=:idnum and id!=:id")
	Integer getWhUserTypeuserIdNumCountForEdit(String idnum, Integer id);


	@Query("SELECT userIdType, count(userIdType) FROM WhUserType GROUP BY userIdType")
	List<Object[]> getWhUserTypUserIDAndCount();
	

	@Query("SELECT id, userCode FROM WhUserType WHERE userType=:type")
	List<Object[]> getWhUserIdAndCodeByType(String type);

}
/*
Этот интерфейс WhUserTypeRepository представляет собой репозиторий Spring Data JPA для сущности WhUserType, который наследует JpaRepository. Это обеспечивает базовые операции CRUD для объектов WhUserType, а также предлагает дополнительные специализированные методы для выполнения конкретных задач, связанных с управлением типами пользователей склада.

Специализированные методы:
getWhUserTypeuserCodeCount и getWhUserTypeuserCodeCountForEdit: Эти методы используются для проверки уникальности кода пользователя (userCode) при регистрации нового пользователя или редактировании существующего, соответственно.

getWhUserTypeuserEmailCount и getWhUserTypeuserEmailCountForEdit: Аналогичны методам для кода пользователя, но проверяют уникальность электронной почты (userEmail).

getWhUserTypeuserIdNumCount и getWhUserTypeuserIdNumCountForEdit: Предназначены для проверки уникальности идентификационного номера пользователя (userIdNum), что важно для избежания дублирования ключевой информации о пользователях.

getWhUserTypUserIDAndCount: Возвращает данные для генерации диаграмм и отчетов, показывая количество пользователей, сгруппированных по типу идентификатора пользователя (userIdType). Этот метод может быть полезен для анализа распределения типов пользователей в системе.

getWhUserIdAndCodeByType: Извлекает идентификаторы и коды пользователей, которые соответствуют определенному типу (userType). Этот метод может использоваться для интеграции данных о пользователях в другие части системы или для формирования списков пользователей по типам.

Основные характеристики и преимущества:
Автоматическая реализация: Наследование от JpaRepository упрощает разработку, автоматически предоставляя реализацию для множества стандартных операций с данными.

Гибкость в определении запросов: Использование аннотации @Query для создания пользовательских запросов на JPQL дает возможность точно управлять выборкой данных, обеспечивая необходимую гибкость для выполнения специализированных задач.

Поддержка проверок уникальности: Специализированные методы для проверки уникальности ключевых атрибутов пользователей помогают поддерживать целостность данных в системе.

Инструменты для анализа и интеграции: Методы, предоставляющие агрегированные данные и способствующие интеграции данных о пользователях, расширяют возможности анализа и упрощают синхронизацию данных между различными частями системы.

WhUserTypeRepository является важным компонентом системы управления складом, предоставляя разработчикам мощные и гибкие инструменты для управления информацией о типах пользователей склада, их регистрации, анализе и интеграции данных.
*/
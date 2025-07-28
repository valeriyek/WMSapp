package ru.demo.wms.model;
/*Класс Role представляет собой модель роли пользователя в системе. Эта модель используется для определения 
различных уровней доступа или прав пользователей в приложении. Каждая роль может быть присвоена одному 
или нескольким пользователям, определяя их возможности в системе.

Основные атрибуты:

id: Уникальный идентификатор роли, автоматически генерируемый.
role: Перечисление RoleType, которое представляет собой тип роли. Это поле хранит наименование роли (например, ADMIN, USER и т.д.) в формате строки.
Конструкторы:

Конструктор без параметров для использования в JPA.
Конструктор со всеми полями для удобства инициализации объектов.
Методы доступа:

Геттеры и сеттеры для всех атрибутов класса.
Использование перечисления EnumType.STRING для поля role позволяет сохранять в базе данных строковое
представление значений перечисления, что облегчает чтение и понимание данных без необходимости сопоставления числовых кодов с их значением.

Эта модель является частью системы управления доступом и используется для определения политик безопасности, например, 
какие действия разрешены для выполнения в системе для пользователей с определенными ролями. 
Наличие выделенной модели ролей в приложении позволяет гибко настраивать права доступа, улучшая управление 
безопасностью и многогранность системы.*/
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.demo.wms.consts.RoleType;

import lombok.Data;

@Entity
@Table(name="roles_tab")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id_col")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role_name_col")
	private RoleType role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public Role(Integer id, RoleType role) {
		super();
		this.id = id;
		this.role = role;
	}

	public Role() {
		super();
		// TODO
	}
	
	
	
}

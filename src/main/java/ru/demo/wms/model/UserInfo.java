package ru.demo.wms.model;
/*

Класс Uom (Unit of Measurement) представляет собой модель для хранения информации об единицах измерения на складе. Этот класс используется для учета и классификации товаров по различным единицам измерения, что важно для точности складских операций, инвентаризации и логистики.

Ключевые характеристики:

Идентификатор (id): Уникальный идентификатор единицы измерения в системе.
Тип (uomType): Общая категория единицы измерения, например, масса, объем, количество и т.д.
Модель (uomModel): Уникальное обозначение или название единицы измерения, например, килограмм, литр, штука.
Описание (uomDesc): Подробное описание единицы измерения, которое может включать в себя дополнительные характеристики или особенности.
Использование в приложении:
Класс Uom играет важную роль в управлении складом, обеспечивая стандартизацию и точность при работе с товарными запасами. Это позволяет:

Учитывать товары в различных единицах измерения.
Обеспечивать точность и единообразие при ведении учета, составлении отчетов и выполнении инвентаризации.
Гибко настраивать параметры товаров и услуг, учитывая их специфику.
Примеры использования:

Ввод новых товаров: При добавлении товаров в систему указывается единица измерения, что позволяет точно классифицировать и учитывать товары.
Составление отчетов и анализ: Анализ складских запасов, отчеты по движению товаров и инвентаризация проводятся с учетом единиц измерения, обеспечивая точность данных.
Логистика и распределение: Планирование логистических операций и распределение товаров осуществляется с учетом единиц измерения, что повышает эффективность процессов.
Взаимодействие с другими компонентами системы:
Uom может использоваться в различных частях системы управления складом, включая учет поступлений и отгрузок, формирование заказов и управление запасами. Единицы измерения связываются с товарами и услугами, позволяя точно отражать их характеристики в системе.
*/
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import ru.demo.wms.consts.UserMode;

import lombok.Data;

@Entity
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String email;
	private String password;
	private String otp;
	
	@Enumerated(EnumType.STRING)
	private UserMode mode = UserMode.DISABLED;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public UserMode getMode() {
		return mode;
	}

	public void setMode(UserMode mode) {
		this.mode = mode;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public UserInfo(Integer id, String name, String email, String password, String otp, UserMode mode,
			Set<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.otp = otp;
		this.mode = mode;
		this.roles = roles;
	}

	public UserInfo() {
		super();
		// TODO
	}
	
	
	
}

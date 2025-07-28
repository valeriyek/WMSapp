package ru.demo.wms.model;


/*Класс ShipmentType представляет собой модель для хранения информации о типе отправления в системе управления складом. Эта модель помогает определить различные аспекты отправлений, такие как режим транспортировки, уникальный код отправления, доступность отправки, класс отправления и его описание.

Ключевые характеристики:

Идентификатор типа отправления (id): Уникальный идентификатор каждого типа отправления в системе.
Режим отправления (shipMode): Описывает метод транспортировки, например, воздушный, морской, железнодорожный и т.д.
Код отправления (shipCode): Уникальный код, присвоенный каждому типу отправления для легкой идентификации.
Доступность отправки (enbleShip): Определяет, доступен ли данный тип отправления для использования (да/нет).
Класс отправления (shipGrade): Определяет класс или категорию отправления, например, стандарт, экспресс и т.д.
Описание отправления (shipDesc): Дает детальное описание типа отправления.
Примеры использования:

Определение метода доставки: При создании заказа на отгрузку система может использовать эту модель для определения доступных методов доставки в зависимости от требований к заказу.
Фильтрация и поиск: Эта информация может использоваться для фильтрации и поиска отправлений в системе управления складом, позволяя пользователям быстро находить необходимую информацию о доступных и предпочтительных методах доставки.
Отчетность и аналитика: Данные о типах отправлений могут быть использованы для анализа тенденций в логистике, планирования маршрутов доставки и оптимизации затрат на перевозку.
Интеграция с другими компонентами системы:

ShipmentType может быть связан с другими сущностями в системе, такими как заказы на отгрузку, чтобы указать, какой метод доставки будет использован для конкретного заказа. Это обеспечивает гибкость в управлении логистическими операциями и позволяет точно соответствовать требованиям клиентов и стандартам доставки.*/
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="shipment_type_tab")
public class ShipmentType {
	@Id
	@GeneratedValue(
			generator = "ship_type_gen"
			)
	@SequenceGenerator(
			name = "ship_type_gen" , 
			sequenceName = "ship_type_seq")
	@Column(
			name="ship_id_col")
	private Integer id;
	
	@Column(
			name="ship_mode_col",
			nullable = false,
			length = 10
			)
	private String shipMode;
	
	@Column(
			name="ship_code_col",
			nullable = false,
			length = 10,
			unique = true
			)
	private String shipCode;
	
	@Column(
			name="ship_enbl_col",
			nullable = false,
			length = 5
			)
	private String enbleShip;
	
	@Column(
			name="ship_grade_col",
			nullable = false,
			length = 3
			)
	private String shipGrade;
	
	@Column(
			name="ship_desc_col",
			nullable = false,
			length = 100
			)
	private String shipDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShipMode() {
		return shipMode;
	}

	public void setShipMode(String shipMode) {
		this.shipMode = shipMode;
	}

	public String getShipCode() {
		return shipCode;
	}

	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	public String getEnbleShip() {
		return enbleShip;
	}

	public void setEnbleShip(String enbleShip) {
		this.enbleShip = enbleShip;
	}

	public String getShipGrade() {
		return shipGrade;
	}

	public void setShipGrade(String shipGrade) {
		this.shipGrade = shipGrade;
	}

	public String getShipDesc() {
		return shipDesc;
	}

	public void setShipDesc(String shipDesc) {
		this.shipDesc = shipDesc;
	}

	public ShipmentType(Integer id, String shipMode, String shipCode, String enbleShip, String shipGrade,
			String shipDesc) {
		super();
		this.id = id;
		this.shipMode = shipMode;
		this.shipCode = shipCode;
		this.enbleShip = enbleShip;
		this.shipGrade = shipGrade;
		this.shipDesc = shipDesc;
	}

	public ShipmentType() {
		super();
		// TODO
	}
	
	
}

package ru.demo.wms.model;
/*

Класс PurchaseOrder представляет собой модель заказа на покупку в системе управления складом. 
Это основная сущность, которая хранит информацию о заказе, включая его уникальный идентификатор, 
код заказа, номер справки, статус качества, общий статус заказа, описание, 
а также связанные с ним сущности типа отправления и поставщика.

Основные атрибуты:

id: Уникальный идентификатор заказа на покупку, автоматически генерируемый.
orderCode: Код заказа на покупку.
refNum: Номер справки заказа.
qltyChck: Статус проверки качества товаров в заказе.
status: Общий статус заказа (например, выполнен, в обработке и т.д.).
description: Описание заказа на покупку.
st: Связь с сущностью ShipmentType, указывающая тип отправления для данного заказа.
vendor: Связь с сущностью WhUserType, указывающая поставщика товаров по данному заказу.
Конструкторы:

Конструктор без параметров для использования в JPA.
Конструктор со всеми полями для удобства инициализации объектов.
Методы доступа:

Геттеры и сеттеры для всех атрибутов класса.
Эта сущность важна для учета и управления заказами на покупку в рамках складской системы. 
Она позволяет отслеживать статусы заказов, управлять информацией о поставщиках и условиях доставки, 
а также осуществлять контроль качества поступающих товаров. Интеграция с другими сущностями системы, 
такими как типы отправлений (ShipmentType) и информация о поставщиках (WhUserType), 
обеспечивает комплексное управление всеми аспектами заказов на покупку.
*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="po_tab")
public class PurchaseOrder {

	@Id
	@GeneratedValue(generator = "po_gen")
	@SequenceGenerator(name = "po_gen",sequenceName = "po_seq")
	@Column(name="po_id_col")
	private Integer id;
	
	@Column(name="po_code_col")
	private String orderCode;
	
	@Column(name="po_ref_col")
	private String refNum;
	
	@Column(name="po_qlty_col")
	private String qltyChck;
	
	@Column(name="po_status_col")
	private String status;

	@Column(name="po_desc_col")
	private String description;
	
	

	@ManyToOne
	@JoinColumn(name="st_id_fk_col")
	private ShipmentType st;

	@ManyToOne
	@JoinColumn(name="wh_ven_id_fk_col")
	private WhUserType vendor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getQltyChck() {
		return qltyChck;
	}

	public void setQltyChck(String qltyChck) {
		this.qltyChck = qltyChck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ShipmentType getSt() {
		return st;
	}

	public void setSt(ShipmentType st) {
		this.st = st;
	}

	public WhUserType getVendor() {
		return vendor;
	}

	public void setVendor(WhUserType vendor) {
		this.vendor = vendor;
	}

	public PurchaseOrder(Integer id, String orderCode, String refNum, String qltyChck, String status,
			String description, ShipmentType st, WhUserType vendor) {
		super();
		this.id = id;
		this.orderCode = orderCode;
		this.refNum = refNum;
		this.qltyChck = qltyChck;
		this.status = status;
		this.description = description;
		this.st = st;
		this.vendor = vendor;
	}

	public PurchaseOrder() {
		super();
		// TODO
	}
	
	
	
}

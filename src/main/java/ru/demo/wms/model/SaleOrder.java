package ru.demo.wms.model;
/*
Класс SaleOrder представляет собой модель заказа на продажу в системе управления складом. Эта модель включает в себя информацию о заказе, такую как код заказа, номер ссылки, режим и источник запасов, статус, описание и связанные сущности, такие как тип отправления и клиент.

Основные атрибуты:

id: Уникальный идентификатор заказа, генерируемый автоматически.
orderCode: Код заказа, уникальный идентификатор заказа, задаваемый пользователем или системой.
refNum: Номер ссылки, который может быть использован для связи заказа с другими документами или системами.
stockMode: Режим запасов, например, наличие или заказ по требованию.
stockSource: Источник запасов, указывающий, откуда берутся товары (например, склад, внешний поставщик).
status: Текущий статус заказа, например, обработан, отправлен, отменен.
description: Описание заказа, предоставляющее дополнительные сведения.
st: Связь с типом отправления (ShipmentType), указывающая на способ доставки заказа.
customer: Связь с клиентом (WhUserType), указывающая на получателя заказа.
Конструкторы:

Конструктор без параметров для использования в JPA.
Конструктор со всеми полями для удобства инициализации объектов.
Методы доступа:

Геттеры и сеттеры для всех атрибутов класса.
Модель SaleOrder играет ключевую роль в процессе управления заказами на продажу, позволяя отслеживать и управлять заказами от момента их создания до выполнения и доставки клиенту. Интеграция с другими сущностями, такими как ShipmentType и WhUserType, обеспечивает связь между заказом и его доставкой, а также информацию о клиенте, что важно для обработки и исполнения заказов.
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
@Table(name = "saleorder_tab")
public class SaleOrder {

	@Id
	@GeneratedValue(generator = "sale_ord_gen")
	@SequenceGenerator(name = "sale_ord_gen", sequenceName = "sale_ord_seq")
	@Column(name = "sale_ord_id")
	private Integer id;

	@Column(name = "sale_ord_code")
	private String orderCode;

	@Column(name = "sale_ord_refno")
	private String refNum;

	@Column(name = "sale_ord_stockmode")
	private String stockMode;

	@Column(name = "sale_ord_stocksource")
	private String stockSource;

	@Column(name = "sale_ord_status")
	private String status;

	@Column(name = "sale_ord_desc")
	private String description;


	@ManyToOne
	@JoinColumn(name = "st_id_fk")
	private ShipmentType st;

	@ManyToOne
	@JoinColumn(name = "wh_id_fk")
	private WhUserType customer;

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

	public String getStockMode() {
		return stockMode;
	}

	public void setStockMode(String stockMode) {
		this.stockMode = stockMode;
	}

	public String getStockSource() {
		return stockSource;
	}

	public void setStockSource(String stockSource) {
		this.stockSource = stockSource;
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

	public WhUserType getCustomer() {
		return customer;
	}

	public void setCustomer(WhUserType customer) {
		this.customer = customer;
	}

	public SaleOrder(Integer id, String orderCode, String refNum, String stockMode, String stockSource, String status,
			String description, ShipmentType st, WhUserType customer) {
		super();
		this.id = id;
		this.orderCode = orderCode;
		this.refNum = refNum;
		this.stockMode = stockMode;
		this.stockSource = stockSource;
		this.status = status;
		this.description = description;
		this.st = st;
		this.customer = customer;
	}

	public SaleOrder() {
		super();
		// TODO
	}
	
	
}

package ru.demo.wms.model;

/*

Класс PurchaseDtl представляет собой сущность, которая является частью детализации 
заказа на покупку (Purchase Order Detail) в системе управления складом. 
Эта сущность хранит информацию о количестве и типе частей (Parts), заказанных 
в рамках конкретного заказа на покупку, а также о самом заказе на покупку.

Основные атрибуты:

id: Уникальный идентификатор детали заказа на покупку, генерируемый автоматически.
qty: Количество частей, заказанных в рамках данной позиции заказа на покупку.
part: Ссылка на часть (Part), заказанную в рамках данной позиции. Описывает, какой именно товар или компонент заказан.
po: Ссылка на заказ на покупку (Purchase Order), в рамках которого была сделана данная покупка.
Конструкторы:

Конструктор без аргументов (по умолчанию), который необходим для работы с JPA.
Конструктор со всеми параметрами для удобства инициализации объектов этого класса.
Методы доступа (геттеры и сеттеры):

Методы для получения и установки значений всех атрибутов класса.
Эта сущность важна для реализации функционала по работе с заказами на покупку, 
позволяя учитывать конкретные детали каждого заказа, включая количество и тип заказываемых 
товаров или компонентов. Связи с другими сущностями (Part и PurchaseOrder) позволяют 
организовать структурированное хранение информации о заказах и связанных с ними 
товарах на складе.
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
@Table(name="po_dtl_tab")
public class PurchaseDtl {

	@Id
	@GeneratedValue(generator = "pdtl_gen")
	@SequenceGenerator(name = "pdtl_gen",sequenceName = "pdtl_seq")
	@Column(name="po_dlt_id_col")
	private Integer id;
	
	@Column(name="po_dlt_qty_col")
	private Integer qty;
	
	@ManyToOne
	@JoinColumn(name="part_id_fk_col")
	private Part part;
	
	@ManyToOne
	@JoinColumn(name="po_id_fk_col")
	private PurchaseOrder po;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public PurchaseOrder getPo() {
		return po;
	}

	public void setPo(PurchaseOrder po) {
		this.po = po;
	}

	public PurchaseDtl(Integer id, Integer qty, Part part, PurchaseOrder po) {
		super();
		this.id = id;
		this.qty = qty;
		this.part = part;
		this.po = po;
	}

	public PurchaseDtl() {
		super();
		// TODO
	}
	
	
}

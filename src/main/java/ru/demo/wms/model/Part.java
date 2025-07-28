package ru.demo.wms.model;
/*
Класс Part представляет собой сущность, описывающую отдельную часть или компонент в системе управления складом. 
Этот класс используется для хранения информации о различных деталях, 
которые могут храниться на складе, включая их стоимость, размеры и описание.

Основные атрибуты:

id: Уникальный идентификатор части, генерируемый автоматически.
partCode: Кодовое обозначение части, используемое для идентификации.
partCurrency: Валюта, в которой выражена базовая стоимость части.
partBaseCost: Базовая стоимость части.
partWid, partHt, partLen: Ширина, высота и длина части соответственно.
partDesc: Описание части, содержащее дополнительную информацию.
Интеграции:

uom: Связь "многие к одному" с сущностью Uom, указывающая единицу измерения части.
om: Связь "многие к одному" с сущностью OrderMethod, указывающая метод заказа, 
связанный с частью.
Конструкторы:

Конструктор по умолчанию, необходимый для JPA.
Конструктор со всеми параметрами для удобства создания экземпляров Part.
Методы доступа (геттеры и сеттеры):

Позволяют получать и устанавливать значения всех атрибутов сущности.
Эта сущность играет ключевую роль в системе управления складом, позволяя организовывать 
информацию о доступных частях, их характеристиках и стоимости, а также обеспечивая 
возможность связывания частей с конкретными методами заказа и единицами измерения.
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
@Table(name="part_tab")
public class Part {

	@Id
	@GeneratedValue(generator = "part_gen")
	@SequenceGenerator(name = "part_gen" , sequenceName = "part_seq")
	@Column(name="part_id_col")
	private Integer id;
	
	@Column(name="part_code_col")
	private String partCode;

	@Column(name="part_curr_col")
	private String partCurrency;

	@Column(name="part_cost_col")
	private Double partBaseCost;
	
	@Column(name="part_wid_col")
	private Double partWid;
	
	@Column(name="part_ht_col")
	private Double partHt;
	
	@Column(name="part_len_col")
	private Double partLen;
	
	@Column(name="part_desc_col")
	private String partDesc;
	

	@ManyToOne
	@JoinColumn(name="uom_id_fk_col")
	private Uom uom;
	
	@ManyToOne
	@JoinColumn(name="om_id_fk_col")
	private OrderMethod om;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartCurrency() {
		return partCurrency;
	}

	public void setPartCurrency(String partCurrency) {
		this.partCurrency = partCurrency;
	}

	public Double getPartBaseCost() {
		return partBaseCost;
	}

	public void setPartBaseCost(Double partBaseCost) {
		this.partBaseCost = partBaseCost;
	}

	public Double getPartWid() {
		return partWid;
	}

	public void setPartWid(Double partWid) {
		this.partWid = partWid;
	}

	public Double getPartHt() {
		return partHt;
	}

	public void setPartHt(Double partHt) {
		this.partHt = partHt;
	}

	public Double getPartLen() {
		return partLen;
	}

	public void setPartLen(Double partLen) {
		this.partLen = partLen;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public Uom getUom() {
		return uom;
	}

	public void setUom(Uom uom) {
		this.uom = uom;
	}

	public OrderMethod getOm() {
		return om;
	}

	public void setOm(OrderMethod om) {
		this.om = om;
	}

	public Part(Integer id, String partCode, String partCurrency, Double partBaseCost, Double partWid, Double partHt,
			Double partLen, String partDesc, Uom uom, OrderMethod om) {
		super();
		this.id = id;
		this.partCode = partCode;
		this.partCurrency = partCurrency;
		this.partBaseCost = partBaseCost;
		this.partWid = partWid;
		this.partHt = partHt;
		this.partLen = partLen;
		this.partDesc = partDesc;
		this.uom = uom;
		this.om = om;
	}

	public Part() {
		super();
		// TODO
	}
	
	
	
}

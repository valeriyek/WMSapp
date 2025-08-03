package ru.demo.wms.model;
/*

Класс GrnDtl представляет детали приходной накладной в системе управления складом. 
Давайте рассмотрим его компоненты:

Аннотация @Entity указывает, что класс является сущностью JPA, то есть его экземпляры можно сохранять в базе данных.
Аннотация @Table(name="grn_dtl_tab") связывает эту сущность с таблицей grn_dtl_tab в базе данных.
Атрибуты класса:

id: Уникальный идентификатор каждой записи деталей приходной накладной. Автоматически генерируется базой данных.
partCode: Код части или детали, которая была получена. Это может быть, например, серийный номер 
или идентификатор детали.
baseCost: Базовая стоимость одной единицы детали.
qty: Количество полученных деталей.
status: Статус детали в приходной накладной (например, принято, отклонено, ожидается).
Конструкторы:

Пустой конструктор: требуется JPA.
Конструктор со всеми параметрами: позволяет инициализировать все поля класса 
при создании объекта.
Геттеры и сеттеры: Методы для доступа и обновления значений атрибутов.

Этот класс служит для представления отдельной записи о товаре, полученном на склад, включая его код, 
стоимость, количество и текущий статус. Каждый объект GrnDtl связан с конкретной приходной накладной (Grn), 
что позволяет детально отслеживать все поступления товаров на склад.
*/


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name="grn_dtl_tab")
public class GrnDtl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="grn_dtl_id_col")
	private Integer id;
	
	@Column(name="grn_dtl_code_col")
	private String partCode;
	
	@Column(name="grn_dtl_cost_col")
	private Double baseCost;
	
	@Column(name="grn_dtl_qty_col")
	private Integer qty;
	
	@Column(name="grn_dtl_status_col")
	private String status;

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

	public Double getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public GrnDtl(Integer id, String partCode, Double baseCost, Integer qty, String status) {
		super();
		this.id = id;
		this.partCode = partCode;
		this.baseCost = baseCost;
		this.qty = qty;
		this.status = status;
	}

	public GrnDtl() {
		super();
		// TODO
	}
	
	
}

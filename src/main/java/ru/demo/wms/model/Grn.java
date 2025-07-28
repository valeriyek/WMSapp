package ru.demo.wms.model;

/*Класс Grn (Goods Receipt Note) представляет сущность приходной накладной в системе складского учета. Давайте рассмотрим его компоненты подробнее:

Аннотация @Entity: Указывает, что класс представляет сущность, которая может быть сохранена в базе данных.
Аннотация @Table(name="grn_tab"): Определяет, что объекты этого класса будут храниться в таблице grn_tab.
Поля класса:

id: Уникальный идентификатор записи, отмечен как первичный ключ с автоматическим генерированием значений (@GeneratedValue(strategy = GenerationType.IDENTITY)).
grnCode: Код приходной накладной.
grnType: Тип приходной накладной (может использоваться для указания типа поставки, например, внутренняя или внешняя).
grnDescription: Описание приходной накладной.
po: Связь между приходной накладной и заказом на покупку (PurchaseOrder). Отношение "многие к одному" (@ManyToOne), указывает на то, что каждая приходная накладная связана с одним заказом на покупку. @JoinColumn(name="po_id_fk_col", unique = true) определяет внешний ключ.
dtls: Коллекция деталей приходной накладной (GrnDtl). Отношение "один ко многим" (@OneToMany), каждая приходная накладная может содержать множество деталей. Использование cascade = CascadeType.ALL и fetch = FetchType.EAGER означает, что операции с объектом Grn каскадно распространяются на объекты GrnDtl, и детали загружаются жадно.
Конструкторы:

Конструктор без параметров: необходим для JPA.
Конструктор с параметрами: позволяет инициализировать все поля класса при создании объекта.
Геттеры и сеттеры: Предоставляют доступ к полям класса.

Этот класс используется для представления приходной накладной в системе складского учета, включая информацию о заказе на покупку и деталях приходной накладной.

*/

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name="grn_tab")
public class Grn {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="grn_id_col")
	private Integer id;
	
	@Column(name="grn_code_col")
	private String grnCode;
	
	@Column(name="grn_type_col")
	private String grnType;
	
	@Column(name="grn_desc_col")
	private String grnDescription;
	

	@ManyToOne
	@JoinColumn(name="po_id_fk_col",unique = true)
	private PurchaseOrder po;
	

	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	@JoinColumn(name="grn_id_fk_col")
	private Set<GrnDtl> dtls;

	public Object getPo() {
		// TODO
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGrnCode() {
		return grnCode;
	}

	public void setGrnCode(String grnCode) {
		this.grnCode = grnCode;
	}

	public String getGrnType() {
		return grnType;
	}

	public void setGrnType(String grnType) {
		this.grnType = grnType;
	}

	public String getGrnDescription() {
		return grnDescription;
	}

	public void setGrnDescription(String grnDescription) {
		this.grnDescription = grnDescription;
	}

	public Set<GrnDtl> getDtls() {
		return dtls;
	}

	public void setDtls(Set<GrnDtl> dtls) {
		this.dtls = dtls;
	}

	public void setPo(PurchaseOrder po) {
		this.po = po;
	}

	public Grn(Integer id, String grnCode, String grnType, String grnDescription, PurchaseOrder po, Set<GrnDtl> dtls) {
		super();
		this.id = id;
		this.grnCode = grnCode;
		this.grnType = grnType;
		this.grnDescription = grnDescription;
		this.po = po;
		this.dtls = dtls;
	}

	public Grn() {
		super();
		// TODO
	}
	
	
}

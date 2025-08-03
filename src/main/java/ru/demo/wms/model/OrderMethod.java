package ru.demo.wms.model;
/*Класс OrderMethod представляет метод заказа в системе управления складом. 
Это может включать различные способы, которыми заказы могут быть приняты и обработаны в системе. 
Давайте рассмотрим его структуру:

Аннотации @Entity и @Table(name="order_method_tab") говорят о том, 
что класс является сущностью JPA и его экземпляры должны быть связаны
с таблицей order_method_tab в базе данных.
Атрибуты класса:

id: Уникальный идентификатор метода заказа. Генерируется с использованием 
заданной последовательности om_sql_gen.
orderMode: Режим заказа, например, "ONLINE" или "OFFLINE".
orderCode: Уникальный код метода заказа.
orderType: Тип заказа, который может быть, например, "SALE" или "PURCHASE".
orderDesc: Описание метода заказа.
orderAcpt: Коллекция способов, которыми заказ может быть принят (например, "EMAIL", 
"PHONE" и так далее). Это значение ассоциируется с дочерней таблицей om_acpt_tab 
через @ElementCollection и @CollectionTable.
Конструкторы:

Предоставляются как пустой конструктор (требуется JPA) и конструктор со всеми 
параметрами для удобства инициализации объектов.
Геттеры и сеттеры: Методы для доступа и изменения атрибутов сущности.

Этот класс позволяет управлять различными способами оформления заказов в системе, 
включая определение способов приема заказов, что обеспечивает гибкость в обработке
заказов и их источниках. Важно отметить, что для каждого метода заказа можно указать 
множество способов приема заказов, что добавляет дополнительную универсальность 
в учет и обработку заказов в системе управления складом.
*/
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="order_method_tab")
public class OrderMethod {
	@Id
	@GeneratedValue(generator = "om_gen")
	@SequenceGenerator(name = "om_gen",sequenceName = "om_sql_gen")
	@Column(name="om_id_col")
	private Integer id;
	
	@Column(name="om_mode_col")
	private String orderMode;
	
	@Column(name="om_code_col")
	private String orderCode;
	
	@Column(name="om_type_col")
	private String orderType;
	
	@Column(name="om_desc_col")
	private String orderDesc;
	
	@ElementCollection
	@CollectionTable(
			name="om_acpt_tab",
			joinColumns =  @JoinColumn(name="om_id_col")
			)
	@Column(name="om_acpt_col")
	private Set<String> orderAcpt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public Set<String> getOrderAcpt() {
		return orderAcpt;
	}

	public void setOrderAcpt(Set<String> orderAcpt) {
		this.orderAcpt = orderAcpt;
	}

	public OrderMethod(Integer id, String orderMode, String orderCode, String orderType, String orderDesc,
			Set<String> orderAcpt) {
		super();
		this.id = id;
		this.orderMode = orderMode;
		this.orderCode = orderCode;
		this.orderType = orderType;
		this.orderDesc = orderDesc;
		this.orderAcpt = orderAcpt;
	}

	public OrderMethod() {
		super();
		// TODO
	}
	
	
}

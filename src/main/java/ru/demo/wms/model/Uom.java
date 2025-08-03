package ru.demo.wms.model;
/*
Класс Uom (Unit of Measurement, Единица Измерения) описывает модель единиц измерения в системе управления складом. Эта модель позволяет стандартизировать и систематизировать единицы измерения товаров, что обеспечивает унификацию данных и упрощает учет и анализ товарных потоков.

Основные атрибуты:

id: Уникальный идентификатор единицы измерения в базе данных.
uomType: Тип единицы измерения (например, масса, объем, длина).
uomModel: Конкретная модель или наименование единицы измерения (например, килограмм, литр, метр).
uomDesc: Описание единицы измерения, которое может включать дополнительные характеристики или особенности единицы измерения.
Применение в системе:
Эта модель используется для определения и учета различных единиц измерения товаров и материалов на складе, что важно для точного учета запасов, формирования заказов и проведения инвентаризации. Единицы измерения могут быть применены к различным категориям товаров, в зависимости от их физических характеристик.

Примеры использования:

Учет товаров: При добавлении нового товара в систему указывается его единица измерения, что позволяет точно учитывать и анализировать остатки на складе.
Формирование отчетов: Для формирования отчетов о движении товаров, инвентаризации и анализа запасов важно использовать стандартизированные единицы измерения.
Операции с товарными партиями: При проведении операций покупки, продажи, перемещения товаров между складами и других логистических операций необходимо учитывать единицы измерения товаров.
Взаимодействие с другими моделями:
Uom может быть связан с такими моделями как Part (детали, компоненты), PurchaseOrder (заказ на покупку) и SaleOrder (заказ на продажу), обеспечивая тем самым унифицированное использование единиц измерения в различных частях системы

*/
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name="uom_tab")
public class Uom {
	@Id
	@GeneratedValue(
			generator = "uom_gen")
	@SequenceGenerator(
			name = "uom_gen", 
			sequenceName = "uom_seq")
	@Column(name="uom_id_col")
	private Integer id;

	@Column(name="uom_type_col",
			nullable = false,
			length = 12)
	private String uomType;

	@Column(name="uom_model_col",
			nullable = false,
			length = 16,
			unique = true)
	private String uomModel;

	@Column(name="uom_desc_col",
			nullable = false,
			length = 110)
	private String uomDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUomType() {
		return uomType;
	}

	public void setUomType(String uomType) {
		this.uomType = uomType;
	}

	public String getUomModel() {
		return uomModel;
	}

	public void setUomModel(String uomModel) {
		this.uomModel = uomModel;
	}

	public String getUomDesc() {
		return uomDesc;
	}

	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	public Uom(Integer id, String uomType, String uomModel, String uomDesc) {
		super();
		this.id = id;
		this.uomType = uomType;
		this.uomModel = uomModel;
		this.uomDesc = uomDesc;
	}

	public Uom() {
		super();
		// TODO
	}
	
	
}

package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Сущность Part представляет деталь (или компонент), хранящуюся на складе.
 * Содержит информацию о стоимости, габаритах, единице измерения и связанном методе заказа.
 */
@Entity
@Table(name = "part_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Part {

	/** Уникальный идентификатор детали. Генерируется автоматически. */
	@Id
	@GeneratedValue(generator = "part_gen")
	@SequenceGenerator(name = "part_gen", sequenceName = "part_seq")
	@Column(name = "part_id_col")
	private Integer id;

	/** Код детали, используемый для идентификации. */
	@NotBlank(message = "Код детали обязателен")
	@Column(name = "part_code_col")
	private String partCode;

	/** Валюта, в которой указана стоимость детали (например, RUB, USD). */
	@NotBlank(message = "Необходимо указать валюту")
	@Column(name = "part_curr_col")
	private String partCurrency;

	/** Базовая стоимость детали. */
	@NotNull(message = "Стоимость не может быть null")
	@DecimalMin(value = "0.0", inclusive = false, message = "Стоимость должна быть положительной")
	@Column(name = "part_cost_col")
	private Double partBaseCost;

	/** Ширина детали в выбранной единице измерения. */
	@DecimalMin(value = "0.0", inclusive = false, message = "Ширина должна быть положительной")
	@Column(name = "part_wid_col")
	private Double partWid;

	/** Высота детали. */
	@DecimalMin(value = "0.0", inclusive = false, message = "Высота должна быть положительной")
	@Column(name = "part_ht_col")
	private Double partHt;

	/** Длина детали. */
	@DecimalMin(value = "0.0", inclusive = false, message = "Длина должна быть положительной")
	@Column(name = "part_len_col")
	private Double partLen;

	/** Текстовое описание детали. */
	@Column(name = "part_desc_col")
	private String partDesc;

	/** Связь с единицей измерения (например, кг, м, шт). */
	@ManyToOne
	@JoinColumn(name = "uom_id_fk_col")
	private Uom uom;

	/** Метод заказа, связанный с данной деталью. */
	@ManyToOne
	@JoinColumn(name = "om_id_fk_col")
	private OrderMethod om;
}

package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Сущность, представляющая детальную строку приходной накладной.
 * Отражает информацию о конкретной части (детали), поступившей на склад.
 */
@Entity
@Table(name = "grn_dtl_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrnDtl {

	/**
	 * Уникальный идентификатор строки детализации.
	 * Генерируется автоматически при сохранении.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grn_dtl_id_col")
	private Integer id;

	/**
	 * Код полученной детали или части (например, серийный номер).
	 */
	@NotBlank(message = "Код детали не должен быть пустым")
	@Column(name = "grn_dtl_code_col")
	private String partCode;

	/**
	 * Базовая стоимость одной единицы полученной детали.
	 */
	@NotNull(message = "Стоимость не может быть null")
	@Min(value = 0, message = "Стоимость должна быть неотрицательной")
	@Column(name = "grn_dtl_cost_col")
	private Double baseCost;

	/**
	 * Количество полученных единиц данной детали.
	 */
	@NotNull(message = "Количество не может быть null")
	@Min(value = 1, message = "Количество должно быть не менее 1")
	@Column(name = "grn_dtl_qty_col")
	private Integer qty;

	/**
	 * Статус строки детализации (например, ACCEPTED, REJECTED).
	 */
	@NotBlank(message = "Статус не должен быть пустым")
	@Column(name = "grn_dtl_status_col")
	private String status;
}

package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность {@code ShipmentType} представляет собой тип отгрузки,
 * используемый в системе WMS для определения характеристик доставки:
 * способ, код, доступность, категория и описание.
 *
 * <p>Эта модель применяется при формировании заказов и выборе способа доставки.</p>
 */
@Entity
@Table(name = "shipment_type_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentType {

	/**
	 * Уникальный идентификатор типа отгрузки.
	 */
	@Id
	@GeneratedValue(generator = "ship_type_gen")
	@SequenceGenerator(name = "ship_type_gen", sequenceName = "ship_type_seq")
	@Column(name = "ship_id_col")
	private Integer id;

	/**
	 * Режим отгрузки (например, Air, Sea, Rail).
	 */
	@Column(name = "ship_mode_col", nullable = false, length = 10)
	private String shipMode;

	/**
	 * Уникальный код отгрузки.
	 */
	@Column(name = "ship_code_col", nullable = false, length = 10, unique = true)
	private String shipCode;

	/**
	 * Доступность отгрузки (например, "Yes"/"No").
	 */
	@Column(name = "ship_enbl_col", nullable = false, length = 5)
	private String enbleShip;

	/**
	 * Класс отгрузки (например, A, B, C).
	 */
	@Column(name = "ship_grade_col", nullable = false, length = 3)
	private String shipGrade;

	/**
	 * Описание типа отгрузки.
	 */
	@Column(name = "ship_desc_col", nullable = false, length = 100)
	private String shipDesc;
}

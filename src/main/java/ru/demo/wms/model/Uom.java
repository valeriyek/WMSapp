package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс {@code Uom} (Unit of Measurement — единица измерения) представляет собой
 * справочник единиц измерения, используемых в WMS-системе для стандартизации
 * товарных характеристик: массы, объема, длины и других параметров.
 *
 * <p>Применяется при работе с заказами, остатками, инвентаризациями и аналитикой.</p>
 */
@Entity
@Table(name = "uom_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Uom {

	/**
	 * Уникальный идентификатор единицы измерения.
	 */
	@Id
	@GeneratedValue(generator = "uom_gen")
	@SequenceGenerator(name = "uom_gen", sequenceName = "uom_seq")
	@Column(name = "uom_id_col")
	private Integer id;

	/**
	 * Тип единицы измерения (например, "Mass", "Volume").
	 */
	@Column(name = "uom_type_col", nullable = false, length = 12)
	private String uomType;

	/**
	 * Модель или название единицы (например, "kg", "m", "litre").
	 */
	@Column(name = "uom_model_col", nullable = false, length = 16, unique = true)
	private String uomModel;

	/**
	 * Описание единицы измерения (например, "Килограмм — мера массы").
	 */
	@Column(name = "uom_desc_col", nullable = false, length = 110)
	private String uomDesc;
}

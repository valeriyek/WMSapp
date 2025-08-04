package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность {@code ShipingDtl} представляет собой деталь отгрузки в системе WMS.
 * Используется для хранения информации о товарных позициях в конкретной отгрузке:
 * их количестве, стоимости и статусе обработки.
 *
 * <p>Связана с {@link Shiping} через внешний ключ в таблице {@code shiping_dtl_tab}.</p>
 */
@Entity
@Table(name = "shiping_dtl_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipingDtl {

	/**
	 * Уникальный идентификатор детали отгрузки.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shiping_dtl_id")
	private Integer id;

	/**
	 * Код товара, входящего в отгрузку.
	 */
	@Column(name = "shiping_dtl_code", nullable = false)
	private String partCode;

	/**
	 * Стоимость одной единицы товара.
	 */
	@Column(name = "shiping_dtl_cost", nullable = false)
	private Double baseCost;

	/**
	 * Количество товара, отгружаемое в данной позиции.
	 */
	@Column(name = "shiping_dtl_qty", nullable = false)
	private Integer qty;

	/**
	 * Статус отгрузки позиции (например, "отгружено", "возврат").
	 */
	@Column(name = "shiping_dtl_status")
	private String status;
}

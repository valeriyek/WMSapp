package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс SaleOrderDetails представляет собой модель детализации заказа на продажу
 * в системе управления складом. Используется для хранения информации о количестве
 * единиц определённого товара, сделанных в рамках конкретного заказа на продажу.
 *
 * <p><b>Связи:</b></p>
 * <ul>
 *     <li>{@link Part} — товар, связанный с данной позицией заказа</li>
 *     <li>{@link SaleOrder} — заказ, которому принадлежит данная позиция</li>
 * </ul>
 */
@Entity
@Table(name = "sale_order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleOrderDetails {

	/**
	 * Уникальный идентификатор детали заказа на продажу.
	 */
	@Id
	@GeneratedValue(generator = "sale_order_dtl_gen")
	@SequenceGenerator(name = "sale_order_dtl_gen", sequenceName = "sale_order_dtl_seq")
	@Column(name = "sale_ordr_dtl_id")
	private Integer id;

	/**
	 * Количество единиц товара в данной позиции заказа.
	 */
	@Column(name = "sale_order_dtl_qty")
	private Integer qty;

	/**
	 * Связь с конкретным товаром (Part).
	 */
	@ManyToOne
	@JoinColumn(name = "part_id_fk")
	private Part part;

	/**
	 * Связь с заказом на продажу (SaleOrder), к которому относится эта деталь.
	 */
	@ManyToOne
	@JoinColumn(name = "sale_order_id_fk")
	private SaleOrder saleOrder;
}

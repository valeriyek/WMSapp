package ru.demo.wms.model;

import lombok.*;
import ru.demo.wms.consts.SaleOrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Сущность SaleOrder представляет заказ на продажу в системе управления складом.
 */
@Entity
@Table(name = "saleorder_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleOrder {

	/** Уникальный идентификатор заказа на продажу. */
	@Id
	@GeneratedValue(generator = "sale_ord_gen")
	@SequenceGenerator(name = "sale_ord_gen", sequenceName = "sale_ord_seq")
	@Column(name = "sale_ord_id")
	private Integer id;

	/** Уникальный код заказа на продажу. */
	@NotBlank(message = "Код заказа не может быть пустым")
	@Column(name = "sale_ord_code")
	private String orderCode;

	/** Справочный номер заказа. */
	@NotBlank(message = "Справочный номер не может быть пустым")
	@Column(name = "sale_ord_refno")
	private String refNum;

	/** Режим работы с запасами (например, 'IN STOCK', 'ON DEMAND'). */
	@NotBlank(message = "Режим запасов не может быть пустым")
	@Column(name = "sale_ord_stockmode")
	private String stockMode;

	/** Источник запасов (например, 'WAREHOUSE', 'VENDOR'). */
	@NotBlank(message = "Источник запасов не может быть пустым")
	@Column(name = "sale_ord_stocksource")
	private String stockSource;

	/** Статус заказа (например, 'OPEN', 'SHIPPED', 'CANCELLED'). */
	@NotBlank(message = "Статус заказа не может быть пустым")
	@Column(name = "sale_ord_status")
	private String status;

	/** Дополнительное описание заказа. */
	@Column(name = "sale_ord_desc")
	private String description;

	/** Тип отправки (Shipment Type), связанный с заказом. */
	@NotNull(message = "Тип отправки не может быть пустым")
	@ManyToOne
	@JoinColumn(name = "st_id_fk")
	private ShipmentType st;

	/** Клиент, связанный с заказом. */
	@NotNull(message = "Клиент не может быть пустым")
	@ManyToOne
	@JoinColumn(name = "wh_id_fk")
	private WhUserType customer;
}

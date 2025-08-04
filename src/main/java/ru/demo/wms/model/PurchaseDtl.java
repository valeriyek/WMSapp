package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Сущность PurchaseDtl представляет собой запись о позиции в заказе на покупку.
 * Хранит информацию о количестве и части (товаре), сделанных в рамках одного заказа.
 */
@Entity
@Table(name = "po_dtl_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDtl {

	/** Уникальный идентификатор позиции в заказе на покупку. */
	@Id
	@GeneratedValue(generator = "pdtl_gen")
	@SequenceGenerator(name = "pdtl_gen", sequenceName = "pdtl_seq")
	@Column(name = "po_dlt_id_col")
	private Integer id;

	/** Количество заказанных единиц данной части. */
	@NotNull(message = "Количество не может быть пустым")
	@Min(value = 1, message = "Количество должно быть не менее 1")
	@Column(name = "po_dlt_qty_col")
	private Integer qty;

	/** Заказываемая часть (товар/компонент). */
	@ManyToOne
	@JoinColumn(name = "part_id_fk_col")
	private Part part;

	/** Заказ на покупку, к которому относится эта позиция. */
	@ManyToOne
	@JoinColumn(name = "po_id_fk_col")
	private PurchaseOrder po;
}

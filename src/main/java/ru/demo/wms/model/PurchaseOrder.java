package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Сущность PurchaseOrder представляет заказ на покупку.
 * Хранит информацию о коде заказа, справочном номере, статусах и связанных сущностях.
 */
@Entity
@Table(name = "po_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

	/** Уникальный идентификатор заказа на покупку. */
	@Id
	@GeneratedValue(generator = "po_gen")
	@SequenceGenerator(name = "po_gen", sequenceName = "po_seq")
	@Column(name = "po_id_col")
	private Integer id;

	/** Код заказа на покупку. */
	@NotBlank(message = "Код заказа не может быть пустым")
	@Column(name = "po_code_col")
	private String orderCode;

	/** Справочный номер заказа (RefNum). */
	@NotBlank(message = "Справочный номер не может быть пустым")
	@Column(name = "po_ref_col")
	private String refNum;

	/** Статус проверки качества. */
	@NotBlank(message = "Статус проверки качества не может быть пустым")
	@Column(name = "po_qlty_col")
	private String qltyChck;

	/** Общий статус заказа. */
	@NotBlank(message = "Статус заказа не может быть пустым")
	@Column(name = "po_status_col")
	private String status;

	/** Текстовое описание заказа. */
	@Column(name = "po_desc_col")
	private String description;

	/** Тип отправления, связанный с заказом. */
	@NotNull(message = "Тип отправления должен быть указан")
	@ManyToOne
	@JoinColumn(name = "st_id_fk_col")
	private ShipmentType st;

	/** Поставщик (пользователь склада), связанный с заказом. */
	@NotNull(message = "Поставщик должен быть указан")
	@ManyToOne
	@JoinColumn(name = "wh_ven_id_fk_col")
	private WhUserType vendor;
}

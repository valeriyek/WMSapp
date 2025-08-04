package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Сущность, представляющая приходную накладную (GRN - Goods Receipt Note).
 * Используется для учета поступления товаров по заказам на закупку.
 */
@Entity
@Table(name = "grn_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grn {

	/**
	 * Уникальный идентификатор приходной накладной.
	 * Генерируется автоматически при сохранении.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grn_id_col")
	private Integer id;

	/**
	 * Код приходной накладной.
	 * Используется для идентификации и отображения GRN.
	 */
	@NotBlank(message = "Код GRN не должен быть пустым")
	@Column(name = "grn_code_col")
	private String grnCode;

	/**
	 * Тип приходной накладной (например, внутренняя, внешняя).
	 */
	@NotBlank(message = "Тип GRN не должен быть пустым")
	@Column(name = "grn_type_col")
	private String grnType;

	/**
	 * Описание приходной накладной (необязательное поле).
	 */
	@Column(name = "grn_desc_col")
	private String grnDescription;

	/**
	 * Ссылка на заказ на закупку (PurchaseOrder), по которому осуществляется приемка.
	 * Каждая приходная накладная связана с одним заказом.
	 */
	@NotNull(message = "GRN должен быть привязан к заказу на закупку")
	@ManyToOne
	@JoinColumn(name = "po_id_fk_col", unique = true)
	private PurchaseOrder po;

	/**
	 * Список деталей приходной накладной (товаров, поступивших по GRN).
	 * Используется каскадная операция и жадная загрузка.
	 */
	@OneToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER
	)
	@JoinColumn(name = "grn_id_fk_col")
	private Set<GrnDtl> dtls;
}

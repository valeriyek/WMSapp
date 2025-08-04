package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Модель сущности "Shiping" — представляет информацию об отгрузке товаров клиенту.
 * Используется для управления логистикой и привязана к заказу на продажу (SaleOrder).
 */
@Entity
@Table(name = "shiping_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shiping {

	/**
	 * Уникальный идентификатор отгрузки.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shiping_id")
	private Integer id;

	/**
	 * Код отгрузки — используется для идентификации.
	 */
	@Column(name = "shiping_code", nullable = false)
	private String shipingCode;

	/**
	 * Внутренний справочный номер отгрузки.
	 */
	@Column(name = "shiping_ref_num")
	private String shipngRefNum;

	/**
	 * Номер справки, предоставленный курьером.
	 */
	@Column(name = "courier_ref_num")
	private String courierRefNum;

	/**
	 * Контактные данные для связи.
	 */
	@Column(name = "contact_details")
	private String contactDetails;

	/**
	 * Дополнительное описание отгрузки.
	 */
	@Column(name = "shiping_desc")
	private String description;

	/**
	 * Связанный заказ на продажу (один к одному).
	 */
	@ManyToOne
	@JoinColumn(name = "so_id_fk", unique = true)
	private SaleOrder so;

	/**
	 * Детализация отгрузки — список позиций отгрузки.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "shiping_id_fk")
	private Set<ShipingDtl> dtls;

}

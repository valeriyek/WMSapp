package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Сущность, представляющая метод заказа в системе управления складом (WMS).
 * Позволяет задать режим, тип, код, описание и способы приёма заказов.
 */
@Entity
@Table(name = "order_method_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMethod {

	/**
	 * Уникальный идентификатор метода заказа.
	 * Генерируется на основе последовательности om_sql_gen.
	 */
	@Id
	@GeneratedValue(generator = "om_gen")
	@SequenceGenerator(name = "om_gen", sequenceName = "om_sql_gen")
	@Column(name = "om_id_col")
	private Integer id;

	/**
	 * Режим заказа, например: ONLINE, OFFLINE.
	 */
	@NotBlank(message = "Режим заказа не должен быть пустым")
	@Column(name = "om_mode_col")
	private String orderMode;

	/**
	 * Уникальный код метода заказа.
	 */
	@NotBlank(message = "Код заказа не должен быть пустым")
	@Column(name = "om_code_col")
	private String orderCode;

	/**
	 * Тип заказа: SALE, PURCHASE и т.п.
	 */
	@NotBlank(message = "Тип заказа не должен быть пустым")
	@Column(name = "om_type_col")
	private String orderType;

	/**
	 * Описание метода заказа.
	 */
	@NotBlank(message = "Описание не должно быть пустым")
	@Column(name = "om_desc_col")
	private String orderDesc;

	/**
	 * Набор способов приёма заказов, например: EMAIL, WEB, PHONE.
	 * Сохраняется в отдельной таблице om_acpt_tab.
	 */
	@ElementCollection
	@CollectionTable(
			name = "om_acpt_tab",
			joinColumns = @JoinColumn(name = "om_id_col")
	)
	@Column(name = "om_acpt_col")
	@NotEmpty(message = "Необходимо указать хотя бы один способ приёма заказа")
	private Set<String> orderAcpt;
}

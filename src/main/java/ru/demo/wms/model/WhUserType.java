package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность {@code WhUserType} представляет пользователя склада (клиента или поставщика)
 * с различными параметрами идентификации и контактной информацией.
 *
 * <p>Связывается с таблицей {@code wh_user_type_tab} и используется для
 * регистрации и управления информацией об участниках складской логистики.</p>
 */
@Entity
@Table(name = "wh_user_type_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WhUserType {

	/** Уникальный идентификатор пользователя склада */
	@Id
	@GeneratedValue(generator = "whusr_gen")
	@SequenceGenerator(name = "whusr_gen", sequenceName = "whgen_seq")
	@Column(name = "wh_usr_id_col")
	private Integer id;

	/** Тип пользователя (например, поставщик, клиент) */
	@Column(name = "wh_usr_type_col", nullable = false)
	private String userType;

	/** Уникальный код пользователя */
	@Column(name = "wh_usr_code_col", nullable = false, unique = true)
	private String userCode;

	/** Назначение пользователя (для чего используется — продажи, закупки и т.д.) */
	@Column(name = "wh_usr_for_col", nullable = false)
	private String userFor;

	/** Email пользователя */
	@Column(name = "wh_usr_email_col", nullable = false)
	private String userEmail;

	/** Контактный номер пользователя */
	@Column(name = "wh_usr_contact_col", nullable = false)
	private String userContact;

	/** Тип удостоверяющего документа (паспорт, ИНН и т.д.) */
	@Column(name = "wh_usr_id_type_col", nullable = false)
	private String userIdType;

	/** Альтернативная информация, если документ "другой" */
	@Column(name = "wh_usr_other_col")
	private String ifOther;

	/** Номер удостоверяющего документа */
	@Column(name = "wh_usr_id_num_col", nullable = false)
	private String userIdNum;
}

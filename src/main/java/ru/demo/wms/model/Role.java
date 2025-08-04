package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.demo.wms.consts.RoleType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Сущность Role представляет роль пользователя в системе.
 * Используется для разграничения прав доступа (например, ADMIN, APPUSER).
 */
@Entity
@Table(name = "roles_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	/** Уникальный идентификатор роли. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id_col")
	private Integer id;

	/** Тип роли в формате перечисления (например, ADMIN, APPUSER). */
	@NotNull(message = "Роль не может быть пустой")
	@Enumerated(EnumType.STRING)
	@Column(name = "role_name_col")
	private RoleType role;
}

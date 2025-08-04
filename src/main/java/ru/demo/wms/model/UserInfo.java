package ru.demo.wms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.demo.wms.consts.UserMode;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс {@code UserInfo} представляет собой модель пользователя в WMS-системе.
 * Хранит данные об имени, email, пароле, OTP-коде, статусе и ролях пользователя.
 *
 * <p>Используется для авторизации, разграничения доступа и регистрации действий в системе.</p>
 */
@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

	/**
	 * Уникальный идентификатор пользователя.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Имя пользователя.
	 */
	@Column(nullable = false, length = 50)
	private String name;

	/**
	 * Email, используется как логин.
	 */
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	/**
	 * Хэш пароля пользователя.
	 */
	@Column(nullable = false)
	private String password;

	/**
	 * Одноразовый код подтверждения (OTP) — используется при регистрации или восстановлении пароля.
	 */
	private String otp;

	/**
	 * Текущий статус пользователя (например, ENABLED, DISABLED).
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserMode mode;

	/**
	 * Роли пользователя — определяют доступ к различным функциям системы.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles;
}

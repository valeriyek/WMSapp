package ru.demo.wms.model;
/*
Этот Java-класс, WhUserType, представляет собой модельный класс, который обычно используется в приложениях, следующих фреймворку Spring, особенно в тех, что используют Spring Data JPA (Java Persistence API) для операций с базой данных. Класс аннотирован таким образом, что его можно сразу использовать с реляционной базой данных, используя аннотации JPA для сопоставления полей класса с таблицей в базе данных. Вот разбор его ключевых компонентов:

Аннотации
@Entity: Помечает этот класс как сущность JPA, что означает его сопоставление с таблицей в базе данных.
@Table(name="wh_user_type_tab"): Указывает, что сущность должна быть сопоставлена с таблицей wh_user_type_tab в базе данных.
@Id: Указывает, что поле отмечено этой аннотацией, является первичным ключом сущности.
@Column: Определяет имя столбца в таблице базы данных, с которым будет сопоставлено поле.
@GeneratedValue: Указывает на стратегию генерации значения идентификаторов. В этом случае используется пользовательский генератор whusr_gen.
@SequenceGenerator: Определяет генератор последовательностей для генерации уникальных идентификаторов.
Поля
Класс содержит поля, представляющие различные атрибуты типа пользователя склада (WhUserType), такие как id, userType, userCode, и так далее, каждое из которых отображается на столбец в таблице базы данных.

Конструкторы
В классе определены два конструктора: один без параметров (требование JPA) и один с параметрами для инициализации всех полей сущности.

Геттеры и сеттеры
Для каждого поля в классе предоставлены геттеры и сеттеры, позволяющие получать и устанавливать значения этих полей. Это стандартный подход в Java-бинах и сущностях JPA, позволяющий взаимодействовать с атрибутами объекта.

Общие наблюдения
Класс WhUserType представляет собой хорошо структурированный пример модели данных, пригодной для использования в проектах на основе Spring и JPA. Он иллюстрирует, как можно использовать аннотации JPA для сопоставления объектов Java с таблицами реляционной базы данных, а также как организовать базовые операции CRUD (создание, чтение, обновление, удаление) с помощью геттеров и сеттеров для управления состоянием объекта.
*/
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
 
@Entity
@Table(name="wh_user_type_tab")
public class WhUserType {
	
	@Id
	@Column(name="wh_usr_id_col")
	@GeneratedValue(generator = "whusr_gen")
	@SequenceGenerator(name = "whusr_gen", sequenceName = "whgen_seq")
	private Integer id;
	
	@Column(name="wh_usr_type_col")
	private String userType;
	
	@Column(name="wh_usr_code_col")
	private String userCode;
	
	@Column(name="wh_usr_for_col")
	private String userFor;
	
	@Column(name="wh_usr_email_col")
	private String userEmail;
	
	@Column(name="wh_usr_contact_col")
	private String userContact;
	
	@Column(name="wh_usr_id_type_col")
	private String userIdType;
	
	@Column(name="wh_usr_other_col")
	private String ifOther;
	
	@Column(name="wh_usr_id_num_col")
	private String userIdNum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserFor() {
		return userFor;
	}

	public void setUserFor(String userFor) {
		this.userFor = userFor;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserContact() {
		return userContact;
	}

	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}

	public String getUserIdType() {
		return userIdType;
	}

	public void setUserIdType(String userIdType) {
		this.userIdType = userIdType;
	}

	public String getIfOther() {
		return ifOther;
	}

	public void setIfOther(String ifOther) {
		this.ifOther = ifOther;
	}

	public String getUserIdNum() {
		return userIdNum;
	}

	public void setUserIdNum(String userIdNum) {
		this.userIdNum = userIdNum;
	}

	public WhUserType(Integer id, String userType, String userCode, String userFor, String userEmail,
			String userContact, String userIdType, String ifOther, String userIdNum) {
		super();
		this.id = id;
		this.userType = userType;
		this.userCode = userCode;
		this.userFor = userFor;
		this.userEmail = userEmail;
		this.userContact = userContact;
		this.userIdType = userIdType;
		this.ifOther = ifOther;
		this.userIdNum = userIdNum;
	}

	public WhUserType() {
		super();
		// TODO
	}
	
	
}

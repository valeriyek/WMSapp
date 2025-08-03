package ru.demo.wms.model;
/*
Класс Document определяет модель для хранения документов в базе данных с использованием JPA (Java Persistence API). 
Каждый экземпляр класса Document представляет собой отдельный документ, который может быть сохранен в базе данных. 
Вот более подробное описание его компонентов:

Аннотация @Entity: Указывает, что класс является сущностью JPA, а его экземпляры могут 
быть сохранены в базе данных.
Аннотация @Table(name="doc_tab"): Определяет, что сущности данного класса будут храниться 
в таблице doc_tab базы данных.
Поля класса:
docId: Идентификатор документа. Аннотирован @Id, что указывает на его роль первичного ключа в таблице. 
Аннотация @Column(name="doc_id_col") задает имя столбца, в котором будет храниться это поле.
docName: Название документа, сохраняемое в столбце doc_name_col.
docData: Данные документа, представленные в виде массива байтов. 
Аннотация @Lob указывает, что это большой объект, который может 
хранить большие объемы данных, например, файл PDF или изображение.
Конструкторы:
Конструктор без параметров: необходим для JPA.
Конструктор с параметрами: позволяет создать и инициализировать экземпляр класса 
Document, указав его docId, docName, и docData.
Геттеры и сеттеры: Методы для доступа и модификации полей экземпляра класса. Эти методы п
озволяют читать и изменять значения docId, docName, и docData.
Класс Document используется для работы с документами на уровне базы данных, позволяя сохранять, 
изменять и извлекать информацию о документах из таблицы doc_tab.

*/
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name="doc_tab")
public class Document {
	@Id
	@Column(name="doc_id_col")
	private Long docId;

	@Column(name="doc_name_col")
	private String docName;
	
	@Lob
	@Column(name="doc_data_col")
	private byte[] docData;

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public byte[] getDocData() {
		return docData;
	}

	public void setDocData(byte[] docData) {
		this.docData = docData;
	}

	public Document(Long docId, String docName, byte[] docData) {
		super();
		this.docId = docId;
		this.docName = docName;
		this.docData = docData;
	}

	public Document() {
		super();
		// TODO
	}

	

}

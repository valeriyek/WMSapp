package ru.demo.wms.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность, представляющая документ в базе данных.
 */
@Entity
@Table(name = "doc_tab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

	/**
	 * Уникальный идентификатор документа.
	 */
	@Id
	@Column(name = "doc_id_col")
	private Long docId;

	/**
	 * Название документа.
	 */
	@Column(name = "doc_name_col")
	@NotBlank(message = "Название документа не должно быть пустым")
	@Size(max = 255, message = "Название не должно превышать 255 символов")
	private String docName;

	/**
	 * Данные документа в виде массива байтов.
	 */
	@Lob
	@Column(name = "doc_data_col")
	@NotNull(message = "Файл документа обязателен для загрузки")
	private byte[] docData;
}

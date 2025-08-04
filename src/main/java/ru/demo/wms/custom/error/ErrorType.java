package ru.demo.wms.custom.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для представления информации об ошибке.
 * Используется для формирования стандартного ответа с описанием ошибки.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorType {
	private String dateTime; // Дата и время возникновения ошибки
	private String module;   // Модуль, в котором произошла ошибка
	private String message;  // Сообщение об ошибке
}

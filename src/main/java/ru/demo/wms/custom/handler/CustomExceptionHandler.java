package ru.demo.wms.custom.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.demo.wms.custom.error.ErrorType;
import ru.demo.wms.exception.ShipmentTypeNotFoundException;
import ru.demo.wms.exception.UomNotFoundException;

/**
 * Глобальный обработчик исключений для REST-контроллеров.
 * Перехватывает специфические исключения и возвращает
 * структурированный ответ с деталями ошибки.
 */
@RestControllerAdvice
public class CustomExceptionHandler {

	/**
	 * Обрабатывает исключения, возникающие при отсутствии UOM (единицы измерения).
	 *
	 * @param unfe объект исключения UomNotFoundException
	 * @return ответ с информацией об ошибке и HTTP статусом 500
	 */
	@ExceptionHandler(UomNotFoundException.class)
	public ResponseEntity<ErrorType> handleUomNotFound(UomNotFoundException unfe) {
		return new ResponseEntity<>(
				new ErrorType(
						new Date().toString(),   // Время возникновения ошибки
						"UOM",                   // Название модуля, где произошла ошибка
						unfe.getMessage()        // Сообщение об ошибке
				),
				HttpStatus.INTERNAL_SERVER_ERROR
		);
	}

	/**
	 * Обрабатывает исключения, возникающие при отсутствии типа доставки.
	 *
	 * @param unfe объект исключения ShipmentTypeNotFoundException
	 * @return ответ с информацией об ошибке и HTTP статусом 500
	 */
	@ExceptionHandler(ShipmentTypeNotFoundException.class)
	public ResponseEntity<ErrorType> handleShipmentTypeNotFound(ShipmentTypeNotFoundException unfe) {
		return new ResponseEntity<>(
				new ErrorType(
						new Date().toString(),   // Время возникновения ошибки
						"SHIPMENT TYPE",         // Название модуля, где произошла ошибка
						unfe.getMessage()        // Сообщение об ошибке
				),
				HttpStatus.INTERNAL_SERVER_ERROR
		);
	}
}

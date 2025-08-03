package ru.demo.wms.custom.handler;
/*
Этот обработчик исключений CustomExceptionHandler использует @RestControllerAdvice, 
чтобы предоставить централизованный способ обработки исключений для вашего приложения. 
При возникновении исключений UomNotFoundException или ShipmentTypeNotFoundException, 
соответствующие методы в этом классе будут перехватывать их и возвращать клиенту подробную информацию об ошибке.

В каждом из методов обработчика исключений используется класс ErrorType для создания 
тела ответа, который включает в себя текущее время (new Date().toString()), модуль, 
где произошло исключение, и сообщение исключения.

@ExceptionHandler(UomNotFoundException.class): Этот метод обрабатывает исключения 
UomNotFoundException. Он создает объект ErrorType с сообщением об ошибке, указывая, 
что исключение произошло в модуле "UOM", и возвращает его в теле ответа со статусом 
HttpStatus.INTERNAL_SERVER_ERROR.

@ExceptionHandler(ShipmentTypeNotFoundException.class): Аналогично первому методу, 
но для обработки исключений ShipmentTypeNotFoundException. Ошибка указывает 
на проблему в модуле "SHIPMENT TYPE".

Каждый метод обрабатывает свой тип исключения, возвращая клиенту информативный ответ 
о произошедшей ошибке. Использование HttpStatus.INTERNAL_SERVER_ERROR говорит о том, 
что ошибка произошла на стороне сервера. Однако, в зависимости от контекста проблемы, возможно, 
будет более уместно использовать другой статус ответа, например, HttpStatus.NOT_FOUND для ситуаций, 
когда что-то не было найдено.
*/

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.demo.wms.custom.error.ErrorType;
import ru.demo.wms.exception.ShipmentTypeNotFoundException;
import ru.demo.wms.exception.UomNotFoundException;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(UomNotFoundException.class)
	public ResponseEntity<ErrorType> handleUomNotFound(
			UomNotFoundException unfe
			)
	{
		return new ResponseEntity<ErrorType>(
				new ErrorType(
						new Date().toString(), 
						"UOM", 
						unfe.getMessage()
						),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ShipmentTypeNotFoundException.class)
	public ResponseEntity<ErrorType> handleShipmentTypeNotFound(
			ShipmentTypeNotFoundException unfe
			)
	{
		return new ResponseEntity<ErrorType>(
				new ErrorType(
						new Date().toString(), 
						"SHIPMENT TYPE", 
						unfe.getMessage()
						),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

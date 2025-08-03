package ru.demo.wms.rest;
/*
Класс ShipmentTypeRestController определяет REST-контроллер в Spring Boot приложении, который управляет типами отправлений (ShipmentType). Этот контроллер взаимодействует с сервисным слоем через интерфейс IShipmentTypeService, предоставляя API для выполнения основных операций CRUD над данными типов отправлений.

Основные методы API:
GET /all: Возвращает список всех типов отправлений. Использует метод getAllShipmentTypes из сервиса для получения данных и возвращает их в виде ответа ResponseEntity со статусом OK.

GET /find/{id}: Возвращает тип отправления по заданному идентификатору (id). Этот метод извлекает один объект ShipmentType с помощью сервиса и возвращает его в ответе ResponseEntity.

POST /create: Создает новый тип отправления на основе данных, полученных в теле запроса (@RequestBody ShipmentType shipmentType). Сервис сохраняет объект в базе данных и возвращает сообщение о создании с идентификатором созданного объекта и статусом CREATED.

DELETE /remove/{id}: Удаляет тип отправления по заданному идентификатору. После успешного удаления возвращает сообщение об удалении и статус OK.

PUT /modify: Обновляет существующий тип отправления на основе предоставленных данных. Если обновление прошло успешно, возвращает сообщение об обновлении и статус OK.

Особенности реализации:
Автовнедрение зависимостей (@Autowired): Spring автоматически внедряет экземпляр сервиса IShipmentTypeService, упрощая доступ к операциям бизнес-логики.

Использование ResponseEntity: Для возврата клиенту данных вместе со статусом HTTP-ответа используется ResponseEntity, что позволяет контролировать содержимое ответа и HTTP-статусы непосредственно из методов контроллера.

Обработка путей и параметров: Аннотации @GetMapping, @PostMapping, @DeleteMapping, и @PutMapping определяют точки входа API для различных операций. @PathVariable и @RequestBody используются для извлечения данных из URL и тела запроса соответственно.

Применение:
Класс ShipmentTypeRestController является частью слоя представления в архитектуре приложения, предоставляя внешний API для взаимодействия с данными о типах отправлений. Этот подход разделения на слои позволяет организовать чистую архитектуру приложения, упрощая разработку, тестирование и поддержку.*/
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.demo.wms.model.ShipmentType;
import ru.demo.wms.service.IShipmentTypeService;

@RestController
@RequestMapping("/rest/api/st")
public class ShipmentTypeRestController {
	
	@Autowired
	private IShipmentTypeService service;


	@GetMapping("/all")
	public ResponseEntity<List<ShipmentType>> getAllShipmentTypes() {
		List<ShipmentType> list = service.getAllShipmentTypes();
		return ResponseEntity.ok(list);
	}


	
	@GetMapping("/find/{id}")
	public ResponseEntity<ShipmentType> getOneShipmentType(
			@PathVariable Integer id
			) 
	{
		ShipmentType st = service.getShipmentType(id);
		return ResponseEntity.ok(st);
	}
	

	
	@PostMapping("/create")
	public ResponseEntity<String> createShipmentType(
			@RequestBody ShipmentType shipmentType
			) 
	{
		Integer id = service.saveShipmentType(shipmentType);
		//String message = String.format("Shipment Type '%d' created!!",id);
		String message = "Shipment Type '"+id+"' created!!";
		return new ResponseEntity<>(message, HttpStatus.CREATED);//201
	}
	

	
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> deleteShipmentType(
			@PathVariable Integer id
			) 
	{
		service.deleteShipmentType(id);
		String message = "Shipment Type '"+id+"' Deleted!!";
		return ResponseEntity.ok(message);
	}
	

	
	@PutMapping("/modify")
	public ResponseEntity<String> updateShipmentType(
			@RequestBody ShipmentType shipmentType
			)
	{
		service.updateShipmentType(shipmentType);
		String message = "Shipment Type updated!!";
		return ResponseEntity.ok(message);
	}
}

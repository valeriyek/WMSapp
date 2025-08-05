package ru.demo.wms.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.demo.wms.model.ShipmentType;
import ru.demo.wms.service.IShipmentTypeService;

/**
 * REST-контроллер для управления типами отправлений (ShipmentType).
 * Предоставляет API для выполнения CRUD-операций.
 *
 * <p>Базовый URL: <code>/rest/api/st</code></p>
 *
 * Методы:
 * <ul>
 *     <li>GET /all — получить все типы отправлений</li>
 *     <li>GET /find/{id} — получить тип отправления по ID</li>
 *     <li>POST /create — создать новый тип отправления</li>
 *     <li>PUT /modify — обновить существующий тип отправления</li>
 *     <li>DELETE /remove/{id} — удалить тип отправления по ID</li>
 * </ul>
 */
@RestController
@RequestMapping("/rest/api/st")
public class ShipmentTypeRestController {

	@Autowired
	private IShipmentTypeService service;

	/**
	 * Получает список всех типов отправлений.
	 *
	 * @return список объектов ShipmentType
	 */
	@GetMapping("/all")
	public ResponseEntity<List<ShipmentType>> getAllShipmentTypes() {
		List<ShipmentType> list = service.getAllShipmentTypes();
		return ResponseEntity.ok(list);
	}

	/**
	 * Получает тип отправления по его ID.
	 *
	 * @param id идентификатор типа отправления
	 * @return объект ShipmentType
	 */
	@GetMapping("/find/{id}")
	public ResponseEntity<ShipmentType> getOneShipmentType(@PathVariable Integer id) {
		ShipmentType st = service.getShipmentType(id);
		return ResponseEntity.ok(st);
	}

	/**
	 * Создает новый тип отправления.
	 *
	 * @param shipmentType объект типа отправления
	 * @return сообщение с ID созданного объекта
	 */
	@PostMapping("/create")
	public ResponseEntity<String> createShipmentType(@RequestBody ShipmentType shipmentType) {
		Integer id = service.saveShipmentType(shipmentType);
		String message = "Shipment Type '" + id + "' created!!";
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	/**
	 * Удаляет тип отправления по ID.
	 *
	 * @param id идентификатор типа отправления
	 * @return сообщение об успешном удалении
	 */
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> deleteShipmentType(@PathVariable Integer id) {
		service.deleteShipmentType(id);
		String message = "Shipment Type '" + id + "' Deleted!!";
		return ResponseEntity.ok(message);
	}

	/**
	 * Обновляет существующий тип отправления.
	 *
	 * @param shipmentType обновленный объект типа отправления
	 * @return сообщение об успешном обновлении
	 */
	@PutMapping("/modify")
	public ResponseEntity<String> updateShipmentType(@RequestBody ShipmentType shipmentType) {
		service.updateShipmentType(shipmentType);
		String message = "Shipment Type updated!!";
		return ResponseEntity.ok(message);
	}
}

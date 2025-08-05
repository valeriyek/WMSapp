package ru.demo.wms.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.demo.wms.exception.UomNotFoundException;
import ru.demo.wms.model.Uom;
import ru.demo.wms.service.IUomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Контроллер для управления единицами измерения (UOM).
 * Обеспечивает REST API для CRUD-операций.
 * Интегрирован со Swagger для автогенерации документации.
 */
@Api(description = "Операции над единицами измерения (UOM)")
@RestController
@RequestMapping("/rest/api/uom")
public class UomRestController {

	private static final Logger LOG = LoggerFactory.getLogger(UomRestController.class);

	@Autowired
	private IUomService service;

	/**
	 * Получить все единицы измерения.
	 */
	@ApiOperation("Получить все единицы измерения")
	@GetMapping("/all")
	public ResponseEntity<?> getAllUoms() {
		LOG.info("Получение всех единиц измерения...");
		try {
			List<Uom> list = service.getAllUoms();
			LOG.debug("Полученные данные: {}", list);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			LOG.error("Ошибка при получении списка UOM: {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Получить одну единицу измерения по ID.
	 */
	@ApiOperation("Получить единицу измерения по ID")
	@GetMapping("/fetch/{id}")
	public ResponseEntity<?> getOneUom(@PathVariable Integer id) {
		LOG.info("Получение UOM по ID: {}", id);
		try {
			Uom uom = service.getOneUom(id);
			LOG.debug("Найденная единица: {}", uom);
			return ResponseEntity.ok(uom);
		} catch (UomNotFoundException e) {
			LOG.error("UOM не найдена: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error("Ошибка при получении UOM: {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Создать новую единицу измерения.
	 */
	@ApiOperation("Создать новую единицу измерения")
	@PostMapping("/create")
	public ResponseEntity<String> createUom(@RequestBody Uom uom) {
		LOG.info("Создание новой единицы измерения...");
		try {
			Integer id = service.saveUom(uom);
			String message = "Единица измерения '" + id + "' успешно создана";
			LOG.debug(message);
			return new ResponseEntity<>(message, HttpStatus.CREATED);
		} catch (Exception e) {
			LOG.error("Ошибка при создании UOM: {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Удалить единицу измерения по ID.
	 */
	@ApiIgnore
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> removeUom(@PathVariable Integer id) {
		LOG.info("Удаление UOM с ID: {}", id);
		try {
			service.deleteUom(id);
			String message = "Единица измерения '" + id + "' удалена";
			LOG.debug(message);
			return ResponseEntity.ok(message);
		} catch (UomNotFoundException e) {
			LOG.error("UOM не найдена для удаления: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error("Ошибка при удалении UOM: {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Обновить существующую единицу измерения.
	 */
	@ApiOperation("Обновить существующую единицу измерения")
	@PutMapping("/modify")
	public ResponseEntity<String> updateUom(@RequestBody Uom uom) {
		LOG.info("Обновление единицы измерения ID: {}", uom.getId());
		try {
			service.updateUom(uom);
			String message = "Единица измерения успешно обновлена";
			LOG.debug(message);
			return ResponseEntity.ok(message);
		} catch (UomNotFoundException e) {
			LOG.error("UOM не найдена для обновления: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error("Ошибка при обновлении UOM: {}", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

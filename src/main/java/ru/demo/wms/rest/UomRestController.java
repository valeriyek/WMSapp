package ru.demo.wms.rest;
/*
Класс UomRestController определяет REST контроллер для управления сущностями типа "Единица измерения" (Uom) в приложении. Контроллер предоставляет API для выполнения стандартных операций CRUD, а также дополнительно интегрирован с Swagger для генерации документации API. Вот ключевые аспекты этого класса:

Операции API:
Получение всех единиц измерения (/all): Метод getAllUoms возвращает список всех единиц измерения. При возникновении исключений возвращает ответ со статусом 500 (Internal Server Error).

Получение единицы измерения по ID (/fetch/{id}): Метод getOneUom возвращает единицу измерения по заданному идентификатору. Если единица измерения не найдена, генерируется исключение UomNotFoundException, что приведет к ответу со статусом 404 (Not Found).

Создание новой единицы измерения (/create): Метод createUom создает новую единицу измерения на основе данных из тела запроса. В случае успеха возвращает сообщение о создании с статусом 201 (Created).

Удаление единицы измерения по ID (/remove/{id}): Метод removeUom удаляет единицу измерения по заданному идентификатору. В случае отсутствия единицы измерения генерируется UomNotFoundException.

Обновление единицы измерения (/modify): Метод updateUom обновляет существующую единицу измерения на основе данных из тела запроса. В случае отсутствия единицы измерения генерируется UomNotFoundException.

Логирование:
Контроллер использует SLF4J для логирования ключевых событий, таких как вход и выход из методов и ошибки выполнения. Это помогает в отладке и мониторинге работы API.

Интеграция с Swagger:
С помощью аннотаций @ApiOperation и @Api класс документирует его методы для Swagger UI, что делает API более понятным для разработчиков и пользователей. @ApiIgnore используется для исключения методов из документации.

Обработка исключений:
Класс использует подход к обработке исключений, генерируя специфичные для контекста исключения (UomNotFoundException), что позволяет возвращать клиенту более точные HTTP статусы (например, 404 при отсутствии ресурса).

Автовнедрение зависимостей:
Сервис IUomService, отвечающий за бизнес-логику управления единицами измерения, внедряется в контроллер через @Autowired, что обеспечивает разделение ответственности и упрощает тестирование компонентов.

Контроллер является центральной точкой взаимодействия клиентской части приложения с бизнес-логикой, касающейся управления единицами измерения, и демонстрирует использование ряда лучших практик разработки в Spring Framework.*/
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import ru.demo.wms.exception.UomNotFoundException;
import ru.demo.wms.model.Uom;
import ru.demo.wms.service.IUomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(description = "UOM SERVICES FOR PART SUPPORT")
@RestController
@RequestMapping("/rest/api/uom")
public class UomRestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UomRestController.class);

	@Autowired
	private IUomService service;
	

	@ApiOperation("TO FETCH ALL UOMS FROM DATABASE")
	@GetMapping("/all")
	public ResponseEntity<?> getAllUoms() {
		LOG.info("ENTERED INTO GET ALL UOMS METHOD");
		ResponseEntity<?> response = null;
		try {
			
			List<Uom> list = service.getAllUoms();
			response = new ResponseEntity<List<Uom>>(
					list,
					HttpStatus.OK);
			LOG.debug("DATA LOADED IS {}",list.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("UNABLE TO PROCESS LOADING {}",e.getMessage());
			response = new ResponseEntity<String>(
					e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOG.info("ABOUT TO LEAVE FETCH ALL");
		return response;
	}
	

	@GetMapping("/fetch/{id}")
	public ResponseEntity<?> getOneUom(
			@PathVariable Integer id
			) 
	{
		LOG.info("ENTERED INTO FETCH ONE UOM");
		ResponseEntity<?> response = null;
		
		try {
			Uom uom = service.getOneUom(id);
			response = new ResponseEntity<Uom>(
					uom,
					HttpStatus.OK);
			LOG.debug(" UOM FOUND {}",uom);
			
		} catch (UomNotFoundException unfe) {
			LOG.error("UNABLE TO FETCH UOM : {}",unfe.getMessage());
			throw unfe;
			
		} catch (Exception e) {
			LOG.error("UNABLE TO PROCESS REQUEST : {}",e.getMessage());
			e.printStackTrace();
			response = new ResponseEntity<String>(
					e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOG.info("ABOUT TO LEAVE FETCH ONE");
		return response;
	}
	
	

	@ApiOperation("TO CREATE NEW UOM AT APPLIATION")
	@PostMapping("/create")
	public ResponseEntity<String> createUom(
			@RequestBody Uom uom) 
	{
		LOG.info("ENTERED INTO SAVE UOM");
		ResponseEntity<String> response  = null;
		try {
			Integer id = service.saveUom(uom);
			String message = "Uom '"+id+"' created!";
			response = new ResponseEntity<String>(
					message,
					HttpStatus.CREATED);//201
			LOG.debug(message);
		} catch (Exception e) {
			LOG.error("UNABLE TO PROCESS REQUEST : {}",e.getMessage());
			e.printStackTrace();
			response = new ResponseEntity<String>(
					e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR); //500
		}
		LOG.info("ABOUT TO LEAVE SAVE UOM");
		return response;
		
	}
	
	

	@ApiIgnore
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> removeUom(
			@PathVariable Integer id) 
	{
		LOG.info("ENTERED INTTO DELETE UOM");
		ResponseEntity<String> response = null;
		
		try {
			service.deleteUom(id);
			String message = "Uom '"+id+"' Deleted";
			response = new ResponseEntity<String>(
					message,
					HttpStatus.OK
					);
			LOG.debug(message);
		}  catch (UomNotFoundException unfe) {
			LOG.error("UNABLE TO FETCH UOM : {}",unfe.getMessage());
			throw unfe;
		}  catch (Exception e) {
			LOG.error("UNABLE TO PROCESS REQUEST : {}",e.getMessage());
			e.printStackTrace();
			response = new ResponseEntity<String>(
					e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOG.info("ABOUT TO LEAVE DELETE METHOD");
		return response;
	}
	
	

	@PutMapping("/modify")
	public ResponseEntity<String> updateUom(
			@RequestBody Uom uom
			) 
	{
		LOG.info("ENTERED INTO UPDATE METHOD");
		ResponseEntity<String> response = null;
		try {
			
			 service.updateUom(uom);
			 String message = "Uom updated!!";
			 response = new ResponseEntity<String>(
					 message,
					 HttpStatus.OK);
			
		} catch (UomNotFoundException unfe) {
			LOG.error("UNABLE TO FETCH UOM : {}",unfe.getMessage());
			throw unfe;
			
		} catch (Exception e) {
			LOG.error("UNABLE TO PROCESS REQUEST : {}",e.getMessage());
			e.printStackTrace();
			response = new ResponseEntity<String>(
					e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOG.info("ABOUT TO LEAVE UPDATE METHOD");
		return response;
	}
	
	
}

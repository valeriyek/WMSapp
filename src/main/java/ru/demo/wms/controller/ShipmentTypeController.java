package ru.demo.wms.controller;


/*
ShipmentTypeController управляет типами доставки в системе управления складом. 
Вот основные функции, которые он выполняет:

Отображение страницы регистрации типа доставки (showRegister): Позволяет пользователю ввести данные для создания нового типа доставки.

Сохранение типа доставки (saveShipmentType): Принимает данные из формы и сохраняет их в базе данных. 
После успешного сохранения отправляет на UI сообщение об успешном создании.

Отображение всех типов доставки (fetchShipmentTypes): Извлекает из базы данных 
и отображает список всех типов доставки.

Удаление типа доставки по ID (deleteShipmentType): Удаляет указанный тип доставки 
из базы данных и обновляет UI, показывая обновленный список.

Отображение страницы редактирования типа доставки (showShipmentTypeEdit): 
Загружает данные выбранного типа доставки для редактирования.

Обновление типа доставки (updateShipmentType): Принимает отредактированные данные 
и обновляет соответствующий тип доставки в базе данных.

Проверка существования кода типа доставки (validateShipmentTypeCode): 
AJAX-запрос для проверки уникальности кода типа доставки. Используется для валидации формы на клиентской стороне.

Экспорт данных в Excel (exportData): Позволяет экспортировать список всех типов доставки в формате Excel.

Генерация диаграмм (generateCharts): Создает диаграммы, 
отображающие статистику по типам доставки, и сохраняет их в файловой системе сервера.

Отображение PDF (showPdf): Генерирует и отображает PDF-отчет со списком всех типов доставки.

Контроллер играет ключевую роль в управлении типами доставки в системе, позволяя пользователю создавать, 
просматривать, редактировать, удалять и экспортировать типы доставки, а также генерировать отчеты и диаграммы для анализа.
*/


import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ru.demo.wms.exception.ShipmentTypeNotFoundException;
import ru.demo.wms.model.ShipmentType;
import ru.demo.wms.service.IShipmentTypeService;
import ru.demo.wms.util.ShipmentTypeUtil;
import ru.demo.wms.view.ShipmentTypeExcelView;
import ru.demo.wms.view.ShipmentTypePdfView;

@Controller
@RequestMapping("/st")
public class ShipmentTypeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeController.class);

	@Autowired
	private IShipmentTypeService service;
	
	@Autowired
	private ShipmentTypeUtil util;
	
	@Autowired
	private ServletContext context;
	


	@GetMapping("/register")
	public String showRegister() {
		return "ShipmentTypeRegister";
	}


	@PostMapping("/save")
	public String saveShipmentType(

			@ModelAttribute ShipmentType shipmentType,
			Model model
			) 
	{
		
		LOG.info("ENTERED INTO SAVE METHOD");
		try {

			Integer id = service.saveShipmentType(shipmentType);
			LOG.debug("RECORD IS CREATED WITH ID {}",id);
			

			String msg = "Shipment Type '"+id+"' is created";

			model.addAttribute("message", msg);
			
		} catch (Exception e) {
			LOG.error("Unable to process request due to {}", e.getMessage());
			model.addAttribute("message", "Unable to Process Request!");
			e.printStackTrace();
		}

		LOG.info("ABOUT TO GO UI PAGE!");

		return "ShipmentTypeRegister";
	}

	@GetMapping("/all")
	public String fetchShipmentTypes(
			Model model ) 
	{
		
		LOG.info("ENTERED INTO FETCH ALL ROWS");
		try {

			List<ShipmentType> list = service.getAllShipmentTypes();
			LOG.debug("DATA FOUND WITH SIZE {}", list!=null?list.size():"NO DATA");

			model.addAttribute("list", list);
		} catch (Exception e) {
			LOG.error("Unable to fetch data {}",e.getMessage());
			e.printStackTrace();
		}

		LOG.info("MOVING TO DATA PAGE TO DISPLAY");

		return "ShipmentTypeData";
	}


	@GetMapping("/delete")
	public String deleteShipmentType(
			@RequestParam Integer id,
			Model model
			) 
	{
		LOG.info("ENTERED INTO DELETE METHOD");
		try {

			service.deleteShipmentType(id);

			String msg = "Shipment Type '"+id+"' Deleted!";
			LOG.debug(msg);
			model.addAttribute("message", msg);
			
		} catch (ShipmentTypeNotFoundException e) {
			LOG.error("Unable to process delete Request {}",e.getMessage());
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}
		

		List<ShipmentType> list = service.getAllShipmentTypes();

		model.addAttribute("list", list);
		

		return "ShipmentTypeData";
	}
	

	@GetMapping("/edit")
	public String showShipmentTypeEdit(
			@RequestParam Integer id,
			Model model) 
	{
		LOG.info("ENTERED INTO EDIT METHOD");
		String page = null;
		try {

			ShipmentType st = service.getShipmentType(id);
			

			model.addAttribute("shipmentType", st);
			

			page = "ShipmentTypeEdit";
			
		} catch (ShipmentTypeNotFoundException e) {
			LOG.error("Unable to process Edit Request : {}",e.getMessage());
			e.printStackTrace();

			page = "ShipmentTypeData";
			model.addAttribute("message", e.getMessage());

			List<ShipmentType> list = service.getAllShipmentTypes();
			model.addAttribute("list", list);
			
		}
		LOG.info("ABOUT TO GO PAGE {} ", page);

		return page;
	}
	

	@PostMapping("/update")
	public String updateShipmentType(
			@ModelAttribute ShipmentType shipmentType) 
	{
		LOG.info("ENTERED INTO UPDATE METHOD");
		try {

			service.updateShipmentType(shipmentType);
		} catch (Exception e) {
			LOG.error("Unable to Perform Update : {}",e.getMessage());
			e.printStackTrace();
		}
		LOG.info("REDIRECTING TO FETCH ALL ROWS");

		return "redirect:all";
	}


	@GetMapping("/validate")
	@ResponseBody
	public String validateShipmentTypeCode(
			@RequestParam String code,
			@RequestParam Integer id
			) 
	{
		String message="";

		if(id==0 && service.isShipmentTypeCodeExist(code)) {
			message = code + ", already exist";
		} else if(id!=0 && service.isShipmentTypeCodeExistForEdit(code,id)) {

			message = code + ", already exist";
		}
		
		return message; 
	}
	

	@GetMapping("/excel")
	public ModelAndView exportData() {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypeExcelView());
		

		List<ShipmentType> list = service.getAllShipmentTypes();
		m.addObject("list", list);
		
		return m;
	}
	

	@GetMapping("/charts")
	public String generateCharts() 
	{
		List<Object[]> list =  service.getShipmentTypeModeAndCount();
		String path = context.getRealPath("/");
		util.generatePieChart(path, list);
		util.generateBarChart(path, list);
		return "ShipmentTypeCharts";
	}
	
	

	@GetMapping("/pdf")
	public ModelAndView showPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypePdfView());
		
		m.addObject("list",service.getAllShipmentTypes());
		
		return m;
	}
	
	
}

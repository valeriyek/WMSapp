package ru.demo.wms.controller;

/*
UomController управляет единицами измерения (UOM) в системе управления складом. 
Вот основные функции, которые он выполняет:

Отображение страницы регистрации UOM (showReg): 
Позволяет пользователю ввести данные для создания новой единицы измерения.

Сохранение UOM (saveUom): Принимает данные из формы и сохраняет их в базе данных. 
После успешного сохранения отправляет на UI сообщение об успешном создании.

Отображение всех UOM (displayUoms): 
Извлекает из базы данных и отображает список всех единиц измерения.

Удаление UOM по ID (deleteUom): 
Удаляет указанную единицу измерения из базы данных и обновляет UI, показывая обновленный список.

Отображение страницы редактирования UOM (showEdit): 
Загружает данные выбранной единицы измерения для редактирования.

Обновление UOM (updateUom): 
Принимает отредактированные данные и обновляет соответствующую единицу измерения в базе данных.

Проверка существования модели UOM (validateUomModel): 
AJAX-запрос для проверки уникальности модели единицы измерения. Используется для валидации формы на клиентской стороне.

Экспорт данных в Excel (showExcel): 
Позволяет экспортировать список всех единиц измерения в формате Excel.

Отображение PDF (shodPdf): 
Генерирует и отображает PDF-отчет со списком всех единиц измерения.

Контроллер играет ключевую роль 
в управлении единицами измерения в системе, позволяя пользователю создавать,
просматривать, редактировать, удалять и экспортировать единицы измерения, 
а также генерировать отчеты для анализа.
*/

import java.util.List;

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

import ru.demo.wms.exception.UomNotFoundException;
import ru.demo.wms.model.Uom;
import ru.demo.wms.service.IUomService;
import ru.demo.wms.view.UomExcelView;
import ru.demo.wms.view.UomPdfView;


@Controller
@RequestMapping("/uom")
public class UomController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UomController.class);

	@Autowired
	private IUomService service;

	@GetMapping("/register")
	public String showReg() {
		return "UomRegister";
	}


	@PostMapping("/save")
	public String saveUom(
			@ModelAttribute Uom uom,
			Model model
			) 
	{
		LOG.info("ENTERED INTO SAVE METHOD");
		try {
			Integer id = service.saveUom(uom);
			LOG.debug("SAVED WITH ID {}",id);
			model.addAttribute("message", "Uom '"+id+"' saved");
			
		} catch (Exception e) {
			LOG.error("UNABLE TO SAVE UOM : {}",e.getMessage());
			e.printStackTrace();
		}
		LOG.info("ABOUT TO LEAVE SAVE METHOD");
		return "UomRegister";
	}


	@GetMapping("/all")
	public String displayUoms(
			Model model) 
	{
		commonFetchAll(model);
		return "UomData";
	}

	private void commonFetchAll(Model model) {
		List<Uom> list =  service.getAllUoms();
		model.addAttribute("list",list);
	}


	@GetMapping("/delete")
	public String deleteUom(
			@RequestParam Integer id,
			Model model
			) 
	{
		LOG.info("ENTERED INTO DELETE METHOD");
		try {
			service.deleteUom(id);
			model.addAttribute("message", "Uom '"+id+"' Deleted");
			LOG.debug("DELETED WITH ID {}",id);
		} catch (UomNotFoundException e) {
			LOG.error("UNABLE TO DELETE UOM : {}",e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}

		commonFetchAll(model);
		LOG.info("ABOUT TO LEAVE DELETE METHOD");
		return "UomData";
	}


	@GetMapping("/edit")
	public String showEdit(
			@RequestParam Integer id,
			Model model
			) 
	{
		LOG.info("ENTERED INTO EDIT METHOD");
		String page = null;
		try {
			Uom uom = service.getOneUom(id);
			LOG.debug("OBJECT FOUND WITH ID {} FOR EDIT",id);
			model.addAttribute("uom", uom);
			page = "UomEdit";
			
		} catch (UomNotFoundException e) {
			LOG.error("UNABLE TO FETCH UOM : {}",e.getMessage());
			e.printStackTrace();
			commonFetchAll(model);
			model.addAttribute("message", e.getMessage());
			page = "UomData";
		}
		LOG.info("ABOUT TO LEAVE EDIT METHOD");
		return page;
	}


	@PostMapping("/update")
	public String updateUom(
			@ModelAttribute Uom uom,
			Model model
			) 
	{
		service.updateUom(uom);
		model.addAttribute("message", "Uom '"+uom.getId()+"' Updated!");

		List<Uom> list =  service.getAllUoms();
		model.addAttribute("list",list);
		return "UomData";
	}


	@GetMapping("/validate")
	@ResponseBody
	public String validateUomModel(
			@RequestParam String model,
			@RequestParam Integer id
			) 
	{
		String message = "";
		if(id==0 && service.isUomModelExist(model)) {
			message = model + ", already exist!";
		} else if(id!=0 && service.isUomModelExistForEdit(model,id)) {
			message = model + ", already exist!";
		}
		return message;
	}
	

	@GetMapping("/excel")
	public ModelAndView showExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new UomExcelView());
		m.addObject("list", service.getAllUoms());
		return m;
	}
	

	@GetMapping("/pdf")
	public ModelAndView shodPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new UomPdfView());
		
		List<Uom> list = service.getAllUoms();
		m.addObject("uoms", list);
		return m;
	}
	

	
}

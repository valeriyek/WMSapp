package ru.demo.wms.controller;

/*

WhUserTypeController управляет типами пользователей склада в системе управления складом. Вот его основные функции:

Регистрация новых типов пользователей (ShowRegister, saveWhUserType): 
Предоставляет страницу для регистрации новых типов пользователей и сохраняет их данные в системе. 
При успешном сохранении отправляется автоматическое уведомление по электронной почте.

Просмотр всех типов пользователей (fetchWhUserTypes): Отображает список всех 
зарегистрированных типов пользователей в системе.

Удаление типа пользователя (deleteWhUserTypeData): Удаляет выбранный тип пользователя 
из системы и обновляет список.

Редактирование типа пользователя (showWhUserTypeData, updateWhUserType): Позволяет редактировать 
данные существующего типа пользователя и сохранять изменения.

Валидация данных через AJAX (validate, validateemail, validateidnum): Предоставляет механизм 
для проверки уникальности кода типа пользователя, электронной почты и идентификационного номера без перезагрузки страницы.

Экспорт данных в Excel (exportData): Предлагает функциональность для экспорта 
списка типов пользователей в формат Excel.

Генерация графиков (generateCharts): Создает графики для визуализации статистики типов пользователей, 
например, распределения типов пользователей по их количеству.

Этот контроллер важен для управления типами пользователей склада, предоставляя возможности 
для их создания, просмотра, редактирования и удаления. Включает в себя функционал валидации 
для предотвращения дублирования данных и инструменты для анализа данных через графики и экспорт.

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

import ru.demo.wms.exception.WhUserTypeNotFound;
import ru.demo.wms.model.WhUserType;
import ru.demo.wms.service.IWhUserTypeService;
import ru.demo.wms.util.MailUtil;
import ru.demo.wms.util.WhUserTypeUtil;
import ru.demo.wms.view.WhUserTypeExcelView;

@RequestMapping("/wh")
@Controller
public class WhUserTypeController {

	private static final Logger LOG=LoggerFactory.getLogger(ShipmentTypeController.class);

	@Autowired
	private IWhUserTypeService service;

	@Autowired
	private WhUserTypeUtil util;

	@Autowired
	private ServletContext context;
	
	@Autowired
	private MailUtil mailUtil;
	

	@GetMapping("/register")
	public String ShowRegister() {
		return "WhUserTypeRegister";
	}


	@PostMapping("/save")
	public String saveWhUserType(
			@ModelAttribute WhUserType whUserType,
			Model model) 
	{

		LOG.info("ENTERED INTO SAVE METHOD");

		try {

			Integer id=service.saveWhUserType(whUserType);
			

			if(id>0) {
				new Thread(()->{
					mailUtil.sendEmail(
							whUserType.getUserEmail(), 
							"AUTO GENERATED EMAIL",
							"HELLO");
				}).start();
			}

			String msg="WhUserType '"+id+"' is created";
			model.addAttribute("message", msg);
		}catch (Exception e) {
			LOG.error(" Unable to process request due to {}",e.getMessage());
			e.printStackTrace();
		}
		LOG.info("ABOUT GO TO UI PAGE !");


		return "WhUserTypeRegister";
	}


	@GetMapping("/all")
	public String fetchWhUserTypes(Model model) {

		LOG.info("ENTERED INTO FETCH ALL ROWS");

		try {

			List<WhUserType> list=service.getAllWhUserTypes();

			model.addAttribute("list", list);
		}
		catch (Exception e) {
			LOG.error(" Unable to fetch data  {}",e.getMessage());
			e.printStackTrace();
		}

		LOG.info("MOVING DATA PAGE TO DISPLAY");

		return "WhUserTypeData";			

	}


	@GetMapping("/delete")
	public String deleteWhUserTypeData(
			@RequestParam Integer id,
			Model model	
			) {
		LOG.info("ENTERED INTO DELETE METHOD");
		try {

			service.deleteWhUserType(id);

			String msg="WhUserType  '"+id+"' is Deleted !";
			LOG.debug(msg);
			model.addAttribute("message",msg);
		} catch (WhUserTypeNotFound e) {
			LOG.error("Unable to process delete Request {}",e.getMessage());
			e.printStackTrace();
			model.addAttribute("message",e.getMessage());
		}


		List<WhUserType> list=service.getAllWhUserTypes();

		model.addAttribute("list", list);

		return "WhUserTypeData";
	}


	@GetMapping("/edit")
	public String showWhUserTypeData(
			@RequestParam Integer id,
			Model model) {
		LOG.info("ENTERED INTO EDIT METHOD");
		String page = null;
		try {
			WhUserType whut=service.getOneWhUserType(id);
			model.addAttribute("whusertype", whut);	

			page= "WhUserTypeEdit";  
		}catch (WhUserTypeNotFound e) {
			LOG.error("Unable to process Edit Request : {}",e.getMessage());
			e.printStackTrace();

			page = "WhUserTypeData";
			model.addAttribute("message", e.getMessage());


			List<WhUserType> list=service.getAllWhUserTypes();

			model.addAttribute("list", list);

		}
		LOG.info("ABOUT TO GO PAGE {} ", page);

		return page;
	}


	@PostMapping("/update")
	public String updateWhUserType(@ModelAttribute WhUserType whut ) {

		LOG.info("ENTERED INTO UPDATE METHOD");
		try {

			service.updateWhUserType(whut);

		}
		catch (Exception e) {
			LOG.error("Unable to Perform Update : {}",e.getMessage());
			e.printStackTrace();
		}

		LOG.info("REDIRECTING TO FETCH ALL ROWS");


		return "redirect:all";
	}


	@GetMapping("/validate")
	@ResponseBody
	public String validateWhUserTypeCode(@RequestParam String code,
			@RequestParam Integer id) {

		String message="";
		if(id==0 && service.isWhUserTypeCodeExit(code)) {
			message=code+" already exit";
		}
		else if(id!=0 && service.isWhUserTypeCodeExitForEdit(code,id)){
			message=code+" already exit";
		}
		return message;

	}

	@GetMapping("/validateemail")
	@ResponseBody
	public String validateWhUserTypeEmail(@RequestParam String email,
			@RequestParam Integer id) {

		String message="";
		if(id==0 && service.getWhUserTypeuserEmailCount(email)) {
			message=email+" already exit";
		}
		else if(id!=0 && service.getWhUserTypeuserEmailCountForEdit(email,id)){
			message=email+" already exit";
		}
		return message;

	}


	@GetMapping("/validateidnum")
	@ResponseBody
	public String validateWhUserIdNum(@RequestParam String idnum,
			@RequestParam Integer id) {

		String message="";
		if(id==0 && service.getWhUserTypeuserIdNumCount(idnum)) {
			message=idnum+" already exit";
		}
		else if(id!=0 && service.getWhUserTypeuserIdNumCountForEdit(idnum,id)){
			message=idnum+" already exit";
		}
		return message;

	}



	@GetMapping("/excel")
	public ModelAndView exportData() {
		ModelAndView m=new ModelAndView();
		m.setView(new WhUserTypeExcelView()); 

		List<WhUserType> list=service.getAllWhUserTypes();
		m.addObject("list", list);
		return m;

	}


	@GetMapping("/charts")
	public String generateCharts() {
		List<Object[]> list=service.getWhUserTypUserIDAndCount();
		String path=context.getRealPath("/");
		util.generatePieChart(path, list);
		util.generateBarChart(path, list);
		return "WhUserTypeCharts";

	}  



}

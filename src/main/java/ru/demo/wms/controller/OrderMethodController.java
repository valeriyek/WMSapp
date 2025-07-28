package ru.demo.wms.controller;


/*Этот контроллер, OrderMethodController, обрабатывает различные HTTP-запросы, 
связанные с операциями над объектами "Методы Заказа" (OrderMethod) в приложении управления складом. 
Вот основные функции контроллера:

Показать страницу регистрации: При обращении по пути /om/register представляет пользователю форму 
для регистрации нового метода заказа.

Сохранение данных метода заказа: При отправке формы на /om/save получает данные метода заказа из запроса, 
сохраняет их в базе данных и возвращает сообщение об успешном сохранении.

Отображение данных: При запросе к /om/all извлекает все записи о методах заказа 
из базы данных и отображает их на странице.

Удаление метода заказа: Позволяет удалить метод заказа по идентификатору, о
бращаясь к /om/delete, и возвращает на страницу с данными о методах заказа с обновленной информацией.

Показать страницу редактирования: По запросу на /om/edit с идентификатором метода заказа 
загружает данные этого метода для редактирования и представляет форму редактирования.

Обновление данных метода заказа: После редактирования, отправка формы на /om/update
обновляет данные метода заказа в базе данных.

Экспорт в Excel: По запросу на /om/excel генерирует и предоставляет файл Excel 
со всеми данными о методах заказа.

Генерация графиков: При обращении к /om/charts генерирует графики на основе данных
методов заказа, например, распределение по типам методов заказа.

Проверка уникальности кода метода заказа через AJAX: При запросе на /om/validate 
проверяет, существует ли уже метод заказа с таким же кодом, и возвращает сообщение об ошибке, если найдено совпадение.

Контроллер взаимодействует с сервисом IOrderMethodService для выполнения операций 
с данными и использует OrderMethodUtil для специфических операций, таких как генерация графиков. 
Также используется ServletContext для работы с путями файловой системы сервера (например, для сохранения графиков).

Контроллер активно использует Spring MVC аннотации для обработки веб-запросов, 
аннотацию @Autowired для внедрения зависимостей, а также работает с Thymeleaf для генерации веб-страниц на основе HTML-шаблонов.

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

import ru.demo.wms.exception.OrderMethodNotFound;
import ru.demo.wms.model.OrderMethod;
import ru.demo.wms.service.IOrderMethodService;
import ru.demo.wms.util.OrderMethodUtil;
import ru.demo.wms.view.OrderMethodExcelView;

@Controller
@RequestMapping("/om")
public class OrderMethodController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderMethodController.class);
	
	@Autowired
	private IOrderMethodService service;//HAS-A
	
	@Autowired
	private OrderMethodUtil util;
	
	@Autowired
	private ServletContext sc;
	

	@GetMapping("/register")
	public String showReg() {
		return "OrderMethodRegister";
	}
	

	@PostMapping("/save")
	public String saveOrderMethod(
			@ModelAttribute OrderMethod orderMethod,
			Model model
			) 
	{
		LOG.info("ENTERED INTO SAVE METHOD");
		try {
			Integer id = service.saveOrderMethod(orderMethod);
			String msg = " Order Method '"+id+"' created";
			model.addAttribute("message", msg);
			LOG.debug(msg);
		} catch (Exception e) {
			LOG.error("UNABLE TO SAVE {}",e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info("ABOUT TO LEAVE SAVE METHOD");
		return "OrderMethodRegister";
	}
	

	@GetMapping("/all")
	public String fetchData(Model model) {
		commonSetup(model);
		return "OrderMethodData";
	}
	
	private void commonSetup(Model model) {
		List<OrderMethod> list = service.getAllOrderMethods();
		model.addAttribute("list", list);
	}
	

	@GetMapping("/delete")
	public String deleteById(
			@RequestParam Integer id,
			Model model
			)
	{
		LOG.info("ENTERED INTO DELETE METHOD");
		try {
			service.deleteOrderMethod(id);
			String msg = "Order Method '"+id+"' Deleted!";
			model.addAttribute("message", msg);
			LOG.debug(msg);
		} catch (OrderMethodNotFound e) {
			LOG.error("UNABLE TO DELETE {}",e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		commonSetup(model);
		LOG.info("ABOUT TO LEAVE DELETE METHOD");
		return "OrderMethodData";
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
			OrderMethod om = service.getOneOrderMethod(id);
			model.addAttribute("orderMethod", om);
			page = "OrderMethodEdit";
			LOG.debug("MOVING TO EDIT PAGE");
		} catch (OrderMethodNotFound e) {
			LOG.error("UNABLE TO FETCH FOR EDIT {}",e.getMessage());
			page = "OrderMethodData";
			commonSetup(model);
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		LOG.info("ABOUT TO LEAVE EDIT METHOD");
		return page;
	}
	

	@PostMapping("/update")
	public String update(
			@ModelAttribute OrderMethod orderMethod,
			Model model) 
	{
		service.updateOrderMethod(orderMethod);
		model.addAttribute("message", "Order Method updated!!");
		commonSetup(model);
		return "OrderMethodData";
	}


	@GetMapping("/excel")
	public ModelAndView exportData() {
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodExcelView());
		

		List<OrderMethod> list = service.getAllOrderMethods();
		m.addObject("list", list);
		
		return m;
	}
	

	@GetMapping("/charts")
	public String generateCharts() {
		List<Object[]> list = service.getOrderMethodModeAndCount();
		String path = sc.getRealPath("/");
		
		util.generatePie(path,list);
		util.generateBar(path,list);
		return "OrderMethodCharts";
	}
	

	@GetMapping("/validate")
	@ResponseBody
	public String validateOm(
			@RequestParam String code,
			@RequestParam Integer id
			) 
	{
		String message = "";
		if(id==0 && service.isOrderMethodCodeExist(code)) {
			message = code + ", already exist!";
		} else if(id!=0 && service.isOrderMethodCodeExistForEdit(code,id)) {
			message = code + ", already exist!";
		}
		return message;
	}
	
}

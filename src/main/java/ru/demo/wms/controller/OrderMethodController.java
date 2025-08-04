package ru.demo.wms.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ru.demo.wms.exception.OrderMethodNotFound;
import ru.demo.wms.model.OrderMethod;
import ru.demo.wms.service.IOrderMethodService;
import ru.demo.wms.util.OrderMethodUtil;
import ru.demo.wms.view.OrderMethodExcelView;

/**
 * Контроллер для управления методами заказа.
 * <p>
 * Поддерживает операции регистрации, отображения, редактирования, удаления,
 * экспорта в Excel, построения графиков и AJAX-проверки уникальности кода.
 */
@Controller
@RequestMapping("/om")
public class OrderMethodController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderMethodController.class);

	@Autowired
	private IOrderMethodService service;

	@Autowired
	private OrderMethodUtil util;

	@Autowired
	private ServletContext sc;

	/**
	 * Отображает страницу регистрации нового метода заказа.
	 *
	 * @return имя шаблона с формой регистрации
	 */
	@GetMapping("/register")
	public String showReg() {
		return "OrderMethodRegister";
	}

	/**
	 * Сохраняет новый метод заказа.
	 *
	 * @param orderMethod объект метода заказа, полученный из формы
	 * @param model       модель для передачи сообщения
	 * @return имя представления для страницы регистрации
	 */
	@PostMapping("/save")
	public String saveOrderMethod(@ModelAttribute OrderMethod orderMethod, Model model) {
		LOG.info("Вход в метод сохранения");
		try {
			Integer id = service.saveOrderMethod(orderMethod);
			String msg = "Метод заказа с ID '" + id + "' успешно создан";
			model.addAttribute("message", msg);
			LOG.debug(msg);
		} catch (Exception e) {
			LOG.error("Ошибка при сохранении: {}", e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Выход из метода сохранения");
		return "OrderMethodRegister";
	}

	/**
	 * Отображает список всех методов заказа.
	 *
	 * @param model модель с данными
	 * @return имя шаблона для отображения списка
	 */
	@GetMapping("/all")
	public String fetchData(Model model) {
		commonSetup(model);
		return "OrderMethodData";
	}

	/**
	 * Загружает все методы заказа и помещает в модель.
	 *
	 * @param model модель
	 */
	private void commonSetup(Model model) {
		List<OrderMethod> list = service.getAllOrderMethods();
		model.addAttribute("list", list);
	}

	/**
	 * Удаляет метод заказа по его идентификатору.
	 *
	 * @param id    идентификатор метода
	 * @param model модель для сообщения
	 * @return имя шаблона со списком методов
	 */
	@GetMapping("/delete")
	public String deleteById(@RequestParam Integer id, Model model) {
		LOG.info("Вход в метод удаления");
		try {
			service.deleteOrderMethod(id);
			String msg = "Метод заказа с ID '" + id + "' удалён";
			model.addAttribute("message", msg);
			LOG.debug(msg);
		} catch (OrderMethodNotFound e) {
			LOG.error("Не удалось удалить: {}", e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		commonSetup(model);
		LOG.info("Выход из метода удаления");
		return "OrderMethodData";
	}

	/**
	 * Отображает форму редактирования метода заказа.
	 *
	 * @param id    идентификатор метода
	 * @param model модель с объектом метода
	 * @return имя шаблона для редактирования или списка
	 */
	@GetMapping("/edit")
	public String showEdit(@RequestParam Integer id, Model model) {
		LOG.info("Вход в метод редактирования");
		String page;
		try {
			OrderMethod om = service.getOneOrderMethod(id);
			model.addAttribute("orderMethod", om);
			page = "OrderMethodEdit";
			LOG.debug("Переход на страницу редактирования");
		} catch (OrderMethodNotFound e) {
			LOG.error("Ошибка загрузки метода: {}", e.getMessage());
			model.addAttribute("message", e.getMessage());
			commonSetup(model);
			page = "OrderMethodData";
			e.printStackTrace();
		}
		LOG.info("Выход из метода редактирования");
		return page;
	}

	/**
	 * Обновляет существующий метод заказа.
	 *
	 * @param orderMethod объект с обновлёнными данными
	 * @param model       модель для сообщения
	 * @return имя шаблона со списком методов
	 */
	@PostMapping("/update")
	public String update(@ModelAttribute OrderMethod orderMethod, Model model) {
		service.updateOrderMethod(orderMethod);
		model.addAttribute("message", "Метод заказа обновлён!");
		commonSetup(model);
		return "OrderMethodData";
	}

	/**
	 * Экспортирует данные о методах заказа в Excel.
	 *
	 * @return {@link ModelAndView} с Excel-представлением
	 */
	@GetMapping("/excel")
	public ModelAndView exportData() {
		ModelAndView m = new ModelAndView();
		m.setView(new OrderMethodExcelView());
		List<OrderMethod> list = service.getAllOrderMethods();
		m.addObject("list", list);
		return m;
	}

	/**
	 * Генерирует круговую и столбчатую диаграммы по методам заказа.
	 *
	 * @return имя шаблона для отображения графиков
	 */
	@GetMapping("/charts")
	public String generateCharts() {
		List<Object[]> list = service.getOrderMethodModeAndCount();
		String path = sc.getRealPath("/"); // Путь к корню веб-приложения
		util.generatePie(path, list);
		util.generateBar(path, list);
		return "OrderMethodCharts";
	}

	/**
	 * AJAX-проверка уникальности кода метода заказа.
	 *
	 * @param code код метода
	 * @param id   идентификатор (0 — создание, не 0 — редактирование)
	 * @return сообщение об ошибке, если код уже существует
	 */
	@GetMapping("/validate")
	@ResponseBody
	public String validateOm(@RequestParam String code, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.isOrderMethodCodeExist(code)) {
			message = code + ", уже существует!";
		} else if (id != 0 && service.isOrderMethodCodeExistForEdit(code, id)) {
			message = code + ", уже существует!";
		}
		return message;
	}
}

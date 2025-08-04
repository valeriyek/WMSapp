package ru.demo.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.demo.wms.model.Part;
import ru.demo.wms.service.IOrderMethodService;
import ru.demo.wms.service.IPartService;
import ru.demo.wms.service.IUomService;

/**
 * Контроллер для управления деталями (Part) в системе WMS.
 * <p>
 * Обрабатывает регистрацию, сохранение и отображение всех частей.
 */
@Controller
@RequestMapping("/part")
public class PartController {

	@Autowired
	private IPartService service;

	@Autowired
	private IUomService uomService;

	@Autowired
	private IOrderMethodService omService;

	/**
	 * Добавляет в модель общие данные, необходимые для форм:
	 * список единиц измерения и список методов заказа.
	 *
	 * @param model модель данных для представления
	 */
	private void commonUi(Model model) {
		model.addAttribute("uoms", uomService.getUomIdAndModel()); // список UOM'ов
		model.addAttribute("oms", omService.getOrderMethodIdAndCode()); // список методов заказа
	}

	/**
	 * Отображает форму регистрации новой детали.
	 *
	 * @param model модель для передачи справочных данных
	 * @return имя шаблона формы регистрации
	 */
	@GetMapping("/register")
	public String showReg(Model model) {
		commonUi(model); // заполняем выпадающие списки
		return "PartRegister";
	}

	/**
	 * Обрабатывает сохранение новой детали, полученной из формы.
	 *
	 * @param part  объект детали
	 * @param model модель для сообщения и справочных данных
	 * @return имя шаблона формы регистрации
	 */
	@PostMapping("/save")
	public String savePart(@ModelAttribute Part part, Model model) {
		Integer id = service.savePart(part);
		model.addAttribute("message", "Part '" + id + "' создан!");
		commonUi(model); // обновляем справочные списки
		return "PartRegister";
	}

	/**
	 * Отображает список всех деталей.
	 *
	 * @param model модель с данными
	 * @return имя шаблона списка деталей
	 */
	@GetMapping("/all")
	public String displayAll(Model model) {
		commonFetchAll(model);
		return "PartData";
	}

	/**
	 * Добавляет в модель список всех деталей.
	 *
	 * @param model модель
	 */
	private void commonFetchAll(Model model) {
		model.addAttribute("list", service.getAllParts());
	}
}

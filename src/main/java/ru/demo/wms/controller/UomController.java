package ru.demo.wms.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ru.demo.wms.exception.UomNotFoundException;
import ru.demo.wms.model.Uom;
import ru.demo.wms.service.IUomService;
import ru.demo.wms.view.UomExcelView;
import ru.demo.wms.view.UomPdfView;
/**
 * Контроллер для управления единицами измерения (UOM) в системе WMS.
 * <p>
 * Позволяет пользователю:
 * - создавать, просматривать, редактировать и удалять UOM;
 * - экспортировать данные в Excel и PDF;
 * - проверять уникальность модели через AJAX-запрос.
 */

@Controller
@RequestMapping("/uom")
public class UomController {

	private static final Logger LOG = LoggerFactory.getLogger(UomController.class);

	@Autowired
	private IUomService service;

	/**
	 * Отображение страницы регистрации новой единицы измерения.
	 */
	@GetMapping("/register")
	public String showReg() {
		return "UomRegister";
	}

	/**
	 * Сохранение новой UOM после отправки формы.
	 */
	@PostMapping("/save")
	public String saveUom(@ModelAttribute Uom uom, Model model) {
		LOG.info("Сохранение UOM...");
		try {
			Integer id = service.saveUom(uom);
			model.addAttribute("message", "Uom '" + id + "' saved");
			LOG.debug("UOM сохранён с ID: {}", id);
		} catch (Exception e) {
			LOG.error("Ошибка при сохранении UOM: {}", e.getMessage());
			e.printStackTrace();
		}
		return "UomRegister";
	}

	/**
	 * Отображение всех единиц измерения.
	 */
	@GetMapping("/all")
	public String displayUoms(Model model) {
		commonFetchAll(model);
		return "UomData";
	}

	/**
	 * Вспомогательный метод для получения списка всех UOM.
	 */
	private void commonFetchAll(Model model) {
		List<Uom> list = service.getAllUoms();
		model.addAttribute("list", list);
	}

	/**
	 * Удаление UOM по ID.
	 */
	@GetMapping("/delete")
	public String deleteUom(@RequestParam Integer id, Model model) {
		LOG.info("Удаление UOM ID={}", id);
		try {
			service.deleteUom(id);
			model.addAttribute("message", "Uom '" + id + "' Deleted");
			LOG.debug("Удалено успешно");
		} catch (UomNotFoundException e) {
			LOG.error("Не удалось удалить UOM: {}", e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		commonFetchAll(model);
		return "UomData";
	}

	/**
	 * Отображение формы редактирования UOM по ID.
	 */
	@GetMapping("/edit")
	public String showEdit(@RequestParam Integer id, Model model) {
		LOG.info("Редактирование UOM ID={}", id);
		String page;
		try {
			Uom uom = service.getOneUom(id);
			model.addAttribute("uom", uom);
			LOG.debug("Объект найден");
			page = "UomEdit";
		} catch (UomNotFoundException e) {
			LOG.error("UOM не найден: {}", e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
			commonFetchAll(model);
			page = "UomData";
		}
		return page;
	}

	/**
	 * Обновление существующего UOM.
	 */
	@PostMapping("/update")
	public String updateUom(@ModelAttribute Uom uom, Model model) {
		service.updateUom(uom);
		model.addAttribute("message", "Uom '" + uom.getId() + "' Updated!");
		commonFetchAll(model);
		return "UomData";
	}

	/**
	 * AJAX-проверка на уникальность модели UOM.
	 * Используется для валидации на клиентской стороне.
	 */
	@GetMapping("/validate")
	@ResponseBody
	public String validateUomModel(@RequestParam String model, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.isUomModelExist(model)) {
			message = model + ", already exist!";
		} else if (id != 0 && service.isUomModelExistForEdit(model, id)) {
			message = model + ", already exist!";
		}
		return message;
	}

	/**
	 * Экспорт списка всех UOM в Excel-файл.
	 */
	@GetMapping("/excel")
	public ModelAndView showExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new UomExcelView());
		m.addObject("list", service.getAllUoms());
		return m;
	}

	/**
	 * Экспорт списка всех UOM в PDF-документ.
	 */
	@GetMapping("/pdf")
	public ModelAndView shodPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new UomPdfView());
		m.addObject("uoms", service.getAllUoms());
		return m;
	}
}

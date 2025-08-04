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

import ru.demo.wms.exception.ShipmentTypeNotFoundException;
import ru.demo.wms.model.ShipmentType;
import ru.demo.wms.service.IShipmentTypeService;
import ru.demo.wms.util.ShipmentTypeUtil;
import ru.demo.wms.view.ShipmentTypeExcelView;
import ru.demo.wms.view.ShipmentTypePdfView;
/**
 * Контроллер для управления типами доставки в WMS-системе.
 * <p>
 * Предоставляет функции регистрации, отображения, удаления, редактирования,
 * экспорта и анализа типов доставки. Также реализует проверку уникальности кода.
 */
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

	/**
	 * Отображение страницы регистрации типа доставки.
	 */
	@GetMapping("/register")
	public String showRegister() {
		return "ShipmentTypeRegister";
	}

	/**
	 * Обработка сохранения нового типа доставки.
	 */
	@PostMapping("/save")
	public String saveShipmentType(@ModelAttribute ShipmentType shipmentType, Model model) {
		LOG.info("Сохранение нового типа доставки");
		try {
			Integer id = service.saveShipmentType(shipmentType);
			model.addAttribute("message", "Shipment Type '" + id + "' is created");
			LOG.debug("Создан тип доставки с ID {}", id);
		} catch (Exception e) {
			LOG.error("Ошибка при сохранении: {}", e.getMessage());
			model.addAttribute("message", "Unable to Process Request!");
			e.printStackTrace();
		}
		return "ShipmentTypeRegister";
	}

	/**
	 * Отображение всех типов доставки.
	 */
	@GetMapping("/all")
	public String fetchShipmentTypes(Model model) {
		LOG.info("Получение списка всех типов доставки");
		try {
			List<ShipmentType> list = service.getAllShipmentTypes();
			model.addAttribute("list", list);
			LOG.debug("Найдено записей: {}", list != null ? list.size() : "0");
		} catch (Exception e) {
			LOG.error("Ошибка при получении данных: {}", e.getMessage());
			e.printStackTrace();
		}
		return "ShipmentTypeData";
	}

	/**
	 * Удаление типа доставки по ID.
	 */
	@GetMapping("/delete")
	public String deleteShipmentType(@RequestParam Integer id, Model model) {
		LOG.info("Удаление типа доставки ID={}", id);
		try {
			service.deleteShipmentType(id);
			model.addAttribute("message", "Shipment Type '" + id + "' Deleted!");
		} catch (ShipmentTypeNotFoundException e) {
			LOG.error("Ошибка при удалении: {}", e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("list", service.getAllShipmentTypes());
		return "ShipmentTypeData";
	}

	/**
	 * Загрузка формы редактирования типа доставки по ID.
	 */
	@GetMapping("/edit")
	public String showShipmentTypeEdit(@RequestParam Integer id, Model model) {
		LOG.info("Редактирование типа доставки ID={}", id);
		String page;
		try {
			ShipmentType st = service.getShipmentType(id);
			model.addAttribute("shipmentType", st);
			page = "ShipmentTypeEdit";
		} catch (ShipmentTypeNotFoundException e) {
			LOG.error("Ошибка при загрузке данных: {}", e.getMessage());
			model.addAttribute("message", e.getMessage());
			model.addAttribute("list", service.getAllShipmentTypes());
			page = "ShipmentTypeData";
		}
		return page;
	}

	/**
	 * Обновление существующего типа доставки.
	 */
	@PostMapping("/update")
	public String updateShipmentType(@ModelAttribute ShipmentType shipmentType) {
		LOG.info("Обновление типа доставки ID={}", shipmentType.getId());
		try {
			service.updateShipmentType(shipmentType);
		} catch (Exception e) {
			LOG.error("Ошибка при обновлении: {}", e.getMessage());
			e.printStackTrace();
		}
		return "redirect:all";
	}

	/**
	 * AJAX-проверка уникальности кода типа доставки.
	 */
	@GetMapping("/validate")
	@ResponseBody
	public String validateShipmentTypeCode(@RequestParam String code, @RequestParam Integer id) {
		if (id == 0 && service.isShipmentTypeCodeExist(code)) {
			return code + ", already exist";
		} else if (id != 0 && service.isShipmentTypeCodeExistForEdit(code, id)) {
			return code + ", already exist";
		}
		return "";
	}

	/**
	 * Экспорт списка типов доставки в Excel.
	 */
	@GetMapping("/excel")
	public ModelAndView exportData() {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypeExcelView());
		m.addObject("list", service.getAllShipmentTypes());
		return m;
	}

	/**
	 * Генерация круговой и столбчатой диаграмм по типам доставки.
	 */
	@GetMapping("/charts")
	public String generateCharts() {
		List<Object[]> list = service.getShipmentTypeModeAndCount();
		String path = context.getRealPath("/");
		util.generatePieChart(path, list);
		util.generateBarChart(path, list);
		return "ShipmentTypeCharts";
	}

	/**
	 * Отображение отчета в формате PDF.
	 */
	@GetMapping("/pdf")
	public ModelAndView showPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new ShipmentTypePdfView());
		m.addObject("list", service.getAllShipmentTypes());
		return m;
	}
}

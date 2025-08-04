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

import ru.demo.wms.exception.WhUserTypeNotFound;
import ru.demo.wms.model.WhUserType;
import ru.demo.wms.service.IWhUserTypeService;
import ru.demo.wms.util.MailUtil;
import ru.demo.wms.util.WhUserTypeUtil;
import ru.demo.wms.view.WhUserTypeExcelView;
/**
 * Контроллер для управления типами пользователей склада в системе WMS.
 * <p>
 * Отвечает за:
 * <ul>
 *     <li>регистрацию новых типов пользователей склада,</li>
 *     <li>просмотр, редактирование и удаление существующих записей,</li>
 *     <li>AJAX-проверку уникальности кода, email и идентификатора,</li>
 *     <li>экспорт данных в Excel,</li>
 *     <li>генерацию графиков по количеству пользователей каждого типа.</li>
 * </ul>
 */

@Controller
@RequestMapping("/wh")
public class WhUserTypeController {

	private static final Logger LOG = LoggerFactory.getLogger(ShipmentTypeController.class);

	@Autowired
	private IWhUserTypeService service;

	@Autowired
	private WhUserTypeUtil util;

	@Autowired
	private ServletContext context;

	@Autowired
	private MailUtil mailUtil;

	/**
	 * Отображает страницу регистрации нового типа пользователя склада.
	 */
	@GetMapping("/register")
	public String ShowRegister() {
		return "WhUserTypeRegister";
	}

	/**
	 * Обрабатывает сохранение нового типа пользователя склада.
	 * Также запускает отправку уведомления на email.
	 */
	@PostMapping("/save")
	public String saveWhUserType(
			@ModelAttribute WhUserType whUserType,
			Model model) {

		LOG.info("Вход в метод save");

		try {
			Integer id = service.saveWhUserType(whUserType);

			// Отправка email в отдельном потоке
			if (id > 0) {
				new Thread(() -> {
					mailUtil.sendEmail(
							whUserType.getUserEmail(),
							"AUTO GENERATED EMAIL",
							"HELLO"
					);
				}).start();
			}

			String msg = "WhUserType '" + id + "' успешно создан";
			model.addAttribute("message", msg);

		} catch (Exception e) {
			LOG.error("Ошибка при сохранении: {}", e.getMessage());
			e.printStackTrace();
		}

		LOG.info("Переход к UI-странице");
		return "WhUserTypeRegister";
	}

	/**
	 * Отображает список всех типов пользователей склада.
	 */
	@GetMapping("/all")
	public String fetchWhUserTypes(Model model) {
		LOG.info("Запрос на получение всех записей");
		try {
			List<WhUserType> list = service.getAllWhUserTypes();
			model.addAttribute("list", list);
		} catch (Exception e) {
			LOG.error("Ошибка при получении списка: {}", e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Переход на страницу отображения");
		return "WhUserTypeData";
	}

	/**
	 * Удаляет тип пользователя по ID.
	 */
	@GetMapping("/delete")
	public String deleteWhUserTypeData(@RequestParam Integer id, Model model) {
		LOG.info("Запрос на удаление");
		try {
			service.deleteWhUserType(id);
			model.addAttribute("message", "WhUserType '" + id + "' удалён!");
		} catch (WhUserTypeNotFound e) {
			LOG.error("Ошибка при удалении: {}", e.getMessage());
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}

		model.addAttribute("list", service.getAllWhUserTypes());
		return "WhUserTypeData";
	}

	/**
	 * Загружает данные типа пользователя по ID для редактирования.
	 */
	@GetMapping("/edit")
	public String showWhUserTypeData(@RequestParam Integer id, Model model) {
		LOG.info("Запрос на редактирование");
		String page;
		try {
			WhUserType whut = service.getOneWhUserType(id);
			model.addAttribute("whusertype", whut);
			page = "WhUserTypeEdit";
		} catch (WhUserTypeNotFound e) {
			LOG.error("Ошибка при загрузке данных для редактирования: {}", e.getMessage());
			model.addAttribute("message", e.getMessage());
			model.addAttribute("list", service.getAllWhUserTypes());
			page = "WhUserTypeData";
		}
		LOG.info("Переход на страницу {}", page);
		return page;
	}

	/**
	 * Обновляет существующий тип пользователя.
	 */
	@PostMapping("/update")
	public String updateWhUserType(@ModelAttribute WhUserType whut) {
		LOG.info("Запрос на обновление");
		try {
			service.updateWhUserType(whut);
		} catch (Exception e) {
			LOG.error("Ошибка при обновлении: {}", e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Редирект на список всех записей");
		return "redirect:all";
	}

	// === AJAX-валидация ===

	/**
	 * AJAX: проверка уникальности кода типа пользователя.
	 */
	@GetMapping("/validate")
	@ResponseBody
	public String validateWhUserTypeCode(@RequestParam String code, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.isWhUserTypeCodeExit(code)) {
			message = code + " уже существует";
		} else if (id != 0 && service.isWhUserTypeCodeExitForEdit(code, id)) {
			message = code + " уже существует";
		}
		return message;
	}

	/**
	 * AJAX: проверка уникальности email пользователя.
	 */
	@GetMapping("/validateemail")
	@ResponseBody
	public String validateWhUserTypeEmail(@RequestParam String email, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.getWhUserTypeuserEmailCount(email)) {
			message = email + " уже существует";
		} else if (id != 0 && service.getWhUserTypeuserEmailCountForEdit(email, id)) {
			message = email + " уже существует";
		}
		return message;
	}

	/**
	 * AJAX: проверка уникальности идентификационного номера пользователя.
	 */
	@GetMapping("/validateidnum")
	@ResponseBody
	public String validateWhUserIdNum(@RequestParam String idnum, @RequestParam Integer id) {
		String message = "";
		if (id == 0 && service.getWhUserTypeuserIdNumCount(idnum)) {
			message = idnum + " уже существует";
		} else if (id != 0 && service.getWhUserTypeuserIdNumCountForEdit(idnum, id)) {
			message = idnum + " уже существует";
		}
		return message;
	}

	// === Экспорт и графики ===

	/**
	 * Экспорт всех записей в Excel-файл.
	 */
	@GetMapping("/excel")
	public ModelAndView exportData() {
		ModelAndView m = new ModelAndView();
		m.setView(new WhUserTypeExcelView());
		m.addObject("list", service.getAllWhUserTypes());
		return m;
	}

	/**
	 * Генерация круговой и столбчатой диаграмм по количеству пользователей каждого типа.
	 */
	@GetMapping("/charts")
	public String generateCharts() {
		List<Object[]> list = service.getWhUserTypUserIDAndCount();
		String path = context.getRealPath("/");
		util.generatePieChart(path, list);
		util.generateBarChart(path, list);
		return "WhUserTypeCharts";
	}
}

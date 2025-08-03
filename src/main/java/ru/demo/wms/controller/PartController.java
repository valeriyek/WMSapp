package ru.demo.wms.controller;
/*
Этот контроллер, PartController, управляет операциями, связанными с компонентами (частями, деталями) 
в приложении управления складом. Вот что он делает:

Общий пользовательский интерфейс (commonUi): Этот метод подготавливает общие данные для пользовательского интерфейса, 
такие как списки единиц измерения (uomService.getUomIdAndModel()) и методов заказа (omService.getOrderMethodIdAndCode()), 
и добавляет их в модель. Эти данные используются для заполнения выпадающих списков на формах веб-интерфейса.

Регистрация новой части (showReg): При GET-запросе к /part/register отображает страницу регистрации новой части. 
Этот метод также использует commonUi для добавления данных в модель, необходимых для динамических выпадающих списков на странице регистрации.

Сохранение новой части (savePart): При POST-запросе к /part/save принимает данные о новой части из формы, 
сохраняет часть через сервис IPartService и возвращает на страницу регистрации с сообщением об успешном создании. 
Повторно вызывает commonUi для обновления данных в модели, необходимых для выпадающих списков.

Отображение всех частей (displayAll): При GET-запросе к /part/all извлекает и отображает 
список всех частей из базы данных. Использует вспомогательный метод commonFetchAll 
для добавления списка частей в модель, которая затем передается в представление для отображения.

Общий метод для извлечения всех частей (commonFetchAll): Этот метод заполняет модель списком всех частей, 
полученным от IPartService, для их последующего отображения на странице данных о частях.

Контроллер интегрируется с различными службами, такими как IPartService для управления данными о частях, 
IUomService и IOrderMethodService для управления данными об единицах измерения и методах заказа соответственно. 
Вся эта интеграция позволяет создавать, сохранять и отображать данные о частях в удобном для пользователя веб-интерфейсе.*/

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

@Controller
@RequestMapping("/part")
public class PartController {

	@Autowired
	private IPartService service;
	
	@Autowired
	private IUomService uomService;
	
	@Autowired
	private IOrderMethodService omService;
	

	private void commonUi(Model model) {
		model.addAttribute("uoms", uomService.getUomIdAndModel());
		model.addAttribute("oms", omService.getOrderMethodIdAndCode());
	}

	@GetMapping("/register")
	public String showReg(Model model) {
		commonUi(model);
		return "PartRegister";
	}

	@PostMapping("/save")
	public String savePart(
			@ModelAttribute Part part,
			Model model) 
	{
		Integer id  = service.savePart(part);
		model.addAttribute("message", "Part '"+id+"' Created!");
		commonUi(model);
		return "PartRegister";
	}

	@GetMapping("/all")
	public String displayAll(
			Model model) 
	{
		commonFetchAll(model);
		return "PartData";
	}

	private void commonFetchAll(Model model) {
		model.addAttribute("list", service.getAllParts());
	}
}

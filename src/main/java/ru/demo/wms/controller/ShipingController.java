package ru.demo.wms.controller;

/*
ShipingController управляет отгрузками в приложении управления складом. Он выполняет следующие задачи:

Отображение формы регистрации отгрузки (showShipingRegisterPage): Показывает страницу для создания новой отгрузки 
с динамически заполненным выпадающим списком заказов на продажу, которые готовы к отгрузке.

Сохранение отгрузки (saveShiping): Принимает данные формы отгрузки, создает детали отгрузки 
на основе деталей заказа на продажу, сохраняет отгрузку и обновляет статус связанного заказа на продажу на "Отгружено".

Создание деталей отгрузки по заказу на продажу (createShipingDetailsBySaleOrder): 
По данному заказу на продажу извлекает детали заказа и создает соответствующие детали отгрузки.

Отображение всех отгрузок (showAllShiping): Предоставляет список всех отгрузок для отображения.

Отображение деталей отгрузки по ID отгрузки (showShipingDetailByShipingId): 
Показывает страницу с деталями конкретной отгрузки, включая список частей, входящих в отгрузку.

Обновление статуса детали отгрузки (updateAccepted и updateRejected): Позволяет обновить 
статус конкретной детали отгрузки на "Получено" или "Возвращено" соответственно.

Контроллер обеспечивает функциональность управления отгрузками от момента создания отгрузки 
до обновления статусов деталей отгрузки, что позволяет отслеживать процесс выполнения заказов на продажу. 
Это ключевой компонент системы управления складом, обеспечивающий связь между продажами и логистикой.
*/

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import ru.demo.wms.consts.SaleOrderStatus;
import ru.demo.wms.consts.ShipingDetailStatus;
import ru.demo.wms.model.SaleOrderDetails;
import ru.demo.wms.model.Shiping;
import ru.demo.wms.model.ShipingDtl;
import ru.demo.wms.service.ISaleOrderService;
import ru.demo.wms.service.IShipingService;

@Controller
@RequestMapping("/shiping")
public class ShipingController {

	private static final Logger log = LoggerFactory.getLogger(ShipingController.class);

	@Autowired
	private IShipingService service;

	@Autowired
	private ISaleOrderService orderService;


	private void commonUI(Model model) {
		model.addAttribute("sos", orderService.findSaleOrderIdAndCodeByStatus(SaleOrderStatus.INVOICED.name()));
	}


	@GetMapping("/register")
	public String showShipingRegisterPage(Model model) {
		log.info("Inside showShipingRegisterPage():");
		commonUI(model);
		return "shipingRegister";
	}


	@PostMapping("/save")
	public String saveShiping(@ModelAttribute Shiping shiping, Model model) {
		log.info("Inside saveShiping():");
		try {

			createShipingDetailsBySaleOrder(shiping);
			Integer id = service.saveShiping(shiping);

			if (id != null)
				orderService.updateSaleOrderStatus(shiping.getSo().getId(), SaleOrderStatus.SHIPPED.name());

			model.addAttribute("message", "Shiping Created :" + id);
		} catch (Exception e) {
			log.error("Exception inside saveShiping():" + e.getMessage());
			e.printStackTrace();
		}
		commonUI(model);
		log.info("About shipingRegister UI Page:");
		return "shipingRegister";
	}

	private void createShipingDetailsBySaleOrder(Shiping shiping) {

		Integer soId = shiping.getSo().getId();

		List<SaleOrderDetails> list = orderService.getSaleDtlsBySaleOrderId(soId);

		Set<ShipingDtl> shipingSet = new HashSet<>();

		for (SaleOrderDetails sdtl : list) {

			ShipingDtl shipingDtl = new ShipingDtl();
			shipingDtl.setPartCode(sdtl.getPart().getPartCode());
			shipingDtl.setBaseCost(sdtl.getPart().getPartBaseCost());
			shipingDtl.setQty(sdtl.getQty());

			shipingSet.add(shipingDtl);
		}
		shiping.setDtls(shipingSet);
	}


	@GetMapping("/all")
	public String showAllShiping(Model model) {
		log.info("Inside showAllShiping():");
		try {
			List<Shiping> list = service.getAllShiping();
			model.addAttribute("list", list);
		} catch (Exception e) {
			log.error("Exception inside showAllShiping():" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About ShipingData UI Page:");
		return "ShipingData";
	}


	@GetMapping("/parts")
	public String showShipingDetailByShipingId(@RequestParam Integer id, Model model) {
		log.info("Inside showShipingDetailByShipingId():");
		Shiping shiping = service.getOneShiping(id);
		model.addAttribute("shiping", shiping);
		model.addAttribute("list", shiping.getDtls());
		return "shipingParts";
	}


	@GetMapping("/accept")
	public String updateAccepted(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateShipingDtlStatus(dtlId, ShipingDetailStatus.RECEIVED.name());
		return "redirect:parts?id=" + id;
	}

	@GetMapping("/reject")
	public String updateRejected(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateShipingDtlStatus(dtlId, ShipingDetailStatus.RETURNED.name());
		return "redirect:parts?id=" + id;
	}

}

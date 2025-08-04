package ru.demo.wms.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.demo.wms.consts.SaleOrderStatus;
import ru.demo.wms.consts.ShipingDetailStatus;
import ru.demo.wms.model.SaleOrderDetails;
import ru.demo.wms.model.Shiping;
import ru.demo.wms.model.ShipingDtl;
import ru.demo.wms.service.ISaleOrderService;
import ru.demo.wms.service.IShipingService;

/**
 * Контроллер для управления отгрузками.
 * <p>
 * Обрабатывает регистрацию, отображение, детализацию и обновление статусов деталей отгрузки.
 * Обеспечивает связь между заказом на продажу и логистикой.
 */
@Controller
@RequestMapping("/shiping")
public class ShipingController {

	private static final Logger log = LoggerFactory.getLogger(ShipingController.class);

	@Autowired
	private IShipingService service;

	@Autowired
	private ISaleOrderService orderService;

	/**
	 * Добавляет в модель список заказов на продажу, готовых к отгрузке (статус INVOICED).
	 */
	private void commonUI(Model model) {
		model.addAttribute("sos", orderService.findSaleOrderIdAndCodeByStatus(SaleOrderStatus.INVOICED.name()));
	}

	/**
	 * Отображает страницу регистрации новой отгрузки.
	 */
	@GetMapping("/register")
	public String showShipingRegisterPage(Model model) {
		log.info("Переход к регистрации отгрузки");
		commonUI(model);
		return "shipingRegister";
	}

	/**
	 * Сохраняет отгрузку и создаёт её детали на основе заказа на продажу.
	 */
	@PostMapping("/save")
	public String saveShiping(@ModelAttribute Shiping shiping, Model model) {
		log.info("Сохранение новой отгрузки");
		try {
			createShipingDetailsBySaleOrder(shiping); // создаём строки на основе заказа
			Integer id = service.saveShiping(shiping);

			// Обновить статус заказа на продажу как "SHIPPED"
			if (id != null)
				orderService.updateSaleOrderStatus(shiping.getSo().getId(), SaleOrderStatus.SHIPPED.name());

			model.addAttribute("message", "Отгрузка создана: " + id);
		} catch (Exception e) {
			log.error("Ошибка при сохранении отгрузки: " + e.getMessage());
			e.printStackTrace();
		}
		commonUI(model);
		return "shipingRegister";
	}

	/**
	 * Создаёт строки отгрузки (ShipingDtl) на основе строк заказа на продажу.
	 *
	 * @param shiping объект отгрузки, в который добавляются детали
	 */
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

	/**
	 * Отображает список всех отгрузок.
	 */
	@GetMapping("/all")
	public String showAllShiping(Model model) {
		log.info("Загрузка всех отгрузок");
		try {
			List<Shiping> list = service.getAllShiping();
			model.addAttribute("list", list);
		} catch (Exception e) {
			log.error("Ошибка при отображении отгрузок: " + e.getMessage());
			e.printStackTrace();
		}
		return "ShipingData";
	}

	/**
	 * Отображает детали конкретной отгрузки.
	 *
	 * @param id идентификатор отгрузки
	 */
	@GetMapping("/parts")
	public String showShipingDetailByShipingId(@RequestParam Integer id, Model model) {
		log.info("Отображение деталей отгрузки ID: " + id);
		Shiping shiping = service.getOneShiping(id);
		model.addAttribute("shiping", shiping);
		model.addAttribute("list", shiping.getDtls());
		return "shipingParts";
	}

	/**
	 * Обновляет статус детали отгрузки на "Получено".
	 *
	 * @param id    ID отгрузки
	 * @param dtlId ID детали
	 */
	@GetMapping("/accept")
	public String updateAccepted(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateShipingDtlStatus(dtlId, ShipingDetailStatus.RECEIVED.name());
		return "redirect:parts?id=" + id;
	}

	/**
	 * Обновляет статус детали отгрузки на "Возвращено".
	 *
	 * @param id    ID отгрузки
	 * @param dtlId ID детали
	 */
	@GetMapping("/reject")
	public String updateRejected(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateShipingDtlStatus(dtlId, ShipingDetailStatus.RETURNED.name());
		return "redirect:parts?id=" + id;
	}
}

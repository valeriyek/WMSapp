package ru.demo.wms.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ru.demo.wms.consts.SaleOrderStatus;
import ru.demo.wms.model.SaleOrder;
import ru.demo.wms.model.SaleOrderDetails;
import ru.demo.wms.service.IPartService;
import ru.demo.wms.service.ISaleOrderService;
import ru.demo.wms.service.IShipmentTypeService;
import ru.demo.wms.service.IWhUserTypeService;
import ru.demo.wms.view.CustomerInvoicePDFView;

/**
 * Контроллер для управления заказами на продажу (Sale Orders).
 * <p>
 * Поддерживает операции регистрации, сохранения, отображения, добавления деталей, изменения количества,
 * смены статуса и генерации PDF-счёта.
 */
@Controller
@RequestMapping("/sale")
public class SaleOrderController {

	private static final Logger log = LoggerFactory.getLogger(SaleOrderController.class);

	@Autowired
	private ISaleOrderService service;

	@Autowired
	private IShipmentTypeService shipmentService;

	@Autowired
	private IWhUserTypeService whUserService;

	@Autowired
	private IPartService partService;

	/**
	 * Добавляет в модель список типов отгрузки и клиентов для использования в форме.
	 */
	private void commonUI(Model model) {
		model.addAttribute("sts", shipmentService.getShipmentIdAndCodeByEnable("Yes"));
		model.addAttribute("customers", whUserService.getWhUserIdAndCodeByType("Customer"));
	}

	/**
	 * Отображает форму для регистрации нового заказа на продажу.
	 */
	@GetMapping("/register")
	public String showSaleOrderRegisterPage(Model model) {
		log.info("Переход на страницу регистрации заказа на продажу");
		commonUI(model);
		return "registerSaleOrder";
	}

	/**
	 * Сохраняет заказ на продажу, полученный из формы.
	 */
	@PostMapping("/save")
	public String saveSaleOrder(@ModelAttribute SaleOrder saleOrder, Model model) {
		log.info("Сохранение нового заказа на продажу");
		try {
			Integer id = service.saveSaleOrder(saleOrder);
			model.addAttribute("message", "Заказ на продажу создан: " + id);
			log.debug("Сохранён заказ ID: " + id);
		} catch (Exception e) {
			log.error("Ошибка при сохранении: " + e.getMessage());
			e.printStackTrace();
		}
		commonUI(model);
		return "registerSaleOrder";
	}

	/**
	 * Отображает список всех заказов на продажу.
	 */
	@GetMapping("/all")
	public String fetchAllSaleOrder(Model model) {
		log.info("Загрузка списка всех заказов на продажу");
		try {
			List<SaleOrder> list = service.getAllSaleOrder();
			model.addAttribute("list", list);
		} catch (Exception e) {
			log.error("Ошибка при загрузке заказов: " + e.getMessage());
			e.printStackTrace();
		}
		return "saleOrderData";
	}

	/**
	 * AJAX-проверка уникальности кода заказа.
	 */
	@GetMapping("/validateOrderCode")
	@ResponseBody
	public String validateOrderCode(@RequestParam String code, @RequestParam Integer id) {
		log.info("Проверка уникальности кода заказа");
		String message = "";
		try {
			if (id == 0 && service.validateOrderCode(code))
				message = code + ", уже существует";
			else if (id != 0 && service.validateOrderCodeAndId(code, id))
				message = code + ", уже существует";
		} catch (Exception e) {
			log.error("Ошибка в validateOrderCode(): " + e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * Добавляет в модель список всех деталей (part), доступных для выбора.
	 */
	private void commonUIForParts(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}

	/**
	 * Отображает страницу управления деталями (позициями) заказа.
	 */
	@GetMapping("/parts")
	public String showSaleOrderPartPage(@RequestParam Integer id, Model model) {
		log.info("Переход к деталям заказа на продажу");
		try {
			SaleOrder saleOrder = service.getOneSaleOrder(id);
			model.addAttribute("saleOrder", saleOrder);
			String status = service.getCurrentStatusOfSaleOrder(id);
			if (SaleOrderStatus.OPEN.name().equals(status) || SaleOrderStatus.READY.name().equals(status)) {
				commonUIForParts(model);
			}
		} catch (Exception e) {
			log.error("Ошибка при загрузке деталей заказа: " + e.getMessage());
			e.printStackTrace();
		}
		List<SaleOrderDetails> list = service.getSaleDtlsBySaleOrderId(id);
		model.addAttribute("list", list);
		return "saleOrderPart";
	}

	/**
	 * Добавляет или обновляет деталь в заказе.
	 */
	@PostMapping("/addPart")
	public String addPart(SaleOrderDetails saleOrderDetails) {
		Integer soId = saleOrderDetails.getSaleOrder().getId();
		String status = service.getCurrentStatusOfSaleOrder(soId);
		if (SaleOrderStatus.OPEN.name().equals(status) || SaleOrderStatus.READY.name().equals(status)) {
			Integer partId = saleOrderDetails.getPart().getId();
			Optional<SaleOrderDetails> optional = service.getSaleDetailByPartIdAndSaleOrderId(partId, soId);
			if (optional.isPresent()) {
				service.updateSaleOrderDetailQtyByDetailId(saleOrderDetails.getQty(), optional.get().getId());
			} else {
				service.savePurchaseDetails(saleOrderDetails);
			}
			if (SaleOrderStatus.OPEN.name().equals(status)) {
				service.updateSaleOrderStatus(soId, SaleOrderStatus.READY.name());
			}
		}
		return "redirect:parts?id=" + soId;
	}

	/**
	 * Удаляет деталь из заказа и меняет статус, если больше нет деталей.
	 */
	@GetMapping("/removePart")
	public String removePart(@RequestParam Integer detailId, @RequestParam Integer saleOrderId) {
		if (SaleOrderStatus.READY.name().equals(service.getCurrentStatusOfSaleOrder(saleOrderId))) {
			service.deleteSaleDetails(detailId);
			if (service.getSaleDtlsCountBySaleOrderId(saleOrderId) == 0) {
				service.updateSaleOrderStatus(saleOrderId, SaleOrderStatus.OPEN.name());
			}
		}
		return "redirect:parts?id=" + saleOrderId;
	}

	/**
	 * Увеличивает количество по позиции на +1.
	 */
	@GetMapping("/increaseQty")
	public String increaseQty(@RequestParam Integer detailId, @RequestParam Integer saleOrderId) {
		log.info("Увеличение количества на 1");
		service.updateSaleOrderDetailQtyByDetailId(1, detailId);
		return "redirect:parts?id=" + saleOrderId;
	}

	/**
	 * Уменьшает количество по позиции на -1.
	 */
	@GetMapping("/decreaseQty")
	public String decreaseQty(@RequestParam Integer detailId, @RequestParam Integer saleOrderId) {
		log.info("Уменьшение количества на 1");
		service.updateSaleOrderDetailQtyByDetailId(-1, detailId);
		return "redirect:parts?id=" + saleOrderId;
	}

	/**
	 * Переводит заказ в статус CONFIRM.
	 */
	@GetMapping("/placeOrder")
	public String placeOrder(@RequestParam Integer saleOrderId) {
		log.info("Подтверждение заказа (CONFIRM)");
		if (SaleOrderStatus.READY.name().equals(service.getCurrentStatusOfSaleOrder(saleOrderId))) {
			service.updateSaleOrderStatus(saleOrderId, SaleOrderStatus.CONFIRM.name());
		}
		return "redirect:parts?id=" + saleOrderId;
	}

	/**
	 * Отменяет заказ, если он ещё не отменён.
	 */
	@GetMapping("/cancelOrder")
	public String cancelOrder(@RequestParam Integer id) {
		log.info("Отмена заказа");
		String status = service.getCurrentStatusOfSaleOrder(id);
		if (SaleOrderStatus.READY.name().equals(status) || SaleOrderStatus.CONFIRM.name().equals(status)
				|| SaleOrderStatus.OPEN.name().equals(status) || !SaleOrderStatus.CANCELLED.name().equals(status)) {
			service.updateSaleOrderStatus(id, SaleOrderStatus.CANCELLED.name());
		}
		return "redirect:all";
	}

	/**
	 * Переводит заказ в статус INVOICED.
	 */
	@GetMapping("/generate")
	public String generateInvoice(@RequestParam Integer id) {
		log.info("Генерация счёта (INVOICED)");
		service.updateSaleOrderStatus(id, SaleOrderStatus.INVOICED.name());
		return "redirect:all";
	}

	/**
	 * Генерирует PDF-счёт для клиента.
	 */
	@GetMapping("/print")
	public ModelAndView showCustomerInvoice(@RequestParam Integer id) {
		log.info("Генерация PDF для клиента");
		ModelAndView mav = new ModelAndView();
		mav.setView(new CustomerInvoicePDFView());

		List<SaleOrderDetails> list = service.getSaleDtlsBySaleOrderId(id);
		mav.addObject("list", list);

		SaleOrder saleOrder = service.getOneSaleOrder(id);
		mav.addObject("saleOrder", saleOrder);

		return mav;
	}
}

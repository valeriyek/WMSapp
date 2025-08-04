package ru.demo.wms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ru.demo.wms.consts.PurchaseOrderStatus;
import ru.demo.wms.model.PurchaseDtl;
import ru.demo.wms.model.PurchaseOrder;
import ru.demo.wms.service.IPartService;
import ru.demo.wms.service.IPurchaseOrderService;
import ru.demo.wms.service.IShipmentTypeService;
import ru.demo.wms.service.IWhUserTypeService;
import ru.demo.wms.view.VendorInvoicePdfView;

/**
 * Контроллер для управления заказами на закупку (Purchase Order).
 * <p>
 * Поддерживает регистрацию, сохранение, отображение, добавление/удаление деталей,
 * изменение количества, смену статуса, а также экспорт в PDF.
 */
@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

	@Autowired
	private IPurchaseOrderService service;

	@Autowired
	private IShipmentTypeService shipmentTypeService;

	@Autowired
	private IWhUserTypeService whUserTypeService;

	@Autowired
	private IPartService partService;

	/**
	 * Добавляет общие данные в модель:
	 * - список типов отправки (Shipment Types)
	 * - список поставщиков (Vendor Users)
	 */
	private void addCommonUi(Model model) {
		model.addAttribute("sts", shipmentTypeService.getShipmentIdAndCodeByEnable("Yes"));
		model.addAttribute("vendors", whUserTypeService.getWhUserIdAndCodeByType("Vendor"));
	}

	/**
	 * Отображает форму регистрации нового заказа.
	 */
	@GetMapping("/register")
	public String showReg(Model model) {
		addCommonUi(model);
		return "PurchaseOrderRegister";
	}

	/**
	 * Сохраняет новый заказ на закупку.
	 */
	@PostMapping("/save")
	public String save(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {
		try {
			Integer id = service.savePurchaseOrder(purchaseOrder);
			model.addAttribute("message", "Заказ '" + id + "' создан!");
		} catch (Exception e) {
			model.addAttribute("message", "Не удалось создать заказ!");
			e.printStackTrace();
		}
		addCommonUi(model);
		return "PurchaseOrderRegister";
	}

	/**
	 * Отображает список всех заказов на закупку.
	 */
	@GetMapping("/all")
	public String getAll(Model model) {
		try {
			List<PurchaseOrder> list = service.getAllPurchaseOrders();
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PurchaseOrderData";
	}

	/**
	 * Добавляет в модель список всех деталей (частей), доступных для выбора.
	 */
	private void commonUiForParts(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}

	/**
	 * Отображает страницу деталей заказа (детали можно добавлять, удалять, менять количество).
	 */
	@GetMapping("/parts")
	public String showPoPartsPage(@RequestParam Integer id, Model model) {
		PurchaseOrder po = service.getOnePurchaseOrder(id);
		model.addAttribute("po", po);

		String status = service.getCurrentStatusOfPo(id);
		if (PurchaseOrderStatus.OPEN.name().equals(status) || PurchaseOrderStatus.PICKING.name().equals(status)) {
			commonUiForParts(model);
		}

		List<PurchaseDtl> poDtls = service.getPurchaseDtlsByPoId(id);
		model.addAttribute("list", poDtls);
		return "PurchaseOrderParts";
	}

	/**
	 * Добавляет или обновляет деталь (позицию) в заказе.
	 */
	@PostMapping("/addPart")
	public String addPart(PurchaseDtl dtl) {
		Integer poId = dtl.getPo().getId();

		String currentStatus = service.getCurrentStatusOfPo(poId);
		if (PurchaseOrderStatus.OPEN.name().equals(currentStatus) || PurchaseOrderStatus.PICKING.name().equals(currentStatus)) {

			Integer partId = dtl.getPart().getId();
			Optional<PurchaseDtl> opt = service.getPurchaseDtlByPartIdAndPoId(partId, poId);

			if (opt.isPresent()) {
				// Увеличить количество существующей детали
				service.updatePurchaseDtlQtyByDtlId(dtl.getQty(), opt.get().getId());
			} else {
				// Добавить новую деталь
				service.savePurchaseDtl(dtl);
			}

			// Если был статус OPEN — перевести в PICKING
			if (PurchaseOrderStatus.OPEN.name().equals(currentStatus)) {
				service.updatePoStatus(poId, PurchaseOrderStatus.PICKING.name());
			}
		}
		return "redirect:parts?id=" + poId;
	}

	/**
	 * Удаляет деталь из заказа. Если это была последняя — меняет статус на OPEN.
	 */
	@GetMapping("/removePart")
	public String removePart(@RequestParam Integer poId, @RequestParam Integer dtlId) {
		if (PurchaseOrderStatus.PICKING.name().equals(service.getCurrentStatusOfPo(poId))) {
			service.deletePurchaseDtl(dtlId);
			if (service.getPurchaseDtlsCountByPoId(poId) == 0) {
				service.updatePoStatus(poId, PurchaseOrderStatus.OPEN.name());
			}
		}
		return "redirect:parts?id=" + poId;
	}

	/**
	 * Увеличивает количество по позиции на +1.
	 */
	@GetMapping("/increaseQty")
	public String increaseQty(@RequestParam Integer poId, @RequestParam Integer dtlId) {
		service.updatePurchaseDtlQtyByDtlId(1, dtlId);
		return "redirect:parts?id=" + poId;
	}

	/**
	 * Уменьшает количество по позиции на -1.
	 */
	@GetMapping("/reduceQty")
	public String reduceQty(@RequestParam Integer poId, @RequestParam Integer dtlId) {
		service.updatePurchaseDtlQtyByDtlId(-1, dtlId);
		return "redirect:parts?id=" + poId;
	}

	/**
	 * Переводит заказ в статус ORDERED, если сейчас он в PICKING.
	 */
	@GetMapping("/placeOrder")
	public String placeOrder(@RequestParam Integer poId) {
		if (PurchaseOrderStatus.PICKING.name().equals(service.getCurrentStatusOfPo(poId))) {
			service.updatePoStatus(poId, PurchaseOrderStatus.ORDERED.name());
		}
		return "redirect:parts?id=" + poId;
	}

	/**
	 * Отменяет заказ, если он ещё не был отменён.
	 */
	@GetMapping("/cancel")
	public String cancelOrder(@RequestParam Integer id) {
		String status = service.getCurrentStatusOfPo(id);
		if (PurchaseOrderStatus.OPEN.name().equals(status)
				|| PurchaseOrderStatus.PICKING.name().equals(status)
				|| PurchaseOrderStatus.ORDERED.name().equals(status)
				|| !PurchaseOrderStatus.CANCELLED.name().equals(status)) {
			service.updatePoStatus(id, PurchaseOrderStatus.CANCELLED.name());
		}
		return "redirect:all";
	}

	/**
	 * Переводит заказ в статус INVOICED.
	 */
	@GetMapping("/generate")
	public String generateInvoice(@RequestParam Integer id) {
		service.updatePoStatus(id, PurchaseOrderStatus.INVOICED.name());
		return "redirect:all";
	}

	/**
	 * Генерирует PDF-счёт по заказу.
	 */
	@GetMapping("/print")
	public ModelAndView showVendorInvoice(@RequestParam Integer id) {
		ModelAndView m = new ModelAndView();
		m.setView(new VendorInvoicePdfView());

		List<PurchaseDtl> list = service.getPurchaseDtlsByPoId(id);
		m.addObject("list", list);

		PurchaseOrder po = service.getOnePurchaseOrder(id);
		m.addObject("po", po);

		return m;
	}
}

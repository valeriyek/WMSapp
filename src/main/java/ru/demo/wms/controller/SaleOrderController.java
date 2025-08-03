package ru.demo.wms.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.servlet.ModelAndView;

import ru.demo.wms.consts.SaleOrderStatus;
import ru.demo.wms.model.SaleOrder;
import ru.demo.wms.model.SaleOrderDetails;
import ru.demo.wms.service.IPartService;
import ru.demo.wms.service.ISaleOrderService;
import ru.demo.wms.service.IShipmentTypeService;
import ru.demo.wms.service.IWhUserTypeService;
import ru.demo.wms.view.CustomerInvoicePDFView;

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

	private void commonUI(Model model) {
		model.addAttribute("sts", shipmentService.getShipmentIdAndCodeByEnable("Yes"));
		model.addAttribute("customers", whUserService.getWhUserIdAndCodeByType("Customer"));
	}

	@GetMapping("/register")
	public String showSaleOrderRegisterPage(Model model) {
		log.info("Inside showSaleOrderRegisterPage():");
		commonUI(model);
		return "registerSaleOrder";
	}

	@PostMapping("/save")
	public String saveSaleOrder(@ModelAttribute SaleOrder saleOrder, Model model) {
		log.info("Inside saveSaleOrder():");
		try {
			Integer id = service.saveSaleOrder(saleOrder);
			String msg = "Sale Order Created : " + id;
			model.addAttribute("message", msg);
			log.debug("Sale Order Save : " + id);
		} catch (Exception e) {
			log.error("Exception inside saveSaleOrder():" + e.getMessage());
			e.printStackTrace();
		}
		commonUI(model);
		log.info("About registerSaleOrder UI Page:");
		return "registerSaleOrder";
	}

	@GetMapping("/all")
	public String fetchAllSaleOrder(Model model) {
		log.info("Inside fetchAllSaleOrder():");
		try {
			List<SaleOrder> list = service.getAllSaleOrder();
			model.addAttribute("list", list);
			log.debug("Fetch All Sale Order Record:" + list);
		} catch (Exception e) {
			log.error("Exception inside fetchAllSaleOrder():" + e.getMessage());
			e.printStackTrace();
		}
		log.info("About saleOrderData UI Page:");
		return "saleOrderData";
	}

	@GetMapping("/validateOrderCode")
	public String validateOrderCode(@RequestParam String code, @RequestParam Integer id) {
		log.info("Inside validateOrderCode():");
		String message = "";
		try {
			if (id == 0 && service.validateOrderCode(code))
				message = code + ",Already Exit";
			else if (id != 0 && service.validateOrderCodeAndId(code, id))
				message = code + ",Already Exit";
		} catch (Exception e) {
			log.error("Exception inside validateOrderCode():" + e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	private void commonUIForParts(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}

	@GetMapping("/parts")
	public String showSaleOrderPartPage(@RequestParam Integer id, Model model) {
		log.info("Inside showSaleOrderPartPage():");
		try {
			SaleOrder saleOrder = service.getOneSaleOrder(id);
			model.addAttribute("saleOrder", saleOrder);
			String status = service.getCurrentStatusOfSaleOrder(id);
			if (SaleOrderStatus.OPEN.name().equals(status) || SaleOrderStatus.READY.name().equals(status)) {
				commonUIForParts(model);
			}
		} catch (Exception e) {
			log.error("Exception inside showSaleOrderPartPage():" + e.getMessage());
			e.printStackTrace();
		}
		List<SaleOrderDetails> list = service.getSaleDtlsBySaleOrderId(id);
		model.addAttribute("list", list);
		log.info("About saleOrderParts UI Page");
		return "saleOrderPart";
	}

	@PostMapping("/addPart")
	public String addPart(SaleOrderDetails saleOrderDetails) {
		Integer soId = saleOrderDetails.getSaleOrder().getId();
		if (SaleOrderStatus.OPEN.name().equals(service.getCurrentStatusOfSaleOrder(soId))
				|| SaleOrderStatus.READY.name().equals(service.getCurrentStatusOfSaleOrder(soId))) {
			Integer partId = saleOrderDetails.getPart().getId();
			Optional<SaleOrderDetails> optional = service.getSaleDetailByPartIdAndSaleOrderId(partId, soId);
			if (optional.isPresent()) {
				service.updateSaleOrderDetailQtyByDetailId(saleOrderDetails.getQty(), optional.get().getId());
			} else {
				service.savePurchaseDetails(saleOrderDetails);
			}
			if (SaleOrderStatus.OPEN.name().equals(service.getCurrentStatusOfSaleOrder(soId))) {
				service.updateSaleOrderStatus(soId, SaleOrderStatus.READY.name());
			}
		}
		return "redirect:parts?id=" + soId;
	}

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

	@GetMapping("/increaseQty")
	public String increaseQty(@RequestParam Integer detailId, @RequestParam Integer saleOrderId) {
		log.info("Inside increaseQty():");
		service.updateSaleOrderDetailQtyByDetailId(1, detailId);
		return "redirect:parts?id=" + saleOrderId;
	}

	@GetMapping("/decreaseQty")
	public String decreaseQty(@RequestParam Integer detailId, @RequestParam Integer saleOrderId) {
		log.info("Inside decreaseQty():");
		service.updateSaleOrderDetailQtyByDetailId(-1, detailId);
		return "redirect:parts?id=" + saleOrderId;
	}

	@GetMapping("/placeOrder")
	public String placeOrder(@RequestParam Integer saleOrderId) {
		log.info("Inside placeOrder():");
		if (SaleOrderStatus.READY.name().equals(service.getCurrentStatusOfSaleOrder(saleOrderId))) {
			service.updateSaleOrderStatus(saleOrderId, SaleOrderStatus.CONFIRM.name());
		}
		return "redirect:parts?id=" + saleOrderId;
	}

	@GetMapping("/cancelOrder")
	public String cancelOrder(@RequestParam Integer id) {
		log.info("Inside cancelOrder():");
		String status = service.getCurrentStatusOfSaleOrder(id);
		if (SaleOrderStatus.READY.name().equals(status) || SaleOrderStatus.CONFIRM.name().equals(status)
				|| SaleOrderStatus.OPEN.name().equals(status) || !SaleOrderStatus.CANCELLED.name().equals(status)) {
			service.updateSaleOrderStatus(id, SaleOrderStatus.CANCELLED.name());
		}
		return "redirect:all";
	}

	@GetMapping("/generate")
	public String generateInvoice(@RequestParam Integer id) {
		log.info("Inside generateInvoice():");
		service.updateSaleOrderStatus(id, SaleOrderStatus.INVOICED.name());
		return "redirect:all";
	}

	@GetMapping("/print")
	public ModelAndView showCustomerInvoice(@RequestParam Integer id) {
		log.info("Inside showCustomerInvoice():");
		ModelAndView mav = new ModelAndView();
		mav.setView(new CustomerInvoicePDFView());
		List<SaleOrderDetails> list = service.getSaleDtlsBySaleOrderId(id);
		mav.addObject("list", list);
		SaleOrder saleOrder = service.getOneSaleOrder(id);
		mav.addObject("saleOrder", saleOrder);
		return mav;
	}
}

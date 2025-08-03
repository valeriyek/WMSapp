package ru.demo.wms.controller;

/*Этот контроллер, PurchaseOrderController, управляет процессами покупки в приложении управления складом. 
Вот его ключевые функции:

Добавление общего пользовательского интерфейса (addCommonUi): Метод добавляет данные для динамических 
выпадающих списков на странице регистрации заказа на покупку, используя услуги для 
типов отправления (shipmentTypeService) и поставщиков (whUserTypeService).

Регистрация заказа на покупку (showReg): При GET-запросе к /po/register отображает 
форму для регистрации нового заказа на покупку и использует addCommonUi для заполнения данных модели.

Сохранение заказа на покупку (save): При POST-запросе принимает данные о заказе на покупку, 
сохраняет их и возвращает на страницу регистрации с сообщением о создании или ошибке.

Отображение всех заказов на покупку (getAll): Отображает список всех заказов на покупку, 
извлечённых из базы данных.

Управление частями заказа на покупку (showPoPartsPage и другие связанные методы): 
Эти методы управляют добавлением, удалением и обновлением частей (деталей) заказа на покупку. 
Они обрабатывают операции, такие как добавление детали к заказу, удаление детали из заказа, 
изменение количества деталей в заказе и т.д.

Изменение статуса заказа на покупку: Включает методы для размещения заказа (placeOrder), 
отмены заказа (cancel), генерации счета (generate) и экспорта данных заказа в PDF (print). 
Эти методы позволяют изменять статус заказа на разных этапах его обработки.

Экспорт в PDF (showVendorInvoice): Генерирует PDF-документ с информацией о заказе на покупку, 
используя представление VendorInvoicePdfView.

Этот контроллер тесно взаимодействует с сервисами, отвечающими за управление типами отправлений, 
поставщиками, частями и самими заказами на покупку. Он предоставляет функционал для создания, управления 
и отслеживания заказов на покупку, а также для изменения их статуса в соответствии с текущим этапом обработки заказа.*/

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ru.demo.wms.consts.PurchaseOrderStatus;
import ru.demo.wms.model.PurchaseDtl;
import ru.demo.wms.model.PurchaseOrder;
import ru.demo.wms.service.IPartService;
import ru.demo.wms.service.IPurchaseOrderService;
import ru.demo.wms.service.IShipmentTypeService;
import ru.demo.wms.service.IWhUserTypeService;
import ru.demo.wms.view.VendorInvoicePdfView;

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



	private void addCommonUi(Model model) {
		model.addAttribute("sts", shipmentTypeService.getShipmentIdAndCodeByEnable("Yes"));
		model.addAttribute("vendors", whUserTypeService.getWhUserIdAndCodeByType("Vendor"));
	}



	@GetMapping("/register")
	public String showReg(Model model) {
		addCommonUi(model);
		return "PurchaseOrderRegister";
	}


	@PostMapping("/save")
	public String save(
			@ModelAttribute PurchaseOrder purchaseOrder,
			Model model) 
	{
		try {
			Integer id = service.savePurchaseOrder(purchaseOrder);
			model.addAttribute("message", "Order '"+id+"' is created!");
		} catch (Exception e) {
			model.addAttribute("message", "Order is failed to created!");
			e.printStackTrace();
		}
		addCommonUi(model);
		return "PurchaseOrderRegister";
	}

	@GetMapping("/all")
	public String getAll(Model model)
	{
		try {
			List<PurchaseOrder> list =  service.getAllPurchaseOrders();
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PurchaseOrderData";
	}

	///---------------------------(Screen#2)------------------------------
	private void commonUiForParts(Model model) {
		model.addAttribute("parts", partService.getPartIdAndCode());
	}


	@GetMapping("/parts")
	public String showPoPartsPage(
			@RequestParam Integer id,
			Model model
			) 
	{

		PurchaseOrder po = service.getOnePurchaseOrder(id);
		model.addAttribute("po", po);

		String status = service.getCurrentStatusOfPo(id);
		if(PurchaseOrderStatus.OPEN.name().equals(status) || 
				PurchaseOrderStatus.PICKING.name().equals(status)
				) 
		{

			commonUiForParts(model);

		}

		List<PurchaseDtl> poDtls = service.getPurchaseDtlsByPoId(id);
		model.addAttribute("list", poDtls);

		return "PurchaseOrderParts";
	}
	/**
	 * On Click Part Add, Read Form data as Dtl object
	 * save using service.
	 * Redirect to same page using /parts?id=<PurchaseOrderId>
	 * 
	 */
	@PostMapping("/addPart")
	public String addPart(PurchaseDtl dtl) {
		Integer poId = dtl.getPo().getId();
		if( PurchaseOrderStatus.OPEN.name()
				.equals(service.getCurrentStatusOfPo(poId))
				|| 
				PurchaseOrderStatus.PICKING.name()
				.equals(service.getCurrentStatusOfPo(poId)) 
				) 
		{
			Integer partId = dtl.getPart().getId();

			Optional<PurchaseDtl> opt = service.getPurchaseDtlByPartIdAndPoId(partId, poId);
			if(opt.isPresent()) {

				service.updatePurchaseDtlQtyByDtlId(dtl.getQty(), opt.get().getId());
			} else {
				service.savePurchaseDtl(dtl);
			}

			if(PurchaseOrderStatus.OPEN.name()
					.equals(service.getCurrentStatusOfPo(poId))
					) 
			{
				service.updatePoStatus(poId, PurchaseOrderStatus.PICKING.name());
			}
		}
		return "redirect:parts?id="+ poId;
	}


	@GetMapping("/removePart")
	public String removePart(
			@RequestParam Integer poId,
			@RequestParam Integer dtlId
			)
	{
		if(PurchaseOrderStatus.PICKING.name()
				.equals(service.getCurrentStatusOfPo(poId))
				) 
		{
			service.deletePurchaseDtl(dtlId);
			if(service.getPurchaseDtlsCountByPoId(poId)==0) {
				service.updatePoStatus(poId, PurchaseOrderStatus.OPEN.name());
			}
		}
		return "redirect:parts?id="+poId;
	}

	/***
	 * IncreaseQty by +1
	 * 
	 */
	@GetMapping("/increaseQty")
	public String increaseQty(
			@RequestParam Integer poId,
			@RequestParam Integer dtlId
			)
	{
		service.updatePurchaseDtlQtyByDtlId(1, dtlId);
		return "redirect:parts?id="+poId;
	}
	/***
	 * reduce Qty by -1
	 */
	@GetMapping("/reduceQty")
	public String reduceQty(
			@RequestParam Integer poId,
			@RequestParam Integer dtlId
			)
	{
		service.updatePurchaseDtlQtyByDtlId(-1, dtlId);
		return "redirect:parts?id="+poId;
	}

	/***
	 * PLACE ORDER
	 */
	@GetMapping("/placeOrder")
	public String placeOrder(@RequestParam Integer poId) 
	{
		if(PurchaseOrderStatus.PICKING.name()
				.equals(service.getCurrentStatusOfPo(poId))
				) 
		{
			service.updatePoStatus(poId, PurchaseOrderStatus.ORDERED.name());
		}
		return "redirect:parts?id="+poId;
	}
	/**
	 * CANCEL ORDER
	 */
	@GetMapping("/cancel")
	public String cancelOrder(@RequestParam Integer id) 
	{

		String status = service.getCurrentStatusOfPo(id);
		if(
				PurchaseOrderStatus.PICKING.name().equals(status) ||
				PurchaseOrderStatus.ORDERED.name().equals(status) ||
				PurchaseOrderStatus.OPEN.name().equals(status)  ||
				!PurchaseOrderStatus.CANCELLED.name().equals(status)  
				) 
		{
			service.updatePoStatus(id, PurchaseOrderStatus.CANCELLED.name());
		}
		return "redirect:all";
	}
	
	
	/**
	 * GENERATE ORDER
	 */
	@GetMapping("/generate")
	public String generateInvoice(@RequestParam Integer id) 
	{
		service.updatePoStatus(id, PurchaseOrderStatus.INVOICED.name());
		return "redirect:all";
	}
	
	/***
	 * PDF Export
	 */
	@GetMapping("/print")
	public ModelAndView showVendorInvoice(
			@RequestParam Integer id)
	{
		ModelAndView m = new ModelAndView();
		m.setView(new VendorInvoicePdfView());
		
		List<PurchaseDtl> list = service.getPurchaseDtlsByPoId(id);
		m.addObject("list", list);
		
		PurchaseOrder po =  service.getOnePurchaseOrder(id);
		m.addObject("po", po);
		return m;
	}
}

package ru.demo.wms.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.demo.wms.consts.GrnDtlStatus;
import ru.demo.wms.consts.PurchaseOrderStatus;
import ru.demo.wms.model.Grn;
import ru.demo.wms.model.GrnDtl;
import ru.demo.wms.model.PurchaseDtl;
import ru.demo.wms.service.IGrnService;
import ru.demo.wms.service.IPurchaseOrderService;

/**
 * <h3>Контроллер для управления приходными ордерами (GRN)</h3>
 * <p>
 * Этот контроллер отвечает за обработку операций, связанных с GRN в системе складского учёта.
 * </p>
 *
 * <h4>Функциональность:</h4>
 * <ul>
 *   <li>Отображение формы регистрации GRN</li>
 *   <li>Сохранение GRN на основе заказа на закупку</li>
 *   <li>Отображение списка всех GRN</li>
 *   <li>Просмотр деталей GRN</li>
 *   <li>Обновление статуса позиции GRN (Принято / Отклонено)</li>
 * </ul>
 *
 * <h4>Рекомендации по улучшению:</h4>
 * <ul>
 *   <li>Заменить <code>e.printStackTrace()</code> на логирование через логгер</li>
 *   <li>Использовать <code>@ControllerAdvice</code> для централизованной обработки ошибок</li>
 *   <li>Проводить валидацию пользовательских данных</li>
 *   <li>Вынести логику преобразования деталей заказа в отдельный сервис</li>
 *   <li>Убедиться в корректной настройке каскадного сохранения</li>
 * </ul>
 */
@Controller
@RequestMapping("/grn")
public class GrnController {

	@Autowired
	private IGrnService service;

	@Autowired
	private IPurchaseOrderService orderService;

	// Подготовить список заказов со статусом INVOICED
	private void commonUi(Model model) {
		model.addAttribute("pos",
				orderService.getPoIdAndCodesByStatus(PurchaseOrderStatus.INVOICED.name())
		);
	}

	// 1. Отобразить страницу регистрации GRN
	@GetMapping("/register")
	public String showReg(Model model) {
		commonUi(model);
		return "GrnRegister";
	}

	// 2. Сохранить GRN и обновить статус заказа
	@PostMapping("/save")
	public String saveGrn(@ModelAttribute Grn grn, Model model) {
		try {
			// Сформировать детали GRN из деталей заказа
			createGrnDtlsByPo(grn);

			// Сохранить GRN (включая GrnDtl через каскад)
			Integer id = service.saveGrn(grn);

			// Обновить статус заказа, если GRN успешно сохранён
			if (id != null)
				orderService.updatePoStatus(
						((Grn) grn.getPo()).getId(),
						PurchaseOrderStatus.RECEIVED.name()
				);

			// Сообщение об успехе
			model.addAttribute("message", "GRN '" + id + "' СОЗДАН");
		} catch (Exception e) {
			e.printStackTrace(); // TODO: заменить на логгер
		}

		commonUi(model);
		return "GrnRegister";
	}

	// Метод для преобразования деталей заказа в детали GRN
	private void createGrnDtlsByPo(Grn grn) {
		Integer poId = ((Grn) grn.getPo()).getId();
		List<PurchaseDtl> poDtls = orderService.getPurchaseDtlsByPoId(poId);
		Set<GrnDtl> grnSet = new HashSet<>();

		for (PurchaseDtl pdtl : poDtls) {
			GrnDtl grnDtl = new GrnDtl();
			grnDtl.setPartCode(pdtl.getPart().getPartCode());
			grnDtl.setBaseCost(pdtl.getPart().getPartBaseCost());
			grnDtl.setQty(pdtl.getQty());
			grnSet.add(grnDtl);
		}

		grn.setDtls(grnSet);
	}

	// 3. Отобразить список всех GRN
	@GetMapping("/all")
	public String showAll(Model model) {
		List<Grn> grns = service.fetchAllGrns();
		model.addAttribute("list", grns);
		return "GrnData";
	}

	// 4. Отобразить детали GRN по ID
	@GetMapping("/parts")
	public String showGrnDtlsByGrnId(@RequestParam Integer id, Model model) {
		Grn grn = service.getOneGrn(id);
		model.addAttribute("grn", grn);
		model.addAttribute("list", grn.getDtls());
		return "GrnParts";
	}

	// 5. Обновить статус детали GRN на "Принято"
	@GetMapping("/accept")
	public String updateAccepted(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateGrnDtlStatus(dtlId, GrnDtlStatus.ACCEPTED.name());
		return "redirect:parts?id=" + id;
	}

	// 6. Обновить статус детали GRN на "Отклонено"
	@GetMapping("/reject")
	public String updateRejected(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateGrnDtlStatus(dtlId, GrnDtlStatus.REJECTED.name());
		return "redirect:parts?id=" + id;
	}
}

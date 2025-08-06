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
 * Контроллер для управления приходными ордерами (GRN — Goods Receipt Note).
 * <p>
 * Отвечает за создание, отображение и детализацию GRN, а также за обновление статусов строк.
 */
@Controller
@RequestMapping("/grn")
public class GrnController {

	@Autowired
	private IGrnService service;

	@Autowired
	private IPurchaseOrderService orderService;

	/**
	 * Подготавливает список заказов со статусом INVOICED для отображения на форме GRN.
	 *
	 * @param model модель данных
	 */
	private void commonUi(Model model) {
		model.addAttribute("pos",
				orderService.getPoIdAndCodesByStatus(PurchaseOrderStatus.INVOICED.name())
		);
	}

	/**
	 * Отображает форму для регистрации нового GRN.
	 *
	 * @param model модель данных
	 * @return имя шаблона для регистрации GRN
	 */
	@GetMapping("/register")
	public String showReg(Model model) {
		commonUi(model);
		return "GrnRegister";
	}

	/**
	 * Сохраняет новый GRN и обновляет статус соответствующего заказа на закупку.
	 *
	 * @param grn объект Goods Receipt Note, заполненный пользователем на веб-форме (выбор заказа на закупку).
	 * Этот объект дополняется деталями и сохраняется в систему.
	 * @param model модель данных
	 * @return редирект на страницу регистрации GRN
	 */
	@PostMapping("/save")
	public String saveGrn(@ModelAttribute Grn grn, Model model) {
		try {
			createGrnDtlsByPo(grn); // создать строки GRN на основе заказа
			Integer id = service.saveGrn(grn);

			if (id != null)
				orderService.updatePoStatus(
						grn.getPo().getId(),
						PurchaseOrderStatus.RECEIVED.name()
				);

			model.addAttribute("message", "GRN '" + id + "' СОЗДАН");
		} catch (Exception e) {
			e.printStackTrace(); // TODO: заменить на логгер
		}

		commonUi(model);
		return "GrnRegister";
	}

	/**
	 * Преобразует строки заказа в строки GRN.
	 *
	 * @param grn приходной ордер, для которого создаются детали
	 */
	private void createGrnDtlsByPo(Grn grn) {
		Integer poId = grn.getPo().getId();
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

	/**
	 * Отображает список всех GRN.
	 *
	 * @param model модель данных
	 * @return имя шаблона с таблицей GRN
	 */
	@GetMapping("/all")
	public String showAll(Model model) {
		List<Grn> grns = service.fetchAllGrns();
		model.addAttribute("list", grns);
		return "GrnData";
	}

	/**
	 * Отображает детали GRN (строки) по его идентификатору.
	 *
	 * @param id    ID GRN
	 * @param model модель данных
	 * @return имя шаблона с деталями GRN
	 */
	@GetMapping("/parts")
	public String showGrnDtlsByGrnId(@RequestParam Integer id, Model model) {
		Grn grn = service.getOneGrn(id);
		model.addAttribute("grn", grn);
		model.addAttribute("list", grn.getDtls());
		return "GrnParts";
	}

	/**
	 * Обновляет статус строки GRN на {@code ACCEPTED}.
	 *
	 * @param id    ID GRN
	 * @param dtlId ID строки GRN
	 * @return редирект обратно к деталям GRN
	 */
	@GetMapping("/accept")
	public String updateAccepted(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateGrnDtlStatus(dtlId, GrnDtlStatus.ACCEPTED.name());
		return "redirect:parts?id=" + id;
	}

	/**
	 * Обновляет статус строки GRN на {@code REJECTED}.
	 *
	 * @param id    ID GRN
	 * @param dtlId ID строки GRN
	 * @return редирект обратно к деталям GRN
	 */
	@GetMapping("/reject")
	public String updateRejected(@RequestParam Integer id, @RequestParam Integer dtlId) {
		service.updateGrnDtlStatus(dtlId, GrnDtlStatus.REJECTED.name());
		return "redirect:parts?id=" + id;
	}
}

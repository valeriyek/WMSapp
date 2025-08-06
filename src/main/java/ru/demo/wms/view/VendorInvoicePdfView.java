package ru.demo.wms.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import ru.demo.wms.model.PurchaseDtl;
import ru.demo.wms.model.PurchaseOrder;

/**
 * Представление {@code VendorInvoicePdfView} формирует профессиональный PDF-документ — счёт-фактуру
 * поставщика на основе информации о заказе и его деталях.
 * Используется для экспорта финансовых и логистических данных в визуальный отчёт.
 */
public class VendorInvoicePdfView extends AbstractPdfView {

	/**
	 * Устанавливает размер страницы PDF-документа.
	 *
	 * @return документ с форматом LETTER.
	 */
	@Override
	protected Document newDocument() {
		return new Document(PageSize.LETTER);
	}

	/**
	 * Добавляет метаданные к PDF-документу, включая колонтитулы и общую информацию.
	 */
	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		HeaderFooter header = new HeaderFooter(new Phrase("СЧЁТ (PDF)"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);

		HeaderFooter footer = new HeaderFooter(new Phrase(new Date() + "  ||  ДАННЫЕ СЧЁТА  ||  СТР. "), true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);

		document.addTitle("Счёт поставщика");
		document.addAuthor("WMSApp");
		document.addSubject("Отчёт по счёту поставщика");
	}

	/**
	 * Основной метод формирования PDF-документа.
	 */
	@Override
	protected void buildPdfDocument(
			Map<String, Object> model,
			Document document,
			PdfWriter writer,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Получение данных из модели
		@SuppressWarnings("unchecked")
		List<PurchaseDtl> list = (List<PurchaseDtl>) model.get("list");
		PurchaseOrder po = (PurchaseOrder) model.get("po");

		// Расчёт финальной стоимости
		double finalCost = list.stream()
				.mapToDouble(dtl -> dtl.getQty() * dtl.getPart().getPartBaseCost())
				.sum();

		// Установка заголовка загрузки
		response.addHeader("Content-Disposition", "attachment;filename=vendorinvoice.pdf");

		// Логотип
		Image logo = Image.getInstance("https://www.example.ru/static/images/logo.svg");
		logo.scaleAbsolute(250, 60);
		logo.setAlignment(Element.ALIGN_CENTER);
		document.add(logo);

		// Заголовок
		Font titleFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD, Color.RED);
		Paragraph title = new Paragraph("СЧЁТ ПОСТАВЩИКА", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(20f);
		title.setSpacingAfter(25f);
		document.add(title);

		// Таблица с основными сведениями
		Font tableHeadFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.WHITE);

		PdfPTable summaryTable = new PdfPTable(4);
		summaryTable.setSpacingAfter(8f);
		summaryTable.setHorizontalAlignment(Element.ALIGN_CENTER);

		summaryTable.addCell(getCell("КОД ПОСТАВЩИКА", tableHeadFont));
		summaryTable.addCell(po.getVendor().getUserCode());
		summaryTable.addCell(getCell("КОД ЗАКАЗА", tableHeadFont));
		summaryTable.addCell(po.getOrderCode());

		PdfPTable costTable = new PdfPTable(4);
		costTable.setSpacingAfter(8f);
		costTable.addCell(getCell("ИТОГОВАЯ СТОИМОСТЬ", tableHeadFont));
		costTable.addCell(String.format("%.2f", finalCost));
		costTable.addCell(getCell("КОД ОТПРАВКИ", tableHeadFont));
		costTable.addCell(po.getSt().getShipCode());

		document.add(summaryTable);
		document.add(costTable);

		// Таблица с деталями заказа
		PdfPTable detailTable = new PdfPTable(new float[]{2.5f, 1.5f, 1.0f, 2.0f});
		detailTable.setSpacingBefore(20f);
		detailTable.setSpacingAfter(20f);

		detailTable.addCell(getCell("КОД ДЕТАЛИ", tableHeadFont));
		detailTable.addCell(getCell("БАЗОВАЯ ЦЕНА", tableHeadFont));
		detailTable.addCell(getCell("КОЛ-ВО", tableHeadFont));
		detailTable.addCell(getCell("ИТОГ", tableHeadFont));

		for (PurchaseDtl dtl : list) {
			detailTable.addCell(dtl.getPart().getPartCode());
			detailTable.addCell(String.format("%.2f", dtl.getPart().getPartBaseCost()));
			detailTable.addCell(String.valueOf(dtl.getQty()));
			double lineValue = dtl.getQty() * dtl.getPart().getPartBaseCost();
			detailTable.addCell(String.format("%.2f", lineValue));
		}

		document.add(detailTable);

		// Подпись о количестве строк
		Paragraph description = new Paragraph(
				"**** ТЕКУЩИЙ ЗАКАЗ СОДЕРЖИТ " + list.size() + " ПОЗИЦИЙ ****",
				new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC, Color.RED)
		);
		description.setSpacingBefore(10f);
		description.setAlignment(Element.ALIGN_CENTER);
		document.add(description);
	}

	/**
	 * Возвращает ячейку таблицы с заданным фоном и текстом.
	 *
	 * @param text текст в ячейке
	 * @param font шрифт текста
	 * @return объект {@link PdfPCell}
	 */
	private PdfPCell getCell(String text, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setBackgroundColor(Color.BLACK);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
}

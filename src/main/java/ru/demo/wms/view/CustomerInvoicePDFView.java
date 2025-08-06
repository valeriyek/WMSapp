package ru.demo.wms.view;

import java.awt.Color;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import ru.demo.wms.model.SaleOrder;
import ru.demo.wms.model.SaleOrderDetails;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Класс для генерации PDF-документа — инвойса (счета) клиента.
 * Использует библиотеку iText (lowagie) и расширяет AbstractPdfView из Spring MVC.
 * PDF содержит сведения о заказе, деталях заказа, стоимости и оформлен в виде таблиц.
 */
public class CustomerInvoicePDFView extends AbstractPdfView {

	/**
	 * Добавляет метаданные к PDF-документу: заголовок и нижний колонтитул.
	 */
	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		HeaderFooter header = new HeaderFooter(new Phrase("Invoice PDF By Warehouse"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);

		HeaderFooter footer = new HeaderFooter(
				new Phrase(new Date() + " ||      INVOICE DATA    ||     PAGE#"), true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);
	}

	/**
	 * Генерирует основной контент PDF-файла, используя данные заказа и его деталей.
	 *
	 * @param model    модель, содержащая SaleOrder и список SaleOrderDetails
	 * @param document сам PDF-документ
	 * @param writer   PDF-писатель
	 */
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
									HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		List<SaleOrderDetails> list = (List<SaleOrderDetails>) model.get("list");
		SaleOrder saleOrder = (SaleOrder) model.get("saleOrder");

		// Подсчет итоговой стоимости всех позиций
		DoubleSummaryStatistics dss = list.stream()
				.mapToDouble(dtl -> dtl.getQty() * dtl.getPart().getPartBaseCost())
				.summaryStatistics();

		double finalCost = dss.getSum();

		// Установка заголовка HTTP-ответа, чтобы файл скачивался
		response.addHeader("Content-Disposition", "attachment;filename=CustomerInvoice.pdf");

		// Заголовок документа
		Font titleFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD, Color.RED);
		Paragraph title = new Paragraph("CUSTOMER INVOICE", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(20.0f);
		title.setSpacingAfter(25.0f);
		document.add(title);

		// Таблица с базовой информацией о заказе
		Font tableHead = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.WHITE);
		PdfPTable table1 = new PdfPTable(4);
		table1.setSpacingAfter(4.0f);
		table1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table1.addCell(getCellData("CUSTOMER CODE", tableHead));
		table1.addCell(saleOrder.getCustomer().getUserCode());
		table1.addCell(getCellData("ORDER CODE", tableHead));
		table1.addCell(saleOrder.getOrderCode());

		PdfPTable table2 = new PdfPTable(4);
		table2.setSpacingAfter(4.0f);
		table2.addCell(getCellData("FINAL COST", tableHead));
		table2.addCell(String.valueOf(finalCost));
		table2.addCell(getCellData("SHIPMENT TYPE", tableHead));
		table2.addCell(saleOrder.getSt().getShipCode());

		document.add(table1);
		document.add(table2);

		// Таблица с деталями заказа
		PdfPTable child = new PdfPTable(new float[] { 2.5f, 1.5f, 1.0f, 2.0f });
		child.setSpacingBefore(20.0f);
		child.setSpacingAfter(20.0f);
		child.addCell(getCellData("CODE", tableHead));
		child.addCell(getCellData("BASE COST", tableHead));
		child.addCell(getCellData("QTY", tableHead));
		child.addCell(getCellData("ITEM VALUE", tableHead));

		for (SaleOrderDetails dtl : list) {
			child.addCell(dtl.getPart().getPartCode());
			child.addCell(dtl.getPart().getPartBaseCost().toString());
			child.addCell(dtl.getQty().toString());
			child.addCell(String.valueOf(dtl.getPart().getPartBaseCost() * dtl.getQty()));
		}
		document.add(child);

		// Итоговая строка с количеством позиций
		Paragraph description = new Paragraph(
				"**** CURRENT ORDER CONTAINS " + dss.getCount() + " PARTS ****",
				new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC, Color.RED));
		description.setSpacingBefore(10.0f);
		description.setAlignment(Element.ALIGN_CENTER);
		document.add(description);
	}

	/**
	 * Создаёт ячейку с заданным текстом и шрифтом.
	 *
	 * @param input текст в ячейке
	 * @param font  шрифт для текста
	 * @return PdfPCell с заданным стилем
	 */
	private PdfPCell getCellData(String input, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(input, font));
		cell.setBackgroundColor(Color.BLACK);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
}




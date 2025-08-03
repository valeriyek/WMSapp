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

public class CustomerInvoicePDFView extends AbstractPdfView {

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		HeaderFooter header = new HeaderFooter(new Phrase("Invoice PDF By Warehouse"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);

		HeaderFooter footer = new HeaderFooter(new Phrase(new Date() + " ||      INVOICE DATA    ||     PAGE#"), true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {


		@SuppressWarnings("unchecked")
		List<SaleOrderDetails> list = (List<SaleOrderDetails>) model.get("list");
		SaleOrder saleOrder = (SaleOrder) model.get("saleOrder");


		double finalCost = 0.0;
		DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
		for (SaleOrderDetails dtl : list) {
			// finalCost += (dtl.getQty() * dtl.getPart().getPartBaseCost());
			dss.accept(dtl.getQty() * dtl.getPart().getPartBaseCost());
		}
		finalCost = dss.getSum();


		response.addHeader("Content-Disposition", "attachment;filename=CustomerInvoice.pdf");


		Font titleFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD, Color.RED);
		Paragraph title = new Paragraph("CUSTOMER INVOICE", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(25.0f);
		title.setSpacingBefore(20.0f);
		document.add(title);


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


		// PdfPTable child = new PdfPTable(4);
		PdfPTable child = new PdfPTable(new float[] { 2.50f, 1.5f, 1.0f, 2.0f });
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

		/***
		 * Description
		 */
		Paragraph description = new Paragraph("**** CURRENT ORDER CONTAINS " + dss.getCount() + " PARTS ****",
				new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC, Color.RED));
		description.setSpacingBefore(10.0f);
		description.setAlignment(Element.ALIGN_CENTER);
		document.add(description);
	}


	private PdfPCell getCellData(String input, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(input, font));
		cell.setBackgroundColor(Color.BLACK);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}

}
/*

Класс CustomerInvoicePDFView расширяет AbstractPdfView, чтобы предоставить возможность генерации PDF-документов, содержащих данные о заказах клиентов, в вашем веб-приложении на Spring. Этот класс настраивает содержимое PDF с помощью данных модели, переданных из контроллера, и использует библиотеку iText (в данном случае, com.lowagie.text относится к более старой версии iText) для создания структурированного документа.

Основные аспекты и функциональность:
Метаданные PDF: В методе buildPdfMetadata, устанавливаются пользовательские заголовок и нижний колонтитул документа, включающие дату создания документа и метку о том, что это инвойс.

Создание содержимого PDF: Основная логика построения документа находится в методе buildPdfDocument, который использует переданные из модели данные о заказе и его деталях (SaleOrder и SaleOrderDetails) для генерации содержимого инвойса.

Генерация таблицы: Используются объекты PdfPTable и PdfPCell для создания таблиц в документе. Эти таблицы включают в себя информацию о заказе, такую как код клиента, код заказа, итоговую стоимость и тип доставки, а также детальную информацию о каждой позиции заказа, включая код, базовую стоимость, количество и общую стоимость каждой позиции.

Форматирование содержимого: Применяются различные стили шрифтов и цветов, чтобы сделать документ более читабельным и визуально привлекательным.

Сохранение в виде изображения: Используется метод ChartUtils.saveChartAsJPEG для сохранения графиков в документе, что предполагает возможность визуализации некоторых данных через графики (хотя в представленном коде это не демонстрируется).

Рекомендации и замечания:
Безопасность и конфиденциальность: Убедитесь, что в инвойсах не раскрывается чувствительная или конфиденциальная информация без должного разрешения.

Версия iText: В коде используется старая версия библиотеки iText (com.lowagie). Рассмотрите возможность обновления до последней версии iText для улучшения безопасности и расширения функциональности.

Настройка ответа: В методе buildPdfDocument настраивается заголовок ответа для обеспечения скачивания файла (Content-Disposition: attachment). Это гарантирует, что PDF будет скачиваться вместо отображения в браузере.

Этот класс предоставляет гибкий способ генерации PDF-документов для инвойсов, которые можно легко адаптировать под конкретные требования вашего приложения, делая вывод данных более профессиональным и удобным для конечного пользователя.
*/
package ru.demo.wms.view;

import java.awt.Color;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import ru.demo.wms.model.PurchaseDtl;
import ru.demo.wms.model.PurchaseOrder;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class VendorInvoicePdfView extends AbstractPdfView {

	protected Document newDocument() {
		return new Document(PageSize.LETTER);
	}
	
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		HeaderFooter header = new HeaderFooter(new Phrase("INVOICE PDF # BY NIT"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);
		
		HeaderFooter footer = new HeaderFooter(new Phrase(new Date()+" ||      INVOICE DATA    ||     PAGE#"), true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);
		
		document.addTitle("SAMPLE#TITLE");
		document.addAuthor("RAGHU#NIT");
		document.addSubject("SAMPLE # SUBJECT");
		
		
	}

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception {
		

		@SuppressWarnings("unchecked")
		List<PurchaseDtl> list = (List<PurchaseDtl>) model.get("list");
		PurchaseOrder po =  (PurchaseOrder) model.get("po");
		

		double finalCost = 0.0;
		DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
		for(PurchaseDtl dtl:list) {

			dss.accept(dtl.getQty() * dtl.getPart().getPartBaseCost());
		}
		finalCost = dss.getSum();

		Image img = Image.getInstance("https://www.pochta.ru/static/images/logo-light.3c5f75f9dc91.svg");

		img.scaleAbsolute(250, 60);

		img.setAlignment(Element.ALIGN_CENTER);

		document.add(img);
		

		response.addHeader("Content-Disposition", "attachment;filename=vendorinvoice.pdf");
		

		Font titleFont = new Font(Font.TIMES_ROMAN,30,Font.BOLD,Color.RED);
		Paragraph title = new Paragraph("VENDOR INVOICE",titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(25.0f);
		title.setSpacingBefore(20.0f);
		document.add(title);
		
		

		Font tableHead = new Font(Font.TIMES_ROMAN,12,Font.BOLD,Color.WHITE);
		PdfPTable table1 = new PdfPTable(4);
		table1.setSpacingAfter(4.0f);
		table1.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		
		table1.addCell(getCellData("VENDOR CODE", tableHead));
		table1.addCell(po.getVendor().getUserCode());
		table1.addCell(getCellData("ORDER CODE",tableHead));
		table1.addCell(po.getOrderCode());
		
		PdfPTable table2 = new PdfPTable(4);
		table2.setSpacingAfter(4.0f);
		table2.addCell(getCellData("FINAL COST",tableHead));
		table2.addCell(String.valueOf(finalCost));
		table2.addCell(getCellData("SHIPMENT CODE",tableHead));
		table2.addCell(po.getSt().getShipCode());
		
	
		document.add(table1);
		document.add(table2);
		
		


		PdfPTable child = new PdfPTable(new float[] {2.50f,1.5f,1.0f,2.0f});
		child.setSpacingBefore(20.0f);
		child.setSpacingAfter(20.0f);
		child.addCell(getCellData("CODE",tableHead));
		child.addCell(getCellData("BASE COST",tableHead));
		child.addCell(getCellData("QTY",tableHead));
		child.addCell(getCellData("LINE VALUE",tableHead));
		
		for(PurchaseDtl dtl:list) {
			child.addCell(dtl.getPart().getPartCode());
			child.addCell(dtl.getPart().getPartBaseCost().toString());
			child.addCell(dtl.getQty().toString());
			child.addCell(String.valueOf(dtl.getPart().getPartBaseCost()*dtl.getQty()));
			
		}
		document.add(child);
		

		Paragraph description = new Paragraph(
				"**** CURRENT ORDER CONTAINS "+dss.getCount()+" PARTS ****",
				new Font(Font.TIMES_ROMAN, 14,Font.BOLDITALIC,Color.RED)
				);
		description.setSpacingBefore(10.0f);
		description.setAlignment(Element.ALIGN_CENTER);
		document.add(description);
	}

	private PdfPCell getCellData(String input,Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(input,font));
		cell.setBackgroundColor(Color.BLACK);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	

}
/*
Класс VendorInvoicePdfView расширяет AbstractPdfView, предоставляя способ создания детализированного PDF-отчета для инвойса поставщика. В этом отчете отображается информация о заказе на покупку (PurchaseOrder), включая код поставщика, код заказа, окончательную стоимость и код отправления, а также детализированные данные о каждой детали заказа (PurchaseDtl), такие как код детали, базовая стоимость, количество и общая стоимость строки.

Методы и их ключевые функции:
newDocument(): Настраивает размер страницы для PDF-документа, в данном случае используется размер PageSize.LETTER.

buildPdfMetadata: Устанавливает метаданные PDF-документа, включая заголовок и нижний колонтитул, а также метаданные, такие как заголовок документа и автор.

buildPdfDocument: Главный метод, в котором создается содержимое PDF-документа. В нем происходит добавление изображения, настройка и добавление заголовка документа, формирование двух таблиц и добавление итогового описания.

Процесс создания содержимого PDF:
Добавление изображения: На верхней части документа добавляется изображение, которое может служить, например, логотипом компании.

Заголовок документа: С использованием настроенного шрифта и цвета добавляется заголовок "VENDOR INVOICE", выравниваемый по центру страницы.

Таблица с информацией о заказе: Создается таблица с данными заказа, включая код поставщика, код заказа, окончательную стоимость и код отправления.

Таблица с деталями заказа: Формируется детализированная таблица, в которой для каждой позиции заказа указывается код детали, базовая стоимость, количество и общая стоимость строки.

Описание: В конце документа добавляется описание, указывающее на общее количество деталей в заказе.

Рекомендации:
Настройка визуального стиля: В дополнение к базовой структуре документа, можно настроить визуальный стиль элементов PDF, включая шрифты, цвета и границы, чтобы улучшить восприятие информации читателями.

Безопасность: Убедитесь, что в отчете не раскрывается чувствительная информация без надлежащих разрешений.

Поддержка различных форматов страниц: Рассмотрите возможность предоставления пользователю выбора формата страницы (A4, LETTER и т.д.) в зависимости от предпочтений или стандартов документооборота.

VendorInvoicePdfView является мощным инструментом для создания профессиональных PDF-отчетов в приложениях Spring, позволяя бизнесу эффективно документировать и архивировать транзакции с поставщиками.
*/
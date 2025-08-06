package ru.demo.wms.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ru.demo.wms.model.Uom;

/**
 * Представление {@code UomPdfView} отвечает за генерацию PDF-документа,
 * содержащего информацию об единицах измерения (UOM - Unit Of Measurement).
 * <p>
 * Является реализацией {@link AbstractPdfView}, интегрируемой с Spring MVC
 * для экспорта данных в виде отчёта.
 * </p>
 *
 * <p>В отчёте отображаются следующие поля:</p>
 * <ul>
 *   <li>ID</li>
 *   <li>TYPE</li>
 *   <li>MODEL</li>
 *   <li>NOTE</li>
 * </ul>
 */
public class UomPdfView extends AbstractPdfView {

	/**
	 * Устанавливает метаданные PDF-документа: заголовок и нижний колонтитул.
	 *
	 * @param model    модель Spring MVC
	 * @param document документ PDF
	 * @param request  HTTP-запрос
	 */
	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		HeaderFooter header = new HeaderFooter(new Phrase("UOM PDF BY NIT"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);

		HeaderFooter footer = new HeaderFooter(new Phrase("UOM PAGE#"), true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);
	}

	/**
	 * Основной метод генерации содержимого PDF-документа.
	 *
	 * @param model     модель, содержащая список {@link Uom}
	 * @param document  объект документа PDF
	 * @param writer    писатель PDF
	 * @param request   HTTP-запрос
	 * @param response  HTTP-ответ, в который добавляется заголовок Content-Disposition
	 */
	@Override
	protected void buildPdfDocument(
			Map<String, Object> model,
			Document document,
			PdfWriter writer,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Устанавливаем имя файла для скачивания
		response.addHeader("Content-Disposition", "attachment;filename=uoms.pdf");

		// Логотип
		Image logo = Image.getInstance("https://www.pochta.ru/static/images/logo-light.3c5f75f9dc91.svg");
		logo.scaleAbsolute(250, 60);
		logo.setAlignment(Element.ALIGN_CENTER);
		document.add(logo);

		// Заголовок
		Font titleFont = new Font(Font.TIMES_ROMAN, 24, Font.BOLD, Color.RED);
		Paragraph title = new Paragraph("UOMS DATA PDF", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(15.0f);
		title.setSpacingAfter(20.0f);
		document.add(title);

		// Таблица с заголовками
		Font tableHeadFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.MAGENTA);
		PdfPTable table = new PdfPTable(4);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new Phrase("ID", tableHeadFont));
		table.addCell(new Phrase("TYPE", tableHeadFont));
		table.addCell(new Phrase("MODEL", tableHeadFont));
		table.addCell(new Phrase("NOTE", tableHeadFont));

		// Данные
		@SuppressWarnings("unchecked")
		List<Uom> uoms = (List<Uom>) model.get("uoms");
		for (Uom uom : uoms) {
			table.addCell(String.valueOf(uom.getId()));
			table.addCell(uom.getUomType());
			table.addCell(uom.getUomModel());
			table.addCell(uom.getUomDesc());
		}

		document.add(table);
	}
}

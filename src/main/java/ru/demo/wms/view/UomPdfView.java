package ru.demo.wms.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import ru.demo.wms.model.Uom;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class UomPdfView extends AbstractPdfView {
	
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		HeaderFooter header = new HeaderFooter(new Phrase("UOM PDF BY NIT"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);
		
		HeaderFooter footer = new HeaderFooter(new Phrase("UOM PAGE#"), true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);
		
		
	}

	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception 
	{

		Image img = Image.getInstance("https://www.pochta.ru/static/images/logo-light.3c5f75f9dc91.svg");

		img.scaleAbsolute(250, 60);

		img.setAlignment(Element.ALIGN_CENTER);

		document.add(img);
		

		response.addHeader("Content-Disposition", "attachment;filename=uoms.pdf");
		
		Font titleFont = new Font(Font.TIMES_ROMAN,24,Font.BOLD,Color.RED);
		Paragraph title = new Paragraph("UOMS DATA PDF",titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingAfter(20.0f);
		title.setSpacingBefore(15.0f);
		
		
		document.add(title);
		
		Font tableHead = new Font(Font.TIMES_ROMAN,12,Font.BOLD,Color.MAGENTA);
		
		PdfPTable table = new PdfPTable(4);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		table.addCell(new Phrase("ID",tableHead));
		table.addCell(new Phrase("TYPE",tableHead));
		table.addCell(new Phrase("MODEL",tableHead));
		table.addCell(new Phrase("NOTE",tableHead));
		
		@SuppressWarnings("unchecked")
		List<Uom> uoms =  (List<Uom>) model.get("uoms");
		
		for(Uom ob: uoms) {
			table.addCell(String.valueOf(ob.getId()));
			table.addCell(ob.getUomType());
			table.addCell(ob.getUomModel());
			table.addCell(ob.getUomDesc());
		}
		
		document.add(table);
		
	}

}
/*
Класс UomPdfView расширяет AbstractPdfView, предоставляя способ создания PDF-документа из данных о единицах измерения (UOM - Unit of Measurement), полученных из модели Spring MVC. Этот класс используется для генерации отчета о единицах измерения, включая их ID, тип, модель и описание. Давайте рассмотрим ключевые компоненты и методы, используемые в этом классе:

Переопределение методов AbstractPdfView:
buildPdfMetadata:
Устанавливает метаданные для PDF-документа, включая заголовок и нижний колонтитул. Заголовок содержит текст "UOM PDF BY NIT", а нижний колонтитул включает маркер страницы "UOM PAGE#".

buildPdfDocument:
Отвечает за создание содержимого PDF-документа. Этот метод извлекает список объектов Uom из модели, переданной контроллером, и использует их для формирования содержимого документа.

Основные шаги создания содержимого PDF:
Добавление изображения:
Вставляет в документ изображение из внешнего источника, устанавливает размер и выравнивает его по центру.

Настройка заголовка документа:
Добавляет заголовок с настроенным шрифтом и цветом, выравнивает его по центру и устанавливает отступы.

Создание таблицы:
Формирует таблицу для отображения данных о каждой единице измерения. В заголовке таблицы указываются "ID", "TYPE", "MODEL" и "NOTE". Для каждой единицы измерения создается строка в таблице с соответствующими данными.

Настройка ответа:
Устанавливает заголовок ответа Content-Disposition, чтобы браузер предлагал скачать файл (uoms.pdf), вместо его отображения.
Рекомендации и замечания:
Кастомизация документа:
Вы можете дополнительно настроить стиль документа, добавив стили к ячейкам таблицы, изменяя шрифты или добавляя другие элементы, такие как графики или логотипы компании.

Безопасность данных:
Убедитесь, что в генерируемом отчете не раскрывается конфиденциальная информация без соответствующих разрешений.

Производительность и ресурсы:
Генерация сложных PDF-документов может потребовать значительных вычислительных ресурсов, особенно при работе с большими объемами данных. Следует учитывать это при проектировании системы, возможно, ограничив размер выходных данных или оптимизируя их обработку.

UomPdfView предоставляет мощный инструмент для преобразования данных о единицах измерения в профессионально оформленные PDF-отчеты, которые могут быть использованы для анализа, аудита или в качестве официальных документов.
*/
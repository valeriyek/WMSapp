package ru.demo.wms.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import ru.demo.wms.model.WhUserType;

/**
 * Класс {@code WhUserTypeExcelView} предназначен для экспорта данных
 * о типах пользователей склада (WhUserType) в формате Excel (.xlsx).
 * <p>
 * Реализует шаблон Spring MVC View с использованием Apache POI.
 * Позволяет пользователям скачать сформированный Excel-файл
 * с данными из модели, переданной контроллером.
 *
 * <p><b>Содержит следующие столбцы:</b></p>
 * <ul>
 *   <li>ID</li>
 *   <li>USER TYPE</li>
 *   <li>CODE</li>
 *   <li>USER FOR</li>
 *   <li>EMAIL</li>
 *   <li>CONTACT NUMBER</li>
 *   <li>ID TYPE</li>
 *   <li>IF OTHER ID</li>
 *   <li>ID NUMBER</li>
 * </ul>
 *
 * <p>Используется в разделе управления пользователями склада для формирования отчётности или аудита.</p>
 */
public class WhUserTypeExcelView extends AbstractXlsxView {

	/**
	 * Формирует Excel-документ на основе данных модели.
	 *
	 * @param model    модель с данными от контроллера
	 * @param workbook объект книги Excel, предоставленный Spring
	 * @param request  текущий HTTP-запрос
	 * @param response текущий HTTP-ответ
	 * @throws Exception если происходит ошибка генерации Excel
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
									  Workbook workbook,
									  HttpServletRequest request,
									  HttpServletResponse response) throws Exception {

		// Устанавливаем заголовок ответа для скачивания файла
		response.addHeader("Content-Disposition", "attachment;filename=WhUserType.xlsx");

		// Извлекаем список объектов WhUserType из модели
		@SuppressWarnings("unchecked")
		List<WhUserType> list = (List<WhUserType>) model.get("list");

		// Создаём новый лист в книге Excel
		Sheet sheet = workbook.createSheet("WH USER TYPE");

		// Добавляем заголовок таблицы
		addHeader(sheet);

		// Добавляем строки с данными
		addBody(sheet, list);
	}

	/**
	 * Добавляет строку заголовка в лист Excel.
	 *
	 * @param sheet лист Excel, в который добавляется заголовок
	 */
	private void addHeader(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("USER TYPE");
		row.createCell(2).setCellValue("CODE");
		row.createCell(3).setCellValue("USER FOR");
		row.createCell(4).setCellValue("EMAIL");
		row.createCell(5).setCellValue("CONTACT NUMBER");
		row.createCell(6).setCellValue("ID TYPE");
		row.createCell(7).setCellValue("IF OTHER ID");
		row.createCell(8).setCellValue("ID NUMBER");
	}

	/**
	 * Добавляет строки с данными о пользователях склада.
	 *
	 * @param sheet лист Excel, в который добавляются строки
	 * @param list  список объектов WhUserType
	 */
	private void addBody(Sheet sheet, List<WhUserType> list) {
		int rowNum = 1;
		for (WhUserType whut : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(whut.getId());
			row.createCell(1).setCellValue(whut.getUserType());
			row.createCell(2).setCellValue(whut.getUserCode());
			row.createCell(3).setCellValue(whut.getUserFor());
			row.createCell(4).setCellValue(whut.getUserEmail());
			row.createCell(5).setCellValue(whut.getUserContact());
			row.createCell(6).setCellValue(whut.getUserIdType());
			row.createCell(7).setCellValue(whut.getIfOther());
			row.createCell(8).setCellValue(whut.getUserIdNum());
		}
	}
}

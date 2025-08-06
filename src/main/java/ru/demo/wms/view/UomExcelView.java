package ru.demo.wms.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import ru.demo.wms.model.Uom;

/**
 * Класс {@code UomExcelView} реализует создание Excel-файла (.xlsx),
 * содержащего информацию об единицах измерения (UOM — Unit Of Measurement).
 * <p>
 * Этот класс используется как Spring MVC View для экспорта списка UOM в виде Excel-отчета.
 * </p>
 *
 * <p>Формат отчета:</p>
 * <ul>
 *   <li>ID</li>
 *   <li>TYPE</li>
 *   <li>MODEL</li>
 *   <li>NOTE</li>
 * </ul>
 */
public class UomExcelView extends AbstractXlsxView {

	/**
	 * Основной метод генерации Excel-документа. Создает заголовок и заполняет таблицу.
	 *
	 * @param model     модель с атрибутами (ожидается список Uom под ключом "list")
	 * @param workbook  рабочая книга Apache POI
	 * @param request   HTTP-запрос
	 * @param response  HTTP-ответ, для добавления заголовка Content-Disposition
	 */
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Установка заголовка, чтобы браузер предложил скачать файл
		response.addHeader("Content-Disposition", "attachment;filename=UOMS.xlsx");

		// Получаем список UOM из модели
		@SuppressWarnings("unchecked")
		List<Uom> list = (List<Uom>) model.get("list");

		// Создание листа Excel
		Sheet sheet = workbook.createSheet("UOMS");

		// Добавление заголовка таблицы
		addHeaderRow(sheet);

		// Заполнение данными
		addDataRows(sheet, list);
	}

	/**
	 * Создает заголовок таблицы (первая строка с названиями колонок).
	 *
	 * @param sheet лист Excel-документа
	 */
	private void addHeaderRow(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("TYPE");
		row.createCell(2).setCellValue("MODEL");
		row.createCell(3).setCellValue("NOTE");
	}

	/**
	 * Добавляет строки с данными по каждому объекту {@link Uom}.
	 *
	 * @param sheet лист Excel-документа
	 * @param list  список UOM для отображения
	 */
	private void addDataRows(Sheet sheet, List<Uom> list) {
		int rowNum = 1;
		for (Uom uom : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(uom.getId());
			row.createCell(1).setCellValue(uom.getUomType());
			row.createCell(2).setCellValue(uom.getUomModel());
			row.createCell(3).setCellValue(uom.getUomDesc());
		}
	}
}

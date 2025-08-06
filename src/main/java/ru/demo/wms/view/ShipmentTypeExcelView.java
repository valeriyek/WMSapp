package ru.demo.wms.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import ru.demo.wms.model.ShipmentType;

/**
 * Excel-представление для экспорта данных о типах отправлений (ShipmentType).
 * Генерирует файл SHIPMENTS.xlsx со структурированной таблицей данных.
 */
public class ShipmentTypeExcelView extends AbstractXlsxView {

	/**
	 * Основной метод, создающий Excel-документ.
	 *
	 * @param model    модель, содержащая список объектов ShipmentType по ключу "list"
	 * @param workbook книга Excel, в которую добавляется лист с данными
	 * @param request  HTTP-запрос (не используется)
	 * @param response HTTP-ответ, в который добавляется заголовок для скачивания файла
	 * @throws Exception при ошибке создания документа
	 */
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {

		// Устанавливаем заголовок ответа для скачивания файла
		response.addHeader("Content-Disposition", "attachment;filename=SHIPMENTS.xlsx");

		// Получение списка объектов ShipmentType из модели
		@SuppressWarnings("unchecked")
		List<ShipmentType> list = (List<ShipmentType>) model.get("list");

		// Создание нового листа в книге Excel
		Sheet sheet = workbook.createSheet("SHIPMENTS");

		// Добавление заголовков таблицы
		addHeader(sheet);

		// Заполнение таблицы строками с данными
		addBody(sheet, list);
	}

	/**
	 * Добавляет заголовок таблицы в первую строку листа.
	 *
	 * @param sheet рабочий лист Excel, куда добавляются заголовки
	 */
	private void addHeader(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("MODE");
		row.createCell(2).setCellValue("CODE");
		row.createCell(3).setCellValue("ENABLED");
		row.createCell(4).setCellValue("GRADE");
		row.createCell(5).setCellValue("NOTE");
	}

	/**
	 * Заполняет тело таблицы значениями из списка объектов ShipmentType.
	 *
	 * @param sheet лист Excel для заполнения
	 * @param list  список типов отправлений, подлежащих экспорту
	 */
	private void addBody(Sheet sheet, List<ShipmentType> list) {
		int rowNum = 1; // начинаем со второй строки (первая — заголовок)
		for (ShipmentType st : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(st.getId());
			row.createCell(1).setCellValue(st.getShipMode());
			row.createCell(2).setCellValue(st.getShipCode());
			row.createCell(3).setCellValue(st.getEnbleShip());
			row.createCell(4).setCellValue(st.getShipGrade());
			row.createCell(5).setCellValue(st.getShipDesc());
		}
	}
}

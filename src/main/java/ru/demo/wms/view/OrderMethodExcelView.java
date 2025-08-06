package ru.demo.wms.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import ru.demo.wms.model.OrderMethod;

/**
 * Представление для экспорта данных о методах заказа (OrderMethod) в Excel (.xlsx).
 * Наследует {@link AbstractXlsxView} и используется для генерации отчета по OrderMethod.
 * <p>
 * Данный класс формирует Excel-документ с таблицей, содержащей данные из модели.
 * Форматирует таблицу с заголовками и заполняет строками данных.
 */
public class OrderMethodExcelView extends AbstractXlsxView {

	/**
	 * Основной метод построения Excel-документа.
	 *
	 * @param model    модель, переданная из контроллера (ожидается ключ "list" с List<OrderMethod>)
	 * @param workbook объект книги Excel, в который будет записан отчет
	 * @param request  HTTP-запрос (не используется)
	 * @param response HTTP-ответ (заголовок Content-Disposition)
	 * @throws Exception если произойдет ошибка при построении документа
	 */
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model,
			Workbook workbook,
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {

		// Настройка заголовка для скачивания
		response.addHeader("Content-Disposition", "attachment;filename=OM.xlsx");

		// Получение списка объектов OrderMethod из модели
		@SuppressWarnings("unchecked")
		List<OrderMethod> list = (List<OrderMethod>) model.get("list");

		// Создание листа Excel
		Sheet sheet = workbook.createSheet("ORDERMETHODS");

		// Заполнение заголовка и данных
		addHeader(sheet);
		addBody(sheet, list);
	}

	/**
	 * Добавляет заголовок таблицы (первая строка) в лист Excel.
	 *
	 * @param sheet лист Excel, в который будет добавлен заголовок
	 */
	private void addHeader(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("MODE");
		row.createCell(2).setCellValue("CODE");
		row.createCell(3).setCellValue("TYPE");
		row.createCell(4).setCellValue("ACCEPT");
		row.createCell(5).setCellValue("NOTE");
	}

	/**
	 * Добавляет строки данных в таблицу Excel, начиная со второй строки (индекс 1).
	 *
	 * @param sheet лист Excel, в который будут записаны данные
	 * @param list  список объектов OrderMethod для отображения
	 */
	private void addBody(Sheet sheet, List<OrderMethod> list) {
		int rowNum = 1; // строка после заголовка
		for (OrderMethod om : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(om.getId());
			row.createCell(1).setCellValue(om.getOrderMode());
			row.createCell(2).setCellValue(om.getOrderCode());
			row.createCell(3).setCellValue(om.getOrderType());
			row.createCell(4).setCellValue(om.getOrderAcpt().toString()); // преобразуем список в строку
			row.createCell(5).setCellValue(om.getOrderDesc());
		}
	}
}

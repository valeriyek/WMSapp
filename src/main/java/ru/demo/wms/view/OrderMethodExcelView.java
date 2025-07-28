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

public class OrderMethodExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception
	{
		
		response.addHeader("Content-Disposition", "attachment;filename=OM.xlsx");
		

		@SuppressWarnings("unchecked")
		List<OrderMethod> list  = (List<OrderMethod>)model.get("list");
		

		Sheet sheet = workbook.createSheet("ORDERMETHODS");
		

		addHeader(sheet);
		addBody(sheet,list);
		
		
	}
	
	private void addHeader(Sheet sheet) {

		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("MODE");
		row.createCell(2).setCellValue("CODE");
		row.createCell(3).setCellValue("TYPE");
		row.createCell(4).setCellValue("ACCEPT");
		row.createCell(5).setCellValue("NOTE");
	}

	private void addBody(Sheet sheet, List<OrderMethod> list) {
		int rowNum = 1;
		for(OrderMethod om : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(om.getId());
			row.createCell(1).setCellValue(om.getOrderMode());
			row.createCell(2).setCellValue(om.getOrderCode());
			row.createCell(3).setCellValue(om.getOrderType());
			row.createCell(4).setCellValue(om.getOrderAcpt().toString());
			row.createCell(5).setCellValue(om.getOrderDesc());
		}
	}

}
/*
Класс OrderMethodExcelView расширяет AbstractXlsxView, предоставляя функциональность для генерации Excel-документа (*.xlsx) с данными о методах заказа (OrderMethod). Этот вид создает структурированный отчет, который можно скачать и использовать для анализа, отчетности или дальнейшей обработки. Вот как работает этот класс:

Переопределенный метод buildExcelDocument:
Подготовка данных: Метод получает данные из модели, переданной контроллером. Ожидается, что модель содержит список (List<OrderMethod>) объектов OrderMethod, который используется для заполнения Excel-документа.

Настройка ответа: Устанавливает заголовок ответа Content-Disposition с именем файла, чтобы обеспечить скачивание файла вместо отображения его в браузере.

Создание листа: В рабочей книге (Workbook) создается новый лист с именем "ORDERMETHODS".

Методы addHeader и addBody:
addHeader(Sheet sheet): Создает заголовок таблицы в Excel-листе. Определяет названия колонок, которые будут использоваться в документе.

addBody(Sheet sheet, List<OrderMethod> list): Заполняет тело таблицы данными. Для каждого объекта OrderMethod в списке создает новую строку (Row) в листе и заполняет ячейки соответствующими значениями из объекта. Этот метод также демонстрирует, как можно обрабатывать коллекции внутри объектов при их конвертации в строки для Excel (например, преобразование списка принимаемых методов заказа orderAcpt в строку).

Примечания и рекомендации:
Дальнейшее форматирование: Класс может быть расширен с помощью библиотеки Apache POI для добавления стилей ячеек, форматирования данных и других элементов визуализации для улучшения читабельности и профессионального вида создаваемого документа.

Безопасность: Важно убедиться, что генерируемые отчеты не содержат чувствительной информации, которая не должна быть раскрыта пользователям или третьим лицам без соответствующих разрешений.

Производительность: При работе с большим объемом данных следует учитывать возможные вопросы производительности и потребления памяти при генерации Excel-документов, особенно если в отчете предполагается содержание тысяч строк.

Класс OrderMethodExcelView является полезным инструментом для экспорта данных в стандартный и широко используемый формат, предоставляя пользователям возможность более глубокого анализа и обработки информации о методах заказа вне приложения.
*/
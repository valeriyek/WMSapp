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

public class UomExcelView extends AbstractXlsxView {

	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception 
	{

		response.addHeader("Content-Disposition", "attachment;filename=UOMS.xlsx");


		@SuppressWarnings("unchecked")
		List<Uom> list = (List<Uom>) model.get("list");


		Sheet sheet = workbook.createSheet("UOMS");


		addHead(sheet);

		addBody(sheet,list);
	}


	private void addHead(Sheet sheet) {
		Row row  = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("TYPE");
		row.createCell(2).setCellValue("MODEL");
		row.createCell(3).setCellValue("NOTE");
	}

	private void addBody(Sheet sheet, List<Uom> list) {
		int rowNum = 1;
		for(Uom uom : list) {
			Row row  = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(uom.getId());
			row.createCell(1).setCellValue(uom.getUomType());
			row.createCell(2).setCellValue(uom.getUomModel());
			row.createCell(3).setCellValue(uom.getUomDesc());
		}
	}
}
/*
Класс UomExcelView расширяет AbstractXlsxView, что позволяет ему служить специализированным компонентом в Spring MVC для генерации Excel-документов из данных модели, переданных в него. В данном случае, он используется для создания отчета по единицам измерения (Uom), который включает информацию об ID, типе, модели и примечании для каждой единицы измерения. Давайте рассмотрим ключевые аспекты этого класса:

Метод buildExcelDocument:
Настройка ответа: Устанавливает заголовок ответа Content-Disposition, предписывая браузеру скачать файл Excel (UOMS.xlsx), а не отображать его содержимое в окне браузера.

Чтение данных: Извлекает список единиц измерения (List<Uom>) из модели, переданной контроллером.

Создание листа: Формирует новый лист в рабочей книге Excel с названием "UOMS".

Генерация заголовков и тела документа: Вызывает вспомогательные методы addHead и addBody для добавления заголовочной строки и заполнения данных о единицах измерения.

Вспомогательные методы addHead и addBody:
addHead(Sheet sheet): Создает первую строку (заголовок) на листе Excel, указывая названия столбцов.

addBody(Sheet sheet, List<Uom> list): Проходит по списку единиц измерения, добавляя для каждой единицы строку в Excel-документ с соответствующими значениями ID, типа, модели и примечания.

Применение:
Этот вид может использоваться для экспорта данных о единицах измерения в Excel, облегчая анализ данных, внешний аудит или предоставление информации сторонним организациям в удобном и широко распространенном формате.

Рекомендации:
Расширенное форматирование: Apache POI предоставляет обширные возможности для стилизации документов Excel, включая установку шрифтов, цветов ячеек и границ. Это может улучшить восприятие отчета пользователями.

Защита данных: При работе с чувствительной информацией важно обеспечить ее защиту, возможно, добавив механизмы шифрования файла или ограничения на его редактирование.

Оптимизация производительности: При генерации больших документов следует учитывать потребление ресурсов и время обработки. Возможно, потребуются оптимизации или предварительная обработка данных.

UomExcelView демонстрирует мощь Spring MVC и Apache POI в создании расширенных пользовательских отчетов Excel, позволяя эффективно использовать данные приложения в различных сценариях.
*/
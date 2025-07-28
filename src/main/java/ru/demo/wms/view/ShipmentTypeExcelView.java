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

public class ShipmentTypeExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception
	{
		
		response.addHeader("Content-Disposition", "attachment;filename=SHIPMENTS.xlsx");
		

		@SuppressWarnings("unchecked")
		List<ShipmentType> list  = (List<ShipmentType>)model.get("list");
		

		Sheet sheet = workbook.createSheet("SHIPMENTS");
		

		addHeader(sheet);
		addBody(sheet,list);
		
		
	}
	
	private void addHeader(Sheet sheet) {

		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("MODE");
		row.createCell(2).setCellValue("CODE");
		row.createCell(3).setCellValue("ENABLED");
		row.createCell(4).setCellValue("GRADE");
		row.createCell(5).setCellValue("NOTE");
	}

	private void addBody(Sheet sheet, List<ShipmentType> list) {
		int rowNum = 1;
		for(ShipmentType st : list) {
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

/*
Класс ShipmentTypeExcelView наследует AbstractXlsxView, предоставляемый Spring для создания Excel-документов. Этот класс предназначен для генерации Excel-файла со списком типов отправлений (ShipmentType), которые предоставляются в виде модели. Вот шаги, которые класс выполняет для создания документа:

1. Настройка ответа HTTP:
java
Copy code
response.addHeader("Content-Disposition", "attachment;filename=SHIPMENTS.xlsx");
Эта строка указывает, что сгенерированный файл должен быть предложен для скачивания с именем SHIPMENTS.xlsx.

2. Чтение данных из модели:
java
Copy code
List<ShipmentType> list = (List<ShipmentType>)model.get("list");
Данные для документа берутся из модели, переданной контроллером. Предполагается, что контроллер добавляет список объектов ShipmentType в модель.

3. Создание листа в книге Excel:
java
Copy code
Sheet sheet = workbook.createSheet("SHIPMENTS");
Создается новый лист в рабочей книге Excel с именем SHIPMENTS.

4. Добавление заголовков:
java
Copy code
private void addHeader(Sheet sheet) {...}
Этот метод создает первую строку листа с заголовками колонок. Заголовки включают ID, MODE, CODE, ENABLED, GRADE, и NOTE.

5. Заполнение данных:
java
Copy code
private void addBody(Sheet sheet, List<ShipmentType> list) {...}
Метод проходит по списку ShipmentType, переданному в модели, и для каждого элемента создает новую строку в листе Excel, заполняя ячейки значениями свойств объекта ShipmentType.

Этот подход позволяет автоматически создавать структурированные документы Excel на основе данных приложения, что может быть очень полезно для отчетности, экспорта данных и их последующего анализа.
*/

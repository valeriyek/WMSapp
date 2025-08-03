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

public class WhUserTypeExcelView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response)
					throws Exception {

		response.addHeader("Content-Disposition", "attachment;filename=WhUserType.xlsx");


		@SuppressWarnings("unchecked")
		List<WhUserType> list  = (List<WhUserType>)model.get("list");


		Sheet sheet = workbook.createSheet("WH USER TYPE");


		addHeader(sheet);
		addBody(sheet,list);

	}

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

	private void addBody(Sheet sheet, List<WhUserType> list) {
		int rowNum = 1;
		for(WhUserType whut : list) {
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

/*
Класс WhUserTypeExcelView расширяет AbstractXlsxView, предоставляя способ создания Excel-документов для отображения данных о типах пользователей склада (WhUserType). Это может включать различные атрибуты, такие как ID пользователя, тип, код, предназначение (например, покупатель, продавец и т. д.), электронная почта, контактный номер, тип идентификации, другие типы идентификации и номер идентификации. Вот как работает этот класс:

Основные шаги для создания Excel-документа:
Настройка HTTP-ответа:
Устанавливает заголовок Content-Disposition для ответа, чтобы указать браузеру скачать файл как WhUserType.xlsx, вместо отображения его содержимого.

Чтение данных из модели:
Извлекает список объектов WhUserType, переданных контроллером через модель, для последующего использования при заполнении Excel-листа.

Создание нового листа:
Создает лист в рабочей книге Excel с названием "WH USER TYPE".

Добавление заголовков столбцов:
Создает первую строку листа с заголовками столбцов, соответствующими атрибутам объектов WhUserType.

Заполнение листа данными:
Для каждого объекта WhUserType из списка создает новую строку в Excel-листе и заполняет ее ячейки значениями атрибутов этого объекта.

Вспомогательные методы:
addHeader(Sheet sheet):
Определяет и добавляет заголовки столбцов на лист.

addBody(Sheet sheet, List<WhUserType> list):
Проходит по списку WhUserType и для каждого элемента списка создает новую строку в листе, заполняя ее данными.

Применение и польза:
Класс WhUserTypeExcelView может быть использован в приложениях для управления складом или цепочками поставок, где необходимо предоставить пользователям экспортируемые отчеты о типах пользователей, зарегистрированных в системе. Это может быть полезно для аналитических целей, для аудита, для обеспечения соответствия или для внутренней документации.

Рекомендации:
Настройка стилей:
Apache POI предоставляет возможности для настройки стилей ячеек, что может быть использовано для улучшения визуального восприятия сгенерированных отчетов (например, использование разных цветов или шрифтов для заголовков).

Безопасность данных:
При экспорте данных важно убедиться, что конфиденциальная информация защищена и доступна только для авторизованных пользователей.

WhUserTypeExcelView предоставляет эффективный способ для визуализации и распространения данных о пользователях склада в формате, который легко можно анализировать и делиться с другими.
*/
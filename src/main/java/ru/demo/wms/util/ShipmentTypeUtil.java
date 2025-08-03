package ru.demo.wms.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class ShipmentTypeUtil {

	public void generatePieChart(String path,List<Object[]> data) {

		DefaultPieDataset dataset = new  DefaultPieDataset();
		for(Object[] ob:data) {
			//key(String)-val(Double)
			dataset.setValue(
					ob[0].toString(), 
					Double.valueOf(ob[1].toString())
					);
		}


		//Input => title, dataset
		JFreeChart chart = ChartFactory.createPieChart("SHIPMENT TYPE MODE", dataset);


		try {
			ChartUtils.saveChartAsJPEG(
					new File(path+"/shipmentModeA.jpg"),  //file location + name
					chart,  //JFreeChart object
					300,  //width
					300); //height
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public void generateBarChart(String path,List<Object[]> data) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Object[] ob:data) {

			dataset.setValue(
					Double.valueOf(ob[1].toString()),
					ob[0].toString(),
					""//display label
					);
		}


		//Input => title, x-axis label, y-axis-label, dataset
		JFreeChart chart = ChartFactory.createBarChart("SHIPMENT TYPE MODE", "MODES", "COUNTS", dataset);


		try {
			ChartUtils.saveChartAsJPEG(
					new File(path+"/shipmentModeB.jpg"),  //file location + name
					chart,  //JFreeChart object
					450,  //width
					400); //height
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
/*
Класс ShipmentTypeUtil предлагает методы для генерации графических отчетов, в частности, круговых и столбчатых диаграмм, используя данные о типах отправлений. Это позволяет визуализировать распределение отправлений по их типам, что может быть полезно для аналитики и отчетности. Давайте разберем методы этого класса более подробно:

Методы ShipmentTypeUtil:
generatePieChart(String path, List<Object[]> data):
Генерирует круговую диаграмму на основе предоставленных данных. Данные (List<Object[]>) содержат пары ключ-значение, где ключ — это тип отправления (например, "Воздушный", "Морской"), а значение — количество отправлений данного типа. Диаграмма сохраняется в виде JPEG-файла по указанному пути.

generateBarChart(String path, List<Object[]> data):
Создает столбчатую диаграмму, используя аналогичный набор данных. В этом случае столбцы представляют количество отправлений для каждого типа, позволяя легко сравнивать объемы между различными типами. Результат также сохраняется в виде JPEG-файла.

Ключевые аспекты:
Подготовка набора данных (Dataset):
Для каждого типа диаграммы создается соответствующий набор данных (DefaultPieDataset для круговой и DefaultCategoryDataset для столбчатой диаграммы) на основе входных данных.

Создание объекта диаграммы (JFreeChart):
С использованием ChartFactory и подготовленного набора данных генерируется объект диаграммы, который настраивается для отображения соответствующих заголовков и легенды.

Сохранение диаграммы в файл:
С помощью ChartUtils.saveChartAsJPEG готовая диаграмма сохраняется в файл на диске по заданному пути. Размеры изображения задаются явно для каждого типа диаграммы.

Применение:
Эти методы могут использоваться для генерации отчетов в приложениях, связанных с управлением логистикой, складом или доставкой товаров, где требуется анализ распределения отправлений по различным типам.

Рекомендации по использованию:
Убедитесь, что указанный путь для сохранения файлов существует и доступен для записи.
При возникновении исключений во время сохранения изображений убедитесь, что обработка ошибок соответствует требованиям вашего приложения, возможно, добавив логирование или уведомления для администраторов.
При необходимости дополнительно настраивайте внешний вид диаграмм, используя возможности JFreeChart, такие как настройка цветовой схемы, шрифтов и т.д., для соответствия корпоративному стилю или улучшения читаемости.
Класс ShipmentTypeUtil эффективно демонстрирует, как можно интегрировать визуализацию данных в Java-приложения, улучшая понимание аналитической информации за счет графического представления.
*/
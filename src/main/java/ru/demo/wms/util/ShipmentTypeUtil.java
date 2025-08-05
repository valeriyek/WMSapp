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

/**
 * Утилитный класс для генерации графиков по типам отгрузки (Shipment Type).
 * Используется для визуализации аналитических данных с помощью библиотеки JFreeChart.
 * Поддерживает генерацию круговых (Pie) и столбчатых (Bar) диаграмм.
 */
@Component
public class ShipmentTypeUtil {

	/**
	 * Генерация круговой диаграммы на основе данных о типах отгрузки.
	 * Создает диаграмму и сохраняет её в формате JPEG по указанному пути.
	 *
	 * @param path путь к директории, где будет сохранено изображение
	 * @param data список массивов, где:
	 *             - data[i][0] — название типа отгрузки (String)
	 *             - data[i][1] — количество (числовое значение, String или Number)
	 */
	public void generatePieChart(String path, List<Object[]> data) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Object[] ob : data) {
			dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}

		JFreeChart chart = ChartFactory.createPieChart("SHIPMENT TYPE MODE", dataset);

		try {
			ChartUtils.saveChartAsJPEG(
					new File(path + "/shipmentModeA.jpg"),
					chart,
					300,
					300
			);
		} catch (IOException e) {
			e.printStackTrace(); // Логгер нужен
		}
	}

	/**
	 * Генерация столбчатой диаграммы на основе данных о типах отгрузки.
	 * Создает диаграмму и сохраняет её в формате JPEG по указанному пути.
	 *
	 * @param path путь к директории, где будет сохранено изображение
	 * @param data список массивов, где:
	 *             - data[i][0] — название типа отгрузки (String)
	 *             - data[i][1] — количество (числовое значение, String или Number)
	 */
	public void generateBarChart(String path, List<Object[]> data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Object[] ob : data) {
			dataset.setValue(
					Double.valueOf(ob[1].toString()),
					ob[0].toString(), // категория (ось Y)
					"" // метка по оси X (оставлена пустой)
			);
		}

		JFreeChart chart = ChartFactory.createBarChart(
				"SHIPMENT TYPE MODE", // заголовок диаграммы
				"MODES",              // метка оси X
				"COUNTS",             // метка оси Y
				dataset
		);

		try {
			ChartUtils.saveChartAsJPEG(
					new File(path + "/shipmentModeB.jpg"),
					chart,
					450,
					400
			);
		} catch (IOException e) {
			e.printStackTrace(); // Логгер нужен
		}
	}
}

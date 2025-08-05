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
 * Утилитный класс для генерации графиков (диаграмм) по методам заказа.
 * Использует библиотеку JFreeChart для визуализации аналитических данных.
 * Поддерживает генерацию круговых и столбчатых диаграмм.
 */
@Component
public class OrderMethodUtil {

	/**
	 * Генерирует круговую диаграмму (Pie Chart) на основе предоставленного списка данных.
	 * Сохраняет диаграмму в виде изображения JPEG по указанному пути.
	 *
	 * @param path путь к директории, где будет сохранено изображение (без расширения)
	 * @param list список объектных массивов, где:
	 *             - ob[0] — название сектора (String)
	 *             - ob[1] — числовое значение (Number)
	 */
	public void generatePie(String path, List<Object[]> list) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Object[] ob : list) {
			dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}

		JFreeChart chart = ChartFactory.createPieChart("ORDER METHOD MODE", dataset);

		try {
			ChartUtils.saveChartAsJPEG(
					new File(path + "/omA.jpg"),
					chart,
					300, 300
			);
		} catch (IOException e) {
			e.printStackTrace(); // В боевом коде — заменить на логирование
		}
	}

	/**
	 * Генерирует столбчатую диаграмму (Bar Chart) на основе предоставленного списка данных.
	 * Сохраняет диаграмму в виде изображения JPEG по указанному пути.
	 *
	 * @param path путь к директории, где будет сохранено изображение (без расширения)
	 * @param list список объектных массивов, где:
	 *             - ob[0] — категория/метка столбца (String)
	 *             - ob[1] — значение (Number)
	 */
	public void generateBar(String path, List<Object[]> list) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Object[] ob : list) {
			dataset.setValue(
					Double.valueOf(ob[1].toString()),
					ob[0].toString(),
					"" // Пустая категория (по оси X)
			);
		}

		JFreeChart chart = ChartFactory.createBarChart(
				"ORDER METHOD MODE",  // Заголовок диаграммы
				"MODE",               // Ось X
				"COUNT",              // Ось Y
				dataset
		);

		try {
			ChartUtils.saveChartAsJPEG(
					new File(path + "/omB.jpg"),
					chart,
					300, 300
			);
		} catch (IOException e) {
			e.printStackTrace(); // В боевом коде — заменить на логирование
		}
	}
}

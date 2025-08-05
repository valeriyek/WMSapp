package ru.demo.wms.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Утилитный класс для генерации круговой диаграммы по единицам измерения (UOM).
 * Используется библиотека JFreeChart.
 */
@Component
public class UomUtil {

	private static final Logger log = LoggerFactory.getLogger(UomUtil.class);

	/**
	 * Генерирует круговую диаграмму по типам единиц измерения.
	 * Сохраняет изображение диаграммы в формате JPEG по заданному пути.
	 *
	 * @param path путь к директории, в которой сохранить изображение
	 * @param data список массивов, где:
	 *             - [0] название единицы измерения (String)
	 *             - [1] количество использований (String, Integer или Double)
	 */
	public void generateFreeChart(String path, List<Object[]> data) {
		log.info("Вызов метода generateFreeChart() для построения диаграммы");

		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Object[] ob : data) {
			String label = ob[0].toString();
			Double value = Double.valueOf(ob[1].toString());
			dataset.setValue(label, value);
		}

		JFreeChart chart = ChartFactory.createPieChart("Типы единиц измерения", dataset);

		File file = new File(path + "/uomType.jpg");
		try {
			ChartUtils.saveChartAsJPEG(file, chart, 300, 300);
			log.info("Диаграмма успешно сохранена по пути: {}", file.getAbsolutePath());
		} catch (IOException e) {
			log.error("Ошибка при сохранении диаграммы в generateFreeChart(): {}", e.getMessage(), e);
		}
	}
}

package ru.demo.wms.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Утилитный класс для генерации диаграмм (круговых и столбчатых)
 * по типам пользователей склада (Warehouse User Types).
 * Использует библиотеку JFreeChart.
 */
@Component
public class WhUserTypeUtil {

	private static final Logger log = LoggerFactory.getLogger(WhUserTypeUtil.class);

	/**
	 * Генерирует круговую диаграмму (Pie Chart) по типам пользователей склада.
	 *
	 * @param path путь к директории, где будет сохранено изображение
	 * @param data список массивов Object[], где:
	 *             - ob[0] — название категории (тип пользователя)
	 *             - ob[1] — числовое значение (количество)
	 */
	public void generatePieChart(String path, List<Object[]> data) {
		log.info("Генерация круговой диаграммы типов пользователей склада");

		if (data == null || data.isEmpty()) {
			log.warn("Данные для круговой диаграммы отсутствуют");
			return;
		}

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Object[] ob : data) {
			dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}

		JFreeChart chart = ChartFactory.createPieChart("WH USER TYPE", dataset);

		try {
			ChartUtils.saveChartAsJPEG(
					new File(path + "/UserIDTypeA.jpg"),
					chart,
					300, // ширина
					300  // высота
			);
			log.info("Круговая диаграмма успешно сохранена: {}/UserIDTypeA.jpg", path);
		} catch (IOException e) {
			log.error("Ошибка при сохранении круговой диаграммы", e);
		}
	}

	/**
	 * Генерирует столбчатую диаграмму (Bar Chart) по типам пользователей склада.
	 *
	 * @param path путь к директории, где будет сохранено изображение
	 * @param data список массивов Object[], где:
	 *             - ob[0] — название категории (тип пользователя)
	 *             - ob[1] — числовое значение (количество)
	 */
	public void generateBarChart(String path, List<Object[]> data) {
		log.info("Генерация столбчатой диаграммы типов пользователей склада");

		if (data == null || data.isEmpty()) {
			log.warn("Данные для столбчатой диаграммы отсутствуют");
			return;
		}

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Object[] ob : data) {
			dataset.setValue(
					Double.valueOf(ob[1].toString()),
					ob[0].toString(),
					"" // метка для оси X (может быть пустой)
			);
		}

		JFreeChart chart = ChartFactory.createBarChart(
				"WH USER TYPE",        // Заголовок диаграммы
				"USER ID TYPE",        // Подпись оси X
				"COUNTS",              // Подпись оси Y
				dataset                // Набор данных
		);

		try {
			ChartUtils.saveChartAsJPEG(
					new File(path + "/UserIDTypeB.jpg"),
					chart,
					450, // ширина
					450  // высота
			);
			log.info("Столбчатая диаграмма успешно сохранена: {}/UserIDTypeB.jpg", path);
		} catch (IOException e) {
			log.error("Ошибка при сохранении столбчатой диаграммы", e);
		}
	}
}

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

@Component
public class UomUtil {

	private static final Logger log = LoggerFactory.getLogger(UomUtil.class);

	public void generateFreeChart(String path, List<Object[]> data) {
		log.info("Inside generateFreeChart() :");


		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Object[] ob : data) {
			// key(String)-val(Double)
			dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}

		// Input => title, dataset
		JFreeChart chart = ChartFactory.createPieChart("UomType", dataset);


		try {
			ChartUtils.saveChartAsJPEG(new File(path + "/uomType.jpg"), // file location + name
					chart, // JFreeChart object
					300, // width
					300); // height
		} catch (IOException e) {
			log.error("Exception Inside generateFreeChart() :" + e);
			e.printStackTrace();
		}

	}

}
/*
Класс UomUtil предоставляет функциональность для создания визуальных отчетов, в частности, круговых диаграмм, с использованием библиотеки JFreeChart на основе данных о типах единиц измерения (UoM - Units of Measurement). Этот класс демонстрирует, как можно преобразовать статистические данные в наглядные графические отчеты, что особенно полезно для аналитических и отчетных целей.

Процесс создания диаграммы:
Подготовка источника данных:

Данные (List<Object[]>) содержат пары ключ-значение, где ключ — это наименование типа единицы измерения, а значение — количество использований этой единицы измерения.
Эти данные используются для создания DefaultPieDataset, который служит источником данных для диаграммы.
Создание объекта диаграммы:

С помощью ChartFactory.createPieChart создается объект JFreeChart, представляющий собой круговую диаграмму с указанным заголовком и данными.
Сохранение диаграммы в файл:

Диаграмма сохраняется в виде изображения (JPEG) по указанному пути. Размер изображения фиксирован (300x300 пикселей).
Логгирование:
В классе используется библиотека slf4j для логгирования, что позволяет отслеживать выполнение метода генерации диаграммы и регистрировать возникшие исключения. Это важно для отладки и обеспечения надежности работы приложения.
Пример использования:
Этот класс может быть использован в приложениях для управления складом или логистикой для визуализации распределения типов единиц измерения среди товаров, что помогает в анализе и планировании закупок, а также в оптимизации складских операций.

Рекомендации по использованию:
Проверка пути сохранения: Перед сохранением изображения убедитесь, что указанный путь доступен и имеет необходимые разрешения на запись.
Обработка исключений: В дополнение к логгированию исключений важно предусмотреть их должную обработку, чтобы предотвратить сбой приложения в случае ошибок.
Настройка внешнего вида диаграммы: JFreeChart предлагает широкие возможности для настройки внешнего вида диаграмм, включая цвета, шрифты и легенды. Рассмотрите возможность использования этих настроек для улучшения восприятия и соответствия корпоративному стилю.
Класс UomUtil является примером эффективного применения визуализации данных для улучшения понимания и анализа информации в приложениях.
*/
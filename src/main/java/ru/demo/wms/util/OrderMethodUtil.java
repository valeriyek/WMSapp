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
public class OrderMethodUtil {

	public void generatePie(String path, List<Object[]> list) {

		DefaultPieDataset dataset = new DefaultPieDataset();
		for(Object[] ob : list) {

			dataset.setValue(ob[0].toString(), Double.valueOf(ob[1].toString()));
		}


		JFreeChart chart = ChartFactory.createPieChart("ORDER METHOD MODE", dataset);

		try {
			ChartUtils.saveChartAsJPEG(
					new File(path+"/omA.jpg"), 
					chart, 300, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateBar(String path, List<Object[]> list) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Object[] ob:list) {

			dataset.setValue(
					Double.valueOf(ob[1].toString()),
					ob[0].toString(),
					"");
		}


		JFreeChart chart = ChartFactory.createBarChart("ORDER METHOD MODE", "MODE", "COUNT", dataset);

		try {
			ChartUtils.saveChartAsJPEG(
					new File(path+"/omB.jpg"), 
					chart, 300, 300);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
/*
Класс OrderMethodUtil предоставляет функциональность для генерации графических отчетов (в виде диаграмм) по методам заказа, используя библиотеку JFreeChart. Этот класс включает два метода для создания круговых (generatePie) и столбчатых (generateBar) диаграмм на основе предоставленных данных. Давайте подробнее рассмотрим каждый из методов:

Методы OrderMethodUtil:
generatePie(String path, List<Object[]> list):
Генерирует круговую диаграмму на основе списка данных. Каждый элемент списка list представляет собой массив, содержащий название сектора диаграммы и числовое значение. Диаграмма сохраняется в виде JPEG-файла по указанному пути path.

generateBar(String path, List<Object[]> list):
Создает столбчатую диаграмму, используя предоставленный список данных. Аналогично, каждый элемент списка list содержит название группы и связанное с ней числовое значение, представляющее высоту столбца на диаграмме. Результат также сохраняется в виде JPEG-файла.

Принцип работы:
Создание набора данных (Dataset):
Для обоих методов первым шагом является создание набора данных из предоставленного списка. В generatePie используется DefaultPieDataset для круговой диаграммы, а в generateBar — DefaultCategoryDataset для столбчатой.

Создание диаграммы (JFreeChart):
С помощью ChartFactory создается объект JFreeChart, который настраивается на основе созданного набора данных. Для круговой диаграммы используется метод createPieChart, а для столбчатой — createBarChart.

Сохранение диаграммы в файл:
С использованием ChartUtils.saveChartAsJPEG генерированная диаграмма сохраняется в виде изображения по указанному пути.

Применение:
Эти методы могут быть использованы для визуализации аналитических данных, например, для отображения распределения заказов по различным методам обработки или для подготовки отчетов, которые требуется представить в графическом виде.

Рекомендации по использованию:
Убедитесь, что указанный путь для сохранения файлов доступен и имеет достаточно прав на запись.
Обработайте возможные исключения, связанные с операциями ввода/вывода, чтобы предотвратить сбои приложения при невозможности сохранения изображений.
При необходимости адаптируйте размеры и другие параметры визуализации диаграмм в соответствии с требованиями к отображению или печати.
Класс OrderMethodUtil представляет собой полезный инструмент для добавления визуального измерения к анализу данных в приложениях, связанных с управлением запасами или логистикой.
*/
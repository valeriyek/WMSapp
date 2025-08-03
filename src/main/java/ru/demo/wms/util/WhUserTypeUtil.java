package ru.demo.wms.util;

import java.io.File;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class WhUserTypeUtil {


	public void generatePieChart(String path,List<Object[]> data) {



		DefaultPieDataset dataset=new DefaultPieDataset();
		for(Object[] ob:data) {
			// key(String)-- val(Double)
			dataset.setValue(ob[0].toString(),
					Double.valueOf(ob[1].toString()));
		}


		JFreeChart chart=ChartFactory.createPieChart(" WH USER TYPE", dataset);



		try {
			ChartUtils.saveChartAsJPEG(
					new File(path+"/UserIDTypeA.jpg"),
					chart, // Jfree object
					300,// width
					300); // object
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void generateBarChart(String path,List<Object[]> data) {



		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Object[] ob:data) {
			// key(String)-- val(Double)
			dataset.setValue(
					Double.valueOf(ob[1].toString()),
					ob[0].toString(),
					"" // display lable
					);
		}


		//Input => title, x-axis label, y-axis-label, dataset
		JFreeChart chart=ChartFactory.createBarChart(" WH USER TYPE", "USER ID TYPE", "COUNTS", dataset);

		try {
			ChartUtils.saveChartAsJPEG(
					new File(path+"/UserIDTypeB.jpg"),
					chart, // Jfree object
					450,// width
					450); // object
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*
Класс WhUserTypeUtil предназначен для генерации визуальных отчетов по типам пользователей склада (Warehouse User Types) с использованием библиотеки JFreeChart. Он предоставляет два метода для создания круговых (generatePieChart) и столбчатых (generateBarChart) диаграмм, основанных на предоставленных данных. Эти визуализации могут помочь в анализе распределения пользователей по типам, поддерживая управленческие и аналитические процессы. Вот детальное рассмотрение функциональности каждого метода:

Методы класса WhUserTypeUtil:
generatePieChart(String path, List<Object[]> data):
Создает круговую диаграмму, которая иллюстрирует долю каждого типа пользователя склада в общем количестве. Для каждого элемента списка data, содержащего пары ключ-значение (тип пользователя и их количество), формируется сектор диаграммы. Результат сохраняется в виде JPEG-изображения по указанному пути.

generateBarChart(String path, List<Object[]> data):
Генерирует столбчатую диаграмму, на которой каждый тип пользователя представлен отдельным столбцом, высота которого соответствует количеству пользователей данного типа. Данные для столбцов берутся из списка data. Готовая диаграмма также сохраняется в виде JPEG-файла.

Реализация:
Оба метода начинаются с подготовки источника данных (DefaultPieDataset для круговой диаграммы и DefaultCategoryDataset для столбчатой), куда вносится информация из списка data. Затем, используя ChartFactory, создается объект диаграммы (JFreeChart), который конфигурируется в соответствии с выбранным типом диаграммы и подготовленными данными. Наконец, диаграмма сохраняется как изображение в файл.

Применение:
Класс WhUserTypeUtil может быть использован в приложениях для управления складом или логистикой, где требуется аналитика по типам пользователей для оптимизации рабочих процессов, планирования ресурсов или в целях отчетности.

Рекомендации:
Проверка доступности пути: Перед сохранением изображения убедитесь, что указанный путь доступен и имеет права на запись.
Обработка исключений: В реализации следует предусмотреть корректную обработку возможных ошибок, связанных с созданием и сохранением изображений, включая обработку исключений IOException.
Настройка визуализации: JFreeChart предоставляет множество опций для настройки внешнего вида диаграмм, включая цвета, стили текста и легенды. Рассмотрите возможность дополнительной конфигурации диаграмм для повышения их информативности и привлекательности.
Этот утилитный класс является отличным примером того, как программные средства визуализации данных могут быть интегрированы в Java-приложения, повышая их аналитическую ценность и пользовательскую привлекательность.
*/
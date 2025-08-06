package ru.demo.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс запуска Spring Boot приложения для системы управления складом (WMS).
 *
 * <p>
 * Аннотация {@code @SpringBootApplication} объединяет три ключевые аннотации:
 * <ul>
 *   <li>{@code @Configuration} — указывает, что класс содержит бины конфигурации Spring</li>
 *   <li>{@code @EnableAutoConfiguration} — включает автоконфигурацию на основе зависимостей в classpath</li>
 *   <li>{@code @ComponentScan} — включает компонентное сканирование текущего пакета и вложенных пакетов</li>
 * </ul>
 * </p>
 *
 * <p>
 * Запуск приложения осуществляется через метод {@code main}, который передаёт управление
 * классу {@link SpringApplication}. Это инициализирует контекст Spring, запускает встроенный
 * веб-сервер (например, Tomcat) и поднимает все компоненты приложения.
 * </p>
 *
 * <p><b>Пример запуска:</b></p>
 * <pre>{@code
 * java -jar warehouse-application.jar
 * }</pre>
 *
 */
@SpringBootApplication
public class WarehouseApplication {

	/**
	 * Метод запуска Spring Boot-приложения.
	 *
	 * @param args аргументы командной строки, передаваемые при старте
	 */
	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}
}

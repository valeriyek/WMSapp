package ru.demo.wms;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WarehouseApplication.class);
	}

	
}
/*
Класс ServletInitializer расширяет SpringBootServletInitializer, который предоставляет способ настройки конфигурации приложения во время его запуска в сервлет-контейнере, таком как Tomcat или Jetty. Этот класс особенно важен при развертывании Spring Boot приложения как традиционного WAR-файла, а не как самодостаточного JAR.

Основная функциональность:
configure(SpringApplicationBuilder application): Этот метод переопределяется для указания источника основного класса приложения Spring Boot, который запускает контекст Spring. application.sources(WarehouseApplication.class) указывает Spring Boot, что основным классом для запуска является WarehouseApplication.
Применение:
При разработке Spring Boot приложений по умолчанию используется встроенный сервлет-контейнер (как правило, Tomcat), что позволяет запускать приложение как самодостаточный JAR. Однако, в некоторых сценариях, например, при необходимости использовать специфические настройки сервера или при ограничениях, налагаемых инфраструктурой, может потребоваться развертывание приложения в виде WAR-файла во внешнем сервлет-контейнере. В этих случаях ServletInitializer используется для адаптации конфигурации Spring Boot к требованиям сервлет-контейнера.

Пример:
Если вы хотите развернуть свое приложение в сервлет-контейнере как WAR, вам необходимо:

Поменять способ сборки в вашем pom.xml с JAR на WAR.
Включить ServletInitializer в ваш проект.
Настроить ваш основной класс приложения (WarehouseApplication в данном случае) для использования в качестве источника приложения.
Заключение:
ServletInitializer играет ключевую роль в адаптации Spring Boot приложений к развертыванию во внешних сервлет-контейнерах, обеспечивая гибкость при развертывании и поддерживая более широкий спектр сценариев использования и развертывания приложений на базе Spring Boot.
*/
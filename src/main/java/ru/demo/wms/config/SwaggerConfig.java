package ru.demo.wms.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Конфигурация Swagger для автогенерации документации REST API.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Создаёт бин {@link Docket} — основной интерфейс настройки Swagger.
     * <p>
     * Ограничивает сканирование контроллеров пакетом {@code ru.demo.wms.rest}
     * и путями, начинающимися с {@code /rest}.
     *
     * @return объект Docket для конфигурации Swagger
     */
    @Bean
    public Docket createDocketApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // Ограничить сканирование контроллеров только этим пакетом
                .apis(RequestHandlerSelectors.basePackage("ru.demo.wms.rest"))
                // Документировать только пути, начинающиеся с /rest
                .paths(PathSelectors.regex("/rest.*"))
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * Описание API, отображаемое в Swagger UI.
     *
     * @return объект ApiInfo с метаданными API
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "WAREHOUSE APP",                  // Название API
                " ",                              // Описание
                "W",                              // Версия
                " ",                              // Условия использования
                new Contact("VALERIY", " ", " "), // Контактная информация разработчика
                "  ",                             // Лицензия
                "     ",                          // URL лицензии
                Collections.emptyList()           // Поставщики (пусто)
        );
    }
}

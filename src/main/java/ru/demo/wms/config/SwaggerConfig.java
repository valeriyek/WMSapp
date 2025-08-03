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

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createDocketApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.demo.wms.rest"))
                .paths(PathSelectors.regex("/rest.*"))
                .build()
                .apiInfo(apiInfo())
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(

                "WAREHOUSE APP", //title
                " ", //description
                "W", //version
                " ", //terms of service URL
                new Contact("VALERIY", " ", " "), //developer contact info: new Contact("Valeriy", " ", " "),
                "  ", //license
                "     ", //license URL
                Collections.emptyList() //Vendor names as list


        );
    }
}

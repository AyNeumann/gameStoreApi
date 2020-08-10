package com.aymeric.gamestore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.aymeric.gamestore.controller.DevelopperController;
import com.aymeric.gamestore.controller.EditorController;
import com.aymeric.gamestore.controller.GameController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * URL:
 * json-file: http://localhost:8080/v2/api-docs
 * swagger-ui: http://localhost:8080/swagger-ui/
 */
@EnableSwagger2
@ComponentScan(basePackageClasses = {
        DevelopperController.class,
        EditorController.class,
        GameController.class
})
public class SwaggerUiWebMvcConfigurer {

  @Bean
  public Docket api() { 
      return new Docket(DocumentationType.SWAGGER_2)
        .groupName("gamestore-api")
        .select()                                  
        .apis(RequestHandlerSelectors.any())              
        .paths(PathSelectors.any())                          
        .build();                                           
  }
}

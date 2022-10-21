
package com.rest.blog.config;

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
public Docket api() {


    return new Docket(DocumentationType.SWAGGER_2)
.apiInfo(getInfo())

.select()
.apis(RequestHandlerSelectors.any())
.paths(PathSelectors.any()).build();

}

private ApiInfo getInfo() {

return new ApiInfo("Java Blogging Application Project", "Developed by Archana", "1.0", "Tearms of Service", new Contact("Archana",null , "archanakumari8n7@gmail.com"), "Liscence", "Api license", Collections.emptyList());
}



}

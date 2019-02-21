package com.payconiq.stockDemo;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class StockDemoConfig {
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper obj = new ObjectMapper();	
		return obj;
	}
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.payconiq.stockDemo.controller"))
          .paths(PathSelectors.any())                          
          .build();
    }
	
	@Bean
	public ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Stock Demo API", 
	      "A demo app for Payconiq", 
	      "API TOS", 
	      "Terms of service", 
	      new Contact("Abi SRK", "www.example.com", "abiiyer@gmail.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
}

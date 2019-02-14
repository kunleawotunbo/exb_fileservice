/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 *
 * @author Olakunle Awotunbo
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    /*
    @Bean
    public Docket productApi() {        
       
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.exb.platform.cloud.fileservice.resources"))
                .paths(PathSelectors.ant("/api.*"))
                .build()
                .apiInfo(metaData());
     
    }
    
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("ExB REST API")
                .description("\"File Service REST API for ExB\"")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Olakunle Awotunbo", "https://olakunleawotunbo.com", "kunleawotunbo@gmail.com"))
                .build();
    } 
    */
   
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build().securitySchemes(Lists.newArrayList(apiKey()));
    }

    // Describe your apis
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Merrybet Principal Agent Portal APIs")
                .description("This page lists all the rest apis for Merrybet Principal Agent Portal App.")
                .version("1.0")
                .build();
    }

    // Only select apis that matches the given Predicates.
    private Predicate<String> paths() {
    	// Match all paths except /error
        return Predicates.and(
        	PathSelectors.regex("/.*"), 
        	Predicates.not(PathSelectors.regex("/error.*"))
        );
    }
    
    
    // Added this to show API Key
    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(null, null, null, null, "", ApiKeyVehicle.HEADER, "Authorization", "");
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
    
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
 
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    } 
    
    
}

package ao.it.chandsoft.coronavirus.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Nelson Chandimba da Silva
 */

@Configuration
@EnableSwagger2
public class CoronavirusCofig {
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ao.it.chandsoft.coronavirus.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)                                   
                .globalResponseMessage(RequestMethod.GET, 
                    Arrays.asList(
                        new ResponseMessageBuilder()   
                            .code(500)
                            .message("500 Internal error server. Please, try again later.")
                            .responseModel(new ModelRef("Error"))
                            .build(),
                        new ResponseMessageBuilder() 
                          .code(404)
                          .message("Resource not found")
                          .build()
                    )
                );
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo("Api Conavirus - Covid-19", 
                "Report statistic about Covid-19 around world", 
                "version 1.0", "Termos of service", 
                new Contact("Nelson Chandimba da Silva", "https://www.youtube.com/channel/UCZhCAQ2dEOYltLcau8QdnyA", "dismao16@gmail.com"), 
                "License of API", 
                "",
                Collections.EMPTY_LIST);
    }
}

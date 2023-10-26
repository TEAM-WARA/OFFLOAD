package dev.creative.creative.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()

                // Controller 경로
                .apis(RequestHandlerSelectors.basePackage("dev.creative.creative.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(List.of(this.securityContext())) // SecurityContext 설정
                .securitySchemes(List.of(this.apiKey())); // ApiKey 설정;

    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("API DOCS")
                .description("this page is api docs")
                .version("MK1")
                .build();

    }

    // JWT SecurityContext 구성
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }

    // ApiKey 정의
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }


}

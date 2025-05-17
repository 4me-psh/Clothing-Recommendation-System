package org.example.clothingrecommendationsystem.infrastructure.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clothing Recommendation System API")
                        .version("1.0")
                        .description("API for Clothing Recommendation System")
                        .contact(new Contact()
                                .name("Pavlo Kostenko")));
    }
}

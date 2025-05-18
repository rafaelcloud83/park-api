package edu.rafael.park_api.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - EstacionaPark")
                        .version("v1")
                        .description("API para gerenciar Estacionamento")
                        .license(new License().name("Licensa - Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact().name("Rafael Ciriaco da Silva").url("https://rafaelcloud83.github.io/"))
                );
    }
}

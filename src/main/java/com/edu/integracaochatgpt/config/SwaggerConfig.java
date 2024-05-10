package com.edu.integracaochatgpt.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Serviço de Transcrição de Voz para Texto utilizando Google Cloud")
                        .version("v1")
                        .description("Integração com a API Google Cloud utilizando o serviço de Speech to text")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                .servers(Collections.singletonList(new Server().url("/integracao-Google-cloud")));
    }
}

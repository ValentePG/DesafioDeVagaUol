package dev.valente.desafio_vagauol.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Desafio Vaga Uol", version = "1.0"))
public class OpenApiConfig {
}

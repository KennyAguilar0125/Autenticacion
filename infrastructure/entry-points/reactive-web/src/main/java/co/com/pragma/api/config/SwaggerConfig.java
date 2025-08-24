package co.com.pragma.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "PowerUp 2025 - Autenticación",
        version = "1.0",
        description = "Autenticación y gestión de solicitantes"
))
public class SwaggerConfig {
}

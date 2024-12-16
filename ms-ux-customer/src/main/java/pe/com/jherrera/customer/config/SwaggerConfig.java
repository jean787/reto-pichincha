package pe.com.jherrera.customer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para Swagger/OpenAPI.
 * Configura la documentación de la API y el esquema de seguridad para autenticación JWT.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura la documentación de la API con OpenAPI.
     * Establece el título, la versión y los requisitos de seguridad para la autenticación JWT.
     *
     * @return Objeto OpenAPI personalizado para la documentación de la API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API UX Customer")
                        .version("1.0.0")
                        .description("Esta API permite gestionar clientes"));
    }
}
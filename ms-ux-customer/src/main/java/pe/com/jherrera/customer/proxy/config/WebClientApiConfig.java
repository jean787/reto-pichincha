package pe.com.jherrera.customer.proxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Clase de configuración para WebClient.
 * Proporciona un cliente web configurado para consumir servicios REST, como el de GoRest.
 */
@Configuration
public class WebClientApiConfig {

    @Value("${rest.endpoints.customer}")
    private String apiCustomerBaseUrl;

    /**
     * Configura un cliente web para interactuar con la API de GoRest.
     *
     * @return una instancia de WebClient configurada con la URL base de GoRest.
     */
    @Bean
    public WebClient webClientCustomer(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(apiCustomerBaseUrl)
                .build();
    }
}

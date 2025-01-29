package dev.valente.desafio_vagauol.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final Propriedades propriedades;

    @Bean
    public RestClient.Builder vingadoresJson() {
        return RestClient.builder()
                .baseUrl(propriedades.url() + propriedades.uriVingadores());
    }

    @Bean
    public RestClient.Builder ligaDaJusticaXml() {
        return RestClient.builder()
                .baseUrl(propriedades.url() + propriedades.uriLigaDaJustica());
    }

}

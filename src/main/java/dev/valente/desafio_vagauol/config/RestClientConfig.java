package dev.valente.desafio_vagauol.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final Properties properties;

    @Bean
    public RestClient vingadoresJson() {
        return RestClient.builder()
                .baseUrl(properties.url() + properties.uri_vingadores())
                .build();
    }

    @Bean
    public RestClient justiceLeagueXml() {
        return RestClient.builder()
                .baseUrl(properties.url() + properties.uri_liga_da_justica())
                .build();
    }
}

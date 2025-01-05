package dev.valente.desafio_vagauol.config.restclientconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private static final String URL = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias";

    @Bean
    public RestClient vingadoresJson() {
        return RestClient.builder()
                .baseUrl(URL + "/vingadores.json")
                .build();
    }

    @Bean
    public RestClient justiceLeagueXml() {
        return RestClient.builder()
                .baseUrl(URL + "/liga_da_justica.xml")
                .build();
    }
}

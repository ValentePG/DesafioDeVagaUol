package dev.valente.desafio_vagauol.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external")
public record Properties(String url, String uri_vingadores, String uri_liga_da_justica) {
}

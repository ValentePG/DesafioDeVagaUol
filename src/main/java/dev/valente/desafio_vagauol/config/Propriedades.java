package dev.valente.desafio_vagauol.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external")
public record Propriedades(String url, String uriVingadores, String uriLigaDaJustica) {
}

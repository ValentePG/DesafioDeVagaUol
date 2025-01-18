package dev.valente.desafio_vagauol;

import dev.valente.desafio_vagauol.config.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Properties.class)
public class DesafioVagauolApplication {
    public static void main(String[] args) {
        SpringApplication.run(DesafioVagauolApplication.class, args);
    }
}

package dev.valente.desafio_vagauol.utils;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;
import org.springframework.stereotype.Component;

@Component
public class JogadorDataUtil {

    public Jogador getJogadorToSave(){
        return Jogador.builder()
                .email("gabriel@gmail.com")
                .nome("Gabriel")
                .telefone("33333333")
                .grupoCodinome(GrupoCodinome.VINGADORES)
                .build();
    }

    public Jogador getJogadorSavedWithId(){
        return Jogador.builder()
                .email("gabriel@gmail.com")
                .nome("Gabriel")
                .telefone("33333333")
                .grupoCodinome(GrupoCodinome.VINGADORES)
                .codinome("Hulk")
                .id(1L)
                .build();
    }
}

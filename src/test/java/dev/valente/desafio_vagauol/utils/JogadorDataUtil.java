package dev.valente.desafio_vagauol.utils;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;
import org.springframework.stereotype.Component;

@Component
public class JogadorDataUtil {

    public static final String EMAIL_FROM_JOGADOR_TO_SAVE = "gabriel@gmail.com";
    public static final GrupoCodinome GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE = GrupoCodinome.VINGADORES;
    public static final Long ID_FROM_JOGADOR_SAVED = 1L;
    public static final Jogador JOGADOR_TO_SAVE = Jogador.builder()
            .email("gabriel@gmail.com")
            .grupoCodinome(GrupoCodinome.VINGADORES)
            .nome("Gabriel")
            .telefone("33333333")
            .build();
    public static final Jogador JOGADOR_SAVED_WITH_ID = Jogador.builder()
            .email("gabriel@gmail.com")
            .nome("Gabriel")
            .telefone("33333333")
            .grupoCodinome(GrupoCodinome.VINGADORES)
            .codinome("Hulk")
            .id(1L)
            .build();

    public Jogador getJogadorToSave(){
        return JOGADOR_TO_SAVE;
    }

    public Jogador getJogadorSavedWithId(){
        return JOGADOR_SAVED_WITH_ID;
    }
}

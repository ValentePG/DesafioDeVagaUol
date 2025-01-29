package dev.valente.desafio_vagauol.utils;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JogadorVingadoresDataUtil implements JogadorInterface {

    private static JogadorVingadoresDataUtil instance;

    public static JogadorVingadoresDataUtil getInstance() {
        if (Objects.isNull(instance)) {
            instance = new JogadorVingadoresDataUtil();
        }
        return instance;
    }

    private JogadorVingadoresDataUtil() {
    }

    public static final Jogador JOGADOR_TO_SAVE = Jogador.builder()
            .email("gabriel@gmail.com")
            .grupoCodinome(GrupoCodinome.VINGADORES)
            .nome("Gabriel")
            .telefone("33333333")
            .build();
    public static final Jogador JOGADOR_TO_SAVE_WITH_CODINOME = Jogador.builder()
            .email("gabriel@gmail.com")
            .grupoCodinome(GrupoCodinome.VINGADORES)
            .nome("Gabriel")
            .codinome("Hulk")
            .telefone("33333333")
            .build();

    public static final Jogador OTHER_JOGADOR_TO_SAVE_WITH_CODINOME = Jogador.builder()
            .email("geovane@gmail.com")
            .grupoCodinome(GrupoCodinome.VINGADORES)
            .nome("Geovane")
            .codinome("Homem aranha")
            .telefone("30302230")
            .build();

    public static final Jogador JOGADOR_SAVED_WITH_ID = Jogador.builder()
            .email("gabriel@gmail.com")
            .nome("Gabriel")
            .telefone("33333333")
            .grupoCodinome(GrupoCodinome.VINGADORES)
            .codinome("Hulk")
            .id(1L)
            .build();
    public static final String EMAIL_FROM_JOGADOR = JOGADOR_TO_SAVE.getEmail();
    public static final Long ID_FROM_JOGADOR = JOGADOR_SAVED_WITH_ID.getId();
    public static final GrupoCodinome GRUPO_CODINOME_FROM_JOGADOR
            = JOGADOR_TO_SAVE.getGrupoCodinome();

    @Override
    public Jogador getJogadorToSave() {
        return JOGADOR_TO_SAVE;
    }

    @Override
    public Jogador getJogadorSavedWithId() {
        return JOGADOR_SAVED_WITH_ID;
    }

    @Override
    public String getEmailFromJogador() {
        return EMAIL_FROM_JOGADOR;
    }

    @Override
    public Long getIdFromJogador() {
        return ID_FROM_JOGADOR;
    }

    @Override
    public GrupoCodinome getGrupoCodinomeFromJogador() {
        return GRUPO_CODINOME_FROM_JOGADOR;
    }
}

package dev.valente.desafio_vagauol.utils;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;
import org.springframework.stereotype.Component;

@Component
public class JogadorLigaDaJusticaDataUtil implements JogadorInterface {
    public static final Jogador JOGADOR_TO_SAVE = Jogador.builder()
            .email("gabriel@gmail.com")
            .grupoCodinome(GrupoCodinome.LIGA_DA_JUSTICA)
            .nome("Gabriel")
            .telefone("33333333")
            .build();
    public static final Jogador JOGADOR_SAVED_WITH_ID = Jogador.builder()
            .email("gabriel@gmail.com")
            .nome("Gabriel")
            .telefone("33333333")
            .grupoCodinome(GrupoCodinome.LIGA_DA_JUSTICA)
            .codinome("Batman")
            .id(1L)
            .build();
    public static final String EMAIL_FROM_JOGADOR_TO_SAVE = JOGADOR_TO_SAVE.getEmail();
    public static final Long ID_FROM_JOGADOR_SAVED = JOGADOR_SAVED_WITH_ID.getId();
    public static final GrupoCodinome GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE
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
    public String getEmailFromJogadorToSave() {
        return EMAIL_FROM_JOGADOR_TO_SAVE;
    }

    @Override
    public Long getIdFromJogadorSaved() {
        return ID_FROM_JOGADOR_SAVED;
    }

    @Override
    public GrupoCodinome getGrupoCodinomeFromJogadorToSave() {
        return GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE;
    }
}

package dev.valente.desafio_vagauol.utils;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;

public interface JogadorInterface {

    public Jogador getJogadorToSave();
    public Jogador getJogadorSavedWithId();
    public String getEmailFromJogadorToSave();
    public Long getIdFromJogadorSaved();
    public GrupoCodinome getGrupoCodinomeFromJogadorToSave();
}

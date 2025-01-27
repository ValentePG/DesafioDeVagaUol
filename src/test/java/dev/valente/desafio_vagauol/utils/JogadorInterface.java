package dev.valente.desafio_vagauol.utils;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;

public interface JogadorInterface {

     Jogador getJogadorToSave();
     Jogador getJogadorSavedWithId();
     String getEmailFromJogadorToSave();
     Long getIdFromJogadorSaved();
     GrupoCodinome getGrupoCodinomeFromJogadorToSave();
}

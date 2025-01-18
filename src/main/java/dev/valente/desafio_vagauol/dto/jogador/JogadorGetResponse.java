package dev.valente.desafio_vagauol.dto.jogador;

import dev.valente.desafio_vagauol.model.GrupoCodinome;

public record JogadorGetResponse(Long id, String nome, String email, String telefone, String codinome, GrupoCodinome grupoCodinome) {
}

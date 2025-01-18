package dev.valente.desafio_vagauol.dto.jogador;

import dev.valente.desafio_vagauol.model.GrupoCodinome;

public record JogadorPostRequest(String nome, String email, String telefone, GrupoCodinome grupocodinome) {
}

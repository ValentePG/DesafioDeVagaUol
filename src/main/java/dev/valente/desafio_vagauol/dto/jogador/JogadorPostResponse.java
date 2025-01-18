package dev.valente.desafio_vagauol.dto.jogador;

import dev.valente.desafio_vagauol.model.GrupoCodinome;

public record JogadorPostResponse(Long id, String nome, String email, String telefone, GrupoCodinome grupocodinome){
}

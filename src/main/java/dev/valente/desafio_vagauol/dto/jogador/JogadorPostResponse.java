package dev.valente.desafio_vagauol.dto.jogador;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;

public record JogadorPostResponse(Long id, String nome, String email, String telefone,
                                  String codinome, GrupoCodinome grupoCodinome) {
}

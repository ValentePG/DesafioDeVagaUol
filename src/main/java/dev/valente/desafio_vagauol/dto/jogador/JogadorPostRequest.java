package dev.valente.desafio_vagauol.dto.jogador;

import dev.valente.desafio_vagauol.model.GrupoCodinome;
import jakarta.validation.constraints.NotBlank;

public record JogadorPostRequest(
        @NotBlank(message = "O campo nome não pode estar em branco") String nome,
        @NotBlank(message = "Email não pode estar em branco") String email,
        String telefone,
        @NotBlank(message = "O campo grupo codinome não pode estar em branco") GrupoCodinome grupocodinome) {
}

package dev.valente.desafio_vagauol.dto.jogador;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record JogadorPostRequest(
        @NotBlank(message = "O campo nome não pode estar em branco")
        String nome,
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email inválido")
        @NotBlank(message = "Email não pode estar em branco")
        String email,
        String telefone,
        GrupoCodinome grupoCodinome) {
}

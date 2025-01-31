package dev.valente.desafio_vagauol.dto.jogador;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record JogadorPostRequest(
        @NotBlank(message = "O campo nome não pode estar em branco")
        @Schema(description = "Nome do Jogador", example = "Gabriel")
        String nome,
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email inválido")
        @NotBlank(message = "Email não pode estar em branco")
        @Schema(description = "Email do Jogador", example = "gabriel@gmail.com")
        String email,
        @Schema(description = "Telefone do jogador", example = "12345556712")
        String telefone,
        @Schema(description = "Grupo pelo qual o usuário escolheu", example = "VINGADORES")
        GrupoCodinome grupoCodinome) {
}

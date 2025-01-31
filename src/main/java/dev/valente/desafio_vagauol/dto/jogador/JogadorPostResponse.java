package dev.valente.desafio_vagauol.dto.jogador;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record JogadorPostResponse(@Schema(description = "Id único do Jogador", example = "1") Long id,
                                  @Schema(description = "Nome do Jogador", example = "Gabriel")
                                  String nome,
                                  @Schema(description = "Email do Jogador", example = "gabriel@gmail.com")
                                  String email,
                                  @Schema(description = "Telefone do Jogador", example = "12345556712")
                                  String telefone,
                                  @Schema(description = "Codinome gerado pelo sistema", example = "Hulk")
                                  String codinome,
                                  @Schema(description = "Grupo escolhido pelo jogador", example = "VINGADORES")
                                  GrupoCodinome grupoCodinome) {
}

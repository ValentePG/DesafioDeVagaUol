package dev.valente.desafio_vagauol.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiError {

    @Schema(description = "TimeStamp da exceção", example = "2025-01-30T21:54:43.653-03:00")
    private String timestamp;
    @Schema(description = "Status code da exceção", example = "400")
    private int statusCode;
    @Schema(description = "Descrição do erro", example = "Bad Request")
    private String error;
    @Schema(description = "Mensagem do erro", example = "O nome não pode estar vazio")
    private String message;
    @Schema(description = "Path do erro", example = "/v1/jogadores")
    private String path;

}

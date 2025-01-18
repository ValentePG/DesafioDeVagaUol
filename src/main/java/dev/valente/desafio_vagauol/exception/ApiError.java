package dev.valente.desafio_vagauol.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiError {

    private String timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;

}

package dev.valente.desafio_vagauol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExist extends ResponseStatusException {
    public EmailAlreadyExist(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

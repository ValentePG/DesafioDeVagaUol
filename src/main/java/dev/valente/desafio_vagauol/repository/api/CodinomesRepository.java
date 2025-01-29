package dev.valente.desafio_vagauol.repository.api;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CodinomesRepository {
    List<String> buscarCodinomes() throws JsonProcessingException;
}

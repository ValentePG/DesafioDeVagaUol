package dev.valente.desafio_vagauol.repository.codinome;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CodinomesRepository {
    List<String> getCodinomes() throws JsonProcessingException;
}

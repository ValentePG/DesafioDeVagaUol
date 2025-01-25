package dev.valente.desafio_vagauol.repository.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.valente.desafio_vagauol.dto.vingadoresdto.Vingadores;
import dev.valente.desafio_vagauol.dto.vingadoresdto.VingadoresDTO;
import dev.valente.desafio_vagauol.repository.codinome.CodinomesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VingadoresRepository implements CodinomesRepository {

    private final RestClient.Builder vingadoresJson;
    private final ObjectMapper objectMapper;

    @Override
    public List<String> buscarCodinomes() throws JsonProcessingException {
        var jsonResponse = vingadoresJson.build().get()
                .retrieve()
                .body(String.class);

        var vingadoresDTO = objectMapper.readValue(jsonResponse, VingadoresDTO.class);

        return vingadoresDTO.vingadores().stream().map(Vingadores::getCodinome).toList();
    }
}

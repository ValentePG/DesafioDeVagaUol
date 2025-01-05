package dev.valente.desafio_vagauol.repository.codinome;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.valente.desafio_vagauol.dto.vingadoresdto.Vingadores;
import dev.valente.desafio_vagauol.dto.vingadoresdto.VingadoresDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VingadoresRepository implements CodinomesRepository {

    private final RestClient vingadoresJson;

    @Override
    public List<String> getCodinomes() throws JsonProcessingException {
        var json = vingadoresJson.get()
                .retrieve()
                .body(String.class);

        var objectMapper = new ObjectMapper();

        var jsonVingadores = objectMapper.readValue(json, VingadoresDTO.class);

        return jsonVingadores.vingadores().stream().map(Vingadores::getCodinome).toList();
    }
}

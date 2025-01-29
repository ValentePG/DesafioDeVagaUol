package dev.valente.desafio_vagauol.repository.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.valente.desafio_vagauol.dto.justiceleague.LigaDaJusticaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LigaDaJusticaRepository implements CodinomesRepository {

    private final RestClient.Builder ligaDaJusticaXml;

    @Override
    public List<String> buscarCodinomes() throws JsonProcessingException {
        var xmlResponse = ligaDaJusticaXml.build().get()
                .retrieve()
                .body(String.class);

        var xmlMapper = new XmlMapper();
        var ligaDaJusticaDTO = xmlMapper.readValue(xmlResponse, LigaDaJusticaDTO.class);

        return ligaDaJusticaDTO.codinomes();
    }
}

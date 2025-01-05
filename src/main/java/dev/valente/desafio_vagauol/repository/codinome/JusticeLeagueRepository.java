package dev.valente.desafio_vagauol.repository.codinome;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.valente.desafio_vagauol.dto.justiceleague.JusticeLeagueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JusticeLeagueRepository implements CodinomesRepository {

    private final RestClient justiceLeagueXml;

    @Override
    public List<String> getCodinomes() throws JsonProcessingException {
        var xml = justiceLeagueXml.get()
                .retrieve()
                .body(String.class);

        var xmlMapper = new XmlMapper();
        var justiceLeague = xmlMapper.readValue(xml, JusticeLeagueDTO.class);

        return justiceLeague.codinomes();
    }
}

package dev.valente.desafio_vagauol.repository.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.valente.desafio_vagauol.config.Propriedades;
import dev.valente.desafio_vagauol.config.RestClientConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import static dev.valente.desafio_vagauol.utils.CodinomeLigaDaJusticaDataUtil.LIGA_DA_JUSTICA_DTO;
import static dev.valente.desafio_vagauol.utils.CodinomeLigaDaJusticaDataUtil.LIST_OF_CODINOMES_LIGA_DA_JUSTICA;

@RestClientTest({Propriedades.class,
        LigaDaJusticaRepository.class,
        XmlMapper.class,
        RestClientConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CodinomesApiLigaDaJusticaTest {

    @Autowired
    private LigaDaJusticaRepository ligaDaJusticaRepository;

    @Autowired
    private RestClient.Builder ligaDaJusticaXml;

    @Autowired
    private Propriedades propriedades;

    @Autowired
    private MockRestServiceServer server;

    @AfterEach
    void reset() {
        server.reset();
    }

    @Test
    @DisplayName("getCodinomes deve retornar uma lista de strings com codinomes vindos do xml")
    @Order(1)
    void getCodinomes_ShouldReturnListOfCodinomes_WhenSuccessfull() throws JsonProcessingException {

        server = MockRestServiceServer.bindTo(ligaDaJusticaXml).build();

        var xmlMapper = new XmlMapper();

        var ligaDaJusticaDTO = xmlMapper.writeValueAsString(LIGA_DA_JUSTICA_DTO);

        var requestTo = MockRestRequestMatchers
                .requestToUriTemplate(propriedades.url() +
                        propriedades.uriLigaDaJustica());


        var withSuccess = MockRestResponseCreators.withSuccess(ligaDaJusticaDTO, MediaType.APPLICATION_JSON);

        server.expect(requestTo).andRespond(withSuccess);

        Assertions.assertThat(ligaDaJusticaRepository.buscarCodinomes())
                .isNotEmpty()
                .isEqualTo(LIST_OF_CODINOMES_LIGA_DA_JUSTICA);
    }
}

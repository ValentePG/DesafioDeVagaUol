package dev.valente.desafio_vagauol.repository.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static dev.valente.desafio_vagauol.utils.CodinomeVingadoresDataUtil.LIST_OF_CODINOMES_VINGADORES;
import static dev.valente.desafio_vagauol.utils.CodinomeVingadoresDataUtil.VINGADORES_DTO;

@RestClientTest({Propriedades.class,
        VingadoresRepository.class,
        ObjectMapper.class,
        RestClientConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CodinomesApiVingadoresTest {

    @Autowired
    private VingadoresRepository vingadores;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestClient.Builder vingadoresJson;

    @Autowired
    private Propriedades propriedades;

    @Autowired
    private MockRestServiceServer server;

    @AfterEach
    void reset() {
        server.reset();
    }

    @Test
    @DisplayName("getCodinomes deve retornar uma lista de strings com codinomes vindos do json")
    @Order(1)
    void getCodinomes_ShouldReturnListOfCodinomes_WhenSuccessfull() throws JsonProcessingException {

        server = MockRestServiceServer.bindTo(vingadoresJson).build();

        var vingadoresDTO = objectMapper.writeValueAsString(VINGADORES_DTO);

        var requestTo = MockRestRequestMatchers
                .requestToUriTemplate(propriedades.url() +
                        propriedades.uriVingadores());


        var withSuccess = MockRestResponseCreators.withSuccess(vingadoresDTO, MediaType.APPLICATION_JSON);

        server.expect(requestTo).andRespond(withSuccess);

        Assertions.assertThat(vingadores.buscarCodinomes())
                .isNotEmpty()
                .isNotNull()
                .isEqualTo(LIST_OF_CODINOMES_VINGADORES);
    }
}

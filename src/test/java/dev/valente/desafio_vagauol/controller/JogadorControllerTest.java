package dev.valente.desafio_vagauol.controller;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;
import dev.valente.desafio_vagauol.repository.api.CodinomesRepository;
import dev.valente.desafio_vagauol.repository.api.CodinomesRepositoryFactory;
import dev.valente.desafio_vagauol.repository.api.LigaDaJusticaRepository;
import dev.valente.desafio_vagauol.repository.api.VingadoresRepository;
import dev.valente.desafio_vagauol.repository.jogador.JogadorRepository;
import dev.valente.desafio_vagauol.utils.*;
import dev.valente.desafio_vagauol.utils.fileutils.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.stream.Stream;

import static dev.valente.desafio_vagauol.utils.JogadoresDataUtil.LIST_OF_JOGADORES;

@WebMvcTest(controllers = JogadorController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"dev.valente.desafio_vagauol.service", "dev.valente.desafio_vagauol.mapper",
        "dev.valente.desafio_vagauol.repository", "dev.valente.desafio_vagauol.config",
        "dev.valente.desafio_vagauol.utils"})
class JogadorControllerTest {
    private static final String URL = "/v1/jogadores";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileUtils fileUtils;

    @InjectMocks
    private JogadorController jogadorController;

    @MockitoBean
    private JogadorRepository jogadorRepository;

    @MockitoBean
    private CodinomesRepositoryFactory factory;

    @MockitoBean
    private VingadoresRepository vingadoresRepository;

    @MockitoBean
    private LigaDaJusticaRepository ligaDaJusticaRepository;

    @Test
    @DisplayName("buscarJogadores deve retornar lista de jogadores quando houver dados registrados no sistema")
    @Order(1)
    void buscarJogadores_ShouldReturnListOfJogadores_WhenItHasData() throws Exception {

        var response = fileUtils.readFile("/http/get/get_buscarjogadoreswithdata_200.json");

        BDDMockito.when(jogadorRepository.findAll()).thenReturn(LIST_OF_JOGADORES);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("buscarJogadores deve retornar lista vazia de jogadores quando não houver dados registrados no sistema")
    @Order(2)
    void buscarJogadores_ShouldReturnEmptyList_WhenItHasNoData() throws Exception {

        var responseFromFile = fileUtils.readFile("/http/get/get_buscarjogadoresempty_200.json");

        BDDMockito.when(jogadorRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseFromFile));
    }

    @ParameterizedTest
    @MethodSource("parameterizedTestForCriarJogadorWhenSuccessfull")
    @DisplayName("criarJogador deve criar e retornar o jogador salvo")
    @Order(3)
    void criarJogador_ShouldSaveAndReturnJogador_WhenSuccessfull(CodinomeInterface codinomeUtil,
                                                                 JogadorInterface jogadorUtil,
                                                                 String pathRequest,
                                                                 String pathResponse) throws Exception {

        var grupoCodinome = jogadorUtil.getGrupoCodinomeFromJogador();
        var listOfCodinomes = codinomeUtil.getListOfCodinomes();
        var jogadorSaved = jogadorUtil.getJogadorSavedWithId();

        var request = fileUtils.readFile(pathRequest);
        var responseFromFile = fileUtils.readFile(pathResponse);

        // Infelizmente não consegui pensar numa solução melhor, por enquanto...
        var repository = (grupoCodinome == GrupoCodinome.VINGADORES) ? vingadoresRepository : ligaDaJusticaRepository;

        BDDMockito.when(factory.create(grupoCodinome)).thenReturn(repository);

        BDDMockito.when(repository.buscarCodinomes()).thenReturn(listOfCodinomes);

        BDDMockito.when(jogadorRepository.save(BDDMockito.any(Jogador.class))).thenReturn(jogadorSaved);

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(responseFromFile));

    }

    private static Stream<Arguments> parameterizedTestForCriarJogadorWhenSuccessfull() {
        var instanceOfJogadorVingadoresUtil = JogadorVingadoresDataUtil.getInstance();
        var instanceOfCodinomeVingadoresUtil = CodinomeVingadoresDataUtil.getInstance();
        var instanceOfJogadorLigaDaJusticaUtil = JogadorLigaDaJusticaDataUtil.getInstance();
        var instanceOfCodinomeLigaDaJusticaUtil = CodinomeLigaDaJusticaDataUtil.getInstance();

        var requestPathForVingadores = "/http/post/post_criarjogadorvingadores_200.json";
        var responsePathForVingadores = "/http/post/post_criarjogadorvingadores_201.json";

        var requestPathForLigaDaJustica = "/http/post/post_criarjogadorligadajustica_200.json";
        var responsePathFromFileForLigaDaJustica = "/http/post/post_criarjogadorligadajustica_201.json";


        return Stream.of(
            Arguments.of(instanceOfCodinomeVingadoresUtil,instanceOfJogadorVingadoresUtil,
                    requestPathForVingadores, responsePathForVingadores),
            Arguments.of(instanceOfCodinomeLigaDaJusticaUtil,instanceOfJogadorLigaDaJusticaUtil,
                    requestPathForLigaDaJustica, responsePathFromFileForLigaDaJustica)
        );
    }
}
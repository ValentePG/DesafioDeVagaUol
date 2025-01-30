package dev.valente.desafio_vagauol.controller;

import dev.valente.desafio_vagauol.domain.Jogador;
import dev.valente.desafio_vagauol.mapper.JogadorMapper;
import dev.valente.desafio_vagauol.repository.jogador.JogadorRepository;
import dev.valente.desafio_vagauol.service.CodinomeService;
import dev.valente.desafio_vagauol.service.JogadorService;
import dev.valente.desafio_vagauol.utils.CodinomeVingadoresDataUtil;
import dev.valente.desafio_vagauol.utils.fileutils.FileUtils;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static dev.valente.desafio_vagauol.utils.CodinomeVingadoresDataUtil.FIRST_CODINOME_OF_LIST_VINGADORES;
import static dev.valente.desafio_vagauol.utils.CodinomeVingadoresDataUtil.LIST_OF_CODINOMES_VINGADORES;
import static dev.valente.desafio_vagauol.utils.JogadorVingadoresDataUtil.*;
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
    private CodinomeService codinomeService;

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

    @Test
    @DisplayName("criarJogador deve criar e retornar o jogador salvo")
    @Order(3)
    void criarJogador_ShouldSaveAndReturnJogador_WhenSuccessfull() throws Exception {
        var request = fileUtils.readFile("/http/post/post_criarjogador_200.json");
        var responseFromFile = fileUtils.readFile("/http/post/post_criarjogador_201.json");

        BDDMockito.when(jogadorRepository.findAllCodinomes(GRUPO_CODINOME_FROM_JOGADOR))
                .thenReturn(LIST_OF_CODINOMES_VINGADORES);

        BDDMockito.when(codinomeService.gerarCodinome(GRUPO_CODINOME_FROM_JOGADOR, LIST_OF_CODINOMES_VINGADORES))
                        .thenReturn(FIRST_CODINOME_OF_LIST_VINGADORES);

        BDDMockito.when(jogadorRepository.save(BDDMockito.any(Jogador.class))).thenReturn(JOGADOR_SAVED_WITH_ID);

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(responseFromFile));

    }
}
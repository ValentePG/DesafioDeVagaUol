package dev.valente.desafio_vagauol.service;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;
import dev.valente.desafio_vagauol.exception.EmailAlreadyExist;
import dev.valente.desafio_vagauol.exception.NotFoundException;
import dev.valente.desafio_vagauol.repository.jogador.JogadorRepository;
import dev.valente.desafio_vagauol.utils.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static dev.valente.desafio_vagauol.utils.JogadorVingadoresDataUtil.EMAIL_FROM_JOGADOR;
import static dev.valente.desafio_vagauol.utils.JogadorVingadoresDataUtil.JOGADOR_TO_SAVE;
import static dev.valente.desafio_vagauol.utils.JogadoresDataUtil.LIST_OF_JOGADORES;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JogadorServiceTest {

    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRepository jogadorRepository;

    @Mock
    private CodinomeService codinomeService;

    @Test
    @DisplayName("buscarJogadores deve retornar uma lista de jogadores registrados")
    @Order(1)
    void buscarJogadores_ShouldReturnRegisteredJogadores_WhenIsNotEmpty() {
        BDDMockito.when(jogadorRepository.findAll()).thenReturn(LIST_OF_JOGADORES);

        var sut = jogadorService.buscarJogadores();

        Assertions.assertThat(sut)
                .doesNotContainNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("buscarJogadores deve retornar uma lista vazia quando não houver jogadores registrados")
    @Order(2)
    void buscarJogadores_ShouldReturnEmptyList_WhenDoesNotHaveAnyoneRegistered() {
        BDDMockito.when(jogadorRepository.findAll()).thenReturn(Collections.emptyList());

        var sut = jogadorService.buscarJogadores();

        Assertions.assertThat(sut)
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("parameterizedSalvarJogadorSuccessfullTest")
    @DisplayName("salvarJogador deve salvar e retornar o jogador salvo")
    @Order(3)
    void salvarJogador_ShouldSaveAndReturnJogador_WhenSuccessfull(JogadorInterface jogadorUtil,
                                                                  CodinomeInterface codinomeUtil) throws Exception {

        var jogadorToSave = jogadorUtil.getJogadorToSave();

        arrangeForSalvarJogadorSuccessfullTest(jogadorUtil, codinomeUtil);

        var sut = jogadorService.salvarJogador(jogadorToSave);

        assertionsForSalvarJogadorSuccessfullTest(sut, jogadorUtil, codinomeUtil);

    }

    private static Stream<Arguments> parameterizedSalvarJogadorSuccessfullTest() {
        var instanceOfJogadorVingadoresDataUtil = JogadorVingadoresDataUtil.getInstance();
        var instanceOfJogadorLigaDaJusticaDataUtil = JogadorLigaDaJusticaDataUtil.getInstance();
        var instanceOfCodinomeVingadoresDataUtil = CodinomeVingadoresDataUtil.getInstance();
        var instanceOfCodinomeLigaDaJusticaDataUtil = CodinomeLigaDaJusticaDataUtil.getInstance();


        return Stream.of(
                Arguments.of(instanceOfJogadorVingadoresDataUtil, instanceOfCodinomeVingadoresDataUtil),
                Arguments.of(instanceOfJogadorLigaDaJusticaDataUtil, instanceOfCodinomeLigaDaJusticaDataUtil)
        );
    }

    private void arrangeForSalvarJogadorSuccessfullTest(JogadorInterface jogadorUtil,
                                                        CodinomeInterface codinomeUtil) throws Exception {

        BDDMockito.when(jogadorRepository.findAllCodinomes(BDDMockito.any(GrupoCodinome.class)))
                .thenReturn(codinomeUtil.getListOfCodinomes());

        BDDMockito.when(jogadorRepository.findByEmail(jogadorUtil.getEmailFromJogador()))
                .thenReturn(Optional.empty());

        BDDMockito.when(codinomeService.gerarCodinome(jogadorUtil.getGrupoCodinomeFromJogador(),
                        codinomeUtil.getListOfCodinomes()))
                .thenReturn(codinomeUtil.getFirstCodinome());

        BDDMockito.when(jogadorRepository.save(BDDMockito.any(Jogador.class)))
                .thenReturn(jogadorUtil.getJogadorSavedWithId());
    }

    private void assertionsForSalvarJogadorSuccessfullTest(Jogador sut, JogadorInterface jogadorUtil,
                                                           CodinomeInterface codinomeUtil) throws Exception {
        var idFromSavedJogador = jogadorUtil.getIdFromJogador();
        var firstCodinome = codinomeUtil.getFirstCodinome();
        var emailFromJogadorToSave = jogadorUtil.getEmailFromJogador();
        var grupoCodinomeFromJogadorToSave = jogadorUtil.getGrupoCodinomeFromJogador();
        var listOfCodinomes = codinomeUtil.getListOfCodinomes();

        Assertions.assertThat(sut)
                .hasFieldOrPropertyWithValue("id", idFromSavedJogador)
                .hasFieldOrPropertyWithValue("codinome", firstCodinome)
                .hasNoNullFieldsOrProperties();

        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(emailFromJogadorToSave);
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findAllCodinomes(grupoCodinomeFromJogadorToSave);
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .save(BDDMockito.any(Jogador.class));


        BDDMockito.verify(codinomeService, BDDMockito.times(1))
                .gerarCodinome(grupoCodinomeFromJogadorToSave, listOfCodinomes);
        BDDMockito.verifyNoMoreInteractions(jogadorRepository, codinomeService);
    }


    @Test
    @DisplayName("salvarJogador deve retornar exceção EmailAlreadyExists")
    @Order(4)
    void salvarJogador_ShouldReturnEmailAlreadyExists_WhenEmailWasFound() throws Exception {

        // Arrange
        BDDMockito.when(jogadorRepository.findByEmail(EMAIL_FROM_JOGADOR))
                .thenReturn(Optional.of(JOGADOR_TO_SAVE));

        // Act / Asserts
        Assertions.assertThatException()
                .isThrownBy(() -> jogadorService.salvarJogador(JOGADOR_TO_SAVE))
                .withMessage("400 BAD_REQUEST \"Email já cadastrado no sistema\"")
                .isInstanceOf(EmailAlreadyExist.class);

        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(EMAIL_FROM_JOGADOR);

        BDDMockito.verifyNoMoreInteractions(jogadorRepository, codinomeService);


    }

    @ParameterizedTest
    @MethodSource("parameterizedSalvarJogadorWhenListOfCodinomeIsEmpty")
    @DisplayName("salvarJogador deve retornar exceção NotFoundException")
    @Order(5)
    void salvarJogador_ShouldReturnNotFoundException_WhenListOfCodinomeIsEmpty(JogadorInterface jogadorUtil) throws Exception {

        var grupoCodinome = jogadorUtil.getGrupoCodinomeFromJogador();
        var nomeDoGrupo = jogadorUtil.getGrupoCodinomeFromJogador().getGroupName();
        var jogadorToSave = jogadorUtil.getJogadorToSave();

        var notFoundException = new NotFoundException("Não há codinomes disponíveis para o grupo " +
                nomeDoGrupo);

        // Arrange
        BDDMockito.doThrow(notFoundException)
                .when(codinomeService).gerarCodinome(grupoCodinome, Collections.emptyList());

        // Act / Asserts
        Assertions.assertThatException()
                .isThrownBy(() -> jogadorService.salvarJogador(jogadorToSave))
                .withMessage("404 NOT_FOUND \"Não há codinomes disponíveis para o grupo %s\""
                        .formatted(nomeDoGrupo))
                .isInstanceOf(NotFoundException.class);

        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(BDDMockito.any());
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findAllCodinomes(BDDMockito.any());

        BDDMockito.verify(codinomeService, BDDMockito.times(1))
                .gerarCodinome(grupoCodinome, Collections.emptyList());

        BDDMockito.verifyNoMoreInteractions(jogadorRepository, codinomeService);
    }


    private static Stream<Arguments> parameterizedSalvarJogadorWhenListOfCodinomeIsEmpty() {
        var instanceOfJogadorVingadoresDataUtil = JogadorVingadoresDataUtil.getInstance();
        var instanceOfJogadorLigaDaJusticaDataUtil = JogadorLigaDaJusticaDataUtil.getInstance();

        return Stream.of(
                Arguments.of(instanceOfJogadorVingadoresDataUtil),
                Arguments.of(instanceOfJogadorLigaDaJusticaDataUtil)
        );
    }

}
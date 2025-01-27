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

import static dev.valente.desafio_vagauol.utils.JogadorVingadoresDataUtil.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JogadorServiceTest {

    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRepository jogadorRepository;

    @Mock
    private CodinomeService codinomeService;

    @ParameterizedTest
    @MethodSource("parameterizedSalvarJogadorSuccessfullTest")
    @DisplayName("salvarJogador deve salvar e retornar o jogador salvo")
    @Order(1)
    void salvarJogador_shouldSaveAndReturnJogador_WhenSuccessfull(JogadorInterface jogadorUtil,
                                                                  CodinomeInterface codinomeUtil) throws Exception {

        arrangeForSalvarJogadorSuccessfullTest(jogadorUtil, codinomeUtil);

        var sut = jogadorService.salvarJogador(jogadorUtil.getJogadorToSave());

        assertionsForSalvarJogadorSuccessfullTest(sut, jogadorUtil, codinomeUtil);

    }

    private static Stream<Arguments> parameterizedSalvarJogadorSuccessfullTest() {
        return Stream.of(
                Arguments.of(new JogadorVingadoresDataUtil(), new CodinomeVingadoresDataUtil()),
                Arguments.of(new JogadorLigaDaJusticaDataUtil(), new CodinomeLigaDaJusticaDataUtil())
        );
    }

    private void arrangeForSalvarJogadorSuccessfullTest(JogadorInterface jogadorUtil,
                                                        CodinomeInterface codinomeUtil) throws Exception {

        BDDMockito.when(jogadorRepository.findAllCodinomes(BDDMockito.any(GrupoCodinome.class)))
                .thenReturn(codinomeUtil.getListOfCodinomes());

        BDDMockito.when(jogadorRepository.findByEmail(jogadorUtil.getEmailFromJogadorToSave()))
                .thenReturn(Optional.empty());

        BDDMockito.when(codinomeService.gerarCodinome(jogadorUtil.getGrupoCodinomeFromJogadorToSave(),
                        codinomeUtil.getListOfCodinomes()))
                .thenReturn(codinomeUtil.getFirstCodinome());

        BDDMockito.when(jogadorRepository.save(BDDMockito.any(Jogador.class)))
                .thenReturn(jogadorUtil.getJogadorSavedWithId());
    }

    private void assertionsForSalvarJogadorSuccessfullTest(Jogador sut, JogadorInterface jogadorUtil,
                                                           CodinomeInterface codinomeUtil) throws Exception {

        Assertions.assertThat(sut)
                .hasFieldOrPropertyWithValue("id", jogadorUtil.getIdFromJogadorSaved())
                .hasFieldOrPropertyWithValue("codinome", codinomeUtil.getFirstCodinome())
                .hasNoNullFieldsOrProperties();

        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(jogadorUtil.getEmailFromJogadorToSave());
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findAllCodinomes(jogadorUtil.getGrupoCodinomeFromJogadorToSave());
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .save(BDDMockito.any(Jogador.class));


        BDDMockito.verify(codinomeService, BDDMockito.times(1))
                .gerarCodinome(jogadorUtil.getGrupoCodinomeFromJogadorToSave(), codinomeUtil.getListOfCodinomes());
        BDDMockito.verifyNoMoreInteractions(jogadorRepository, codinomeService);
    }


    @Test
    @DisplayName("salvarJogador deve retornar exceção EmailAlreadyExists")
    @Order(2)
    void salvarJogador_shouldReturnEmailAlreadyExists_WhenEmailWasFound() throws Exception {

        // Arrange
        BDDMockito.when(jogadorRepository.findByEmail(EMAIL_FROM_JOGADOR_TO_SAVE))
                .thenReturn(Optional.of(JOGADOR_TO_SAVE));

        // Act / Asserts
        Assertions.assertThatException()
                .isThrownBy(() -> jogadorService.salvarJogador(JOGADOR_TO_SAVE))
                .withMessage("400 BAD_REQUEST \"Email já cadastrado no sistema\"")
                .isInstanceOf(EmailAlreadyExist.class);

        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(EMAIL_FROM_JOGADOR_TO_SAVE);

        BDDMockito.verifyNoMoreInteractions(jogadorRepository, codinomeService);


    }

    @ParameterizedTest
    @MethodSource("parameterizedSalvarJogadorWhenListOfCodinomeIsEmpty")
    @DisplayName("salvarJogador deve retornar exceção NotFoundException")
    @Order(3)
    void salvarJogador_shouldReturnNotFoundException_WhenListOfCodinomeIsEmpty(JogadorInterface jogadorUtil) throws Exception {

        var grupoCodinome = jogadorUtil.getGrupoCodinomeFromJogadorToSave();
        var grupoName = jogadorUtil.getGrupoCodinomeFromJogadorToSave().getGroupName();
        var jogadorToSave = jogadorUtil.getJogadorToSave();


        // Arrange
        BDDMockito.doThrow(new NotFoundException("Não há codinomes disponíveis para o grupo " +
                        grupoName))
                .when(codinomeService).gerarCodinome(grupoCodinome, Collections.emptyList());

        // Act / Asserts
        Assertions.assertThatException()
                .isThrownBy(() -> jogadorService.salvarJogador(jogadorToSave))
                .withMessage("404 NOT_FOUND \"Não há codinomes disponíveis para o grupo %s\""
                        .formatted(grupoName))
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
        return Stream.of(
                Arguments.of(new JogadorVingadoresDataUtil()),
                Arguments.of(new JogadorLigaDaJusticaDataUtil())
        );
    }

}
package dev.valente.desafio_vagauol.service;

import dev.valente.desafio_vagauol.exception.NotFoundException;
import dev.valente.desafio_vagauol.repository.api.CodinomesRepository;
import dev.valente.desafio_vagauol.repository.api.CodinomesRepositoryFactory;
import dev.valente.desafio_vagauol.utils.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CodinomeServiceTest {

    @InjectMocks
    CodinomeService codinomeService;

    @Mock
    CodinomesRepositoryFactory codinomesRepositoryFactory;

    @Mock
    CodinomesRepository codinomesRepository;

    @ParameterizedTest
    @MethodSource("parameterizedGerarCodinomeWhenSuccessfull")
    @DisplayName("gerarCodinome deve gerar um codinome disponível aleatoriamente para o usuário")
    @Order(1)
    void gerarCodinome_ShouldReturnCodinome_WhenSuccessfull(CodinomeInterface codinomeUtil,
                                                            JogadorInterface jogadorUtil) throws Exception {

        var grupoCodinome = jogadorUtil.getGrupoCodinomeFromJogador();
        var listOfCodinomes = codinomeUtil.getListOfCodinomes();
        var firstCodinomeOfList = codinomeUtil.getFirstCodinome();

        BDDMockito.when(codinomesRepositoryFactory.create(grupoCodinome))
                .thenReturn(codinomesRepository);

        BDDMockito.when(codinomesRepository.buscarCodinomes()).thenReturn(listOfCodinomes);

        var sut = codinomeService.gerarCodinome(grupoCodinome, Collections.emptyList());

        Assertions.assertThat(sut)
                .isIn(listOfCodinomes)
                .isEqualTo(firstCodinomeOfList);

    }

    private static Stream<Arguments> parameterizedGerarCodinomeWhenSuccessfull() {
        var instanceCodinomeVingadores = CodinomeVingadoresDataUtil.getInstance();
        var instanceCodinomeLigaDaJustica = CodinomeLigaDaJusticaDataUtil.getInstance();
        var instanceJogadorLigaDaJustica = JogadorLigaDaJusticaDataUtil.getInstance();
        var instanceJogadorVingadores = JogadorVingadoresDataUtil.getInstance();
        return Stream.of(
                Arguments.of(instanceCodinomeVingadores, instanceJogadorVingadores),
                Arguments.of(instanceCodinomeLigaDaJustica, instanceJogadorLigaDaJustica)
        );
    }

    @ParameterizedTest
    @MethodSource("parameterizedGerarCodinomeWhenAllCodinomesAreUsed")
    @DisplayName("gerarCodinome deve retornar NOT FOUND quando todos os codinomes ja estiverem em uso")
    @Order(2)
    void gerarCodinome_ShouldReturnNotFound_WhenAllCodinomesAreUsed(CodinomeInterface codinomeUtil,
                                                                    JogadorInterface jogadorUtil) throws Exception {

        var grupoCodinome = jogadorUtil.getGrupoCodinomeFromJogador();
        var nomeDoGrupo = grupoCodinome.getGroupName();
        var listOfCodinomes = codinomeUtil.getListOfCodinomes();

        BDDMockito.when(codinomesRepositoryFactory.create(grupoCodinome))
                .thenReturn(codinomesRepository);

        BDDMockito.when(codinomesRepository.buscarCodinomes()).thenReturn(listOfCodinomes);

        Assertions.assertThatException()
                .isThrownBy(() -> codinomeService.gerarCodinome(grupoCodinome,
                        listOfCodinomes))
                .withMessage("404 NOT_FOUND \"Não há codinomes disponíveis para o grupo %s\""
                        .formatted(nomeDoGrupo))
                .isInstanceOf(NotFoundException.class);
    }

    private static Stream<Arguments> parameterizedGerarCodinomeWhenAllCodinomesAreUsed() {
        var instanceCodinomeVingadores = CodinomeVingadoresDataUtil.getInstance();
        var instanceCodinomeLigaDaJustica = CodinomeLigaDaJusticaDataUtil.getInstance();
        var instanceJogadorLigaDaJustica = JogadorLigaDaJusticaDataUtil.getInstance();
        var instanceJogadorVingadores = JogadorVingadoresDataUtil.getInstance();
        return Stream.of(
                Arguments.of(instanceCodinomeVingadores, instanceJogadorVingadores),
                Arguments.of(instanceCodinomeLigaDaJustica, instanceJogadorLigaDaJustica)
        );
    }
}
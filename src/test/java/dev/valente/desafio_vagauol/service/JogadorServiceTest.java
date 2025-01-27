package dev.valente.desafio_vagauol.service;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;
import dev.valente.desafio_vagauol.exception.EmailAlreadyExist;
import dev.valente.desafio_vagauol.exception.NotFoundException;
import dev.valente.desafio_vagauol.repository.jogador.JogadorRepository;
import dev.valente.desafio_vagauol.utils.CodinomeDataUtil;
import dev.valente.desafio_vagauol.utils.JogadorDataUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static dev.valente.desafio_vagauol.utils.CodinomeDataUtil.FIRST_OF_LIST;
import static dev.valente.desafio_vagauol.utils.CodinomeDataUtil.LIST_OF_CODINOMES;
import static dev.valente.desafio_vagauol.utils.JogadorDataUtil.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JogadorServiceTest {

    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRepository jogadorRepository;

    @Mock
    private CodinomeService codinomeService;

    private final JogadorDataUtil jogadorDataUtil = new JogadorDataUtil();

    private final CodinomeDataUtil codinomeDataUtil = new CodinomeDataUtil();

    @Test
    @DisplayName("salvarJogador deve salvar e retornar o jogador salvo")
    @Order(1)
    void salvarJogador_shouldSaveAndReturnJogador_WhenSuccessfull() throws Exception {

        arrangeForSuccessfullTest();

        var sut = jogadorService.salvarJogador(JOGADOR_TO_SAVE);

        assertionsForSuccessfullTest(sut);

    }

    private void arrangeForSuccessfullTest() throws Exception {

        BDDMockito.when(jogadorRepository.findAllCodinomes(BDDMockito.any(GrupoCodinome.class)))
                .thenReturn(LIST_OF_CODINOMES);

        BDDMockito.when(jogadorRepository.findByEmail(EMAIL_FROM_JOGADOR_TO_SAVE))
                .thenReturn(Optional.empty());

        BDDMockito.when(codinomeService.gerarCodinome(GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE, LIST_OF_CODINOMES))
                .thenReturn(FIRST_OF_LIST);

        BDDMockito.when(jogadorRepository.save(BDDMockito.any(Jogador.class))).thenReturn(JOGADOR_SAVED_WITH_ID);
    }

    private void assertionsForSuccessfullTest(Jogador sut) throws Exception {

        Assertions.assertThat(sut)
                .hasFieldOrPropertyWithValue("id", ID_FROM_JOGADOR_SAVED)
                .hasFieldOrPropertyWithValue("codinome", FIRST_OF_LIST)
                .hasNoNullFieldsOrProperties();

        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(EMAIL_FROM_JOGADOR_TO_SAVE);
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1) )
                .findAllCodinomes(GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE);
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1) )
                .save(BDDMockito.any(Jogador.class));


        BDDMockito.verify(codinomeService, BDDMockito.times(1))
                .gerarCodinome(GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE, LIST_OF_CODINOMES);
        BDDMockito.verifyNoMoreInteractions(jogadorRepository, codinomeService);
    }


    @Test
    @DisplayName("salvarJogador deve retornar exceção EmailAlreadyExists")
    @Order(2)
    void salvarJogador_shouldReturnEmailAlreadyExists_WhenEmailWasFound() throws Exception {


        BDDMockito.when(jogadorRepository.findByEmail(EMAIL_FROM_JOGADOR_TO_SAVE))
                .thenReturn(Optional.of(JOGADOR_TO_SAVE));

        Assertions.assertThatException()
                .isThrownBy(() -> jogadorService.salvarJogador(JOGADOR_TO_SAVE))
                .withMessage("400 BAD_REQUEST \"Email já cadastrado no sistema\"")
                .isInstanceOf(EmailAlreadyExist.class);

        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(EMAIL_FROM_JOGADOR_TO_SAVE);

        BDDMockito.verifyNoMoreInteractions(jogadorRepository, codinomeService);


    }

    @Test
    @DisplayName("salvarJogador deve retornar exceção NotFoundException")
    @Order(3)
    void salvarJogador_shouldReturnNotFoundException_WhenListOfCodinomeIsEmpty() throws Exception {


        BDDMockito.doThrow(new NotFoundException("Não há codinomes disponíveis para o grupo " +
                        GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE.getGroupName()))
                .when(codinomeService).gerarCodinome(GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE, Collections.emptyList());

        Assertions.assertThatException()
                .isThrownBy(() -> jogadorService.salvarJogador(JOGADOR_TO_SAVE))
                .withMessage("404 NOT_FOUND \"Não há codinomes disponíveis para o grupo %s\""
                        .formatted(GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE.getGroupName()))
                .isInstanceOf(NotFoundException.class);

        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(BDDMockito.any());
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findAllCodinomes(BDDMockito.any());

        BDDMockito.verify(codinomeService, BDDMockito.times(1))
                .gerarCodinome(GRUPO_CODINOME_FROM_JOGADOR_TO_SAVE, Collections.emptyList());

        BDDMockito.verifyNoMoreInteractions(jogadorRepository, codinomeService);
    }

}
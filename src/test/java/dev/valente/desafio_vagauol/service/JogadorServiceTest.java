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
        var jogadorToSave = jogadorDataUtil.getJogadorToSave();

        arrangeForSuccessfullTest(jogadorToSave);

        var sut = jogadorService.salvarJogador(jogadorToSave);

        assertionsForSuccessfullTest(sut, jogadorToSave);

    }

    private void arrangeForSuccessfullTest(Jogador jogadorToSave) throws Exception {
        var jogadorSaved = jogadorDataUtil.getJogadorSavedWithId();
        var listOfCodinomes = codinomeDataUtil.getListOfCodinomes();
        var firstOfListOfCodinomes = listOfCodinomes.getFirst();
        var emailFromJogadorToSave = jogadorToSave.getEmail();
        var grupoCodinomeFromJogadorToSave = jogadorToSave.getGrupoCodinome();

        BDDMockito.when(jogadorRepository.findAllCodinomes(BDDMockito.any(GrupoCodinome.class)))
                .thenReturn(listOfCodinomes);

        BDDMockito.when(jogadorRepository.findByEmail(emailFromJogadorToSave))
                .thenReturn(Optional.empty());

        BDDMockito.when(codinomeService.gerarCodinome(grupoCodinomeFromJogadorToSave, listOfCodinomes))
                .thenReturn(firstOfListOfCodinomes);

        BDDMockito.when(jogadorRepository.save(BDDMockito.any(Jogador.class))).thenReturn(jogadorSaved);
    }

    private void assertionsForSuccessfullTest(Jogador sut, Jogador jogadorToSave) throws Exception {
        var emailFromJogadorToSave = jogadorToSave.getEmail();
        var grupoCodinomeFromJogadorToSave = jogadorToSave.getGrupoCodinome();
        var jogadorSaved = jogadorDataUtil.getJogadorSavedWithId();
        var idFromJogadorSaved = jogadorSaved.getId();
        var listOfCodinomes = codinomeDataUtil.getListOfCodinomes();
        var firstOfListOfCodinomes = listOfCodinomes.getFirst();

        Assertions.assertThat(sut)
                .hasFieldOrPropertyWithValue("id", idFromJogadorSaved)
                .hasFieldOrPropertyWithValue("codinome", firstOfListOfCodinomes)
                .hasNoNullFieldsOrProperties();

        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(emailFromJogadorToSave);
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1) )
                .findAllCodinomes(grupoCodinomeFromJogadorToSave);
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1) )
                .save(BDDMockito.any(Jogador.class));


        BDDMockito.verify(codinomeService, BDDMockito.times(1))
                .gerarCodinome(grupoCodinomeFromJogadorToSave, listOfCodinomes);
        BDDMockito.verifyNoMoreInteractions(jogadorRepository, codinomeService);
    }


    @Test
    @DisplayName("salvarJogador deve retornar exceção EmailAlreadyExists")
    @Order(2)
    void salvarJogador_shouldReturnEmailAlreadyExists_WhenEmailWasFound() throws Exception {

        var jogadorToSave = jogadorDataUtil.getJogadorToSave();
        var emailFromJogadorToSave = jogadorToSave.getEmail();

        var jogadorSaved = jogadorDataUtil.getJogadorSavedWithId();

        BDDMockito.when(jogadorRepository.findByEmail(emailFromJogadorToSave))
                .thenReturn(Optional.of(jogadorSaved));

        Assertions.assertThatException()
                .isThrownBy(() -> jogadorService.salvarJogador(jogadorToSave))
                .withMessage("400 BAD_REQUEST \"Email já cadastrado no sistema\"")
                .isInstanceOf(EmailAlreadyExist.class);

        BDDMockito.verify(jogadorRepository, BDDMockito.atMost(1));
        BDDMockito.verify(jogadorRepository, BDDMockito.times(1))
                .findByEmail(emailFromJogadorToSave);

        BDDMockito.verify(codinomeService, BDDMockito.times(0));

    }

    @Test
    @DisplayName("salvarJogador deve retornar exceção NotFoundException")
    @Order(3)
    void salvarJogador_shouldReturnNotFoundException_WhenListOfCodinomeIsEmpty() throws Exception {

        var jogadorToSave = jogadorDataUtil.getJogadorToSave();
        var grupoCodinomeFromJogadorToSave = jogadorToSave.getGrupoCodinome();

        BDDMockito.doThrow(new NotFoundException("Não há codinomes disponíveis para o grupo " +
                        grupoCodinomeFromJogadorToSave.getGroupName()))
                .when(codinomeService).gerarCodinome(grupoCodinomeFromJogadorToSave, Collections.emptyList());

        Assertions.assertThatException()
                .isThrownBy(() -> codinomeService.gerarCodinome(grupoCodinomeFromJogadorToSave,
                        Collections.emptyList()))
                .withMessage("404 NOT_FOUND \"Não há codinomes disponíveis para o grupo %s\""
                        .formatted(grupoCodinomeFromJogadorToSave.getGroupName()))
                .isInstanceOf(NotFoundException.class);
    }

}
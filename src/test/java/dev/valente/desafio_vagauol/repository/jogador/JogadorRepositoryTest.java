package dev.valente.desafio_vagauol.repository.jogador;

import dev.valente.desafio_vagauol.utils.JogadorInterface;
import dev.valente.desafio_vagauol.utils.JogadorLigaDaJusticaDataUtil;
import dev.valente.desafio_vagauol.utils.JogadorVingadoresDataUtil;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Stream;

import static dev.valente.desafio_vagauol.utils.JogadorVingadoresDataUtil.EMAIL_FROM_JOGADOR;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
class JogadorRepositoryTest {

    @Autowired
    private JogadorRepository jogadorRepository;

    @ParameterizedTest
    @MethodSource("parameterizedTestFindAllCodinomes")
    @DisplayName("findAllCodinomes deve retornar todos os codinomes registrados no banco")
    @Order(1)
    @Sql(value = "/sql/init_four_jogadores.sql")
    @Sql(value = "/sql/drop_jogadores.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllCodinomes_ShouldReturnAllCodinomesRegistered_WhenListIsNotEmpty(JogadorInterface jogadorUtil) {
        var grupoCodinome = jogadorUtil.getGrupoCodinomeFromJogador();
        var groupName = grupoCodinome.getGroupName();

        var sut = jogadorRepository.findAllCodinomes(grupoCodinome);

        log.info("Fazendo busca pelos codinomes do grupo {}", groupName);

        Assertions.assertThat(sut)
                .isNotEmpty()
                .hasSize(2);

        sut.forEach(s -> log.info("Foi encontrado no grupo {} o codinome {}",
                groupName, s));

    }

    private static Stream<Arguments> parameterizedTestFindAllCodinomes() {
        var instanceVingadores = JogadorVingadoresDataUtil.getInstance();
        var instanceLigaDaJustica = JogadorLigaDaJusticaDataUtil.getInstance();
        return Stream.of(
                Arguments.of(instanceVingadores),
                Arguments.of(instanceLigaDaJustica)
        );
    }

    @ParameterizedTest
    @MethodSource("parameterizedTestFindAllCodinomesWhenListIsEmpty")
    @DisplayName("findAllCodinomes deve retornar uma lista vazia quando não houver registros")
    @Order(2)
    void findAllCodinomes_ShouldReturnEmptyList_WhenListIsEmpty(JogadorInterface jogadorUtil) {
        var grupoCodinome = jogadorUtil.getGrupoCodinomeFromJogador();
        var groupName = grupoCodinome.getGroupName();

        log.info("Fazendo busca pelos codinomes do grupo {}", groupName);

        var sut = jogadorRepository.findAllCodinomes(grupoCodinome);

        Assertions.assertThat(sut)
                .isEmpty();

        log.info("Não foram encontrados codinomes registrados no grupo {}", groupName);
    }

    private static Stream<Arguments> parameterizedTestFindAllCodinomesWhenListIsEmpty() {
        var instanceVingadores = JogadorVingadoresDataUtil.getInstance();
        var instanceLigaDaJustica = JogadorLigaDaJusticaDataUtil.getInstance();
        return Stream.of(
                Arguments.of(instanceVingadores),
                Arguments.of(instanceLigaDaJustica)
        );
    }

    @Test
    @DisplayName("findByEmail deve retornar um Optional com um jogador quando for encontrado")
    @Order(3)
    @Sql(value = "/sql/init_four_jogadores.sql")
    @Sql(value = "/sql/drop_jogadores.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByEmail_ShouldReturnOptionalOfJogador_WhenSuccessfull() {

        var sut = jogadorRepository.findByEmail(EMAIL_FROM_JOGADOR);

        log.info("Fazendo busca por jogador pelo email {}", EMAIL_FROM_JOGADOR);

        Assertions.assertThat(sut)
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("email", EMAIL_FROM_JOGADOR);

        var jogador = sut.get();
        var nomeJogador = jogador.getNome();
        var emailJogador = jogador.getEmail();

        log.info("Jogador {} encontrado através do email {}", nomeJogador, emailJogador);
    }

    @Test
    @DisplayName("findByEmail deve retornar um Optional empty quando não for encontrado jogador")
    @Order(4)
    void findByEmail_ShouldReturnOptionalEmpty_WhenSuccessfull() {

        var inexistentEmail = "June@gmail.com";
        var sut = jogadorRepository.findByEmail(inexistentEmail);

        log.info("Fazendo busca por jogador pelo email {}", inexistentEmail);

        Assertions.assertThat(sut)
                .isEmpty();

        log.info("Jogador não encontrado");
    }
}
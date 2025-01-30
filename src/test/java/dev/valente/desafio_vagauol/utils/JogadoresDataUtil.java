package dev.valente.desafio_vagauol.utils;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JogadoresDataUtil {

    private static JogadoresDataUtil instance;

    public static JogadoresDataUtil getInstance() {
        if (Objects.isNull(instance)) {
            instance = new JogadoresDataUtil();
        }
        return instance;
    }

    private JogadoresDataUtil() {
    }

    private static final Jogador JOGADOR1 = Jogador.builder()
            .email("gabriel@gmail.com")
            .nome("Gabriel")
            .telefone("33333333")
            .grupoCodinome(GrupoCodinome.VINGADORES)
            .codinome("Hulk")
            .id(1L)
            .build();
    private static final Jogador JOGADOR2 = Jogador.builder()
            .email("jorge@gmail.com")
            .nome("Jorge")
            .telefone("1111111")
            .grupoCodinome(GrupoCodinome.VINGADORES)
            .codinome("Capitão América")
            .id(2L)
            .build();
    private static final Jogador JOGADOR3 = Jogador.builder()
            .email("gustavo@gmail.com")
            .nome("Gustavo")
            .telefone("222222")
            .grupoCodinome(GrupoCodinome.LIGA_DA_JUSTICA)
            .codinome("Batman")
            .id(3L)
            .build();
    private static final Jogador JOGADOR4 = Jogador.builder()
            .email("jony@gmail.com")
            .nome("Jony")
            .telefone("444444")
            .grupoCodinome(GrupoCodinome.LIGA_DA_JUSTICA)
            .codinome("Flash")
            .id(4L)
            .build();

    public static final List<Jogador> LIST_OF_JOGADORES =
            new ArrayList<>(List.of(JOGADOR1, JOGADOR2, JOGADOR3, JOGADOR4));

}

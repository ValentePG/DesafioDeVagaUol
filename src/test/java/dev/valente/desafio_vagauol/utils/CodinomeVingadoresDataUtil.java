package dev.valente.desafio_vagauol.utils;

import dev.valente.desafio_vagauol.dto.vingadores.Vingadores;
import dev.valente.desafio_vagauol.dto.vingadores.VingadoresDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CodinomeVingadoresDataUtil implements CodinomeInterface {

    private static CodinomeVingadoresDataUtil instance;

    public static CodinomeVingadoresDataUtil getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CodinomeVingadoresDataUtil();
        }
        return instance;
    }

    private CodinomeVingadoresDataUtil() {
    }

    public static final Vingadores HULK =  Vingadores.builder()
            .codinome("Hulk").build();
    public static final Vingadores HOMEM_DE_FERRO =  Vingadores.builder()
            .codinome("Homem de ferro").build();
    public static final Vingadores CAPITAO_AMERICA =  Vingadores.builder()
            .codinome("Capitão América").build();

    public static final List<Vingadores> LIST_OF_VINGADORES =
            new ArrayList<>(List.of(HULK, HOMEM_DE_FERRO, CAPITAO_AMERICA));

    public static final List<String> LIST_OF_CODINOMES_VINGADORES =
            new ArrayList<>(List.of("Hulk", "Homem de ferro", "Capitão América"));

    public static final VingadoresDTO VINGADORES_DTO = VingadoresDTO.builder()
            .vingadores(LIST_OF_VINGADORES)
            .build();

    public static final String FIRST_CODINOME_OF_LIST_VINGADORES = LIST_OF_CODINOMES_VINGADORES.getFirst();

    @Override
    public List<String> getListOfCodinomes() {
        return LIST_OF_CODINOMES_VINGADORES;
    }

    @Override
    public String getFirstCodinome() {
        return FIRST_CODINOME_OF_LIST_VINGADORES;
    }
}

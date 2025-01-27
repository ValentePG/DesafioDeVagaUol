package dev.valente.desafio_vagauol.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CodinomeVingadoresDataUtil implements CodinomeInterface{

    public static final List<String> LIST_OF_CODINOMES_VINGADORES =
            new ArrayList<>(List.of("Hulk", "Homem de ferro", "Capitão América"));

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

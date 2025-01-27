package dev.valente.desafio_vagauol.utils;

import java.util.ArrayList;
import java.util.List;

public class CodinomeLigaDaJusticaDataUtil implements CodinomeInterface{
    public static final List<String> LIST_OF_CODINOMES_LIGA_DA_JUSTICA =
            new ArrayList<>(List.of("Batman", "Mulher Maravilha", "Flash"));

    public static final String FIRST_CODINOME_OF_LIST_LIGA_DA_JUSTICA = LIST_OF_CODINOMES_LIGA_DA_JUSTICA.getFirst();

    @Override
    public List<String> getListOfCodinomes() {
        return LIST_OF_CODINOMES_LIGA_DA_JUSTICA;
    }

    @Override
    public String getFirstCodinome() {
        return FIRST_CODINOME_OF_LIST_LIGA_DA_JUSTICA;
    }
}

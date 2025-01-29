package dev.valente.desafio_vagauol.utils;

import dev.valente.desafio_vagauol.dto.justiceleague.LigaDaJusticaDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CodinomeLigaDaJusticaDataUtil implements CodinomeInterface {

    private static CodinomeLigaDaJusticaDataUtil instance;

    public static CodinomeLigaDaJusticaDataUtil getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CodinomeLigaDaJusticaDataUtil();
        }
        return instance;
    }

    private CodinomeLigaDaJusticaDataUtil() {
    }

    public static final List<String> LIST_OF_CODINOMES_LIGA_DA_JUSTICA =
            new ArrayList<>(List.of("Batman", "Mulher Maravilha", "Flash"));

    public static final LigaDaJusticaDTO LIGA_DA_JUSTICA_DTO = LigaDaJusticaDTO.builder()
            .codinomes(LIST_OF_CODINOMES_LIGA_DA_JUSTICA)
            .build();

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

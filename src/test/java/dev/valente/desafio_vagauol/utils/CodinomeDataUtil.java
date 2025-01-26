package dev.valente.desafio_vagauol.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CodinomeDataUtil {

    public static final List<String> LIST_OF_CODINOMES =
            new ArrayList<>(List.of("Hulk", "Homem de ferro", "Capitão América"));

    public static final String FIRST_OF_LIST = "Hulk";

    public List<String> getListOfCodinomes(){
        return LIST_OF_CODINOMES;
    }

}

package dev.valente.desafio_vagauol.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CodinomeDataUtil {

    private final List<String> listOfCodinome = new ArrayList<>(List.of("Hulk", "Homem de ferro", "Capitão América"));

    public List<String> getListOfCodinomes(){
        return listOfCodinome;
    }

}

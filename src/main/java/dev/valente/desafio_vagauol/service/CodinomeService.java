package dev.valente.desafio_vagauol.service;


import dev.valente.desafio_vagauol.model.GrupoCodinome;
import dev.valente.desafio_vagauol.repository.codinome.CodinomesRepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodinomeService {

    private final CodinomesRepositoryFactory codinomesRepositoryFactory;

    public String gerarCodinome(GrupoCodinome groupCodinome, List<String> codinomesEmUso) throws Exception {
        var codinomesDispoiniveis = listarCodinomesDisponiveis(groupCodinome, codinomesEmUso);

        if (codinomesDispoiniveis.isEmpty()) {
            throw new Exception("Não há codinomes disponíveis para o grupo " + groupCodinome.getGroupName());
        }

        return sortearCodinome(codinomesDispoiniveis);
    }

    private List<String> listarCodinomesDisponiveis(GrupoCodinome grupoCodinome, List<String> codinomesEmUso) throws Exception {
        var codinomes = buscarCodinomes(grupoCodinome);

        return codinomes.stream()
                .filter(codinome -> !codinomesEmUso.contains(codinome))
                .toList();
    }

    private List<String> buscarCodinomes(GrupoCodinome grupoCodinome) throws Exception {
        var codinomes = codinomesRepositoryFactory.create(grupoCodinome);

        return codinomes.getCodinomes();
    }

    private String sortearCodinome(List<String> codinomesDisponiveis) throws Exception {
        return codinomesDisponiveis.stream().findAny().get();
    }
}

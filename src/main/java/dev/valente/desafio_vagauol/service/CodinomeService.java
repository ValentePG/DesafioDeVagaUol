package dev.valente.desafio_vagauol.service;


import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.exception.NotFoundException;
import dev.valente.desafio_vagauol.repository.codinome.CodinomesRepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodinomeService {

    private final CodinomesRepositoryFactory codinomesRepositoryFactory;

    public String gerarCodinome(GrupoCodinome grupoCodinome, List<String> codinomesEmUso) throws Exception {

        var codinomesDisponiveis = listarCodinomesDisponiveis(grupoCodinome, codinomesEmUso);

        assertListIsNotEmpty(codinomesDisponiveis, grupoCodinome);

        return sortearCodinome(codinomesDisponiveis);
    }

    private List<String> listarCodinomesDisponiveis(GrupoCodinome grupoCodinome, List<String> codinomesEmUso) throws Exception {
        var codinomes = buscarCodinomes(grupoCodinome);

        return codinomes.stream()
                .filter(codinome -> !codinomesEmUso.contains(codinome))
                .toList();
    }

    private List<String> buscarCodinomes(GrupoCodinome grupoCodinome) throws Exception {
        var codinomes = codinomesRepositoryFactory.create(grupoCodinome);

        return codinomes.buscarCodinomes();
    }

    private String sortearCodinome(List<String> codinomesDisponiveis) {
        return codinomesDisponiveis.stream().findAny().orElse("");
    }

    private void assertListIsNotEmpty(List<String> codinomesDisponiveis, GrupoCodinome grupoCodinome){
        if (codinomesDisponiveis.isEmpty()) throwNotFoundException(grupoCodinome);
    }

    private void throwNotFoundException(GrupoCodinome grupoCodinome) throws NotFoundException {
        throw new NotFoundException("Não há codinomes disponíveis para o grupo " + grupoCodinome.getGroupName());
    }
}

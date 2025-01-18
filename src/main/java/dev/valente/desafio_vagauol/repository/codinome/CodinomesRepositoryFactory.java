package dev.valente.desafio_vagauol.repository.codinome;

import dev.valente.desafio_vagauol.model.GrupoCodinome;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodinomesRepositoryFactory {

    private final JusticeLeagueRepository justiceLeagueRepository;
    private final VingadoresRepository vingadoresRepository;

    public CodinomesRepository create(GrupoCodinome grupoCodinome) {
        return switch (grupoCodinome) {
            case LIGA_DA_JUSTICA -> justiceLeagueRepository;
            case VINGADORES -> vingadoresRepository;
        };
    }

}

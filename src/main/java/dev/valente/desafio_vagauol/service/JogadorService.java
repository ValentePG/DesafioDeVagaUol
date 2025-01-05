package dev.valente.desafio_vagauol.service;

import dev.valente.desafio_vagauol.model.Jogador;
import dev.valente.desafio_vagauol.repository.jogador.JogadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JogadorService {

    private final JogadorRepository jogadoresRepository;
    private final CodinomeService codinomeService;

    public Jogador save(Jogador jogador) throws Exception {
        gerarCodinome(jogador);
        return jogador;
    }

    private void gerarCodinome(Jogador jogador) throws Exception {
        var codinomesUtilizados = jogadoresRepository.findAllCodinomes();
        var codinome = codinomeService.gerarCodinome(jogador.getGrupoCodinome(), codinomesUtilizados);
        jogador.setCodinome(codinome);
    }
}

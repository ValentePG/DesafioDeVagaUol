package dev.valente.desafio_vagauol.service;

import dev.valente.desafio_vagauol.model.Jogador;
import dev.valente.desafio_vagauol.repository.jogador.JogadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class JogadorService {

    private final JogadorRepository jogadoresRepository;
    private final CodinomeService codinomeService;

    public List<Jogador> getAllJogadores() {
        return jogadoresRepository.findAll();
    }

    public Jogador save(Jogador jogador) throws Exception {
        gerarCodinome(jogador);
        return jogadoresRepository.save(jogador);
    }

    private void gerarCodinome(Jogador jogador) throws Exception {
        var codinomesUtilizados = jogadoresRepository.findAllCodinomes();
        var codinome = codinomeService.gerarCodinome(jogador.getGrupoCodinome(), codinomesUtilizados);
        jogador.setCodinome(codinome);
    }
}

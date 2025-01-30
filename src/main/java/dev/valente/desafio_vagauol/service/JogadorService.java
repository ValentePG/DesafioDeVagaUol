package dev.valente.desafio_vagauol.service;

import dev.valente.desafio_vagauol.domain.Jogador;
import dev.valente.desafio_vagauol.exception.EmailAlreadyExist;
import dev.valente.desafio_vagauol.repository.jogador.JogadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JogadorService {

    private final JogadorRepository jogadoresRepository;
    private final CodinomeService codinomeService;

    public List<Jogador> buscarJogadores() {
        return jogadoresRepository.findAll();
    }

    public Jogador salvarJogador(Jogador jogador) throws Exception {
        assertEmailDoesNotExist(jogador);
        gerarCodinome(jogador);
        log.info("'{}'", jogador);
        return jogadoresRepository.save(jogador);
    }

    private void gerarCodinome(Jogador jogador) throws Exception {

        var codinomesUtilizados = jogadoresRepository.findAllCodinomes(jogador.getGrupoCodinome());
        var codinome = codinomeService.gerarCodinome(jogador.getGrupoCodinome(), codinomesUtilizados);
        jogador.setCodinome(codinome);
    }

    private void assertEmailDoesNotExist(Jogador jogador) {
        jogadoresRepository.findByEmail(jogador.getEmail())
                .ifPresent(this::throwEmailAlreadyExist);
    }

    private void throwEmailAlreadyExist(Jogador jogador) {
        throw new EmailAlreadyExist("Email já cadastrado no sistema");
    }
}

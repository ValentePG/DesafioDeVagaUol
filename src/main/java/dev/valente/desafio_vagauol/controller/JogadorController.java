package dev.valente.desafio_vagauol.controller;

import dev.valente.desafio_vagauol.dto.JogadorPostRequest;
import dev.valente.desafio_vagauol.model.Jogador;
import dev.valente.desafio_vagauol.service.JogadorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/jogadores")
@RequiredArgsConstructor
@Slf4j
public class JogadorController {

    private final JogadorService jogadorService;

    @GetMapping
    public ResponseEntity<List<Jogador>> getAllJogadores() {
        var list = jogadorService.getAllJogadores();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Jogador> saveJogador(@RequestBody JogadorPostRequest jogador) throws Exception {
        var novoJogador = new Jogador(jogador.nome(), jogador.email(), jogador.telefone(), jogador.grupocodinome());

        var novojogadorretorno = jogadorService.save(novoJogador);

        return ResponseEntity.ok(novojogadorretorno);
    }
}

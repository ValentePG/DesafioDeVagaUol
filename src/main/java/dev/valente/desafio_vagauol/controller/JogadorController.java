package dev.valente.desafio_vagauol.controller;

import dev.valente.desafio_vagauol.dto.jogador.JogadorGetResponse;
import dev.valente.desafio_vagauol.dto.jogador.JogadorPostRequest;
import dev.valente.desafio_vagauol.dto.jogador.JogadorPostResponse;
import dev.valente.desafio_vagauol.mapper.JogadorMapper;
import dev.valente.desafio_vagauol.service.JogadorService;
import jakarta.validation.Valid;
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
    private final JogadorMapper jogadorMapper;

    @GetMapping
    public ResponseEntity<List<JogadorGetResponse>> buscarJogadores() {
        log.info("Fazendo busca por todos os jogadores");

        var listOfPlayers = jogadorService.buscarJogadores().stream().map(jogadorMapper::toJogadorGetResponse).toList();
        return ResponseEntity.ok(listOfPlayers);

    }

    @PostMapping
    public ResponseEntity<JogadorPostResponse> criarJogador(@RequestBody @Valid JogadorPostRequest jogador) throws Exception {
        log.info("Salvando jogador '{}'", jogador);

        var novoJogador = jogadorMapper.toJogador(jogador);
        var novoJogadorSalvo = jogadorService.salvarJogador(novoJogador);
        var postResponse = jogadorMapper.toJogadorPostResponse(novoJogadorSalvo);

        return ResponseEntity.status(201).body(postResponse);
    }
}

package dev.valente.desafio_vagauol.controller;

import dev.valente.desafio_vagauol.dto.jogador.JogadorGetResponse;
import dev.valente.desafio_vagauol.dto.jogador.JogadorPostRequest;
import dev.valente.desafio_vagauol.dto.jogador.JogadorPostResponse;
import dev.valente.desafio_vagauol.exception.ApiError;
import dev.valente.desafio_vagauol.mapper.JogadorMapper;
import dev.valente.desafio_vagauol.service.JogadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
    @Operation(summary = "Busca Jogadores registrados no sistema",
            responses = {
                    @ApiResponse(description = "Retorna uma lista com os jogadores registrados no sistema",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = JogadorGetResponse.class)))),
            })
    public ResponseEntity<List<JogadorGetResponse>> buscarJogadores() {
        log.info("Fazendo busca por todos os jogadores");

        var listOfPlayers = jogadorService.buscarJogadores().stream().map(jogadorMapper::toJogadorGetResponse).toList();
        return ResponseEntity.ok(listOfPlayers);

    }

    @PostMapping
    @Operation(summary = "Cria jogador no sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Json correto para requisição ser aceita",
                    required = true,
                    content = @Content(schema = @Schema(implementation = JogadorPostRequest.class))),
            responses = {
                    @ApiResponse(description = "Cria jogador com um codinome gerado pelo sistema",
                            responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JogadorPostResponse.class))),
                    @ApiResponse(description = "Retorna EmailAlreadyExists quando o email já estiver registrado no sistema",
                            responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(description = "Retorna Bad Request quando email for vazio ou inválido e o nome for vazio",
                            responseCode = "4.0.0",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    public ResponseEntity<JogadorPostResponse> criarJogador(@RequestBody @Valid JogadorPostRequest jogador) throws Exception {
        log.info("Salvando jogador '{}'", jogador);

        var novoJogador = jogadorMapper.toJogador(jogador);
        var novoJogadorSalvo = jogadorService.salvarJogador(novoJogador);
        var postResponse = jogadorMapper.toJogadorPostResponse(novoJogadorSalvo);

        return ResponseEntity.status(201).body(postResponse);
    }
}

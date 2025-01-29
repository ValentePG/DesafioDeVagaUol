package dev.valente.desafio_vagauol.mapper;

import dev.valente.desafio_vagauol.domain.Jogador;
import dev.valente.desafio_vagauol.dto.jogador.JogadorGetResponse;
import dev.valente.desafio_vagauol.dto.jogador.JogadorPostRequest;
import dev.valente.desafio_vagauol.dto.jogador.JogadorPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JogadorMapper {

    JogadorGetResponse toJogadorGetResponse(Jogador jogador);

    Jogador toJogador(JogadorPostRequest jogadorPostRequest);

    JogadorPostResponse toJogadorPostResponse(Jogador jogador);
}

package dev.valente.desafio_vagauol.dto.vingadores;

import lombok.Builder;

import java.util.List;

@Builder
public record VingadoresDTO(List<Vingadores> vingadores) {
}

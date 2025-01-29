package dev.valente.desafio_vagauol.dto.justiceleague;

import lombok.Builder;

import java.util.List;

@Builder
public record LigaDaJusticaDTO(List<String> codinomes) {
}

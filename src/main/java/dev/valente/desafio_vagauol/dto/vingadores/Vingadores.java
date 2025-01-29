package dev.valente.desafio_vagauol.dto.vingadores;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@Builder
public class Vingadores {

    private String codinome;
}

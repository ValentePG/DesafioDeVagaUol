package dev.valente.desafio_vagauol.dto.vingadoresdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Vingadores {

    private String codinome;
}

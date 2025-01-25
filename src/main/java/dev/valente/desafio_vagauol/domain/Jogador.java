package dev.valente.desafio_vagauol.domain;

import jakarta.persistence.*;
import lombok.*;

import javax.print.attribute.standard.MediaSize;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_jogador")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;

    private String telefone;

    @Setter
    @Column(unique = true)
    private String codinome;

    @Enumerated(EnumType.STRING)
    @Setter
    private GrupoCodinome grupoCodinome;


}

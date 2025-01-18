package dev.valente.desafio_vagauol.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String email;

    private String telefone;

    @Setter
    @Column(unique = true)
    private String codinome;

    @Enumerated(EnumType.STRING)
    @Setter
    private GrupoCodinome grupoCodinome;

    public Jogador(String nome, String email, String telefone, GrupoCodinome grupoCodinome) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.grupoCodinome = grupoCodinome;
    }


}

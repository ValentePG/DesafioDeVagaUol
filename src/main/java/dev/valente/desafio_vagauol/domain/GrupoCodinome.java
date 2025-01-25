package dev.valente.desafio_vagauol.domain;

import lombok.Getter;

@Getter
public enum GrupoCodinome {

    LIGA_DA_JUSTICA("Liga da Justiça"), VINGADORES("Vingadores");

    private final String groupName;

    GrupoCodinome(String groupName) {
        this.groupName = groupName;
    }

}

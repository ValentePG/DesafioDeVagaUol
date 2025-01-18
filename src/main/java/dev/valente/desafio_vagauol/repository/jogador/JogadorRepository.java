package dev.valente.desafio_vagauol.repository.jogador;

import dev.valente.desafio_vagauol.model.GrupoCodinome;
import dev.valente.desafio_vagauol.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {

    @Query("SELECT jg.codinome FROM Jogador jg WHERE jg.grupoCodinome = ?1")
    List<String> findAllCodinomes(GrupoCodinome grupoCodinome);

}

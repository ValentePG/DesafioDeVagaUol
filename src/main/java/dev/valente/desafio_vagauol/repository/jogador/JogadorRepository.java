package dev.valente.desafio_vagauol.repository.jogador;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import dev.valente.desafio_vagauol.domain.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {

    @Query("SELECT jg.codinome FROM Jogador jg WHERE jg.grupoCodinome = ?1")
    List<String> findAllCodinomes(GrupoCodinome grupoCodinome);

    @Query("SELECT jg FROM Jogador jg WHERE jg.email = ?1")
    Optional<Jogador> findByEmail(String email);
}

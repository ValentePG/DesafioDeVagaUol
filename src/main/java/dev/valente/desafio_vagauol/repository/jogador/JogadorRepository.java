package dev.valente.desafio_vagauol.repository.jogador;

import dev.valente.desafio_vagauol.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {

//    @Query(value = "SELECT CODINOME FROM JOGADOR", nativeQuery = true)
//    List<String> findAllCodinomes();

    @Query("SELECT jg.codinome FROM Jogador jg")
    List<String> findAllCodinomes();
}

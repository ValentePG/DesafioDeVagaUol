package dev.valente.desafio_vagauol.repository.api;

import dev.valente.desafio_vagauol.domain.GrupoCodinome;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CodinomesRepositoryFactoryTest {

    @Mock
    private LigaDaJusticaRepository ligaDaJusticaRepository;

    @Mock
    private VingadoresRepository vingadoresRepository;

    @InjectMocks
    private CodinomesRepositoryFactory codinomesRepositoryFactory;

    @BeforeEach
    void setUp() {
        codinomesRepositoryFactory = new CodinomesRepositoryFactory(ligaDaJusticaRepository, vingadoresRepository);
    }

    @Test
    @DisplayName("create deve retornar uma instância de ligaDaJusticaRepository")
    @Order(1)
    void create_ShouldReturnInstanceOfLigaDaJusticaRepository() {
        CodinomesRepository codinomesRepository = codinomesRepositoryFactory.create(GrupoCodinome.LIGA_DA_JUSTICA);

        Assertions.assertThat(codinomesRepository)
                .isInstanceOf(LigaDaJusticaRepository.class);
    }

    @Test
    @DisplayName("create deve retornar uma instância de VingadoresRepository")
    @Order(2)
    void create_ShouldReturnInstanceOfVingadoresRepository() {
        CodinomesRepository codinomesRepository = codinomesRepositoryFactory.create(GrupoCodinome.VINGADORES);

        Assertions.assertThat(codinomesRepository)
                .isInstanceOf(VingadoresRepository.class);
    }
}
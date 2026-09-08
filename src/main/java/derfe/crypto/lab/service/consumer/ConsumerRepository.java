package derfe.crypto.lab.service.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositorio encargado del acceso a datos de los consumidores.
// Spring Data JPA genera automáticamente la implementación.
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    // Busca un consumidor utilizando su nombre único.
    Optional<Consumer> findByName(String name);

}
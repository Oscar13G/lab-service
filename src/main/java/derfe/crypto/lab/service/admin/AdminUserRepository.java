package derfe.crypto.lab.service.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio encargado del acceso a datos de los usuarios administrativos.
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    // Busca un usuario administrativo por su username.
    Optional<AdminUser> findByUsername(String username);
}
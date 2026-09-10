package derfe.crypto.lab.service.admin;

import java.util.Optional;

import org.springframework.stereotype.Service;

// Contiene la lógica relacionada con usuarios administrativos.
@Service
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    // Spring inyecta automáticamente el repositorio.
    public AdminUserService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    // Busca un usuario administrativo por su username.
    public Optional<AdminUser> findByUsername(String username) {
        return adminUserRepository.findByUsername(username);
    }

    // Guarda un usuario administrativo en PostgreSQL.
    public AdminUser save(AdminUser adminUser) {
        return adminUserRepository.save(adminUser);
    }
}
package derfe.crypto.lab.service.admin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Crea el usuario ADMIN inicial si todavía no existe.
@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final AdminUserService adminUserService;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(
            AdminUserService adminUserService,
            PasswordEncoder passwordEncoder) {

        this.adminUserService = adminUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        String username = System.getenv("ADMIN_USERNAME");
        String password = System.getenv("ADMIN_PASSWORD");

        // Si no existen las variables, no se crea ningún usuario.
        if (username == null || password == null) {
            return;
        }

        // Evita crear el mismo usuario más de una vez.
        if (adminUserService.findByUsername(username).isPresent()) {
            return;
        }

        String passwordHash = passwordEncoder.encode(password);

        AdminUser adminUser = new AdminUser(
                username,
                passwordHash,
                AdminRole.ADMIN
        );

        adminUserService.save(adminUser);
    }
}
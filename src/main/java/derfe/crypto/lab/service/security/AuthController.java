package derfe.crypto.lab.service.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// Expone el endpoint de autenticación del Admin Service.
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthController(
    AuthenticationManager authenticationManager,
      JwtService jwtService) {

    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse login(@RequestBody LoginRequest request) {

    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  request.getUsername(),
                  request.getPassword()
              )
          );

      String role = authentication.getAuthorities()
          .iterator()
          .next()
          .getAuthority()
          .replace("ROLE_", "");

      String token = jwtService.generateToken(
          authentication.getName(),
          role
      );

      return new LoginResponse(
          authentication.getName(),
          role,
          "Login correcto",
          token
      );

    } catch (AuthenticationException exception) {

      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED,
          "Usuario o contraseña incorrectos"
      );
    }
  }
}
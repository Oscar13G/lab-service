package derfe.crypto.lab.service.security;

// Respuesta devuelta cuando el login es correcto.
public class LoginResponse {

  private final String username;
  private final String role;
  private final String message;
  private final String token;

  public LoginResponse(
      String username,
      String role,
      String message,
      String token) {

    this.username = username;
    this.role = role;
    this.message = message;
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public String getRole() {
    return role;
  }

  public String getMessage() {
    return message;
  }

  public String getToken() {
    return token;
  }
}
package derfe.crypto.lab.service.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Service
public class JwtService {

  private final JwtEncoder jwtEncoder;
  private final long expirationMinutes;

  public JwtService(
      @Value("${security.jwt.secret}") String secret,
      @Value("${security.jwt.expiration-minutes}") long expirationMinutes) {

    byte[] secretBytes = Base64.getDecoder().decode(secret);

    SecretKey secretKey = new SecretKeySpec(
        secretBytes,
        "HmacSHA256"
    );

    this.jwtEncoder = new NimbusJwtEncoder(
        new ImmutableSecret<>(secretKey)
    );

    this.expirationMinutes = expirationMinutes;
  }

  public String generateToken(String username, String role) {

    Instant now = Instant.now();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .subject(username)
        .issuedAt(now)
        .expiresAt(now.plus(expirationMinutes, ChronoUnit.MINUTES))
        .claim("role", role)
        .build();

    JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

    JwtEncoderParameters parameters =
        JwtEncoderParameters.from(
            jwsHeader,
            claims
        );

    return jwtEncoder.encode(parameters).getTokenValue();
  }
}
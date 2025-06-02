package Dutkercz.com.github.project_login_app.services;

import Dutkercz.com.github.project_login_app.entities.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

  @Value("${alg.key}")
  private String algKey;

  @Value("${issuer}")
  private String issuer;

  public String jwtCreate(Usuario usuario){
    try {
      Algorithm algorithm = Algorithm.HMAC256(algKey);
      return JWT.create()
        .withIssuer(issuer)
        .withSubject(usuario.getUsername())
        .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
        .sign(algorithm);
    } catch (IllegalArgumentException | JWTCreationException e){
      throw new JWTCreationException(e.getMessage(), e);
    }
  }

  public String getSubject(String token){
    try {
      Algorithm algorithm = Algorithm.HMAC256(algKey);
      return JWT.require(algorithm)
        .withIssuer(issuer)
        .build()
        .verify(token)
        .getSubject();
    } catch (IllegalArgumentException | JWTVerificationException e) {
      throw new JWTVerificationException(e.getMessage(), e);
    }
  }

}


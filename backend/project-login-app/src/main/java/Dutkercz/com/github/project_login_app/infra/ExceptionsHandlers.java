package Dutkercz.com.github.project_login_app.infra;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandlers {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<? > entityNotFound(EntityNotFoundException e){
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> methodArgsNotValid(MethodArgumentNotValidException e){
    List<FieldError> error = e.getFieldErrors();
    return ResponseEntity.badRequest()
      .body(error.stream().map(x -> new ErrorMapping(x.getField(), x.getDefaultMessage())));
  }

  @ExceptionHandler(JWTCreationException.class)
  public ResponseEntity<?> jwtCreationException(JWTCreationException e){
    return ResponseEntity.badRequest().body("Erro ao criar o token " + e.getMessage());
  }

  @ExceptionHandler(JWTVerificationException.class)
  public ResponseEntity<?> jwtVerificationException(JWTVerificationException e){
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido " + e.getMessage());
  }



}

package Dutkercz.com.github.project_login_app.infra;

import org.springframework.validation.FieldError;

/* Mapeia erro que contenham field e mensagem como MethodsArgsNotValid
Map errors that contain field and message, like MethodsArgsNotValid */

public record ErrorMapping(String field, String message) {
  public ErrorMapping(FieldError e){
    this(e.getField(), e.getDefaultMessage());
  }
}

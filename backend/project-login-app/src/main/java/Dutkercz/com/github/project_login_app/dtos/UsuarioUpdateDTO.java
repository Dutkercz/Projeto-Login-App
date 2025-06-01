package Dutkercz.com.github.project_login_app.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioUpdateDTO(
        @NotBlank(message = "O campo email não pode estar em branco.")
        @Email(message = "Nâo é um email valido.")
        String email,
        @NotBlank(message = "O campo password não pode estar em branco.")
        @Pattern(regexp = "^[a-zA-Z0-9]{6,}$", message = "O campo senha deve conter no mínimo 6 letras e números.")
        String password
        ) {
}

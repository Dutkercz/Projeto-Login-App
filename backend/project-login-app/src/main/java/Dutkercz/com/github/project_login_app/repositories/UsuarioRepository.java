package Dutkercz.com.github.project_login_app.repositories;

import Dutkercz.com.github.project_login_app.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<UserDetails> findByEmail(String username);
}

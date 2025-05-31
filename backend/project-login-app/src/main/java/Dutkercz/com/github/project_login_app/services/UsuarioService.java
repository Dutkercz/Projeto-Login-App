package Dutkercz.com.github.project_login_app.services;

import Dutkercz.com.github.project_login_app.dtos.UsuarioRequestDTO;
import Dutkercz.com.github.project_login_app.entities.Usuario;
import Dutkercz.com.github.project_login_app.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.getByEmail(username);
    }

    @Transactional
    public Usuario saveNewUsuario(@Valid UsuarioRequestDTO requestDTO) {
        Usuario usuario = new Usuario(requestDTO);
        return usuarioRepository.save(usuario);
    }
}

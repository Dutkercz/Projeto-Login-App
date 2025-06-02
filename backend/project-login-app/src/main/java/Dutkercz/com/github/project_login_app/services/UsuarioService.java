package Dutkercz.com.github.project_login_app.services;

import Dutkercz.com.github.project_login_app.dtos.UsuarioRequestDTO;
import Dutkercz.com.github.project_login_app.dtos.UsuarioUpdateDTO;
import Dutkercz.com.github.project_login_app.entities.Usuario;
import Dutkercz.com.github.project_login_app.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
      this.usuarioRepository = usuarioRepository;
      this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
          .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }

    @Transactional
    public Usuario saveNewUsuario(@Valid UsuarioRequestDTO requestDTO) {
        String pwEncoded = encoder.encode(requestDTO.password());
        Usuario usuario = new Usuario(requestDTO, pwEncoded);
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
      return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Transactional
    public Usuario updateUsuario(Long id, @Valid UsuarioUpdateDTO updateDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
        usuario.update(updateDTO);
        return usuario;
    }
}

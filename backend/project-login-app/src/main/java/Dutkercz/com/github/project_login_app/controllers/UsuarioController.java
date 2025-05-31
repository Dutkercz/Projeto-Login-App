package Dutkercz.com.github.project_login_app.controllers;

import Dutkercz.com.github.project_login_app.dtos.UsuarioRequestDTO;
import Dutkercz.com.github.project_login_app.dtos.UsuarioResponseDTO;
import Dutkercz.com.github.project_login_app.entities.Usuario;
import Dutkercz.com.github.project_login_app.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody UsuarioRequestDTO requestDTO, UriComponentsBuilder builder){
      Usuario usuario = usuarioService.saveNewUsuario(requestDTO);
      URI uri = builder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
      return ResponseEntity.created(uri).body(new UsuarioResponseDTO(usuario));
    }
}

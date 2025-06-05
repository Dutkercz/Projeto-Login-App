package Dutkercz.com.github.project_login_app.controllers;

import Dutkercz.com.github.project_login_app.dtos.UsuarioRequestDTO;
import Dutkercz.com.github.project_login_app.dtos.UsuarioResponseDTO;
import Dutkercz.com.github.project_login_app.dtos.UsuarioUpdateDTO;
import Dutkercz.com.github.project_login_app.entities.Usuario;
import Dutkercz.com.github.project_login_app.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
      System.out.println(usuario.getPassword() + usuario);
      URI uri = builder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
      return ResponseEntity.created(uri).body(new UsuarioResponseDTO(usuario));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> detalhes(@PathVariable Long id){
      Usuario usuario = usuarioService.findById(id);
      return ResponseEntity.ok().body(new UsuarioResponseDTO(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> editar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO updateDTO){
      Usuario usuario = usuarioService.updateUsuario(id, updateDTO);
      return ResponseEntity.ok().body(new UsuarioResponseDTO(usuario));
    }
}

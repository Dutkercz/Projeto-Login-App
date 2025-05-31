package Dutkercz.com.github.project_login_app.dtos;

import Dutkercz.com.github.project_login_app.entities.Usuario;

public record UsuarioResponseDTO(Long id, String name, String role) {

  public UsuarioResponseDTO(Usuario usuario) {
    this(usuario.getId(), usuario.getName(), String.valueOf(usuario.getRoles()).substring(5));
  }
}

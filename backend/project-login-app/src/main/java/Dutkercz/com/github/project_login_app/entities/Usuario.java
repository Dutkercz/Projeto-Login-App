package Dutkercz.com.github.project_login_app.entities;

import Dutkercz.com.github.project_login_app.dtos.UsuarioRequestDTO;
import Dutkercz.com.github.project_login_app.dtos.UsuarioUpdateDTO;
import Dutkercz.com.github.project_login_app.entities.enums.UserRoles;
import Dutkercz.com.github.project_login_app.entities.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles roles;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public Usuario(){}

    public Usuario(Long id, String name, String email, String password, UserRoles roles, UserStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.status = status;
    }

  public Usuario(@Valid UsuarioRequestDTO requestDTO, String pwEncoded) {
      this.id = null;
      this.name = requestDTO.name();
      this.email = requestDTO.email();
      this.password = pwEncoded;
      this.roles = UserRoles.ROLE_USUARIO;
      this.status = UserStatus.ACTIVE;
  }

  public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserRoles getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void update(@Valid UsuarioUpdateDTO updateDTO) {
       this.email = updateDTO.email();
       this.password = updateDTO.password();
    }
}

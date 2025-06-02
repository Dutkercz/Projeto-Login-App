package Dutkercz.com.github.project_login_app.controllers;

import Dutkercz.com.github.project_login_app.dtos.UsuarioLoginDTO;
import Dutkercz.com.github.project_login_app.dtos.UsuarioRequestDTO;
import Dutkercz.com.github.project_login_app.entities.Usuario;
import Dutkercz.com.github.project_login_app.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;


  public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping
  public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO loginDTO) {
    UsernamePasswordAuthenticationToken authToken =
      new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
    Authentication authentication = authenticationManager.authenticate(authToken);
    String tokenJWT = jwtService.jwtCreate((Usuario) authentication.getPrincipal());
    return ResponseEntity.ok().body(tokenJWT);
  }
}

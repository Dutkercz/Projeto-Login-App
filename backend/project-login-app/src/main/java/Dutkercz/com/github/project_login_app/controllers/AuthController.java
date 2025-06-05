package Dutkercz.com.github.project_login_app.controllers;

import Dutkercz.com.github.project_login_app.dtos.UsuarioLoginDTO;
import Dutkercz.com.github.project_login_app.dtos.UsuarioRequestDTO;
import Dutkercz.com.github.project_login_app.entities.Usuario;
import Dutkercz.com.github.project_login_app.services.JwtService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;


  public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  //Página de login
  @PostMapping
  public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO loginDTO, HttpServletResponse response) {
    UsernamePasswordAuthenticationToken authToken =
      new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
    System.out.println(loginDTO.username() + " " + loginDTO.password());
    Authentication authentication = authenticationManager.authenticate(authToken);
    String tokenJWT = jwtService.jwtCreate((Usuario) authentication.getPrincipal());

    //Experimentando o envio de jwt em cookie
    ResponseCookie cookie = ResponseCookie.from("jwt", tokenJWT)//"jwt" é como vai ser identificado no navegador
      .httpOnly(true)//Torna o cookie inacessível via JS.
      .secure(false) // usar false em desenv. e true em prod.
      .path("/") //deixa o cookie disponivel para todas as rotas da aplicação
      .sameSite("Strict")
      .maxAge(Duration.ofHours(1))//duração do cookie
      .build();//finalizando a construição do cookie

    //adicionar o cookie no response
    System.out.println("Bolacha -> " + cookie);
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());


    return ResponseEntity.ok().build();
  }
}

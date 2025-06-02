package Dutkercz.com.github.project_login_app.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain (HttpSecurity security){
    try{
      return security.csrf(x -> x.disable())
        .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(x -> {
          x.anyRequest().permitAll();
        }).build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Bean
  public PasswordEncoder encode(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
    try{
      return configuration.getAuthenticationManager();
    } catch (Exception e) {
      throw new RuntimeException("[Erro]" + e.getMessage());
    }

  }
}

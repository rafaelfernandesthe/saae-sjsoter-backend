package com.saae.backend.config;

import com.saae.backend.security.JwtAuthenticationEntryPoint;
import com.saae.backend.security.JwtRequestFilter;
import com.saae.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;  // Usando o serviço para carregar o usuário

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // Configuração do SecurityFilterChain para Spring Security 5+ e superior
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/auth/**").permitAll()  // Permite acessar sem autenticação
                    .anyRequest().authenticated()  // Exige autenticação para qualquer outra requisição
            )
            .exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint) // Ponto de entrada para erros de autenticação
            )
            .addFilterBefore(jwtRequestFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class); // Filtro JWT antes da autenticação

        return http.build();  // Retorna o filtro de segurança configurado
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usando BCrypt para criptografar senhas
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (UserDetailsService) usuarioService;  // Usando o UsuarioService como o UserDetailsService
    }
}

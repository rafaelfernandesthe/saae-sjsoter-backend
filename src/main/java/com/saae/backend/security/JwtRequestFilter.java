package com.saae.backend.security;

import com.saae.backend.entities.Usuario;
import com.saae.backend.services.UsuarioService;
import com.saae.backend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Extrair o cabeçalho de autorização
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Verificar se o cabeçalho contém o token JWT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Remover o "Bearer "
            username = jwtUtil.extrairEmail(jwt);  // Extrair o email (username) do token
        }

        // Se o nome de usuário (email) foi extraído e o contexto de segurança ainda não contém autenticação
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Buscar o usuário no banco de dados
            Optional<Usuario> usuarioOptional = usuarioService.obterUsuarioPorEmail(username);

            if (usuarioOptional.isPresent()) {
                var usuario = usuarioOptional.get();
                
                // Validar o token JWT
                if (jwtUtil.validarToken(jwt, usuario)) {
                    // Criar a autenticação com base no usuário autenticado
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Definir a autenticação no contexto de segurança
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // Continuar a cadeia de filtros
        chain.doFilter(request, response);
    }
}

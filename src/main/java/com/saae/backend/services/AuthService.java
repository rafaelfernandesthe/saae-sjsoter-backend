package com.saae.backend.services;

import com.saae.backend.entities.Usuario;
import com.saae.backend.repositories.UsuarioRepository;
import com.saae.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder encoder;  // Instância do encoder injetada

    // Registrar novo usuário
    public String registrarUsuario(Usuario usuario) {
        // Criptografar a senha
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuario = usuarioRepository.save(usuario);
        return jwtUtil.gerarToken(usuario);
    }

    // Autenticar e gerar JWT
    public String autenticarUsuario(String email, String senha) {
        // Buscar o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas")); // Lança exceção se não encontrar o usuário

        // Verificar se a senha corresponde à senha armazenada
        if (encoder.matches(senha, usuario.getSenha())) {
            // Gerar o token JWT se as credenciais forem válidas
            return jwtUtil.gerarToken(usuario);
        }

        // Lança exceção se as credenciais não coincidirem
        throw new BadCredentialsException("Credenciais inválidas");
    }
}

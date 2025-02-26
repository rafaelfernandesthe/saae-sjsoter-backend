package com.saae.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.saae.backend.entities.Usuario;
import com.saae.backend.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Implementação do método loadUserByUsername da interface UserDetailsService
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        // Retorna um UserDetails com as credenciais do usuário
        return new User(usuario.getEmail(), usuario.getSenha(), List.of());
    }
    
    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obter um usuário por ID
    public Optional<Usuario> obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Obter um usuário por email (para autenticação)
    public Optional<Usuario> obterUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Criar um novo usuário
    public Usuario criarUsuario(Usuario usuario) {
        // Criptografar a senha antes de salvar
        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    // Atualizar um usuário existente
    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            // Se a senha for informada, criptografar antes de salvar
            if (usuario.getSenha() != null) {
                usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
            }
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    // Deletar um usuário
    public boolean deletarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

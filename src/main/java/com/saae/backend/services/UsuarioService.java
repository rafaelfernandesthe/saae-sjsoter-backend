package com.saae.backend.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saae.backend.entities.Usuario;
import com.saae.backend.repositories.UsuarioRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class UsuarioService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// Implementação do método loadUserByUsername da interface UserDetailsService
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

		// Retorna um UserDetails com as credenciais do usuário
		return new User(usuario.getEmail(), usuario.getSenha(), List.of(new SimpleGrantedAuthority(usuario.getTipo().name())));
	}

	// Listar todos os usuários
	@Cacheable(value = "usuariosPaginados", key = "T(String).valueOf(#pageable.pageNumber) + '-' + T(String).valueOf(#pageable.pageSize) + '-' + " + "T(String).valueOf(#nome ?: '')")
	public Page<Usuario> listar(Pageable pageable, String nome) {
		return usuarioRepository.findAll((root, query, criteriaBuilder) -> {
			var predicates = new ArrayList<Predicate>();
			if (StringUtils.hasText(nome)) {
				predicates.add(criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		}, pageable);
	}

	// Obter um usuário por ID
	public Optional<Usuario> obterPorId(Long id) {
		return usuarioRepository.findById(id);
	}

	// Obter um usuário por email (para autenticação)
	public Optional<Usuario> obterPorEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	// Criar um novo usuário
	public Usuario criar(Usuario usuario) {
		usuario.setAtivo(true);
		usuario.setDataCriacao(LocalDateTime.now());
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}

	// Atualizar um usuário existente
	public Usuario atualizar(Long id, Usuario usuario) {
		if (usuarioRepository.existsById(id)) {
			usuario.setId(id);
			usuarioRepository.findById(id).ifPresent(existingUsuario -> {
				usuario.setDataCriacao(existingUsuario.getDataCriacao());
				usuario.setDataUltimoLogin(existingUsuario.getDataUltimoLogin());
				usuario.setSenha(existingUsuario.getSenha());
			});
			return usuarioRepository.save(usuario);
		}
		return null;
	}

	// Deletar um usuário
	public boolean deletar(Long id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	@CacheEvict(value = "usuariosPaginados", allEntries = true)
	public void limparCache() {
	    
	}
}

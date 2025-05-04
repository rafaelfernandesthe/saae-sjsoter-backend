package com.saae.backend.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.saae.backend.entities.enums.TipoUsuario;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = -6690265995004458981L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome não pode ser vazio")
	private String nome;
	
	@NotBlank(message = "CPF não pode ser vazio")
	private String cpf;
	
	@NotBlank(message = "Telefone não pode ser vazio")
	private String telefone;

	@Email(message = "Email inválido")
	@NotBlank(message = "Email não pode ser vazio")
	private String email;

	@NotBlank(message = "Senha não pode ser vazia")
	@Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
	private String senha; // A senha será criptografada antes de ser salva

	@Enumerated(EnumType.STRING)
	private TipoUsuario tipo;

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dataUltimoLogin;

	private boolean ativo;
	
	@PrePersist
	public void prePersist() {
		if (dataCriacao == null) {
			dataCriacao = LocalDateTime.now(); 
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + tipo.name()));
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // O usuário não expira
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // O usuário não é bloqueado
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // As credenciais não expiram
	}

	@Override
	public boolean isEnabled() {
		return true; // O usuário está habilitado
	}
	
}

package com.saae.backend.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = -6690265995004458981L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome não pode ser vazio")
	private String nome;

	@Email(message = "Email inválido")
	@NotBlank(message = "Email não pode ser vazio")
	private String email;

	@NotBlank(message = "Senha não pode ser vazia")
	@Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
	private String senha; // A senha será criptografada antes de ser salva

	@Enumerated(EnumType.STRING)
	private TipoUsuario tipo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;

	private boolean ativo;
	
	public Usuario() {
		// Construtor padrão
	}

	@PrePersist
	public void prePersist() {
		if (dataCriacao == null) {
			dataCriacao = new Date(); // Define a data de criação antes de persistir
		}
	}

	// Implementação do método getAuthorities() da interface UserDetails
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Retorna as permissões do usuário. Aqui, um exemplo simples com um único
		// papel.
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
	


	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}

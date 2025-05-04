package com.saae.backend.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.saae.backend.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}") // Carrega o segredo do arquivo de configuração
	private String SECRET_KEY; // Use um segredo mais seguro em produção

	@Value("${jwt.expiration}") // Carrega o tempo de expiração do token
	private long EXPIRATION_TIME; // Tempo de expiração configurado (ex: 3600000ms = 1 hora)

	// Gerar a chave do tokem
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Gera um token JWT para o usuário
	public String gerarToken(Usuario usuario) {
		return Jwts.builder().subject(usuario.getEmail()) // O email do usuário é o "subject" do token
				.issuedAt(new Date()) // Data de emissão
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiração configurada
				.signWith(getSigningKey()) // Algoritmo e chave secreta
				.compact();
	}

	// Extrai o email do usuário do token JWT
	public String extrairEmail(String token) {
		return extrairClaims(token).getSubject(); // Retorna o "subject", que no caso é o email do usuário
	}

	// Verifica se o token é válido
	public boolean validarToken(String token, UserDetails usuario) {
		final String email = extrairEmail(token);
		return (email.equals(usuario.getUsername()) && !isTokenExpirado(token));
	}

	// Extrai as claims (informações) do token
	private Claims extrairClaims(String token) {
		try {
			// Uso correto do Jwts.parser() com a chave secreta
			return Jwts.parser().verifyWith((SecretKey) getSigningKey()) // A chave secreta usada para assinar o token
					.build().parseSignedClaims(token) // Parse do token JWT
					.getPayload(); // Retorna as claims do token
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("Token expirado", e); // Lança exceção adequada para token expirado
		} catch (Exception e) {
			throw new RuntimeException("Token inválido ou malformado", e); // Lançar exceção genérica para erro geral
		}
	}

	// Verifica se o token está expirado
	private boolean isTokenExpirado(String token) {
		return extrairClaims(token).getExpiration().before(new Date());
	}
}

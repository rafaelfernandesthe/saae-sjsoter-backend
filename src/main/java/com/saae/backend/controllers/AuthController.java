package com.saae.backend.controllers;

import com.saae.backend.entities.Usuario;
import com.saae.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Registrar novo usuário
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        String token = authService.registrarUsuario(usuario);
        return ResponseEntity.ok("Usuário registrado com sucesso. Token: " + token);
    }

    // Login e gerar token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
        String token = authService.autenticarUsuario(email, senha);
        if (token != null) {
            return ResponseEntity.ok("Token JWT: " + token);
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}

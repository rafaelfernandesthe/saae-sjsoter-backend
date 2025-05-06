package com.saae.backend.controllers;

import com.saae.backend.entities.Usuario;
import com.saae.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        String token = authService.autenticarUsuario(usuario.getEmail(), usuario.getSenha());
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}

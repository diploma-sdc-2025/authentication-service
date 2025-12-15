package org.java.diploma.service.authservice.controller;

import jakarta.validation.Valid;
import org.java.diploma.service.authservice.dto.*;
import org.java.diploma.service.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        auth.register(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(auth.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest req) {
        return ResponseEntity.ok(auth.refresh(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshRequest req) {
        auth.logout(req.refreshToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/forgot")
    public ResponseEntity<?> forgot(@Valid @RequestBody ForgotPasswordRequest req) {
        auth.createPasswordResetToken(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> reset(@Valid @RequestBody ResetPasswordRequest req) {
        auth.resetPassword(req);
        return ResponseEntity.ok().build();
    }
}

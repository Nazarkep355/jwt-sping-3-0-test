package com.example.jwtsping30test.controller;

import com.example.jwtsping30test.entity.AuthenticationResponse;
import com.example.jwtsping30test.entity.LoginRequest;
import com.example.jwtsping30test.entity.RegisterRequest;
import com.example.jwtsping30test.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>
    register( @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>
    login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}

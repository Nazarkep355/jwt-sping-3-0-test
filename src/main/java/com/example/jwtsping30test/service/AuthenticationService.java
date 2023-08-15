package com.example.jwtsping30test.service;

import com.example.jwtsping30test.config.JwtService;
import com.example.jwtsping30test.entity.AuthenticationResponse;
import com.example.jwtsping30test.entity.LoginRequest;
import com.example.jwtsping30test.entity.RegisterRequest;
import com.example.jwtsping30test.repo.UserRepository;
import com.example.jwtsping30test.user.Role;
import com.example.jwtsping30test.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                 .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return AuthenticationResponse.builder()
                    .token("User exists").build();
        }
        userRepository.save(user);
        String token= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token).build();
    }
    public AuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token).build();
    }
}

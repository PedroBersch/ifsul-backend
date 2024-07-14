package br.com.example.trabalhobackend.controller;

import br.com.example.trabalhobackend.model.AuthenticationRequest;
import br.com.example.trabalhobackend.model.LoginResponse;
import br.com.example.trabalhobackend.model.RegisterRequest;
import br.com.example.trabalhobackend.model.User;
import br.com.example.trabalhobackend.model.response.MessageResponse;
import br.com.example.trabalhobackend.repository.UserRepository;
import br.com.example.trabalhobackend.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "User login", description = "Autoriza usuario e retorna token", security = {})
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        LoginResponse loginResponse = new LoginResponse(token);
        return ResponseEntity.ok(loginResponse);
    }
    @Operation(summary = "Registra usuario", description = "Registra usuario e retorna mensagem", security = {})
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()) != null)
            return ResponseEntity.badRequest().build();

        String passwordEncoded = new BCryptPasswordEncoder().encode(registerRequest.getPassword());
        User newUser = new User(registerRequest.getEmail(),passwordEncoded,registerRequest.getRole());

        userRepository.save(newUser);

        return new ResponseEntity<>(new MessageResponse("Usuario registrado com sucesso"), HttpStatus.CREATED);
    }

}

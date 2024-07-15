package br.com.example.trabalhobackend.controller;

import br.com.example.trabalhobackend.model.AuthenticationRequest;
import br.com.example.trabalhobackend.model.LoginResponse;
import br.com.example.trabalhobackend.model.RegisterRequest;
import br.com.example.trabalhobackend.model.User;
import br.com.example.trabalhobackend.model.response.MessageResponse;
import br.com.example.trabalhobackend.repository.UserRepository;
import br.com.example.trabalhobackend.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Tag(name = "Usuario")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Operation(summary = "Realiza login", description = "Autoriza usuario e retorna token.")
    @SecurityRequirements(value = {})
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationRequest data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        LoginResponse loginResponse = new LoginResponse(token);
        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "Registra usuario", description = "Registra usuario e retorna mensagem")

    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Cadastro com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MessageResponse.class),
                examples = @ExampleObject(value = "{\"message\": \"Usuario registrado com sucesso\"}"))),
    })
    @SecurityRequirements(value = {})
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()) != null)
            return ResponseEntity.badRequest().build();

        String passwordEncoded = new BCryptPasswordEncoder().encode(registerRequest.getPassword());
        User newUser = new User(registerRequest.getEmail(), passwordEncoded, registerRequest.getRole());

        userRepository.save(newUser);

        return new ResponseEntity<>(new MessageResponse("Usuario registrado com sucesso"), HttpStatus.CREATED);
    }
}

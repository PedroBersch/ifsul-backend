package br.com.example.trabalhobackend.controller;

import br.com.example.trabalhobackend.model.ForgetPasswordRequest;
import br.com.example.trabalhobackend.model.ResetPasswordRequest;
import br.com.example.trabalhobackend.model.ResetPasswordResponse;
import br.com.example.trabalhobackend.model.User;
import br.com.example.trabalhobackend.model.response.MessageResponse;
import br.com.example.trabalhobackend.repository.UserRepository;
import br.com.example.trabalhobackend.service.EmailSenderService;
import br.com.example.trabalhobackend.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Gerenciamento de senhas")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordResetService passwordResetService;
    private final EmailSenderService senderService;
    private final UserRepository userRepository;

    @Operation(
        summary = "Esqueci minha senha",
        description = "Gera um token de redefinição de senha para o usuário",
        tags = {"Gerenciamento de senhas"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token de redefinição de senha gerado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ResetPasswordResponse.class),
                examples = @ExampleObject(value = """
                    {
                    "message": "Senha redefinida com sucesso",
                    "token": "TOKEN"
                    }
                    """))),
        @ApiResponse(
            responseCode = "400",
            description = "Usuario não encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MessageResponse.class),
                examples = @ExampleObject(value = "{\"message\": \"Usuario não encontrado\"}")))
    })
    @SecurityRequirements(value = {})
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgetPasswordRequest email) {
        User user = (User) userRepository.findByEmail(email.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Usuario não encontrado"));
        }

        String token = UUID.randomUUID().toString();
        passwordResetService.createPasswordResetTokenForUser(user, token);

        senderService.sendEmail(email.getEmail(),"FORGOT PASSWORD TOKEN",
            "Aqui esta seu token: " + token + "\n nao compartilhe com ninguem e é nois");

        return ResponseEntity.ok(new MessageResponse("Email enviado para recuperar senha."));
    }
    @SecurityRequirements(value = {})
    @PostMapping("/local/forgot-password")
    public ResponseEntity<?> forgotPasswordLocal(@RequestBody ForgetPasswordRequest email) {
        User user = (User) userRepository.findByEmail(email.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Usuario não encontrado"));
        }

        String token = UUID.randomUUID().toString();
        passwordResetService.createPasswordResetTokenForUser(user, token);

        return ResponseEntity.ok(new ResetPasswordResponse("Email enviado para recuperar senha.",token));
    }



    @Operation(
        summary = "Redefinir senha",
        description = "Redefine a senha do usuário usando um token de redefinição",
        tags = {"Gerenciamento de senhas"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Cadastro com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MessageResponse.class),
                examples = @ExampleObject(value = "{\"message\": \"Senha redefinida com sucesso\"}"))),
        @ApiResponse(
            responseCode = "400",
            description = "Token invalido",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MessageResponse.class),
                examples = @ExampleObject(value = "{\"message\": \"Token Invalido\"}")))
    })

    @SecurityRequirements(value = {})
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        User user = passwordResetService.validatePasswordResetToken(resetPasswordRequest.getToken());
        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Token Invalido"));
        }
        String newPassword = new BCryptPasswordEncoder().encode(resetPasswordRequest.getPassword());
        user.setPassword(newPassword);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Senha redefinida com sucesso"));
    }
}


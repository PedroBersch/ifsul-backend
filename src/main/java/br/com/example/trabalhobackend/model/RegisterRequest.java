package br.com.example.trabalhobackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Schema(example = "user@example.com", description = "Email do usuario")
    private String email;
    @Schema(example = "password123", description = "Senha do usuario")
    private String password;
    @Schema(example = "USER | ADMIN", description = "Cargos do usuario, adminastro | usuario")
    private Role role;
}

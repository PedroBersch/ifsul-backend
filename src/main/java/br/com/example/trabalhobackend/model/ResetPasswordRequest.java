package br.com.example.trabalhobackend.model;

import lombok.Data;

@Data
public class ResetPasswordRequest {
private String token;
private String password;
}

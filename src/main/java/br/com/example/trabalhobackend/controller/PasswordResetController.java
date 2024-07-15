package br.com.example.trabalhobackend.controller;

import br.com.example.trabalhobackend.model.ForgetPasswordRequest;
import br.com.example.trabalhobackend.model.ResetPasswordRequest;
import br.com.example.trabalhobackend.model.User;
import br.com.example.trabalhobackend.repository.UserRepository;
import br.com.example.trabalhobackend.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgetPasswordRequest email) {
        User user = (User) userRepository.findByEmail(email.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String token = UUID.randomUUID().toString();
        passwordResetService.createPasswordResetTokenForUser(user, token);

        return ResponseEntity.ok("Password reset token: " + token);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        User user = passwordResetService.validatePasswordResetToken(resetPasswordRequest.getToken());
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        String newPassword = new BCryptPasswordEncoder().encode(resetPasswordRequest.getPassword());
        user.setPassword(newPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successfully");
    }
}


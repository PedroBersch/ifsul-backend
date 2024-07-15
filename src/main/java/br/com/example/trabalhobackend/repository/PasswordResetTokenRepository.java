package br.com.example.trabalhobackend.repository;

import br.com.example.trabalhobackend.model.PasswordResetToken;
import br.com.example.trabalhobackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUser(User user);
}
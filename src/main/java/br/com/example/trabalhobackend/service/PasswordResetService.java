package br.com.example.trabalhobackend.service;

import br.com.example.trabalhobackend.model.PasswordResetToken;
import br.com.example.trabalhobackend.model.User;
import br.com.example.trabalhobackend.repository.PasswordResetTokenRepository;
import br.com.example.trabalhobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        myToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 30)); // 24 hours expiration
        tokenRepository.save(myToken);
    }

    public User validatePasswordResetToken(String token) {
        PasswordResetToken passToken = tokenRepository.findByToken(token);
        if (passToken == null || passToken.getExpiryDate().before(new Date())) {
            return null;
        }
        return passToken.getUser();
    }
}
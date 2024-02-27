package com.akoua.WheelDeal.Auth;

import com.akoua.WheelDeal.Config.JWTService;
import com.akoua.WheelDeal.User.User;
import com.akoua.WheelDeal.User.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    public AuthService(UserRepository u, PasswordEncoder p, JWTService j){
        this.userRepository = u;
        this.passwordEncoder = p;
        this.jwtService = j;
    }

    public String authenticate(User user, String passwordRaw){
        boolean isCorrectPassword = passwordEncoder.matches(passwordRaw, user.password);

        if(!isCorrectPassword){
            System.out.println("--- Incorrect password when logging in! ---");
            return null;
        }

        return jwtService.generateToken(user);
    }

    public String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

}

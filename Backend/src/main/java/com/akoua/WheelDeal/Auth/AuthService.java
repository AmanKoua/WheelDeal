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
    AuthService(UserRepository u, PasswordEncoder p, JWTService j){
        this.userRepository = u;
        this.passwordEncoder = p;
        this.jwtService = j;
    }

    public LoginResponse authenticate(LoginRequestBody request){
        Optional<User> user = userRepository.findUserByEmail(request.email);

        if(user.isEmpty()){
            System.out.println("--- User not found when logging in! ---");
            return null;
        }

        boolean isCorrectPassword = passwordEncoder.matches(request.password, user.get().getPassword());

        if(!isCorrectPassword){
            System.out.println("--- Incorrect password when logging in! ---");
            return null;
        }

        var jwtToken = jwtService.generateToken(user.get());

        LoginResponse response = new LoginResponse();
        response.token = jwtToken;
        response.message = "Login successful!";

        return response;
    }



}

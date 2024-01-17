package com.akoua.WheelDeal.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    UserRepository userRepository;

    UserController(UserRepository u){
        this.userRepository = u;
    }

    @GetMapping
    @RequestMapping("/self")
    public ResponseEntity<Object> getOwnProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName();
            User user = userRepository.findUserByEmail(username).get();
            return ResponseEntity.ok(user);
        }

        String message = "Invalid credentials for profile retrieval!";
        return ResponseEntity.internalServerError().body(message);

    }

}

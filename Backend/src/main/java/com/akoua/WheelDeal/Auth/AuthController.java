package com.akoua.WheelDeal.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService a){
        this.authService = a;
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestBody login){
        System.out.println(login.email);
        System.out.println(login.password);
        return ResponseEntity.ok(authService.authenticate(login));
    }

    @PostMapping
    @RequestMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestBody signup){



        String response = "Signup successful!";
        return ResponseEntity.ok(response);

    }

}

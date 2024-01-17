package com.akoua.WheelDeal.Auth;

import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.User.User;
import com.akoua.WheelDeal.User.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    AuthController(AuthService a, CityRepository c, UserRepository u){
        this.authService = a;
        this.cityRepository = c;
        this.userRepository = u;
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestBody login){

        LoginResponse response = new LoginResponse();
        String token;
        Optional<User> user;

        if(!login.areFieldsValid()){
            response.message = "Email or password fields are null / empty!";
            return ResponseEntity.badRequest().body(response);
        }

        user = userRepository.findUserByEmail(login.email);

        if(user.isEmpty()){
            response.message = "No user found for provided email!";
            return ResponseEntity.badRequest().body(response);
        }

        token = authService.authenticate(user.get(), login.password);

        if(token == null){
            response.message = "Invalid password!";
            return ResponseEntity.status(401).body(response);
        }

        response.message = "Login Successful!";
        response.token = token;
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequestMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestBody signup){

        SignupResponse response = new SignupResponse();
        Long cityId;
        List<City> cityList;
        Optional<User> user;
        User newUser;

        if(!signup.areFieldsValid()){
            response.message = "Required fields are not provided or are invalid in request body!";
            return ResponseEntity.badRequest().body(response);
        }

        user = userRepository.findUserByEmail(signup.email);

        if(!user.isEmpty()){
            response.message = "A user is already registered with the provided email!";
            return ResponseEntity.status(409).body(response);
        }

        cityList = cityRepository.getCityByName(signup.cityName);

        if(cityList.size() < 1){
            City temp = new City();
            temp.name = signup.cityName;
            cityId = cityRepository.save(temp).id;
        }
        else{
            cityId = cityList.get(0).id;
        }

        newUser = new User(signup, cityId, authService.hashPassword(signup.password));
        userRepository.save(newUser);

        response.message = "Signup successful";
        return ResponseEntity.ok(response);

    }

}

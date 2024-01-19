package com.akoua.WheelDeal.Config;

import com.akoua.WheelDeal.Auth.AuthService;
import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.User.User;
import com.akoua.WheelDeal.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitConfiguration {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final AuthService authService;

    InitConfiguration(UserRepository u,CityRepository c, AuthService a){
        this.userRepository = u;
        this.cityRepository = c;
        this.authService = a;
    }

    @Bean
    CommandLineRunner relationsInitializer(CityRepository cityRepository){
        // ignore default constructor errors. Lombok will create at runtime
        return args -> {

            City city1 = new City();
            city1.name = "Detroit";

            City city2 = new City();
            city2.name = "Ann Arbor";

            cityRepository.saveAll(List.of(city1, city2));

            User user1 = new User();
            user1.firstName = "Aman";
            user1.middleName= "Steele";
            user1.lastName = "Koua";
            user1.age = 24;
            user1.gender = "M";
            user1.cityId = city1.id;
            user1.phoneNumber = "1234567890";
            user1.email = "akoua@umich.edu";
            user1.password = authService.hashPassword("password");
            user1.dealCount = 0;
            user1.avgRating = 0f;

            User user2 = new User();
            user1.firstName = "Susan";
            user1.lastName = "Hicks";
            user1.age = 64;
            user1.gender = "F";
            user1.cityId = city2.id;
            user1.phoneNumber = "1234567890";
            user1.email = "akoua1@umich.edu";
            user1.password = authService.hashPassword("password");
            user1.dealCount = 0;
            user1.avgRating = 0f;

            userRepository.saveAll(List.of(user1, user2));

        };
    }

}

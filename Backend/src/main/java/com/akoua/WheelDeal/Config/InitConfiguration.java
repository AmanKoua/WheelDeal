package com.akoua.WheelDeal.Config;

import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.User.User;
import com.akoua.WheelDeal.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitConfiguration {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    InitConfiguration(UserRepository u,CityRepository c){
        this.userRepository = u;
        this.cityRepository = c;
    }

    @Bean
    CommandLineRunner relationsInitializer(CityRepository cityRepository){
        return args -> {
//            User temp1 = new User();
        };
    }

}

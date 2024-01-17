package com.akoua.WheelDeal.Config;

import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitConfiguration {

    private final CityRepository cityRepository;

    InitConfiguration(CityRepository c){
        this.cityRepository = c;
    }

    @Bean
    CommandLineRunner relationsInitializer(CityRepository cityRepository){
        return args -> {
//            cityRepository.save(new City());
        };
    }

}

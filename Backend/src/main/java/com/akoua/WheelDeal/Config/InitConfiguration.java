package com.akoua.WheelDeal.Config;

import com.akoua.WheelDeal.Auth.AuthService;
import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.Transaction.Transaction;
import com.akoua.WheelDeal.Transaction.TransactionRepository;
import com.akoua.WheelDeal.User.User;
import com.akoua.WheelDeal.User.UserRepository;
import com.akoua.WheelDeal.Vehicle.Vehicle;
import com.akoua.WheelDeal.Vehicle.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitConfiguration {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final VehicleRepository vehicleRepository;
    private final TransactionRepository transactionRepository;
    private final AuthService authService;

    InitConfiguration(UserRepository u,CityRepository c, AuthService a, VehicleRepository v, TransactionRepository t){
        this.userRepository = u;
        this.cityRepository = c;
        this.authService = a;
        this.vehicleRepository = v;
        this.transactionRepository = t;
    }

    @Bean
    CommandLineRunner relationsInitializer(CityRepository cityRepository){
        // ignore default constructor errors. Lombok will create them at runtime
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
            user1.totalRating = 0f;

            User user2 = new User();
            user2.firstName = "Susan";
            user2.lastName = "Hicks";
            user2.age = 64;
            user2.gender = "F";
            user2.cityId = city2.id;
            user2.phoneNumber = "1234567890";
            user2.email = "akoua1@umich.edu";
            user2.password = authService.hashPassword("password");
            user2.dealCount = 0;
            user2.avgRating = 0f;
            user2.totalRating = 0f;

            userRepository.saveAll(List.of(user1, user2));

            Vehicle vehicle1 = new Vehicle();
            vehicle1.isAvailable = true;
            vehicle1.ownerEmail = user1.email;
            vehicle1.category = "truck";
            vehicle1.make = "Tesla";
            vehicle1.model = "Cybertruck";
            vehicle1.year = 2024;
            vehicle1.color = "Red";
            vehicle1.cityId = user1.cityId;
            vehicle1.defaultPrice = 89000.00d;
            vehicle1.condition = 4;

            Vehicle vehicle2 = new Vehicle();
            vehicle2.isAvailable = true;
            vehicle2.ownerEmail = user1.email;
            vehicle2.category = "car";
            vehicle2.make = "Pegany";
            vehicle2.model = "Hydra";
            vehicle2.year = 2014;
            vehicle2.color = "Neon green";
            vehicle2.cityId = user1.cityId;
            vehicle2.defaultPrice = 890000.00d;
            vehicle2.condition = 5;

            Vehicle vehicle3 = new Vehicle();
            vehicle3.isAvailable = true;
            vehicle3.ownerEmail = user2.email;
            vehicle3.category = "car";
            vehicle3.make = "Subaru";
            vehicle3.model = "Lmao";
            vehicle3.year = 2010;
            vehicle3.color = "Gray";
            vehicle3.cityId = user2.cityId;
            vehicle3.defaultPrice = 52000d;
            vehicle3.condition = 2;

            Vehicle vehicle4 = new Vehicle();
            vehicle4.isAvailable = true;
            vehicle4.ownerEmail = user2.email;
            vehicle4.category = "motorcycle";
            vehicle4.make = "Harley";
            vehicle4.model = "Davidson";
            vehicle4.year = 1998;
            vehicle4.color = "Black";
            vehicle4.cityId = user2.cityId;
            vehicle4.defaultPrice = 520000d;
            vehicle4.condition = 4;

            vehicleRepository.saveAll(List.of(vehicle1, vehicle2, vehicle3, vehicle4));

            Transaction t1 = new Transaction();
            t1.areDetailsNewForSwapper = true;
            t1.areDetailsNewForOwner = true;
            t1.status = 1;
            t1.dateChanged = LocalDate.now();
            t1.dateCreated = LocalDate.now();
            t1.isPendingAmendmentAgreement = false;
            t1.ownerVehicleId = vehicle2.id;
            t1.swapperVehicleId = vehicle3.id;
            t1.ownerEmail = vehicle2.ownerEmail;
            t1.swapperEmail = vehicle3.ownerEmail;
            t1.ownerPriceOffer = vehicle2.defaultPrice;
            t1.swapperPriceOffer = 1530d;
            t1.ownerLocationOffer = city1.name;
            t1.swapperLocationOffer = city2.name;
            t1.doesOwnerAgree = false;
            t1.doesSwapperAgree = false;
            t1.ownerRating = user1.avgRating;
            t1.swapperRating = user2.avgRating;

            Transaction t2 = new Transaction();
            t2.areDetailsNewForSwapper = true;
            t2.areDetailsNewForOwner = true;
            t2.status = 1;
            t2.dateChanged = LocalDate.now();
            t2.dateCreated = LocalDate.now();
            t2.isPendingAmendmentAgreement = false;
            t2.ownerVehicleId = vehicle4.id;
            t2.swapperVehicleId = vehicle1.id;
            t2.ownerEmail = vehicle4.ownerEmail;
            t2.swapperEmail = vehicle1.ownerEmail;
            t2.ownerPriceOffer = vehicle4.defaultPrice;
            t2.swapperPriceOffer = 135000d;
            t2.ownerLocationOffer = city2.name;
            t2.swapperLocationOffer = city1.name;
            t2.doesOwnerAgree = false;
            t2.doesSwapperAgree = false;
            t2.ownerRating = user2.avgRating;
            t2.swapperRating = user1.avgRating;

            transactionRepository.saveAll(List.of(t1,t2));

        };
    }

}

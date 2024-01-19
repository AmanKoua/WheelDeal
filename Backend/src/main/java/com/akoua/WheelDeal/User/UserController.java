package com.akoua.WheelDeal.User;

import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.Vehicle.PostVehicleRequestBody;
import com.akoua.WheelDeal.Vehicle.Vehicle;
import com.akoua.WheelDeal.Vehicle.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    UserRepository userRepository;
    VehicleRepository vehicleRepository;
    CityRepository cityRepository;

    UserController(UserRepository u, VehicleRepository v, CityRepository c){
        this.userRepository = u;
        this.vehicleRepository = v;
        this.cityRepository = c;
    }

    @GetMapping
    @RequestMapping("/self")
    public ResponseEntity<Object> getOwnProfile(){
        String message;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName();
            User user = userRepository.findUserByEmail(username).get();
            Optional<City> city = cityRepository.findById(user.cityId);

            if(city.isEmpty()){
                message = "Internal server error. City not found!";
                return ResponseEntity.internalServerError().body(message);
            }

            SelfProfileSlice slice = new SelfProfileSlice(user, city.get().name);
            return ResponseEntity.ok(slice);
        }

        return null; // do not need to handle this case, because filter will reject unauthorized requests automatically.

    }

}

package com.akoua.WheelDeal.Vehicle;

import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.User.User;
import com.akoua.WheelDeal.User.UserRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    UserRepository userRepository;
    VehicleRepository vehicleRepository;
    CityRepository cityRepository;

    VehicleController(UserRepository u, VehicleRepository v, CityRepository c){
        this.userRepository = u;
        this.vehicleRepository = v;
        this.cityRepository = c;
    }

    @PostMapping
    @RequestMapping("")
    public ResponseEntity<Object> registerVehicle(@RequestBody PostVehicleRequestBody request){
        String message;
        User user;
        Vehicle vehicle;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!request.isValid()){
            request.printAllFields();
            message = "Request body has invalid / missing field(s)!";
            return ResponseEntity.ok(message);
        }

        if(authentication != null && authentication.isAuthenticated()){
            user = userRepository.findUserByEmail(authentication.getName()).get();
            vehicle = new Vehicle(request, user.email, user.cityId);

            vehicleRepository.save(vehicle);

            message = "Successfully registered new vehicle!";
            return ResponseEntity.ok(message);
        }

        return null;
    }

    @GetMapping
    @RequestMapping("/")
    public ResponseEntity<Object> getVehicleById (@RequestParam("id") Long id){
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();

        if(vehicle.isEmpty()){
            return null;
        }

        vehicleList.add(vehicle.get());
        return ResponseEntity.ok(vehicleList);
    }

    @GetMapping
    @RequestMapping("/filter")
    public ResponseEntity<Object> filterVehicles(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max
    ){
        String message;
        User user;
        Optional<City> city;
        Vehicle vehicle;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<City> cityList = cityRepository.getCityByName(cityName);

        if(cityList.isEmpty()){
            message = "City " + cityName + " was not found!";
            return ResponseEntity.badRequest().body(message);
        }

        city = cityRepository.findById(cityList.get(0).id);
        List<Vehicle> vehicles = vehicleRepository.filterVehicles(category,make,model,year,city.get().id,min,max);

        return ResponseEntity.ok(vehicles);
    }

}
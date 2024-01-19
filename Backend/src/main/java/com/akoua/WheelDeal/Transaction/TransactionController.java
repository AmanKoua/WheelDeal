package com.akoua.WheelDeal.Transaction;

import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.User.User;
import com.akoua.WheelDeal.User.UserRepository;
import com.akoua.WheelDeal.Vehicle.Vehicle;
import com.akoua.WheelDeal.Vehicle.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final CityRepository cityRepository;

    public TransactionController(TransactionRepository t, UserRepository u, CityRepository c, VehicleRepository v){
        this.transactionRepository = t;
        this.userRepository = u;
        this.cityRepository = c;
        this.vehicleRepository = v;
    }

    @PostMapping
    @RequestMapping("")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionPostRequest request){

        if(!request.areFieldsValid()){
            return ResponseEntity.badRequest().body("Transaction post request field(s) are invalid!");
        }

        Optional<User> owner;
        Optional<User> swapper;
        Optional<Vehicle> ownerVehicle;
        Optional<Vehicle> swapperVehicle;
        Optional<City> ownerCity;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        owner = userRepository.findUserByEmail(request.ownerEmail);
        swapper = userRepository.findUserByEmail(authentication.getName());
        ownerVehicle = vehicleRepository.findById(request.ownerVehicleId);
        swapperVehicle = vehicleRepository.findById(request.swapperVehicleId);

        if(owner.isEmpty()){
            return ResponseEntity.status(404).body("No user found for provided owner email!");
        }

        ownerCity = cityRepository.findById(owner.get().cityId);

        if(ownerCity.isEmpty()){
            return ResponseEntity.status(500).body("Internal server error. Owner city does not exist!");
        }

        if(swapper.isEmpty()){
            return ResponseEntity.status(404).body("No user found for provided swapper email!");
        }

        if(ownerVehicle.isEmpty()){
            return ResponseEntity.status(404).body("No owner vehicle was found for provided id!");
        }

        if(!ownerVehicle.get().ownerEmail.equals(owner.get().email)){
            return ResponseEntity.badRequest().body("Provided owner email is not the owner of the provided owner vehicle id!");
        }

        if(!ownerVehicle.get().isAvailable){
            return ResponseEntity.status(403).body("Owner vehicle is not available!");
        }

        if(swapperVehicle.isEmpty()){
            return ResponseEntity.status(404).body("No swapper vehicle was found for provided id!");
        }

        if(!swapperVehicle.get().ownerEmail.equals(swapper.get().email)){
            return ResponseEntity.status(403).body("Swapper vehicle email and swapper email do not match!");
        }

        if(!swapperVehicle.get().isAvailable){
            return ResponseEntity.status(403).body("Swapper vehicle is not available!");
        }

        Transaction transaction = new Transaction(
                true,
                LocalDate.now(),
                LocalDate.now(),
                false,
                ownerVehicle.get().id,
                swapperVehicle.get().id,
                owner.get().email,
                swapper.get().email,
                ownerVehicle.get().defaultPrice,
                request.swapperPriceOffer,
                ownerCity.get().name,
                request.swapperLocationOffer,
                false,
                true,
                owner.get().avgRating,
                swapper.get().avgRating
                );

        transactionRepository.save(transaction);

        return ResponseEntity.ok("Transaction registered successfull!");

    }

}

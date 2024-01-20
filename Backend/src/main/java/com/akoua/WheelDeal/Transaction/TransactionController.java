package com.akoua.WheelDeal.Transaction;

import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.User.User;
import com.akoua.WheelDeal.User.UserRepository;
import com.akoua.WheelDeal.Vehicle.Vehicle;
import com.akoua.WheelDeal.Vehicle.VehicleRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

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

    @GetMapping
    @RequestMapping("/updates")
    public ResponseEntity<Object> getNotificationCountByVehicle(){

        Optional<User> user;
        List<Vehicle> userVehicles;
        List<HashMap<String,HashMap<Long, Integer>>> updateList = new ArrayList<HashMap<String,HashMap<Long, Integer>>>();
        HashMap<Long, Integer> incommingMap = new HashMap<Long, Integer>();
        HashMap<Long, Integer> outgoingMap = new HashMap<Long, Integer>();
        HashMap<String,HashMap<Long, Integer>> incommingWrapper = new HashMap<String,HashMap<Long, Integer>>();
        HashMap<String,HashMap<Long, Integer>> outgoingWrapper = new HashMap<String,HashMap<Long, Integer>>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = userRepository.findUserByEmail(auth.getName());
        userVehicles = vehicleRepository.getMyVehicles(user.get().email);

        for (Vehicle userVehicle : userVehicles) {
            incommingMap.put(userVehicle.id, transactionRepository.getIncommingUpdateCountByVehicle(userVehicle.id));
            outgoingMap.put(userVehicle.id, transactionRepository.getOutgoingUpdateCountByVehicle(userVehicle.id));
        }

        incommingWrapper.put("Incomming", incommingMap);
        outgoingWrapper.put("outgoing", outgoingMap);

        updateList.add(incommingWrapper);
        updateList.add(outgoingWrapper);

        return ResponseEntity.ok().body(updateList);
    }

    @GetMapping
    @RequestMapping("/vehicle")
    public ResponseEntity<Object> getTransactionForVehicle(@RequestParam("id") Long id, @RequestParam("type") String type){

        if(!type.equals("owner") && !type.equals("swapper")){
            return ResponseEntity.badRequest().body("Invalid type! only \"owner\" and \"swapper\" are allowed!");
        }

        Optional<User> user;
        Optional<Vehicle> vehicle;
        List<Transaction> transactions;
        boolean isOwner = type.equals("owner");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        user = userRepository.findUserByEmail(auth.getName());
        vehicle = vehicleRepository.findById(id);

        if(vehicle.isEmpty()){
            return ResponseEntity.status(404).body("No vehicle found for provided id!");
        }

        if(!vehicle.get().ownerEmail.equals(user.get().email)){
            return ResponseEntity.status(404).body("You are not authorized to view transaction(s) for the provided vehicle id!");
        }

        if(isOwner){
            // View incomming transaction requests
            transactions = transactionRepository.getTransactionForVehicleAsOwner(vehicle.get().id);
        }
        else{
            // View outgoing transaction offers
            transactions = transactionRepository.getTransactionForVehicleAsSwapper(vehicle.get().id);
        }

        return ResponseEntity.ok().body(transactions);
    }

    // TODO : Test!
    @PostMapping()
    @RequestMapping("/mark")
    ResponseEntity<Object> markAsSeen(@RequestParam("id") Long id, @RequestParam("type") String type){

        if(!type.equals("owner") && !type.equals("swapper")){
            return ResponseEntity.badRequest().body("Invalid type! only \"owner\" and \"swapper\" are allowed!");
        }

        Optional<User> user;
        boolean isOwner = type.equals("owner");
        Optional<Vehicle> vehicle;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        user = userRepository.findUserByEmail(authentication.getName());
        vehicle = vehicleRepository.findById(id);

        if(user.isEmpty()){
            return null;
        }

        if(vehicle.isEmpty()){
            return ResponseEntity.status(404).body("The requested vehicle was not found for the specified id");
        }

        if(!vehicle.get().ownerEmail.equals(user.get().email)){
            return ResponseEntity.status(403).body("You are not authorized to mark this transaction as seen!");
        }

        if(isOwner){
            transactionRepository.markTransactionSeenAsOwner(vehicle.get().id);
        }
        else{
            transactionRepository.markTransactionSeenAsSwapper(vehicle.get().id);
        }

        return ResponseEntity.ok().body("Transaction successfully marked as seen!");
    }

}
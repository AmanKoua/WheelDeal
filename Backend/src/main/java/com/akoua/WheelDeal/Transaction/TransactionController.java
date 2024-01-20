package com.akoua.WheelDeal.Transaction;

import com.akoua.WheelDeal.City.City;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.ResponseObjects.Message;
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
            return ResponseEntity.badRequest().body(new Message("Transaction post request field(s) are invalid!"));
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
            return ResponseEntity.status(404).body(new Message("No user found for provided owner email!"));
        }

        ownerCity = cityRepository.findById(owner.get().cityId);

        if(ownerCity.isEmpty()){
            return ResponseEntity.status(500).body(new Message("Internal server error. Owner city does not exist!"));
        }

        if(swapper.isEmpty()){
            return ResponseEntity.status(404).body(new Message("No user found for provided swapper email!"));
        }

        if(ownerVehicle.isEmpty()){
            return ResponseEntity.status(404).body(new Message("No owner vehicle was found for provided id!"));
        }

        if(!ownerVehicle.get().ownerEmail.equals(owner.get().email)){
            return ResponseEntity.badRequest().body(new Message("Provided owner email is not the owner of the provided owner vehicle id!"));
        }

        if(!ownerVehicle.get().isAvailable){
            return ResponseEntity.status(403).body(new Message("Owner vehicle is not available!"));
        }

        if(swapperVehicle.isEmpty()){
            return ResponseEntity.status(404).body(new Message("No swapper vehicle was found for provided id!"));
        }

        if(!swapperVehicle.get().ownerEmail.equals(swapper.get().email)){
            return ResponseEntity.status(403).body(new Message("Swapper vehicle email and swapper email do not match!"));
        }

        if(!swapperVehicle.get().isAvailable){
            return ResponseEntity.status(403).body(new Message("Swapper vehicle is not available!"));
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
                false,
                owner.get().avgRating,
                swapper.get().avgRating
        );

        transactionRepository.save(transaction);

        return ResponseEntity.ok(new Message("Transaction registered successfull!"));

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
            return ResponseEntity.badRequest().body(new Message("Invalid type! only \"owner\" and \"swapper\" are allowed!"));
        }

        Optional<User> user;
        Optional<Vehicle> vehicle;
        List<Transaction> transactions;
        boolean isOwner = type.equals("owner");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        user = userRepository.findUserByEmail(auth.getName());
        vehicle = vehicleRepository.findById(id);

        if(vehicle.isEmpty()){
            return ResponseEntity.status(404).body(new Message("No vehicle found for provided id!"));
        }

        if(!vehicle.get().ownerEmail.equals(user.get().email)){
            return ResponseEntity.status(404).body(new Message("You are not authorized to view transaction(s) for the provided vehicle id!"));
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

    @PutMapping()
    @RequestMapping("/mark")
    ResponseEntity<Object> markAsSeen(@RequestParam("id") Long id, @RequestParam("type") String type){

        if(!type.equals("owner") && !type.equals("swapper")){
            return ResponseEntity.badRequest().body(new Message("Invalid type! only \"owner\" and \"swapper\" are allowed!"));
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
            return ResponseEntity.status(404).body(new Message("The requested vehicle was not found for the specified id"));
        }

        if(!vehicle.get().ownerEmail.equals(user.get().email)){
            return ResponseEntity.status(403).body(new Message("You are not authorized to mark this transaction as seen!"));
        }

        if(isOwner){
            transactionRepository.markTransactionSeenAsOwner(vehicle.get().id);
        }
        else{
            transactionRepository.markTransactionSeenAsSwapper(vehicle.get().id);
        }

        return ResponseEntity.ok().body(new Message("Transaction successfully marked as seen!"));
    }

    @PutMapping
    @RequestMapping("/accept")
    public ResponseEntity<Object> acceptTransaction(@RequestParam("id") Long id, @RequestParam("rating") float rating){

        if(rating > 5 || rating < 0){
            return ResponseEntity.badRequest().body(new Message("cannot have rating outside of range 0 to 5!"));
        }

        Optional<User> user;
        Optional<User> other;
        Optional<Transaction> transaction;
        boolean isOwner;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        user = userRepository.findUserByEmail(authentication.getName());
        transaction = transactionRepository.findById(id);

        if(user.isEmpty()){
            return ResponseEntity.status(404).body(new Message("Authenticated user not found!"));
        }

        if(transaction.isEmpty()){
            return ResponseEntity.status(404).body(new Message("No transaction found for provided id!"));
        }

        if(transaction.get().ownerEmail.equals(user.get().email)){
            isOwner = true;

            if(transaction.get().doesOwnerAgree){
                return ResponseEntity.status(403).body(new Message("Cannot accept a transaction multiple times!"));
            }

            transactionRepository.acceptTransactionAsOwner(user.get().email, id);
        }
        else if(transaction.get().swapperEmail.equals(user.get().email)){
            isOwner = false;

            if(transaction.get().doesSwapperAgree){
                return ResponseEntity.status(403).body(new Message("Cannot accept a transaction multiple times!"));
            }

            transactionRepository.acceptTransactionAsSwapper(user.get().email, id);
        }
        else{
            return ResponseEntity.status(403).body(new Message("You are not authorized to accept this transaction!"));
        }

        if(isOwner){
            other = userRepository.findUserByEmail(transaction.get().swapperEmail);
        }
        else{
            other = userRepository.findUserByEmail(transaction.get().ownerEmail);
        }

        if(other.isEmpty()){
            return ResponseEntity.status(500).body(new Message("Internal server error! Other transaction user not found!"));
        }

        if(other.get().dealCount == 0){
            userRepository.updateUserRating(rating, rating, other.get().email);
        }
        else{
            float avgRating = (other.get().totalRating + rating) / (other.get().dealCount + 1);
            userRepository.updateUserRating(avgRating, rating, other.get().email);
        }

        if((isOwner && transaction.get().doesSwapperAgree) || (!isOwner && transaction.get().doesOwnerAgree)){
            vehicleRepository.deleteById(transaction.get().ownerVehicleId);
            vehicleRepository.deleteById(transaction.get().swapperVehicleId);
            transactionRepository.removeHangingTransactions(transaction.get().ownerEmail, transaction.get().ownerVehicleId);
            transactionRepository.removeHangingTransactions(transaction.get().swapperEmail, transaction.get().swapperVehicleId);
            return ResponseEntity.ok().body(new Message("Transaction accepted successfully! Transaction closed!"));
        }
        else{
            return ResponseEntity.ok().body(new Message("Transaction accepted successfully! Pending other user's approval"));
        }
    }

    @PutMapping
    @RequestMapping("/reject")
    public ResponseEntity<Object> handleTransaction(@RequestParam("id") Long id){

        Optional<User> user;
        Optional<Transaction> transaction;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        user = userRepository.findUserByEmail(authentication.getName());
        transaction = transactionRepository.findById(id);

        if(user.isEmpty()){
            return ResponseEntity.internalServerError().body(new Message("No user found for authenticated user!"));
        }

        if(transaction.isEmpty()){
            return ResponseEntity.status(404).body(new Message("No transaction found for provided id!"));
        }

        if(!transaction.get().ownerEmail.equals(user.get().email)){
            return ResponseEntity.status(403).body(new Message("You are not authorized to reject this transaction"));
        }

        transactionRepository.deleteTransaction(user.get().email, id);

        return ResponseEntity.ok().body(new Message("Successfuly deleted transaction!"));
    }

}
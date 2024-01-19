package com.akoua.WheelDeal.City;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("city")
public class CityController {

    public CityRepository cityRepository;

    CityController(CityRepository c){
        this.cityRepository = c;
    }

    @GetMapping
    @RequestMapping("/")
    public ResponseEntity<Object> getCityById(@RequestParam("id") Long id){

        Optional<City> city = cityRepository.findById(id);

        if(city.isEmpty()){
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.ok(city.get());

    }

}

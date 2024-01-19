package com.akoua.WheelDeal.Vehicle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="vehicle")
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @SequenceGenerator(
            name="vehicle_id_sequence",
            sequenceName = "vehicle_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="vehicle_id_sequence"
    )
    public Long id;
    public boolean isAvailable;
    public String ownerEmail;
    public String category;
    public String make;
    public String model;
    public int year;
    public String color;
    public Long cityId;
    public double defaultPrice;
    public int condition;

    public Vehicle(PostVehicleRequestBody r, String ownerEmail, Long cityId){
        this.isAvailable = true;
        this.ownerEmail = ownerEmail;
        this.category = r.category;
        this.make = r.make;
        this.model = r.model;
        this.year = r.year;
        this.color = r.color;
        this.cityId = cityId;
        this.defaultPrice = r.defaultPrice;
        this.condition = r.condition;
    }

}

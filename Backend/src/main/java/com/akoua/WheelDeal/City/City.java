package com.akoua.WheelDeal.City;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="city")
@NoArgsConstructor
@AllArgsConstructor
public class City {

    @Id
    @SequenceGenerator(
            name = "city_id_sequence",
            sequenceName = "city_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "city_id_sequence"
    )
    public long id;
    public String name;

}

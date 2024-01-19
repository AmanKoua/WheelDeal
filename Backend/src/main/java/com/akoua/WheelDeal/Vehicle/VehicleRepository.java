package com.akoua.WheelDeal.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v WHERE " +
            "(:category IS NULL OR v.category = :category) AND " +
            "(:make IS NULL OR v.make = :make) AND " +
            "(:model IS NULL OR v.model = :model) AND " +
            "(:year IS NULL OR v.year = :year) AND " +
            "(:cityId IS NULL OR v.cityId = :cityId) AND " +
            "(:minPrice IS NULL OR v.defaultPrice >= :minPrice) AND " +
            "(:maxPrice IS NULL OR v.defaultPrice <= :maxPrice)"
    )
    List<Vehicle> filterVehicles(
            @Param("category") String category,
            @Param("make") String make,
            @Param("model") String model,
            @Param("year") Integer year,
            @Param("cityId") Long cityId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    @Query("SELECT v FROM Vehicle v WHERE v.ownerEmail = :ownerEmail")
    List <Vehicle> getMyVehicles(@Param("ownerEmail") String email);

}

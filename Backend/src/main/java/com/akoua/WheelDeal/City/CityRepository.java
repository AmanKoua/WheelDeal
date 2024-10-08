package com.akoua.WheelDeal.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c where c.name = :name")
    public List<City> getCityByName(@Param("name") String name);

    @Query("SELECT c FROM City c where c.id > -1")
    public List<City> getAllCities();

}

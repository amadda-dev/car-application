package com.carapp.repository;

import com.carapp.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByCustomerId(Long customerId);

    Optional<Vehicle> findByVin(String vin);

    boolean existsByVin(String vin);

    List<Vehicle> findBySold(Boolean sold);

    @Query("SELECT v FROM Vehicle v WHERE " +
            "(:sold IS NULL OR v.sold = :sold) AND " +
            "(:make IS NULL OR v.make = :make) AND " +
            "(:model IS NULL OR v.model = :model) AND " +
            "(:car_year IS NULL OR v.car_year = :car_year) AND " +
            "(:color IS NULL OR v.color = :color)")
    List<Vehicle> searchVehicles(
            @Param("sold") Boolean sold,
            @Param("make") String make,
            @Param("model") String model,
            @Param("car_year") Integer year,
            @Param("color") String color
    );


}
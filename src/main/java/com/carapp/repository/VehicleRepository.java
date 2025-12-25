package com.carapp.repository;

import com.carapp.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByCustomerId(Long customerId);

    Optional<Vehicle> findByVin(String vin);

    boolean existsByVin(String vin);


}
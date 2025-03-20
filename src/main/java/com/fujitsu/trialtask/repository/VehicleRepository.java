package com.fujitsu.trialtask.repository;


import com.fujitsu.trialtask.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    @Query("""
           select vehicle.regionalBaseFee 
           from Vehicle vehicle
           where vehicle.vehicleType = :vehicleType 
             and vehicle.regionName = :regionName
           """)
    Float findRegionalBaseFeeByVehicleTypeRegionName(
            @Param("vehicleType") String vehicleType,
            @Param("regionName") String regionName);
}

package com.fujitsu.trialtask.repository;


import com.fujitsu.trialtask.model.RegionalBaseFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RegionalBaseFeeRepository extends JpaRepository<RegionalBaseFee, Integer> {
    @Query("""
           select regionalBaseFee.regionalBaseFee 
           from RegionalBaseFee regionalBaseFee
           where regionalBaseFee.vehicleType = :vehicleType 
             and regionalBaseFee.regionName = :regionName
           """)
    Float findRegionalBaseFeeByVehicleTypeRegionName(
            @Param("vehicleType") String vehicleType,
            @Param("regionName") String regionName);
}

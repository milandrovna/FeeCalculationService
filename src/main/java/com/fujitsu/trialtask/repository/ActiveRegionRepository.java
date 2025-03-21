package com.fujitsu.trialtask.repository;

import com.fujitsu.trialtask.model.ActiveRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ActiveRegionRepository extends JpaRepository<ActiveRegion, Integer> {


    @Query("SELECT a.stationName FROM ActiveRegion a WHERE a.regionName = :region")
    String findStationNameByRegionName(@Param("region") String region);


}

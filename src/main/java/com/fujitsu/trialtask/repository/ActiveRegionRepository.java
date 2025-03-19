package com.fujitsu.trialtask.repository;

import com.fujitsu.trialtask.model.ActiveRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ActiveRegionRepository extends JpaRepository<ActiveRegion, Integer> {

    @Query("""
           SELECT region.stationName FROM ActiveRegion region 
           WHERE region.regionName = :region
           """)
    Optional<String> findStationNameByRegion(@Param("region") String region);
}

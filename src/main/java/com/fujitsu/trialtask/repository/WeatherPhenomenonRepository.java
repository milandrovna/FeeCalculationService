package com.fujitsu.trialtask.repository;

import com.fujitsu.trialtask.model.WeatherPhenomenon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WeatherPhenomenonRepository extends JpaRepository<WeatherPhenomenon, Integer> {

    @Query("""
        SELECT phenomenon.prohibition FROM WeatherPhenomenon phenomenon 
        WHERE :weatherPhenomenon LIKE CONCAT('%', phenomenon.phenomenonType, '%')
        """)
    Optional<Boolean> findProhibitionByPhenomenonType(String weatherPhenomenon);

    @Query("""
        SELECT phenomenon.fee
        FROM WeatherPhenomenon phenomenon
        WHERE :weatherPhenomenon LIKE CONCAT('%', phenomenon.phenomenonType, '%')
    """)
    Optional<Float> findFeeByPhenomenonType(@Param("weatherPhenomenon") String weatherPhenomenon);
}
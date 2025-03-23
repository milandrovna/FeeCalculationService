package com.fujitsu.trialtask.repository;

import com.fujitsu.trialtask.model.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Integer> {
    WeatherCondition findByTimestamp(Long timestamp);

    @Query("""
           SELECT weather FROM WeatherCondition weather
           WHERE weather.stationName = :station
           ORDER BY weather.timestamp DESC
           LIMIT 1
           """)
    Optional<WeatherCondition> findLatestWeatherByStation(@Param("station") String station);

    @Query("""
           SELECT weather
           FROM WeatherCondition weather
           WHERE weather.stationName = :station
           ORDER BY ABS(weather.timestamp - :timestamp) ASC
           Limit 1
       """)
    Optional<WeatherCondition> findByStationAndTimestamp(@Param("station") String station,
                                                         @Param("timestamp") Long timestamp);
}

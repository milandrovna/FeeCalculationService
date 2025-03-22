package com.fujitsu.trialtask.repository;

import com.fujitsu.trialtask.model.BusinessRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BusinessRuleRepository extends JpaRepository<BusinessRule, Integer> {

    @Query("""
       SELECT DISTINCT rule.metricType FROM BusinessRule rule
       WHERE rule.vehicleName = :vehicleType
       """)
    List<String> findMetricTypesByVehicleType(@Param("vehicleType") String vehicleType);

    @Query("""
           SELECT MIN(rule.additionalFee) FROM BusinessRule rule
           WHERE rule.metricType = 'Air Temperature'
           AND :airTemperature BETWEEN rule.minValue AND rule.maxValue
           """)
    Optional<Float> findFeeByAirTemperature(@Param("airTemperature") float airTemperature);

    @Query("""
           SELECT MIN(rule.additionalFee) FROM BusinessRule rule
           WHERE rule.metricType = 'Wind Speed'
           AND :windSpeed BETWEEN rule.minValue AND rule.maxValue
           """)
    Optional<Float> findFeeByWindSpeed(@Param("windSpeed") float windSpeed);


}

package com.fujitsu.trialtask.repository;

import com.fujitsu.trialtask.model.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Integer> {
    WeatherCondition findByTimestamp(Long timestamp);
}

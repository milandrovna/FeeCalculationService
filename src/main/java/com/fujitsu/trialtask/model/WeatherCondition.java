package com.fujitsu.trialtask.model;

import com.fujitsu.trialtask.enums.WeatherPhenomenon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherCondition {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String stationName;

    @NotNull
    private Integer wmoStationCode;

    @NotNull
    private Float airTemperature;

    @NotNull
    private Float windSpeed;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WeatherPhenomenon weatherPhenomenon;

    @NotNull
    private Long timestamp;

}

package com.fujitsu.trialtask.model;

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
    private String weatherPhenomenon;

    @NotNull
    private Long timestamp;

}

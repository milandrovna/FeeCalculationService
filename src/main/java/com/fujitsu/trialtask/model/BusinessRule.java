package com.fujitsu.trialtask.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String vehicleType;

    @NotNull
    private String metricType;

    @NotNull
    private Float minValue;

    @NotNull
    private Float maxValue;

    private Float additionalFee;
}
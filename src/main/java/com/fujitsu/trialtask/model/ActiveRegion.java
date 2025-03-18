package com.fujitsu.trialtask.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class ActiveRegion {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String regionName;

    @NotNull
    private String stationName;
}

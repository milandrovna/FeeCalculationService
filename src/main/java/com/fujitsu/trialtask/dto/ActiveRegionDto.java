package com.fujitsu.trialtask.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ActiveRegionDto {

    @XmlElement(name = "id")
    private Integer id;

    @XmlElement(name = "regionName")
    private String regionName;

    @XmlElement(name = "stationName")
    private String stationName;

    @XmlElement(name = "carRBFee")
    private Float carRBFee;

    @XmlElement(name = "scooterRBFee")
    private Float scooterRBFee;

    @XmlElement(name = "bikeRBFee")
    private Float bikeRBFee;

}

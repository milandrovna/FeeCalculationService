package com.fujitsu.trialtask.dto;

import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class WeatherConditionDto {

    private Integer id;

    @XmlElement(name = "name")
    private String stationName;

    @XmlElement(name = "wmocode")
    private Integer wmoStationCode;

    @XmlElement(name = "phenomenon")
    private String weatherPhenomenon;

    @XmlElement(name = "airtemperature")
    private Float airTemperature;

    @XmlElement(name = "windspeed")
    private Float windSpeed;

    private Long timestamp;

    void afterUnmarshal(Unmarshaller u, Object parent) {
        if (parent instanceof ObservationsDto) {
            this.timestamp = ((ObservationsDto) parent).getTimestamp();
        }
    }
}

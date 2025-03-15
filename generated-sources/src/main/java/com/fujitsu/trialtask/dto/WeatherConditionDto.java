package com.fujitsu.trialtask.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * WeatherConditionDto
 */
@lombok.Builder
@lombok.AllArgsConstructor

@JsonTypeName("WeatherCondition")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class WeatherConditionDto {

  private Integer id;

  private String stationName;

  private Integer wmoStationCode;

  @lombok.Builder.Default
  private Float airTemperature = null;

  @lombok.Builder.Default
  private Float windSpeed = null;

  /**
   * Observed weather phenomenon
   */
  public enum WeatherPhenomenonEnum {
    CLEAR("CLEAR"),
    
    FEW_CLOUDS("FEW_CLOUDS"),
    
    VARIABLE_CLOUDS("VARIABLE_CLOUDS"),
    
    CLOUDY_WITH_CLEAR_SPELLS("CLOUDY_WITH_CLEAR_SPELLS"),
    
    OVERCAST("OVERCAST"),
    
    LIGHT_SNOW_SHOWER("LIGHT_SNOW_SHOWER"),
    
    MODERATE_SNOW_SHOWER("MODERATE_SNOW_SHOWER"),
    
    HEAVY_SNOW_SHOWER("HEAVY_SNOW_SHOWER"),
    
    LIGHT_RAIN("LIGHT_RAIN"),
    
    MODERATE_RAIN("MODERATE_RAIN"),
    
    HEAVY_RAIN("HEAVY_RAIN"),
    
    GLAZE("GLAZE"),
    
    LIGHT_SLEET("LIGHT_SLEET"),
    
    MODERATE_SLEET("MODERATE_SLEET"),
    
    LIGHT_SNOWFALL("LIGHT_SNOWFALL"),
    
    MODERATE_SNOWFALL("MODERATE_SNOWFALL"),
    
    HEAVY_SNOWFALL("HEAVY_SNOWFALL"),
    
    HAIL("HAIL"),
    
    MIST("MIST"),
    
    FOG("FOG");

    private String value;

    WeatherPhenomenonEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static WeatherPhenomenonEnum fromValue(String value) {
      for (WeatherPhenomenonEnum b : WeatherPhenomenonEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private WeatherPhenomenonEnum weatherPhenomenon;

  private Long timestamp;

  public WeatherConditionDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Id of the WeatherCondition
   * @return id
   */
  
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public WeatherConditionDto stationName(String stationName) {
    this.stationName = stationName;
    return this;
  }

  /**
   * Name of the station, from where weather condition info comes
   * @return stationName
   */
  
  @JsonProperty("stationName")
  public String getStationName() {
    return stationName;
  }

  public void setStationName(String stationName) {
    this.stationName = stationName;
  }

  public WeatherConditionDto wmoStationCode(Integer wmoStationCode) {
    this.wmoStationCode = wmoStationCode;
    return this;
  }

  /**
   * WMO code of the station
   * @return wmoStationCode
   */
  
  @JsonProperty("wmoStationCode")
  public Integer getWmoStationCode() {
    return wmoStationCode;
  }

  public void setWmoStationCode(Integer wmoStationCode) {
    this.wmoStationCode = wmoStationCode;
  }

  public WeatherConditionDto airTemperature(Float airTemperature) {
    this.airTemperature = airTemperature;
    return this;
  }

  /**
   * Air temperature
   * @return airTemperature
   */
  
  @JsonProperty("airTemperature")
  public Float getAirTemperature() {
    return airTemperature;
  }

  public void setAirTemperature(Float airTemperature) {
    this.airTemperature = airTemperature;
  }

  public WeatherConditionDto windSpeed(Float windSpeed) {
    this.windSpeed = windSpeed;
    return this;
  }

  /**
   * Wind speed
   * @return windSpeed
   */
  
  @JsonProperty("windSpeed")
  public Float getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(Float windSpeed) {
    this.windSpeed = windSpeed;
  }

  public WeatherConditionDto weatherPhenomenon(WeatherPhenomenonEnum weatherPhenomenon) {
    this.weatherPhenomenon = weatherPhenomenon;
    return this;
  }

  /**
   * Observed weather phenomenon
   * @return weatherPhenomenon
   */
  
  @JsonProperty("weatherPhenomenon")
  public WeatherPhenomenonEnum getWeatherPhenomenon() {
    return weatherPhenomenon;
  }

  public void setWeatherPhenomenon(WeatherPhenomenonEnum weatherPhenomenon) {
    this.weatherPhenomenon = weatherPhenomenon;
  }

  public WeatherConditionDto timestamp(Long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Integer64 timestamp in seconds since epoch. Represents timestamp when the measurement was taken
   * @return timestamp
   */
  
  @JsonProperty("timestamp")
  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WeatherConditionDto weatherCondition = (WeatherConditionDto) o;
    return Objects.equals(this.id, weatherCondition.id) &&
        Objects.equals(this.stationName, weatherCondition.stationName) &&
        Objects.equals(this.wmoStationCode, weatherCondition.wmoStationCode) &&
        Objects.equals(this.airTemperature, weatherCondition.airTemperature) &&
        Objects.equals(this.windSpeed, weatherCondition.windSpeed) &&
        Objects.equals(this.weatherPhenomenon, weatherCondition.weatherPhenomenon) &&
        Objects.equals(this.timestamp, weatherCondition.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, stationName, wmoStationCode, airTemperature, windSpeed, weatherPhenomenon, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WeatherConditionDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    stationName: ").append(toIndentedString(stationName)).append("\n");
    sb.append("    wmoStationCode: ").append(toIndentedString(wmoStationCode)).append("\n");
    sb.append("    airTemperature: ").append(toIndentedString(airTemperature)).append("\n");
    sb.append("    windSpeed: ").append(toIndentedString(windSpeed)).append("\n");
    sb.append("    weatherPhenomenon: ").append(toIndentedString(weatherPhenomenon)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


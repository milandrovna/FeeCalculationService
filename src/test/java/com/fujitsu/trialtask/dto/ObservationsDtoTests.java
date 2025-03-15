package com.fujitsu.trialtask.dto;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObservationsDtoTests {

    // TO DO: write more unit tests

    @Test
    public void testConvertXmlToObservationsDto() throws JAXBException {
        String xml = """
            <observations timestamp="1742045290">
                <station>
                    <name>Tallinn-Harku</name>
                    <wmocode>26038</wmocode>
                    <longitude>24.602891666624284</longitude>
                    <latitude>59.398122222355134</latitude>
                    <phenomenon>Clear</phenomenon>
                    <visibility>35.0</visibility>
                    <precipitations>0</precipitations>
                    <airpressure>1013.8</airpressure>
                    <relativehumidity>41</relativehumidity>
                    <airtemperature>4.7</airtemperature>
                    <winddirection>245</winddirection>
                    <windspeed>7.8</windspeed>
                    <windspeedmax>12.8</windspeedmax>
                    <waterlevel/>
                    <waterlevel_eh2000/>
                    <watertemperature/>
                    <uvindex>1.5</uvindex>
                    <sunshineduration>502</sunshineduration>
                    <globalradiation>383</globalradiation>
                </station>
            </observations>
            """;

        List<WeatherConditionDto> stations = ObservationsDto.convertXmlToObservationsDto(xml);

        // Verify that one station was parsed
        assertNotNull(stations, "Stations list should not be null");
        assertEquals(1, stations.size(), "There should be exactly 1 station");

        // Validate station (Tallinn-Harku)
        WeatherConditionDto station2 = stations.getFirst();
        assertEquals("Tallinn-Harku", station2.getStationName(), "Station name should match");
        assertEquals(26038, station2.getWmoStationCode(), "WMO station code should be 26038");
        assertEquals("Clear", station2.getWeatherPhenomenon(), "Weather phenomenon should be 'Clear'");
        assertEquals(4.7, station2.getAirTemperature(), 0.001, "Air temperature should be 4.7");
        assertEquals(7.8, station2.getWindSpeed(), 0.001, "Wind speed should be 7.8");
    }
}

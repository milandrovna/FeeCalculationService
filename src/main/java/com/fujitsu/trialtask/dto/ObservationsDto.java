package com.fujitsu.trialtask.dto;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.StringReader;
import java.util.List;

@Data
@XmlRootElement(name = "observations")
@XmlAccessorType(XmlAccessType.FIELD)
public class ObservationsDto {

    @XmlAttribute(name = "timestamp")
    private String timestamp;

    @XmlElement(name = "station")
    private List<WeatherConditionDto> stations;

    @SneakyThrows
    public static ObservationsDto convertXmlToObservationsDto(String xml) {
        JAXBContext context = JAXBContext.newInstance(ObservationsDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        return (ObservationsDto) unmarshaller.unmarshal(reader);
    }
}

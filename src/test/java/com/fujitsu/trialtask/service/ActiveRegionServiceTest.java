package com.fujitsu.trialtask.service;


import com.fujitsu.trialtask.model.ActiveRegion;
import com.fujitsu.trialtask.repository.ActiveRegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActiveRegionServiceTest {

    @Mock
    private ActiveRegionRepository activeRegionRepository;

    private ActiveRegionService activeRegionService;

    @BeforeEach
    void setUp() {
        activeRegionService = new ActiveRegionService(activeRegionRepository);
    }

    @Test
    void testGetActiveRegions_returnsAllRegions() {
        List<ActiveRegion> regions = List.of(
                new ActiveRegion(1, "TARTU", "Tartu-Tõravere"),
                new ActiveRegion(2, "Tallinn", "Tallinn-Harku")
        );

        when(activeRegionRepository.findAll()).thenReturn(regions);

        List<ActiveRegion> result = activeRegionService.getActiveRegions();

        assertEquals(2, result.size());
        assertEquals("TARTU", result.get(0).getRegionName());
    }

    @Test
    void testGetActiveStationNames_returnsStationNameSet() {
        List<ActiveRegion> regions = List.of(
                new ActiveRegion(1, "TARTU", "Tartu-Tõravere"),
                new ActiveRegion(2, "TALLINN", "Tallinn-Harku")
        );

        when(activeRegionRepository.findAll()).thenReturn(regions);

        Set<String> stationNames = activeRegionService.getActiveStationNames();

        assertEquals(Set.of("Tartu-Tõravere", "Tallinn-Harku"), stationNames);
    }
}

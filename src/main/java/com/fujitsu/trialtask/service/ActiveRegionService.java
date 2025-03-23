package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.model.ActiveRegion;
import com.fujitsu.trialtask.repository.ActiveRegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveRegionService {

    private final ActiveRegionRepository activeRegionRepository;

    /**
     * Retrieves all active regions from the database.
     *
     * @return list of active regions
     */
    public List<ActiveRegion> getActiveRegions() { return activeRegionRepository.findAll();
    }

    /**
     * Returns a set of station names from all active regions.
     * Useful for filtering weather data based on active stations.
     *
     * @return set of active station names
     */
    public Set<String> getActiveStationNames() {
        return getActiveRegions().stream()
                .map(ActiveRegion::getStationName)
                .collect(Collectors.toSet());
    }
}

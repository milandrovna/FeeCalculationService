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

    public List<ActiveRegion> getActiveRegions() { return activeRegionRepository.findAll();
    }

    public Set<String> getActiveStationNames() {
        return getActiveRegions().stream()
                .map(ActiveRegion::getStationName) // Extract stationNam
                .collect(Collectors.toSet());
    }

    public ActiveRegion insertActiveRegion(ActiveRegion activeRegion) {
        return activeRegionRepository.save(activeRegion);
    }

    public List<ActiveRegion> insertActiveRegions(List<ActiveRegion> activeRegions) {
        return activeRegionRepository.saveAll(activeRegions);
    }
}

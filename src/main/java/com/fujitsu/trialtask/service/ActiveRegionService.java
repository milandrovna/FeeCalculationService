package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.model.ActiveRegion;
import com.fujitsu.trialtask.repository.ActiveRegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveRegionService {

    private final ActiveRegionRepository activeRegionRepository;

    public List<ActiveRegion> getActiveRegions() {
        return activeRegionRepository.findAll();
    }

    public ActiveRegion insertActiveRegion(ActiveRegion activeRegion) {
        return activeRegionRepository.save(activeRegion);
    }

    public List<ActiveRegion> insertActiveRegions(List<ActiveRegion> activeRegions) {
        return activeRegionRepository.saveAll(activeRegions);
    }
}

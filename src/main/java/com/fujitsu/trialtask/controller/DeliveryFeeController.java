package com.fujitsu.trialtask.controller;

import com.fujitsu.trialtask.service.FeeCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/delivery-fee")
public class DeliveryFeeController {

    private final FeeCalculationService feeCalculationService;

    public DeliveryFeeController(FeeCalculationService feeCalculationService) {
        this.feeCalculationService = feeCalculationService;
    }

    @GetMapping
    public ResponseEntity<?> getDeliveryFee(@RequestParam("city") String city,
                                            @RequestParam("vehicleType") String vehicleType) {
        try {
            float fee = feeCalculationService.calculateFee(city, vehicleType);
            return ResponseEntity.ok(fee);
        } catch (ResponseStatusException ex) {

            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
    }
}

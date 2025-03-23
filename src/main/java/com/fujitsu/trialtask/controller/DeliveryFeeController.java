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

    /**
     * Calculates the delivery fee based on the vehicle and the weather conditions of
     * the provided region. If a timestamp is given, the weather record at that timestamp
     * (or the closest available record) is used.
     * @param city name of the region of delivery
     * @param vehicleType name of the vehicle used for delivery
     * @param timestamp optional parameter; the time at which the weather conditions were recorded
     * @return the fee of a delivery
     */
    @GetMapping
    public ResponseEntity<?> getDeliveryFee(@RequestParam("city") String city,
                                            @RequestParam("vehicleType") String vehicleType,
                                            @RequestParam(value = "timestamp", required = false) Long timestamp) {
        try {
            float fee = feeCalculationService.calculateFee(city, vehicleType, timestamp);
            return ResponseEntity.ok(fee);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
    }
}

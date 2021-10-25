package com.codibly.producer.controller;

import com.codibly.producer.model.SerializationType;
import com.codibly.producer.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
@AllArgsConstructor
public class CityController {
    private final CityService cityService;

    @PostMapping("/measurements")
    public String sendCityMeasurements(@RequestParam(defaultValue = "100") int numberOfMeasurements,
                                       @RequestParam(defaultValue = "100") int numberOfMessages,
                                       @RequestParam(defaultValue = "JSON") SerializationType serializationType) {
        cityService.sendCityMeasurementsMessages(numberOfMeasurements, numberOfMessages, serializationType);
        return "Success";
    }
}

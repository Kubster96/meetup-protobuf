package com.codibly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Weather extends Measurement {
    private double temp;
    private int pressure;
    private int humidity;
    private double windSpeed;
    private WindSpeedUnit windSpeedUnit;
    private int windDeg;
}

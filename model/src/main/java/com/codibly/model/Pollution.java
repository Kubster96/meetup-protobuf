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
public class Pollution extends Measurement {
    // Concentration of CO (Carbon monoxide), μg/m3
    private double co;
    // Concentration of NO (Nitrogen monoxide), μg/m3
    private double no;
    // Concentration of PM2.5 (Fine particles matter),
    private double pm25;
}

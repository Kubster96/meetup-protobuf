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
public class Solar extends Measurement {
    // Global Horizontal Irradiance
    private int ghi;
    // Diffuse Horizontal Irradiance
    private int dhi;
    //Direct Normal Irradiance
    private int dni;
}

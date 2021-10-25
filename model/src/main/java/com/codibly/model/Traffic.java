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
public class Traffic extends Measurement {
    private String roadNo;
    private int currentSpeed;
    private int freeFlowSpeed;
    private int currentTravelTime;
    private int freeFlowTravelTime;
}

package com.dev.managementsystem.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticsDto {
    private double averageAge;
    private double standardDeviation;

    public StatisticsDto(double averageAge, double standardDeviation) {
        this.averageAge = averageAge;
        this.standardDeviation = standardDeviation;
    }

}
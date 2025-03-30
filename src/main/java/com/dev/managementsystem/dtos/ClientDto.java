package com.dev.managementsystem.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private LocalDate birthDate;

    @Schema(description = "Estimated life expectancy date of the client", example = "2074-05-10")
    private LocalDate estimatedLifeExpectancy;
}

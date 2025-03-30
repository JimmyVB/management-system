package com.dev.managementsystem.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreateDto {

    @Schema(description = "Client name", example = "Juan")
    @NotBlank(message = "Name cannot be empty")
    private String firstName;

    @Schema(description = "Last name of the client", example = "Pérez")
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Schema(description = "Years of the client", example = "27")
    @Min(value = 0, message = "Years must be greater than 0")
    private int age;

    @Schema(description = "Client's birth date", example = "1994-05-10")
    @NotNull(message = "Birth date cannot be empty")
    private LocalDate birthDate;
}


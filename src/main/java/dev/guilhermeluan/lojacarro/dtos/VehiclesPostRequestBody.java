package dev.guilhermeluan.lojacarro.dtos;

import dev.guilhermeluan.lojacarro.model.VehicleType;
import jakarta.validation.constraints.NotBlank;

public record VehiclesPostRequestBody(
        @NotBlank
        VehicleType type,
        @NotBlank
        String model,
        @NotBlank
        String color,
        @NotBlank
        Double price,
        @NotBlank
        int year
) { }

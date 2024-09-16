package dev.guilhermeluan.lojacarro.dtos.request;

import dev.guilhermeluan.lojacarro.model.VehicleType;
import jakarta.validation.constraints.NotBlank;

public record VehiclesPutRequest(
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
) {
}

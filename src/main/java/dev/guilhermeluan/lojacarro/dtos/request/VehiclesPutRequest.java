package dev.guilhermeluan.lojacarro.dtos.request;

import dev.guilhermeluan.lojacarro.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehiclesPutRequest(
        @NotNull
        Long id,
        @NotNull
        VehicleType type,
        @NotBlank
        String model,
        @NotBlank
        String color,
        @NotNull
        Double price,
        @NotNull
        int year
) {
}

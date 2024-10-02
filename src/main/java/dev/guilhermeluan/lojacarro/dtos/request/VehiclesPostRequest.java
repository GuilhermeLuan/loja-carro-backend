package dev.guilhermeluan.lojacarro.dtos.request;

import dev.guilhermeluan.lojacarro.model.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehiclesPostRequest(
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

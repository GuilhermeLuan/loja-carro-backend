package dev.guilhermeluan.lojacarro.dtos.response;

import dev.guilhermeluan.lojacarro.model.VehicleType;

public record VehiclesGetResponse(
        Long id,
        VehicleType type,
        String model,
        String color,
        Double price,
        int year
) {
}

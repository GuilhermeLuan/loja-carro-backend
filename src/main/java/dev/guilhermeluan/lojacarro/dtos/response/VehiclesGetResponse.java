package dev.guilhermeluan.lojacarro.dtos.response;

import dev.guilhermeluan.lojacarro.model.enums.Vehicle.VehicleBrand;
import dev.guilhermeluan.lojacarro.model.enums.Vehicle.VehicleType;

public record VehiclesGetResponse(
        Long id,
        VehicleType type,
        String model,
        String color,
        VehicleBrand brand,
        Double price,
        int year,
        String imageLink
) {
}

package dev.guilhermeluan.lojacarro.dtos.response;

import dev.guilhermeluan.lojacarro.model.enums.Vehicle.VehicleBrand;
import dev.guilhermeluan.lojacarro.model.enums.Vehicle.VehicleType;

public record VehiclesPostResponse(
        Long id,
        VehicleType type,
        String model,
        VehicleBrand brand,
        String color,
        Double price,
        int year,
        String imageLink

) {
}

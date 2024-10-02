package dev.guilhermeluan.lojacarro.dtos.response;

import dev.guilhermeluan.lojacarro.model.enums.VehicleBrand;
import dev.guilhermeluan.lojacarro.model.enums.VehicleType;

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

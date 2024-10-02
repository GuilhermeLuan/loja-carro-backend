package dev.guilhermeluan.lojacarro.dtos.response;

import dev.guilhermeluan.lojacarro.model.enums.VehicleType;

public record VehiclesPostResponse(
        Long id,
        VehicleType type,
        String model,
        String color,
        Double price,
        int year
) { }

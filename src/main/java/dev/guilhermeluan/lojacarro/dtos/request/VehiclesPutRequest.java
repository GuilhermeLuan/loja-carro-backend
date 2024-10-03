package dev.guilhermeluan.lojacarro.dtos.request;

import dev.guilhermeluan.lojacarro.model.enums.VehicleBrand;
import dev.guilhermeluan.lojacarro.model.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

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
        VehicleBrand brand,
        @NotNull
        Double price,
        @NotNull
        int year,
        @NotBlank @URL(protocol = "URL")
        String imageLink
) {
}

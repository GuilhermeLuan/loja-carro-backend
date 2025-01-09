package dev.guilhermeluan.lojacarro.dtos.request;

import dev.guilhermeluan.lojacarro.model.enums.VehicleBrand;
import dev.guilhermeluan.lojacarro.model.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record VehiclesPutRequest(
        @NotNull(message = "The field 'id' is required")
        Long id,
        @NotNull(message = "The field 'VehicleType' is required")
        VehicleType type,
        @NotBlank(message = "The field 'Model' is required")
        String model,
        @NotBlank(message = "The field 'Color' is required")
        String color,
        @NotNull(message = "The field 'Brand' is required")
        VehicleBrand brand,
        @NotNull(message = "The field 'Price' is required")
        Double price,
        @NotNull(message = "The field 'Year' is required")
        Integer year,
        @NotBlank(message = "The field 'ImageLink' is required")
        @URL(protocol = "https", message = "The field 'ImageLink' must be a valid URL")
        String imageLink
) {
}

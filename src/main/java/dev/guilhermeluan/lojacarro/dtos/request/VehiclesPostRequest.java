package dev.guilhermeluan.lojacarro.dtos.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.guilhermeluan.lojacarro.exception.VehiclesEnumDeserializer;
import dev.guilhermeluan.lojacarro.model.enums.VehicleBrand;
import dev.guilhermeluan.lojacarro.model.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record VehiclesPostRequest(
        @NotNull(message = "The field 'VehicleType' is required")
        @JsonDeserialize(using = VehiclesEnumDeserializer.class)
        VehicleType type,
        @NotBlank(message = "The field 'Model' is required")
        String model,
        @NotBlank(message = "The field 'Color' is required")
        String color,
        @NotNull(message = "The field 'Brand' is required")
        @JsonDeserialize(using = VehiclesEnumDeserializer.class)
        VehicleBrand brand,
        @NotNull(message = "The field 'Price' is required")
        Double price,
        @NotNull(message = "The field 'Year' is required")
        int year,
        @NotBlank(message = "The field 'ImageLink' is required")
        @URL(protocol = "http", message = "The field 'ImageLink' must be a valid URL")
        String imageLink
) {
}


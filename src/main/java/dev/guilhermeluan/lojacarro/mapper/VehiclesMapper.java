package dev.guilhermeluan.lojacarro.mapper;

import dev.guilhermeluan.lojacarro.dtos.VehiclesPostRequestBody;
import dev.guilhermeluan.lojacarro.dtos.VehiclesPutRequestBody;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehiclesMapper {
    VehiclesMapper INSTANCE = Mappers.getMapper(VehiclesMapper.class);

    Vehicles toVehicles(VehiclesPostRequestBody vehiclesPostRequestBody);
    Vehicles toVehicles(VehiclesPutRequestBody vehiclesPutRequestBody);
}

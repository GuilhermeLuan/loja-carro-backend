package dev.guilhermeluan.lojacarro.mapper;

import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPostRequest;
import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPutRequest;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesGetResponse;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesPostResponse;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehiclesMapper {
    VehiclesMapper INSTANCE = Mappers.getMapper(VehiclesMapper.class);

    Vehicles toVehicles(VehiclesPostRequest vehiclesPostRequestBody);
    Vehicles toVehicles(VehiclesPutRequest vehiclesPutRequestBody);

    VehiclesPostResponse toVehiclesPostResponse(Vehicles vehicles);
    VehiclesGetResponse toVehiclesGetResponse(Vehicles vehicles);
}

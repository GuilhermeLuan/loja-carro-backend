package dev.guilhermeluan.lojacarro.commons;

import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.model.enums.VehicleBrand;
import dev.guilhermeluan.lojacarro.model.enums.VehicleType;
import dev.guilhermeluan.lojacarro.service.VehiclesService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class VehicleUtils {
    private final VehiclesService service;

    public List<Vehicles> newVehicleList() {
        var sedan = Vehicles.builder()
                .id(1L)
                .type(VehicleType.AUTOMOVEL)
                .model("Sedan")
                .color("Red")
                .brand(VehicleBrand.BMW)
                .price(25000.0)
                .year(2021)
                .imageLink("https://img.freepik.com/free-vector/red-sedan-car-isolated-white-vector_53876-64366.jpg")
                .build();

        var suv = Vehicles.builder()
                .id(2L)
                .type(VehicleType.AUTOMOVEL)
                .model("SUV")
                .color("Black")
                .price(35000.0)
                .brand(VehicleBrand.BMW)
                .year(2023)
                .imageLink("https://img.freepik.com/free-vector/red-sedan-car-isolated-white-vector_53876-64366.jpg")
                .build();

        var truck = Vehicles.builder()
                .id(3L)
                .type(VehicleType.ONIBUS)
                .model("Truck")
                .color("Blue")
                .price(45000.0)
                .brand(VehicleBrand.BMW)
                .year(2022)
                .imageLink("https://img.freepik.com/free-vector/red-sedan-car-isolated-white-vector_53876-64366.jpg")
                .build();

        return List.of(sedan, suv, truck);
    }

    public Vehicles newVehicleToSave() {
        return Vehicles.builder()
                .id(1L)
                .type(VehicleType.AUTOMOVEL)   // Supondo que VehicleType seja um enum com CAR, BIKE, etc.
                .model("Toyota Corolla")
                .color("Preto")
                .brand(VehicleBrand.TOYOTA)
                .price(85000.00)
                .year(2020)
                .imageLink("https://img.freepik.com/free-vector/red-sedan-car-isolated-white-vector_53876-64366.jpg")
                .build();
    }
}

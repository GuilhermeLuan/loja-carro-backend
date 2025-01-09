package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPostRequest;
import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPutRequest;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesGetResponse;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesPostResponse;
import dev.guilhermeluan.lojacarro.mapper.VehiclesMapper;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.service.VehiclesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/v1/vehicles")
public class VehiclesControllerImpl implements VehiclesControllerI {
    private static final VehiclesMapper MAPPPER = VehiclesMapper.INSTANCE;
    private final VehiclesService service;

    @GetMapping
    @Override
    public ResponseEntity<Page<Vehicles>> listAll(@RequestParam(required = false) String model, Pageable pageable) {
        var vehicles = service.findALl(pageable, model);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<VehiclesGetResponse> listById(@PathVariable Long id) {
        var vehicles = service.findByIdOrThrowBadRequestException(id);

        var response = MAPPPER.toVehiclesGetResponse(vehicles);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Override
    public ResponseEntity<VehiclesPostResponse> save(@RequestBody @Valid VehiclesPostRequest request) {
        var vehicles = MAPPPER.toVehicles(request);
        var savedVehicle = service.save(vehicles);

        var response = MAPPPER.toVehiclesPostResponse(savedVehicle);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> update(@RequestBody @Valid VehiclesPutRequest request) {
        var vehicleToUpdate = MAPPPER.toVehicles(request);

        service.update(vehicleToUpdate);

        return ResponseEntity.noContent().build();
    }
}

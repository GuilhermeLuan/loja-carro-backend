package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPostRequest;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesGetResponse;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesPostResponse;
import dev.guilhermeluan.lojacarro.mapper.VehiclesMapper;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.service.VehiclesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/v1/vehicles")
public class VehiclesController {
    private static final VehiclesMapper MAPPPER = VehiclesMapper.INSTANCE;
    private final VehiclesService service;

    @GetMapping
    public ResponseEntity<Page<Vehicles>> listAll(@RequestParam(required = false) String model, Pageable pageable) {
        var vehicles = service.findALl(pageable, model);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiclesGetResponse> listById(@PathVariable Long id) {
         var vehicles = service.findByIdOrThrowBadRequestException(id);

        var response = MAPPPER.toVehiclesGetResponse(vehicles);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<VehiclesPostResponse> save(@RequestBody VehiclesPostRequest request) {
        var vehicles = MAPPPER.toVehicles(request);
        var savedVehicle = service.save(vehicles);

        var response = MAPPPER.toVehiclesPostResponse(savedVehicle);

        return ResponseEntity.ok(response);
    }


}

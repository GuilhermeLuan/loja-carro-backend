package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.dtos.VehiclesPostRequestBody;
import dev.guilhermeluan.lojacarro.mapper.VehiclesMapper;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.service.VehiclesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/v1/vehicles")
public class VehiclesController {
    private static final VehiclesMapper MAPPPER = VehiclesMapper.INSTANCE;
    private final VehiclesService vehiclesService;

    @GetMapping
    public ResponseEntity<Page<Vehicles>> listAllPageable(Pageable pageable) {
        var vehicles = vehiclesService.listAll(pageable);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vehicles>> listAllNonPageable() {
        var vehicles = vehiclesService.listAllNonPageable();
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    public ResponseEntity<Vehicles> save(@RequestBody VehiclesPostRequestBody vehiclesPostRequestBody) {
        var savedVehicle = vehiclesService.save(vehiclesPostRequestBody);
        return ResponseEntity.ok(savedVehicle);
    }

}

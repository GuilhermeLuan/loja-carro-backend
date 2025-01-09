package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPostRequest;
import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPutRequest;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesGetResponse;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesPostResponse;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface VehiclesControllerI {
    @GetMapping
    ResponseEntity<Page<Vehicles>> listAll(@RequestParam(required = false) String model, Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<VehiclesGetResponse> listById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<VehiclesPostResponse> save(@RequestBody @Valid VehiclesPostRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PutMapping
    ResponseEntity<Void> update(@RequestBody @Valid VehiclesPutRequest request);
}

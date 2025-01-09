package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPostRequest;
import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPutRequest;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesGetResponse;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesPostResponse;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Vehicles API", description = "Vehicles API endpoints")

public interface VehiclesControllerI {
    @GetMapping
    @Operation(summary = "Get all vehicles", description = "Get all vehicles available in the system",
            responses = {
                    @ApiResponse(description = "List all vehicles",
                            responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = VehiclesGetResponse.class)))

                    )
            })

    ResponseEntity<Page<Vehicles>> listAll(@Parameter(description = "models of vehicles to be searched") @RequestParam(required = false) String model, @ParameterObject Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<VehiclesGetResponse> listById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<VehiclesPostResponse> save(@RequestBody @Valid VehiclesPostRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PutMapping
    ResponseEntity<Void> update(@RequestBody @Valid VehiclesPutRequest request);
}

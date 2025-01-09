package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPostRequest;
import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPutRequest;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesGetResponse;
import dev.guilhermeluan.lojacarro.dtos.response.VehiclesPostResponse;
import dev.guilhermeluan.lojacarro.exception.DefaultErrorMessage;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    @Operation(summary = "Get a vehicle by its id", responses = {
            @ApiResponse(description = "Get vehicle by its id",
                    responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VehiclesGetResponse.class))

            ),
            @ApiResponse(description = "Vehicle Not Found",
                    responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultErrorMessage.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "message": "Vehicle not found",
                                      "status": 404
                                    }
                                    """))
            )
    })
    ResponseEntity<VehiclesGetResponse> listById(@Parameter(description = "id of vehicle to be searched") @PathVariable Long id);

    @PostMapping
    @Operation(summary = "Create a new vehicle",
            responses = {
                    @ApiResponse(description = "Creates a vehicle",
                            responseCode = "201",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VehiclesPostResponse.class))

                    ),
                    @ApiResponse(description = "Bad Request",
                            responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultErrorMessage.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "message": "The field 'ImageLink' is required",
                                              "status": 400
                                            }
                                            """))

                    )
            })
    ResponseEntity<VehiclesPostResponse> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Vehicle to be created",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VehiclesPostRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "AUTOMOVEL",
                                      "model": "Civic",
                                      "color": "Blue",
                                      "brand": "HONDA",
                                      "price": 30000.00,
                                      "year": 2022,
                                      "imageLink": "https://www.example.com"
                                    }
                                    """)))

            @RequestBody @Valid VehiclesPostRequest request);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vehicle by its id", responses = {
            @ApiResponse(description = "Delete a vehicle by its id",
                    responseCode = "204"
            ),
            @ApiResponse(description = "Vehicle Not Found",
                    responseCode = "404",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultErrorMessage.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "message": "Vehicle not found",
                                      "status": 404
                                    }
                                    """))
            )
    })
    ResponseEntity<Void> delete(@Parameter(description = "id of vehicle to be delete") @PathVariable Long id);

    @PutMapping
    @Operation(summary = "Update a vehicle",
            responses = {
                    @ApiResponse(description = "Updates a vehicle, returns no content",
                            responseCode = "204"

                    ),
                    @ApiResponse(description = "Bad Request",
                            responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultErrorMessage.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "message": "The field 'ImageLink' is required",
                                              "status": 400
                                            }
                                            """))

                    ),
                    @ApiResponse(description = "Vehicle Not Found",
                            responseCode = "404",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultErrorMessage.class),
                                    examples = @ExampleObject(value = """
                                            {
                                              "message": "Vehicle not found",
                                              "status": 404
                                            }
                                            """))
                    )
            })
    ResponseEntity<Void> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Vehicle to be created",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VehiclesPutRequest.class),
                            examples = @ExampleObject(value = """
                                            {
                                               "id": 14,
                                               "type": "AUTOMOVEL",
                                               "model": "Civic",
                                               "color": "Blue",
                                               "brand": "HONDA",
                                               "price": 30000.00,
                                               "year": 2022,
                                               "imageLink": "https://www.example.com"
                                             }
                                    """)))

            @RequestBody @Valid VehiclesPutRequest request);
}

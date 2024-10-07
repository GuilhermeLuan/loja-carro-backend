package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.commons.FileUtils;
import dev.guilhermeluan.lojacarro.commons.VehicleUtils;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.repositories.VehiclesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VehiclesController.class)
@ComponentScan(basePackages = "dev.guilhermeluan.lojacarro")
class VehiclesControllerTest {
    private static final String URL = "/v1/vehicles";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private VehicleUtils vehicleUtils;
    private List<Vehicles> vehiclesList;
    @MockBean
    private VehiclesRepository repository;

    @BeforeEach
    void setUp() {
        vehiclesList = vehicleUtils.newVehicleList();
    }

    @Test
    @DisplayName("GET /v1/vehicles/{id} returns vehicle with given id")
    void findById_ReturnsVehicle_WithGivenId() throws Exception {
        var response = fileUtils.readResourceFile("vehicles/get/get-vehicle-by-id-200.json");
        Long id = 1l;
        var vehicleFound = vehiclesList.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(vehicleFound);

        mockMvc.perform(get(URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    @DisplayName("GET /v1/vehicles/{id} throws bad request exception when vehicle is not found")
    void findById_ThrowsBadRequestException_WhenVehicleIsNotFound() throws Exception {
        var response = fileUtils.readResourceFile("vehicles/get/get-vehicle-by-id-404.json");
        Long id = 99l;

        mockMvc.perform(get(URL + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(response));
    }

    @Test
    @DisplayName("POST /v1/vehicles creates vehicles when successful")
    void save_CreatesVehicle_WhenSuccessful() throws Exception {
        var request = fileUtils.readResourceFile("vehicles/post/post-vehicle-200-request.json");
        var response = fileUtils.readResourceFile("vehicles/post/post-vehicle-200-response.json");
        var userToSave = vehicleUtils.newVehicle();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(userToSave);

        mockMvc.perform(post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(response));
    }

    @Test
    @DisplayName("DELETE /v1/vehicles/{id} removes vehicle with given id")
    void delete_RemovesVehicle_WithGivenId() throws Exception {
        Long id = vehiclesList.get(0).getId();

        var vehicleToDelete = vehiclesList.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(vehicleToDelete);
        BDDMockito.doNothing().when(repository).deleteById(id);

        mockMvc.perform(delete(URL + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /v1/vehicles/{id} throws Not Found exception when vehicle is not found")
    void delete_ThrowsNotFoundException_WhenVehicleIsNotFound() throws Exception {
        var response = fileUtils.readResourceFile("vehicles/delete/delete-vehicle-by-id-404.json");
        Long id = 99l;

        mockMvc.perform(delete(URL + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(response));
    }

    @Test
    @DisplayName("PUT /v1/vehicles updates vehicles when successful")
    void update_UpdatesVehicle_WhenSuccessful() throws Exception {
        Long id = vehiclesList.get(0).getId();

        var request = fileUtils.readResourceFile("vehicles/put/put-vehicle-200-request.json");
        var foundVehicle = vehiclesList.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst();

        BDDMockito.when(repository.findById(id)).thenReturn(foundVehicle);

        mockMvc.perform(put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PUT /v1/vehicles throws Not Found exception when vehicle is not found")
    void update_ThrowsNotFoundException_WhenVehicleIsNotFound() throws Exception {
        var request = fileUtils.readResourceFile("vehicles/put/put-vehicle-404-request.json");
        var response = fileUtils.readResourceFile("vehicles/put/put-vehicle-404-response.json");

        mockMvc.perform(put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(response));
    }


    private static List<String> invalidImageLink(){
        var imageLinkUrlError = "The field 'ImageLink' must be a valid URL";
        return List.of(imageLinkUrlError);
    }

    private static List<String> allRequiredErrors() {
        var vehicleTypeRequiredError = "The field 'VehicleType' is required";
        var modelRequiredError = "The field 'Model' is required";
        var colorRequiredError = "The field 'Color' is required";
        var brandRequiredError = "The field 'Brand' is required";
        var priceRequiredError = "The field 'Price' is required";
        var yearRequiredError = "The field 'Year' is required";
        var imageLinkRequiredError = "The field 'ImageLink' is required";


        return new ArrayList<>(List.of(
                vehicleTypeRequiredError,
                modelRequiredError,
                colorRequiredError,
                brandRequiredError,
                priceRequiredError,
                yearRequiredError,
                imageLinkRequiredError
        ));
    }
}
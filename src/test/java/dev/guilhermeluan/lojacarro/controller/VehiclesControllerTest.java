package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.commons.FileUtils;
import dev.guilhermeluan.lojacarro.commons.VehicleUtils;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.repositories.VehiclesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @ParameterizedTest
    @MethodSource("postVehiclesBadRequest")
    @DisplayName("POST /v1/vehicles returns bad request when fields are invalid")
    void save_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("vehicles/post/%s".formatted(fileName));

        MvcResult mvcResult = mockMvc.perform(post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();
        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    @ParameterizedTest
    @MethodSource("putVehiclesBadRequest")
    @DisplayName("PUT v1/users returns bad request when fields are invalid")
    void update_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("vehicles/put/%s".formatted(fileName));

        MvcResult mvcResult = mockMvc.perform(put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();
        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    private static Stream<Arguments> putVehiclesBadRequest() {
        List<String> allRequiredErros = allRequiredErrors();
        allRequiredErros.add("The field 'id' is required");
        List<String> invalidUrlLink = invalidUrlLink();

        return Stream.of(
                Arguments.of("put-vehicle-blank-fields-request.json", allRequiredErros),
                Arguments.of("put-vehicle-empty-fields-request.json", allRequiredErros),
                Arguments.of("put-vehicle-invalid-url-request.json", invalidUrlLink)
        );
    }


    private static Stream<Arguments> postVehiclesBadRequest() {
        List<String> allRequiredErrors = allRequiredErrors();
        List<String> invalidUrlLink = invalidUrlLink();

        return Stream.of(
                Arguments.of("post-vehicle-blank-fields-request.json", allRequiredErrors),
                Arguments.of("post-vehicle-empty-fields-request.json", allRequiredErrors),
                Arguments.of("post-vehicle-invalid-url-request.json", invalidUrlLink));
    }

    private static List<String> invalidUrlLink() {
        var invalidUrlLink = "The field 'ImageLink' must be a valid URL";

        return List.of(invalidUrlLink);
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
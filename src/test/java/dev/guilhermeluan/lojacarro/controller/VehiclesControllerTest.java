package dev.guilhermeluan.lojacarro.controller;

import dev.guilhermeluan.lojacarro.commons.FileUtils;
import dev.guilhermeluan.lojacarro.service.VehiclesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
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
    @MockBean
    private VehiclesService vehiclesService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("GET /v1/vehicles/{id} returns vehicle with given id")
    void findById_ReturnsVehicle_WithGivenId() throws Exception {
        var response = fileUtils.readResourceFile("vehicles/get/get-vehicle-by-id-200.json");

        Long id = 1l;
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }
}
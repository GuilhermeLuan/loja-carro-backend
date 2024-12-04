package dev.guilhermeluan.lojacarro.service;

import dev.guilhermeluan.lojacarro.commons.VehicleUtils;
import dev.guilhermeluan.lojacarro.exception.NotFoundException;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.repositories.VehiclesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VehiclesServiceTest {
    @InjectMocks
    private VehiclesService service;

    @Mock
    private VehiclesRepository repository;
    private List<Vehicles> vehiclesList;

    @InjectMocks
    private VehicleUtils vehicleUtils;

    @BeforeEach
    void setUp() {
        vehiclesList = vehicleUtils.newVehicleList();
    }

    @Test
    @DisplayName("findAll returns list of vehicle inside page object when argument is null")
    void findAll_ReturnsListOfVehiclesInsidePagesObject_WhenArgumentIsNull() {
        var expectedId = vehiclesList.get(0).getId();

        PageImpl<Vehicles> vehiclesPage = new PageImpl<>(vehiclesList);

        BDDMockito.when(repository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(vehiclesPage);

        Page<Vehicles> vehiclePage = service.findALl(PageRequest.of(1, 1), null);

        Assertions.assertThat(vehiclePage).isNotNull();
        Assertions.assertThat(vehiclePage.toList()).isEqualTo(vehiclesList);
        Assertions.assertThat(vehiclePage.toList()).isNotEmpty().hasSize(vehiclesList.size());
        Assertions.assertThat(vehiclePage.toList().get(0).getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findAll returns list of vehicle inside page object when model existis")
    void findAll_ReturnsListOfVehiclesInsidePagesObject_WhenModelExistis() {
        var vehicles = vehiclesList.get(0);
        var expectedVehicleFounds = Collections.singletonList(vehicles);

        PageImpl<Vehicles> vehiclesPage = new PageImpl<>(expectedVehicleFounds);

        BDDMockito.when(repository.findByModelIgnoreCase(ArgumentMatchers.any(String.class), ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(vehiclesPage);

        Page<Vehicles> vehiclePage = service.findALl(PageRequest.of(1, 1), expectedVehicleFounds.get(0).getModel());

        Assertions.assertThat(vehiclePage).isNotNull();
        Assertions.assertThat(vehiclePage).containsAll(expectedVehicleFounds);
        Assertions.assertThat(vehiclePage.toList().get(0).getModel()).isEqualTo(expectedVehicleFounds.get(0).getModel());
    }

    @Test
    @DisplayName("findAll returns an empty list when model is not found")
    void findAll_ReturnsEmptyList_WhenVehicleIsNotFound() {
        var vehicles = vehiclesList.get(0);
        PageImpl<Vehicles> vehiclesPage = new PageImpl<>(Collections.emptyList());

        BDDMockito.when(repository.findByModelIgnoreCase(ArgumentMatchers.any(String.class), ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(vehiclesPage);

        Page<Vehicles> vehiclePage = service.findALl(PageRequest.of(1, 1), vehicles.getModel());

        Assertions.assertThat(vehiclePage).isNotNull();
        Assertions.assertThat(vehiclePage.getContent()).isEmpty();
    }

    @Test
    @DisplayName("save saves vehicle when successful")
    void save_ReturnsVehicle_WhenVehicleSaved() {
        var vehicleToSave = vehicleUtils.newVehicle();

        BDDMockito.when(repository.save(vehicleToSave)).thenReturn(vehicleToSave);

        var vehicleSaved = service.save(vehicleToSave);

        Assertions.assertThat(vehicleSaved).isNotNull().isEqualTo(vehicleToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("findById returns a vehicle with given id")
    void findById_ReturnsVehicle_WhenIdExists() {
        var vehicleExpected = vehiclesList.get(0);

        BDDMockito.when(repository.findById(vehicleExpected.getId())).thenReturn(Optional.of(vehicleExpected));

        var vehicleFound = service.findByIdOrThrowBadRequestException(vehicleExpected.getId());

        Assertions.assertThat(vehicleFound).isNotNull().isEqualTo(vehicleExpected).hasNoNullFieldsOrPropertiesExcept();

    }

    @Test
    @DisplayName("findById throws a BadRequestException when vehicle is not found")
    void findById_ReturnBadRequestException_WhenIdIsNotFound() {
        var vehicleExpected = vehiclesList.get(0);

        BDDMockito.when(repository.findById(vehicleExpected.getId())).thenThrow(NotFoundException.class);

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowBadRequestException(vehicleExpected.getId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("delete removes an vehicle when is successful ")
    void delete_RemovesVehicle_WhenIsSuccessful() {
        var vehiclesToDelete = vehiclesList.get(0);

        BDDMockito.when(repository.findById(vehiclesToDelete.getId())).thenReturn(Optional.of(vehiclesToDelete));

        Assertions.assertThatNoException().isThrownBy(() -> service.deleteById(vehiclesToDelete.getId()));
    }

    @Test
    @DisplayName("delete throws BadRequestException when vehicle is not found")
    void delete_ThrowsBadRequestException_WhenVehiclesIsNotFound() {
        var vehiclesToDelete = vehiclesList.get(0);

        BDDMockito.when(repository.findById(vehiclesToDelete.getId())).thenThrow(NotFoundException.class);

        Assertions.assertThatException()
                .isThrownBy(() -> service.deleteById(vehiclesToDelete.getId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("update updates an vehicle when is successful ")
    void update_UpdatesVehicle_WhenIsSuccessful() {
        var vehiclesToUpdate = vehiclesList.get(0);
        vehiclesToUpdate.setModel("New Model");

        BDDMockito.when(repository.findById(vehiclesToUpdate.getId())).thenReturn(Optional.of(vehiclesToUpdate));
        BDDMockito.when(repository.save(vehiclesToUpdate)).thenReturn(vehiclesToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(vehiclesToUpdate));
    }

    @Test
    @DisplayName("update throws BadRequestException when vehicle is not found")
    void update_ThrowsRBadRequestException_WhenVehiclesIsNotFound() {
        var vehiclesToUpdate = vehiclesList.get(0);
        vehiclesToUpdate.setModel("New Model");


        BDDMockito.when(repository.findById(vehiclesToUpdate.getId())).thenThrow(NotFoundException.class);

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(vehiclesToUpdate))
                .isInstanceOf(NotFoundException.class);
    }
}
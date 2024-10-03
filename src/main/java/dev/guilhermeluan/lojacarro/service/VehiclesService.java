package dev.guilhermeluan.lojacarro.service;

import dev.guilhermeluan.lojacarro.exception.NotFoundException;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.repositories.VehiclesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehiclesService {
    private final VehiclesRepository repository;

    public Page<Vehicles> findALl(Pageable pageable, String param) {
        return param == null ? repository.findAll(pageable) : repository.findByModelIgnoreCase(param, pageable);
    }

    public Vehicles save(Vehicles vehicle) {
        return repository.save(vehicle);
    }

    public Vehicles findByIdOrThrowBadRequestException(Long id) {
        return repository.findById(id).
                orElseThrow(() -> new NotFoundException("Vehicle not found"));
    }

    public void deleteById(Long id) {
        assertVehicleExist(id);
        repository.deleteById(id);
    }

    public void update(Vehicles vehicle) {
        assertVehicleExist(vehicle.getId());
        repository.save(vehicle);
    }

    private void assertVehicleExist(Long id) {
        findByIdOrThrowBadRequestException(id);
    }
}

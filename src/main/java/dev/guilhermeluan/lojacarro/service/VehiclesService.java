package dev.guilhermeluan.lojacarro.service;

import dev.guilhermeluan.lojacarro.dtos.VehiclesPostRequestBody;
import dev.guilhermeluan.lojacarro.exception.BadRequestException;
import dev.guilhermeluan.lojacarro.mapper.VehiclesMapper;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.repositories.VehiclesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiclesService {
    private static final VehiclesMapper MAPPPER = VehiclesMapper.INSTANCE;
    private final VehiclesRepository vehiclesRepository;

    public Page<Vehicles> listAll(Pageable pageable) {
        return vehiclesRepository.findAll(pageable);
    }

    public List<Vehicles> listAllNonPageable() {
        return vehiclesRepository.findAll();
    }

    public List<Vehicles> listByModel(String model) {
        return vehiclesRepository.findByModel(model);
    }

    public Vehicles save(VehiclesPostRequestBody request) {
        var vehicles = MAPPPER.toVehicles(request);
        return vehiclesRepository.save(vehicles);
    }

    public Vehicles findByIdOrThrowBadRequestException(Long id) {
        return vehiclesRepository.findById(id).
                orElseThrow(() -> new BadRequestException("Vehicle not found"));
    }
}

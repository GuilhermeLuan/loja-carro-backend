package dev.guilhermeluan.lojacarro.service;

import dev.guilhermeluan.lojacarro.dtos.request.VehiclesPostRequest;
import dev.guilhermeluan.lojacarro.exception.BadRequestException;
import dev.guilhermeluan.lojacarro.mapper.VehiclesMapper;
import dev.guilhermeluan.lojacarro.model.Vehicles;
import dev.guilhermeluan.lojacarro.repositories.VehiclesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehiclesService {
    private static final VehiclesMapper MAPPPER = VehiclesMapper.INSTANCE;
    private final VehiclesRepository vehiclesRepository;

    public Page<Vehicles> findALl(Pageable pageable, String param) {
        return param == null ? vehiclesRepository.findAll(pageable) : vehiclesRepository.findByModelIgnoreCase(param, pageable);
    }

    public Vehicles save(Vehicles vehicle) {
        return vehiclesRepository.save(vehicle);
    }

    public Vehicles findByIdOrThrowBadRequestException(Long id) {
        return vehiclesRepository.findById(id).
                orElseThrow(() -> new BadRequestException("Vehicle not found"));
    }
}

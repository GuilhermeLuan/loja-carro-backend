package dev.guilhermeluan.lojacarro.repositories;

import dev.guilhermeluan.lojacarro.model.Vehicles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiclesRepository extends JpaRepository<Vehicles, Long> {
    Page<Vehicles> findByModelIgnoreCase(String model, Pageable pageable);
}

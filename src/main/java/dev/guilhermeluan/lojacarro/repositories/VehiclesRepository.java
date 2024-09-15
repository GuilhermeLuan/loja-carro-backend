package dev.guilhermeluan.lojacarro.repositories;

import dev.guilhermeluan.lojacarro.model.Vehicles;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface VehiclesRepository extends JpaRepository<Vehicles, Long> {
    List<Vehicles> findByModel(String model);
}

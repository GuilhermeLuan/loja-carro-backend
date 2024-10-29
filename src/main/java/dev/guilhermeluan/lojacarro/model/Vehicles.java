package dev.guilhermeluan.lojacarro.model;

import dev.guilhermeluan.lojacarro.model.enums.Vehicle.VehicleBrand;
import dev.guilhermeluan.lojacarro.model.enums.Vehicle.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "tb_vehicles")
public class Vehicles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private String model;
    @Enumerated(EnumType.STRING)
    private VehicleBrand brand;
    private String color;
    private Double price;
    private int year;
    @Column(name = "image_link")
    private String imageLink;
}


package com.fiveguys.robocar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;
    private Double longitude;

    public Garage(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void edit(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

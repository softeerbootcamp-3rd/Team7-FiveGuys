package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.GarageReqDto;
import com.fiveguys.robocar.entity.Garage;
import com.fiveguys.robocar.repository.GarageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GarageService {
    private final GarageRepository garageRepository;

    @Transactional
    public void insertGarage(GarageReqDto garageReqDto) {
        Double latitude = garageReqDto.getLatitude();
        Double longitude = garageReqDto.getLongitude();

        Garage findGarage = garageRepository.findByLatitudeAndLongitude(latitude, longitude)
                .orElseThrow(() -> new IllegalArgumentException(ResponseStatus.GARAGE_ALREADY_EXIST.getMessage()));

        if (findGarage != null) {
            throw new IllegalArgumentException(ResponseStatus.GARAGE_ALREADY_EXIST.getMessage());
        }

        Garage garage = new Garage(latitude, longitude);
        garageRepository.save(garage);
    }

    public Garage getGarage(Long id) {
        Garage findGarage = garageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.GARAGE_NOT_FOUND.getMessage()));

        return findGarage;
    }

    public List getGarageAll() {
        return garageRepository.findAll();
    }

    @Transactional
    public Garage editGarage(Long id, GarageReqDto garageReqDto) {
        Garage findGarage = garageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.GARAGE_NOT_FOUND.getMessage()));

        Double updateLatitude = garageReqDto.getLatitude();
        Double updateLongitude = garageReqDto.getLongitude();
        if (findGarage.getLatitude() == updateLatitude
                && findGarage.getLongitude() == updateLongitude) {
            throw new IllegalArgumentException(ResponseStatus.GARAGE_ALREADY_EXIST.getMessage());
        }

        findGarage.edit(updateLatitude, updateLongitude);

        return findGarage;
    }

    @Transactional
    public void deleteGarage(Long id) {
        Garage findGarage = garageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.GARAGE_NOT_FOUND.getMessage()));

        garageRepository.delete(findGarage);
    }
}

package com.fiveguys.robocar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fiveguys.robocar.dto.req.GarageReqDto;
import com.fiveguys.robocar.entity.Garage;현
import com.fiveguys.robocar.repository.GarageRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GarageServiceTest {

    @Mock
    private GarageRepository garageRepository;

    @InjectMocks
    private GarageService garageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertGarage() {
        // given
        GarageReqDto garageReqDto1 = createDemoGarageReqDto1();
        GarageReqDto garageReqDto2 = createDemoGarageReqDto2();

        // when
        garageService.insertGarage(garageReqDto1);
        garageService.insertGarage(garageReqDto2);

        // then
        verify(garageRepository, times(2)).save(any(Garage.class));
    }

    private static GarageReqDto createDemoGarageReqDto2() {
        return new GarageReqDto(2.1, 3.3);
    }

    @Test
    public void testGetGarage() {
        // given
        Garage garage = createGarage1();
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));

        // when
        Garage foundGarage = garageService.getGarage(1L);

        // then
        assertThat(foundGarage).isEqualTo(garage);
    }

    @Test
    public void testGetGarageAll() {
        // given
        Garage garage1 = createGarage1();
        Garage garage2 = createGarage2();
        List<Garage> garages = Arrays.asList(garage1, garage2);
        when(garageRepository.findAll()).thenReturn(garages);

        // when
        List<Garage> foundGarages = garageService.getGarageAll();

        // then
        assertThat(foundGarages).isEqualTo(garages);
    }

    @Test
    public void testEditGarage() {
        // given
        Garage garage = createGarage1();
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));
        GarageReqDto garageReqDto = createDemoGarageReqDto1();

        // when
        garageService.editGarage(1L, garageReqDto);

        // then
        verify(garageRepository, times(1)).findById(1L);
        assertThat(garage.getLatitude()).isEqualTo(garageReqDto.getLatitude());
        assertThat(garage.getLongitude()).isEqualTo(garageReqDto.getLongitude());
    }

    @Test
    public void testDeleteGarage() {
        // given
        Garage garage = createGarage1();
        when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));

        // when
        garageService.deleteGarage(1L);

        // then
        verify(garageRepository, times(1)).findById(1L);
        verify(garageRepository, times(1)).delete(garage);
    }

    private static Garage createGarage1() {
        return new Garage(12.345, 543.21);
    }

    private static Garage createGarage2() {
        return new Garage(67.89, 98.76);
    }

    private static GarageReqDto createDemoGarageReqDto1() {
        return new GarageReqDto(45.67, 76.54);
    }
}

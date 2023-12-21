package com.urfu.services;

import com.urfu.domain.car.Car;
import com.urfu.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Тесты для проверки работы менеджера автомобилей
 */
public class TestCarService {
    /**
     * Тест, проверяющий получение наименований машин в виде строки
     */
    @Test
    public void testGetNamesCars() {
        CarService carService = new CarService();
        Assert.assertEquals("BMW Renault Lada", carService.getNamesCars(" "));
    }

    /**
     * Тест, проверяющий получение объекта класса {@link com.urfu.domain.car.Car} по его имени
     */
    @Test
    public void testGetCar() {
        CarService carService = new CarService();
        Storage storage = new Storage();
        List<Car> cars = storage.getListCars();
        List<String> carNames = storage.getListCars().stream().map(Car::getName).toList();
        for (int i = 0; i < cars.size(); i++) {
            Assert.assertEquals(cars.get(i), carService.getCar(carNames.get(i)));
        }
    }
}

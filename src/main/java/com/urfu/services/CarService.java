package com.urfu.services;

import com.urfu.domain.car.Car;
import com.urfu.storage.Storage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Сервис машины
 */
public class CarService {
    private final Storage storage = new Storage();

    /**
     * Возвращает список машин
     */
    private List<Car> getCars() {
        return storage.getListCars();
    }

    /**
     * Возвращает строку, содержащую имена машин через заданный символ
     *
     * @param symbol символ, который нужно вставить между названиями машин
     */
    public String getNamesCars(String symbol) {
        List<Car> cars = getCars();
        return cars.stream().map(Car::getName).collect(Collectors.joining(symbol));
    }

    /**
     * Возвращает машину по названию
     */
    public Car getCar(String name) {
        List<Car> cars = getCars();
        for (Car car : cars) {
            if (Objects.equals(car.getName(), name))
                return car;
        }
        return cars.get(0);
    }
}

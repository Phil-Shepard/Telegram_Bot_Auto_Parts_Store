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

    Storage storage = new Storage();
    /**
     * возвращает список машин
     */
    public List<Car> getCars() {
        return storage.getStorage();
    }

    /**
     * Возвращает имена машин через запятую(нужно для объявления имеющихся в магазине машин)
     */
    public String getNamesCars() {
        List<Car> cars = getCars();
        return cars.stream().map(Car::getName).collect(Collectors.joining(", "));
    }

    /**
     * Возвращает машину по названию
     */
    public Car getCar(String name) {
        List<Car> cars = getCars();
        for (Car car: cars) {
            if (Objects.equals(car.getName(), name))
                return car;
        }
        return cars.get(0);
    }
}

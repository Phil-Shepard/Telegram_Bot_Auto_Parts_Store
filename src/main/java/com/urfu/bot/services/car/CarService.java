package com.urfu.bot.services.car;

import com.urfu.bot.domain.car.Car;
import com.urfu.bot.storage.Storage;
import java.util.List;
import java.util.StringJoiner;
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
    public String GetNamesCars() {
        List<Car> cars = getCars();
        String names = cars.stream().map(Car::getName).collect(Collectors.joining(", "));
        return names;
    }

    /**
     * Возвращает машину по названию
     */
    public Car getCar(String name) {
        List<Car> cars = getCars();
        for (Car car: cars) {
            if (car.getName() == name)
                return car;
        }
        return cars.get(0);
    }
}

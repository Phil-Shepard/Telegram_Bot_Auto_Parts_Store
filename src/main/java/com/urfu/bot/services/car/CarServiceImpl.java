package com.urfu.bot.services.car;


import com.urfu.bot.domain.car.Car;
import com.urfu.bot.storage.Storage;

import java.util.List;

public class CarServiceImpl implements CarService{

    @Override
    public List<Car> getCars() {
        return new Storage().getStorage();
    }

    @Override
    public Car getCarsName(String name) {
        List<Car> cars = getCars();
        for (Car car: cars
             ) {
            if (car.getName() == name)
                return car;
        }
        return cars.get(0);
    }
}

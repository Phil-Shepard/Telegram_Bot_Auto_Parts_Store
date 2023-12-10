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
    public String GetNamesCars() {
        List<Car> cars = getCars();
        String names = "";
        for (int i = 0; i < cars.size(); i++) {
            if(i == cars.size() - 1)
                names +=" " + cars.get(i).getName().toString();
            else
                names +=" " + cars.get(i).getName().toString() + ",";
        }
        return names;
    }


    @Override
    public Car getCar(String name) {
        List<Car> cars = getCars();
        for (Car car: cars
        ) {
            if (car.getName() == name)
                return car;
        }
        return cars.get(0);
    }
}

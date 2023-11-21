package com.urfu.bot.services.car;

import com.urfu.bot.domain.car.Car;

import java.util.List;

public interface CarService {

    public List<Car> getCars();

    public String GetNamesCars();

    public Car getCar(String name);

}

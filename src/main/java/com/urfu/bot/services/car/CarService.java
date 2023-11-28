package com.urfu.bot.services.car;

import com.urfu.bot.domain.car.Car;

import java.util.List;

/**
 * Описывает машину
 */
public interface CarService {

    /**
     * Возвращает список машин, на которые есть запчасти.
     * @return
     */
    List<Car> getCars();

    /**
     * Возвращает список машин, на которые есть запчасти в формате строки.
     * @return
     */
    String GetNamesCars();

    /**
     * Возвращает машину по её имени.
     * @param name
     * @return
     */
    Car getCar(String name);

}

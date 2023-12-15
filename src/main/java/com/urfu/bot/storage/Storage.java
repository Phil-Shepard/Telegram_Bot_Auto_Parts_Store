package com.urfu.bot.storage;

import com.urfu.bot.domain.car.Car;
import com.urfu.bot.domain.spareParts.SpareParts;

import java.util.ArrayList;
import java.util.List;

/**
 * Формирует список инициализированных машин с их запчастями.
 */
public class Storage {
    private final List<Car> listCars = List.of(
            new Car("BMW", new SpareParts("колёса"), new SpareParts("фары"), new SpareParts("дворники")),
            new Car("Renault", new SpareParts("колёса"), new SpareParts("фары"), new SpareParts("дворники")),
            new Car("Lada", new SpareParts("колёса"), new SpareParts("фары"), new SpareParts("дворники")));

    /**
     * Возвращает список машин.
     *
     * @return listCars
     */
    public List<Car> getStorage() {
        return listCars;
    }
}

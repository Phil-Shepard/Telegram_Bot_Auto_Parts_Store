package com.urfu.bot.storage;

import com.urfu.bot.domain.car.Car;
import com.urfu.bot.domain.spareParts.SpareParts;

import java.util.ArrayList;
import java.util.List;

/**
 * Формирует список инициализированных машин с их запчастями.
 */
public class Storage {
    private final List<Car> listCars = new ArrayList<>();
    private final Car bmw = new Car("BMW", new SpareParts("колёса"), new SpareParts("фары"), new SpareParts("дворники"));
    private final Car renault = new Car("Renault", new SpareParts("колёса"), new SpareParts("фары"), new SpareParts("дворники"));
    private final Car lada = new Car("Lada", new SpareParts("колёса"), new SpareParts("фары"), new SpareParts("дворники"));

    /**
     * Возвращает список машин.
     * @return listCars
     */
    public List<Car> getStorage(){
        AddStorage();
        return listCars;
    }

    /**
     * Заполняет список машин
     */
    public void AddStorage(){
        if (listCars.isEmpty()){
            listCars.add(bmw);
            listCars.add(renault);
            listCars.add(lada);
        }
    }
}

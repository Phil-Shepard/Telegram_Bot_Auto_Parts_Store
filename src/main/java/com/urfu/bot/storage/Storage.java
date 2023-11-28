package com.urfu.bot.storage;

import com.urfu.bot.domain.car.Bmw;
import com.urfu.bot.domain.car.Car;
import com.urfu.bot.domain.car.Lada;
import com.urfu.bot.domain.car.Renault;
import com.urfu.bot.domain.spareParts.headlights.Headlights;
import com.urfu.bot.domain.spareParts.wheels.Wheels;
import com.urfu.bot.domain.spareParts.wipers.Wipers;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Car> listCars = new ArrayList<Car>();
    private Bmw bmw = new Bmw("BMW", new Wheels("колёса"), new Headlights("фары"), new Wipers("дворники"));
    private Renault renault = new Renault("Renault", new Wheels("колёса"), new Headlights("фары"), new Wipers("дворники"));
    private Lada lada = new Lada("Lada", new Wheels("колёса"), new Headlights("фары"), new Wipers("дворники"));

    public List<Car> getStorage(){
        listCars.clear();
        listCars.add(bmw);
        listCars.add(renault);
        listCars.add(lada);
        return listCars;
    }


}

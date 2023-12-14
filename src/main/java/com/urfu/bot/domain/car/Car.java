package com.urfu.bot.domain.car;

import com.urfu.bot.domain.spareParts.SpareParts;

/**
 * Описывает сущность машины
 */

public class Car {

    private String name;
    private SpareParts wheels;
    private SpareParts headlights;
    private SpareParts wipers;

    public Car(String name, SpareParts wheels, SpareParts headlights, SpareParts wipers){
        this.name = name;
        this.wheels = wheels;
        this.headlights = headlights;
        this.wipers = wipers;
    }

    /**
     * Возвращает название машины
     */
    public String getName(){
        return name;
    }

    /**
     * Возвращает список запчастей, которые имеются на машину в виде строки
     */
    public String getAvailabilityParts(){
        return getAvailabilitySparePart(wheels) + " " + getAvailabilitySparePart(headlights) + " " + getAvailabilitySparePart(wipers);
    }

    /**
     * Возвращает название запчасти, если она есть в наличии, в качестве аргумента принимает саму запчасть
     */
    public String getAvailabilitySparePart(SpareParts sparePart){
        if (sparePart.getName() == null)
            return "";
        return sparePart.getName();
    }
}

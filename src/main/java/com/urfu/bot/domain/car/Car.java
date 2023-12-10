package com.urfu.bot.domain.car;

import com.urfu.bot.domain.spareParts.wheels.Wheels;
import com.urfu.bot.domain.spareParts.headlights.Headlights;
import com.urfu.bot.domain.spareParts.wipers.Wipers;

/**
 * Описывает сущность машины
 */

public abstract class Car {

    protected String name;
    protected Wheels wheels;
    protected Headlights headlights;
    protected Wipers wipers;

    public Car(String name, Wheels wheels, Headlights headlights, Wipers wipers){
        this.name = name;
        this.wheels = wheels;
        this.headlights = headlights;
        this.wipers = wipers;
    }

    /**
     * Возвращает название машины
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Возвращает список запчастей, которые имеются на машину в виде строки
     * @return
     */
    public String getAvailabilityParts(){
        return getWheels() + ", " + getHeadlights() + ", " + getWipers();
    }

    /**
     * Возвращает наличие колёс на выбранную машину в виде строки
     * @return
     */
    public String getWheels(){
        if (wheels.getAvailability() == null)
            return "";
        return wheels.getAvailability();
    }

    /**
     * Возвращает наличие фар на выбранную машину в виде строки
     * @return
     */
    public String getHeadlights(){
        if (headlights.getAvailability() == null)
            return "";
        return headlights.getAvailability();
    }
    /**
     * Возвращает наличие дворников на выбранную машину в виде строки
     * @return
     */
    public String getWipers(){
        if (wipers.getAvailability() == null)
            return "";
        return wipers.getAvailability();
    }

}

package com.urfu.bot.domain.car;

import com.urfu.bot.domain.spareParts.wheels.Wheels;
import com.urfu.bot.domain.spareParts.headlights.Headlights;
import com.urfu.bot.domain.spareParts.wipers.Wipers;


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

    public String getName(){
        return name;
    }

    public String getAvailabilityParts(){
        return getWheels() + " " + getHeadlights() + " " + getWipers();
    }

    public String getWheels(){
        if (wheels.getAvailability() == null)
            return "";
        return wheels.getAvailability();
    }

    public String getHeadlights(){
        if (headlights.getAvailability() == null)
            return "";
        return headlights.getAvailability();
    }

    public String getWipers(){
        if (wipers.getAvailability() == null)
            return "";
        return wipers.getAvailability();
    }
}

package com.urfu.bot.domain.car;

import com.urfu.bot.domain.spareParts.headlights.Headlights;
import com.urfu.bot.domain.spareParts.wheels.Wheels;
import com.urfu.bot.domain.spareParts.wipers.Wipers;

public class Bmw extends Car{
    public Bmw(String name, Wheels wheels, Headlights headlights, Wipers wipers) {
        super(name, wheels, headlights, wipers);
    }
}

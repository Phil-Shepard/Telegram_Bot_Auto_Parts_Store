package com.urfu.bot.domain.car;

import com.urfu.bot.domain.headlights.Headlights;
import com.urfu.bot.domain.wheels.Wheels;
import com.urfu.bot.domain.wipers.Wipers;

public class Renault extends Car{
    public Renault(String name, Wheels wheels, Headlights headlights, Wipers wipers) {
        super(name, wheels, headlights, wipers);
    }
}

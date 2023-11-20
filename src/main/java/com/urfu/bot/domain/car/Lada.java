package com.urfu.bot.domain.car;

import com.urfu.bot.domain.headlights.Headlights;
import com.urfu.bot.domain.wheels.Wheels;
import com.urfu.bot.domain.wipers.Wipers;

public class Lada extends Car{
    public Lada(String name, Wheels wheels, Headlights headlights, Wipers wipers) {
        super(name, wheels, headlights, wipers);
    }
}

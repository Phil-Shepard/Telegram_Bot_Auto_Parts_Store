package com.urfu.bot.domain.spareParts.wheels;

import com.urfu.bot.domain.spareParts.SpareParts;

public class Wheels implements SpareParts {
    private String availability;
    public Wheels(String availability){
        this.availability = availability;
    }

    @Override
    public String getAvailability() {
        return availability;
    }
}

package com.urfu.bot.domain.spareParts.headlights;

import com.urfu.bot.domain.spareParts.SpareParts;

public class Headlights implements SpareParts {
    private String availability;
    public Headlights(String availability){
        this.availability = availability;
    }

    @Override
    public String getAvailability() {
        return availability;
    }
}

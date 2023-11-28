package com.urfu.bot.domain.spareParts.wipers;

import com.urfu.bot.domain.spareParts.SpareParts;

public class Wipers implements SpareParts {
    private String availability;
    public Wipers(String availability){
        this.availability = availability;
    }

    @Override
    public String getAvailability() {
        return availability;
    }
}

package com.urfu.domain.car;

import com.urfu.domain.sparePart.SparePart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Описывает сущность машины
 */
public class Car {
    private final String name;
    private final List<SparePart> spareParts = new ArrayList<>();

    public Car(String name, List<SparePart> spareParts){
        this.name = name;
        this.spareParts.addAll(spareParts);
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
        return spareParts.stream().map(SparePart::getName).collect(Collectors.joining(" "));
    }
}

package com.urfu.domain.car;

import com.urfu.domain.sparePart.SparePart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Описывает сущность машины
 */
public class Car {
    private final String name;
    private final List<SparePart> spareParts = new ArrayList<>();
    private final LocalDate localDate;

    public Car(String name, List<SparePart> spareParts) {
        this.name = name;
        this.spareParts.addAll(spareParts);
        localDate = LocalDate.now();
    }

    /**
     * Возвращает название машины
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает список автозапчастей запчастей, которые имеются на машину в виде строки
     *
     * @param symbol символ, который нужно вставить между автозапчастями
     */
    public String getAvailabilityParts(String symbol) {
        return spareParts.stream().map(SparePart::getName).collect(Collectors.joining(symbol));
    }

    public List<SparePart> getSpareParts() {
        return spareParts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(name, car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}

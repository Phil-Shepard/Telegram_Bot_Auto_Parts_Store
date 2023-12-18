package com.urfu.services;

import com.urfu.domain.car.Car;

/**
 * Менеджер запчастей
 */
public class SparePartService {
    /**
     * Возвращает список имеющихся запчастей на конкретную машину в виде строки
     */
    public String getParts(Car car) {
        return car.getAvailabilityParts();
    }
}

package com.urfu.services;

import com.urfu.domain.car.Car;
import com.urfu.domain.sparePart.SparePartHistory;
import com.urfu.storage.Storage;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Сервис для работы с деталями машин
 */
public class SpareService {
    private final Storage storage = new Storage();

    /**
     * Возвращает список машин
     */
    private List<Car> getCars() {
        return storage.getListCars();
    }

    /**
     * Получение количества деталей в диапазоне заданных дат
     */
    public Long getCountSparesInDateRange(
            String typeSpare,
            LocalDate startDate,
            LocalDate endDate,
            SparePartHistory sparePartHistory
    ) {
        return sparePartHistory.getSparePartHistory().stream()
                .filter(spare ->
                        spare.getLocalDate().isBefore(endDate)
                                && spare.getLocalDate().isAfter(startDate)
                                && (Objects.equals(typeSpare, "any") || spare.getName().equals(typeSpare))
                ).count();
    }

    /**
     * Получение количества деталей после определённой даты
     */
    public Long getCountSparesAfterDate(
            String typeSpare,
            LocalDate startDate,
            SparePartHistory sparePartHistory
    ) {
        return sparePartHistory.getSparePartHistory().stream().filter(spare ->
                spare.getLocalDate().isAfter(startDate)
                        && (Objects.equals(typeSpare, "any") || spare.getName().equals(typeSpare))
        ).count();
    }
}
package com.urfu.storage;

import com.urfu.domain.car.Car;
import com.urfu.domain.sparePart.SparePart;

import java.util.List;

/**
 * Формирует список инициализированных машин с их запчастями.
 */
public class Storage {
    private final List<Car> listCars = List.of(
            new Car("BMW",
                    List.of(
                            new SparePart("колёса"),
                            new SparePart("фары"),
                            new SparePart("дворники")
                    )
            ),
            new Car("Renault",
                    List.of(
                            new SparePart("колёса"),
                            new SparePart("фары"),
                            new SparePart("дворники")
                    )
            ),
            new Car("Lada",
                    List.of(
                            new SparePart("колёса"),
                            new SparePart("фары"),
                            new SparePart("дворники")
                    )
            )
    );

    /**
     * Возвращает список машин.
     */
    public List<Car> getListCars() {
        return listCars;
    }
}

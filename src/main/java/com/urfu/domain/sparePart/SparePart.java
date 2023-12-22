package com.urfu.domain.sparePart;

import java.time.LocalDate;

/**
 * Сущность запчасти
 */
public class SparePart {
    private final String name;
    private final LocalDate localDate;

    public SparePart(String name){
        this.name = name;
        this.localDate = LocalDate.now();
    }

    /**
     * Возвращает название запачсти
     */
    public String getName() {
        return name;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}

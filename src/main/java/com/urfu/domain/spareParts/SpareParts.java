package com.urfu.domain.spareParts;

/**
 * Интерфейс запчастей
 */
public class SpareParts {
    private final String name;
    public SpareParts(String name){
        this.name = name;
    }

    /**
     * Возвращает название запачсти
     */
    public String getName() {
        return name;
    }
}

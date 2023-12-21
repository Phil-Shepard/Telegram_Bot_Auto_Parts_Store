package com.urfu.domain.sparePart;

/**
 * Сущность запчасти
 */
public class SparePart {
    private final String name;
    public SparePart(String name){
        this.name = name;
    }

    /**
     * Возвращает название запачсти
     */
    public String getName() {
        return name;
    }
}

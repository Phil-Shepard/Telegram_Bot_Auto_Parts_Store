package com.urfu.bot.domain.spareParts;

/**
 * Интерфейс запчастей
 */
public class SpareParts {
    private String name;
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

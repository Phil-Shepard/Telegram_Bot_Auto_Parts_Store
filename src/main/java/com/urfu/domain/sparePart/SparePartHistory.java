package com.urfu.domain.sparePart;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс для сохранения истории всех покупок
 */
public class SparePartHistory {
    private List<SparePart> orderList;

    public SparePartHistory() {
        orderList = new LinkedList<>();
    }

    /**
     * Добавление элемента в историю
     *
     * @param sparePart Деталь
     */
    public void addSparePart(SparePart sparePart) {
        orderList.add(sparePart);
    }

    /**
     * Получение всех купленных деталей
     */
    public List<SparePart> getSparePartHistory() {
        return orderList;
    }

}

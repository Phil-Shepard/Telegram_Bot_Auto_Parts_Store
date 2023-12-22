package com.urfu.domain.basket;

import com.urfu.domain.sparePart.SparePart;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Класс корзины, в которой у нас хранятся названия машин и выбранные
 * к ним запчасти в виде словаря.
 * Также в классе находятся все методы взаимодействия с корзиной.
 */
public class Basket {
    private final HashMap<String, ArrayList<SparePart>> contentsBasket = new HashMap<>();

    /**
     * Добавляет выбранную запчасть в корзину
     */
    public void addSparePartInBasket(String carName, SparePart sparePart) {
        ArrayList<SparePart> partsList = contentsBasket.getOrDefault(carName, new ArrayList<>());
        partsList.add(sparePart);
        contentsBasket.put(carName, partsList);
    }

    /**
     * Возвращает содержимое корзины в виде словаря
     */
    public HashMap<String, ArrayList<SparePart>> getBasket() {
        return contentsBasket;
    }


    /**
     * Полностью очищает корзину
     */
    public void deleteAllPartsFromBasket() {
        contentsBasket.clear();
    }
}

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
    private HashMap<String, ArrayList<SparePart>> contentsBasket = new HashMap<>();

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
     * Удаляет выбранную запчасть из корзины
     */
    public void deleteSparePartsFromBasket(String input) {
        String[] parts = input.split(":");
        if (parts.length != 2) {
            return;
        }

        String carName = parts[0].trim();
        String partName = parts[1].trim();

        if (contentsBasket.containsKey(carName)) {
            ArrayList<SparePart> partsList = contentsBasket.get(carName);

            partsList.removeIf(part -> part.getName().equalsIgnoreCase(partName));
            contentsBasket.put(carName, partsList);
        }
    }

    /**
     * Полностью очищает корзину
     */
    public void deleteAllPartsFromBasket() {
        contentsBasket.clear();
    }
}
